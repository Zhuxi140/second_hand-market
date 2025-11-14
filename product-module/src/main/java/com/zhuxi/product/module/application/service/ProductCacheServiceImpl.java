package com.zhuxi.product.module.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.utils.JackSonUtils;
import com.zhuxi.common.shared.utils.RedisUtils;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.model.ProductStatic;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.interfaces.vo.CategoryVO;
import com.zhuxi.product.module.interfaces.vo.ProductDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuxi
 */
@Slf4j
@Service
public class ProductCacheServiceImpl implements ProductCacheService {
    private final CacheKeyProperties properties;
    private final RedisUtils redisUtils;
    private final DefaultRedisScript<Integer> saveScript;
    private final DefaultRedisScript<List<ProductDetailVO>> getScript;

    public ProductCacheServiceImpl(CacheKeyProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        this.getScript = new DefaultRedisScript<>();
        this.saveScript = new DefaultRedisScript<>();
        ClassPathResource savePath = new ClassPathResource("lua/saveInfo.lua");
        ClassPathResource getPath = new ClassPathResource("lua/getInfo.lua");
        ResourceScriptSource getScriptResource = new ResourceScriptSource(getPath);
        ResourceScriptSource saveScriptResource = new ResourceScriptSource(savePath);
        saveScript.setScriptSource(saveScriptResource);
        saveScript.setResultType(Integer.class);
        getScript.setScriptSource(getScriptResource);
        getScript.setResultType((Class<List<ProductDetailVO>>)(Class<?>)List.class);
    }

    @Override
    public void saveCategoryList(List<CategoryVO> list) {
        if (list == null){
            log.warn("category-list-is-null,商品分类缓存失败");
        }
        String json = JackSonUtils.toJson(list);
        redisUtils.ssSetValue(properties.getCategoryKey(), json, 7, TimeUnit.DAYS);
    }

    @Override
    public List<CategoryVO> getCategoryList() {
        String json = redisUtils.ssGetValue(properties.getCategoryKey());
        if (json == null){
            return null;
        }
        return JackSonUtils.readValue(json, new TypeReference<List<CategoryVO>>() {});
    }

    @Override
    public void saveShProduct(Product product,String productSn) {
        if (product == null){
            log.warn("product-is-null,商品信息缓存失败-productSn:{}", productSn);
            return;
        }
        List<String> hashKey = new ArrayList<>();
        hashKey.add(properties.getShProductKey()+productSn);
        List<Object> values = new ArrayList<>();
        values.add(properties.getShProductExpire());
        values.add("id");
        values.add(product.getId());
        values.add("productSn");
        values.add(product.getProductSn());
        values.add("sellerId");
        values.add(product.getSellerId());
        values.add("shopId");
        values.add(product.getShopId());
        values.add("title");
        values.add(product.getTitle().getTitle());
        values.add("description");
        values.add(product.getDescription());
        values.add("categoryId");
        values.add(product.getCategoryId());
        values.add("categoryName");
        values.add(product.getCategoryName());
        values.add("price");
        values.add(product.getPrice().getPrice());
        values.add("conditionId");
        values.add(product.getConditionId());
        values.add("conditionName");
        values.add(product.getConditionName());
        values.add("status");
        values.add(product.getStatus().getCode());
        values.add("location");
        values.add(product.getLocation().getLocation());
        values.add("viewCount");
        values.add(product.getViewCount());
        values.add("hostScore");
        values.add(product.getHostScore().getScore());
        values.add("isDraft");
        values.add(product.getIsDraft().getCode());
        values.add("createTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        values.add(product.getCreateTime().format(formatter));

        redisUtils.executeLuaScript(
                saveScript,
                hashKey,
                values
        );
    }

    @Override
    public void saveProductStatic(List<ProductStatic> pStatics, String productSn) {
        if (pStatics == null){
            log.warn("product-static-list-is-null,商品静态资源缓存失败 productSn:{}", productSn);
            return;
        }
        String json = JackSonUtils.toJson(pStatics);
        redisUtils.ssSetValue(
                properties.getProductStaticKey() + productSn,
                json,
                properties.getShProductExpire(),
                TimeUnit.SECONDS
        );
    }

    @Override
    public List<Object> getShProductDetail(String productSn) {
        if (productSn == null){
            throw new IllegalArgumentException("productSn-is-null");
        }
        List<String> values = List.of("productSn","sellerId", "title", "description", "categoryId","categoryName", "price", "conditionId","conditionName","status", "location", "viewCount", "hostScore");
        List<String> hashKey = List.of(properties.getShProductKey() + productSn);
        Object obj = redisUtils.executeLuaScript(getScript, hashKey, values);
        if (obj == null){
            return null;
        }

        List<Object> productInfo = null;
        if (obj instanceof List<?> list){
            productInfo = list.stream()
                    .map(e -> (Object) e)
                    .toList();
        }

        if (productInfo == null || productInfo.isEmpty()){
            return null;
        }
        String sellerId = productInfo.get(1).toString();

        // 获取id-sn映射
        Object seller = redisUtils.hashGet(properties.getShProductKey() + productSn, sellerId);
        if (seller == null){
            ArrayList<Object> list = new ArrayList<>();
            list.add(productInfo);
            return list;
        }

        //获取用户信息
        String userSn = seller.toString();
        List<Object> sellerInfo = redisUtils.hashGetAll(properties.getUserInfoKey() + userSn, List.of("nickname", "avatar"));
        if (sellerInfo == null){
            ArrayList<Object> list = new ArrayList<>();
            list.add(productInfo);
            list.add(userSn);
            return list;
        }

        String staticKey = properties.getProductStaticKey() + productSn;
        String json = redisUtils.ssGetValue(staticKey);
        if (json == null){
            ArrayList<Object> list = new ArrayList<>();
            list.add(productInfo);
            list.add(userSn);
            list.add(sellerInfo);
            return list;
        }
        List<ProductStatic> productStatics = JackSonUtils.readValue(json, new TypeReference<List<ProductStatic>>() {});

        return List.of(
                productInfo,
                userSn,
                sellerInfo,
                productStatics
        );
    }

}
