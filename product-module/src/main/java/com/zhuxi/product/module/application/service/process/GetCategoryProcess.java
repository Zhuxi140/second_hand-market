package com.zhuxi.product.module.application.service.process;

import com.zhuxi.common.applicaiton.cache.CommonCacheServiceImpl;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.common.shared.exception.cache.ValueNullException;
import com.zhuxi.product.module.domain.repository.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.interfaces.vo.CategoryTreeVO;
import com.zhuxi.product.module.interfaces.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.sleep;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
@Slf4j(topic = "GetCategoryProcess")
public class GetCategoryProcess {

    private final ProductRepository repository;
    private final CommonCacheServiceImpl commonCache;
    private final ProductCacheService cache;
    private final CacheKeyProperties properties;

    /**
     * 获取缓存数据
     * @return 缓存数据
     */
    public List<CategoryTreeVO> getDataByCache(){
        List<CategoryVO> list;
        try{
            list = cache.getCategoryList();
            if (list != null){
                return buildTree(list);
            }
            //捕获 到空值标记 的异常 则代表根本不存在对应数据 直接返回空 防止穿透
        }catch (ValueNullException e){
            return List.of();
        }
        return null;
    }

    /**
     * 获取互斥锁 保证仅单个线程可查库并更新缓存
     * 未获取到锁的线程 睡眠50ms后 尝试获取缓存 获取不到直接返回空列表
     * @return null: 获取到锁  data: 返回缓存数据 无论是否有数据
     */
    public List<CategoryTreeVO> getLock(long threadId){
        boolean lock = Boolean.TRUE.equals(commonCache.getLock(properties.getCategoryLockKey(),threadId,30, TimeUnit.SECONDS));
        if (!lock){
                try {
                    sleep(50);
                    List<CategoryVO> categoryList = cache.getCategoryList();
                    return categoryList == null ? List.of() : buildTree(categoryList);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
        }
        return null;
    }

    /**
     * 查库并更新缓存
     * @param limit 查询数量
     * @param offset 查询偏移量
     * @return 缓存数据
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CategoryTreeVO> getDataByDb(long threadId,int limit, int offset){
        //未命中
        List<CategoryVO> list;
        try {
            list = repository.getCategoryList(limit, offset);
            // 异步缓存
            CompletableFuture.runAsync(() -> cache.saveCategoryList(list));
        }catch (BusinessException e){
            commonCache.saveNullValue(properties.getCategoryKey());
            log.error("获取商品分类失败：error：查库获取数据为null,exception:{}.category-key:{}",e.getMessage(),properties.getCategoryKey());
            return List.of();
        }catch (Exception e){
            log.error("未知错误-message:{}",e.getMessage());
            return List.of();
        } finally {
            try {
                Object value = commonCache.soGetValue(properties.getCategoryLockKey());
                if (value != null && value.equals(threadId)) {
                    commonCache.delKey(properties.getCategoryLockKey());
                }
            }catch (Exception e){
                log.warn("释放锁异常-message:{}",e.getMessage());
            }
        }
        return buildTree(list);
    }

    private List<CategoryTreeVO> buildTree(List<CategoryVO> list){
        // 构建树形结构
        List<CategoryTreeVO> nodes = list.stream()
                .map(CategoryTreeVO::new)
                .toList();

        Map<Long, CategoryTreeVO> index = new HashMap<>(nodes.size());
        List<CategoryTreeVO> roots = new ArrayList<>();
        for (CategoryTreeVO n : nodes) {
            index.put(n.getId(), n);
            n.setChildren(new ArrayList<>());
        }
        for (CategoryTreeVO n : nodes) {
            Long pid = n.getParentId();
            if (pid == null || pid == 0) {
                roots.add(n);
            } else {
                CategoryTreeVO p = index.get(pid);
                if (p != null){
                    p.getChildren().add(n);
                }
            }
        }
        return roots;
    }
}
