package com.zhuxi.product.module.application.service;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.exception.PersistenceException;
import com.zhuxi.product.module.application.service.process.PublishShProcess;
import com.zhuxi.product.module.domain.enums.IsDraft;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.repository.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductCacheService;
import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import com.zhuxi.product.module.interfaces.dto.UpdateProductDTO;
import com.zhuxi.product.module.interfaces.param.ShProductParam;
import com.zhuxi.product.module.interfaces.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author zhuxi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductCacheService cache;
    private final PublishShProcess publishShProcess;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CategoryTreeVO> getCategoryList(int limit, int offset) {

        List<CategoryVO> list = cache.getCategoryList();
        if (list == null) {
            list = repository.getCategoryList(limit, offset);
            // 异步缓存
            List<CategoryVO> finalList = list;
            CompletableFuture.runAsync(() -> cache.saveCategoryList(finalList));
        }

        // 构建树形结构
        List<CategoryTreeVO> treeVO = list.stream()
                .map(CategoryTreeVO::new)
                .toList();

        return buildTree(treeVO);
    }

    @Override
    public String publishSh(PublishSHDTO sh, String userSn) {
        List<String> otherNames = publishShProcess.getOtherNames(sh.getCategoryId(), sh.getCategoryId());
        Product product = new Product();
        product.publish(sh,1L,otherNames); //

        try{
            repository.save(product);
        }catch (PersistenceException e){
            saveDraft( product);
            throw new BusinessException(BusinessMessage.SAVE_PRODUCT_ERROR_SAVE_DRAFT);
        }
        // 异步缓存
        CompletableFuture.runAsync(()->cache.saveShProduct(product,product.getProductSn().getSn()));
        return product.getProductSn().getSn();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ConditionSHVO> getShConditions() {
        return repository.getShConditions();
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ShProductVO> getShProductList(ShProductParam shProductParam) {

        boolean isFirst = shProductParam.getLastSn() == null;

        List<ShProductVO> list;
        if ("DESC".equals(shProductParam.getSortOrder())){
            setProductIdAndTime(shProductParam,isFirst,true);
            list = repository.getShProductListDesc(shProductParam);
        }else if ("ASC".equals(shProductParam.getSortOrder())){
            setProductIdAndTime(shProductParam,isFirst,false);
            list = repository.getShProductListAsc(shProductParam);
        }else {
            setProductIdAndTime(shProductParam,isFirst,true);
            list = repository.getShProductListDesc(shProductParam);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductDetailVO getShProductDetail(String productSn) {
        return repository.getShProductDetail(productSn);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateProduct(UpdateProductDTO update, String userSn) {

        // 获取商品id
        Long productId = repository.getProductIdBySn(update.getProductSn());
        // 获取用户id
        Long sellerId = repository.getUserIdBySn(userSn);
        update.setSellerId(sellerId);

        Product product = new Product();
        // 修改商品
        product.modify(update,productId);

        //写入
        repository.save(product);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void delProduct(String productSn) {

        Long productId = repository.getProductIdBySn(productSn);
        repository.delProduct(productId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MeShProductVO> getMeShProductList(String userSn) {
        return repository.getMeShProductList(repository.getUserIdBySn(userSn));
    }

    // 保存草稿
    @Transactional(rollbackFor = BusinessException.class,propagation = Propagation.REQUIRES_NEW)
    public void saveDraft(Product product){
        product.modifyToDraft(IsDraft.ENABLE);
        try {
            repository.save(product);
        }catch (PersistenceException | BusinessException e){
            log.error("draft-save-error: {}",e.getMessage());
            throw new BusinessException(BusinessMessage.SAVE_PRODUCT_ERROR);
        }
    }

    private List<CategoryTreeVO> buildTree(List<CategoryTreeVO> list){
        List<CategoryTreeVO> parentNodes = new ArrayList<>();

        for (CategoryTreeVO node : list){
            if (node.getParentId() == null || node.getParentId() == 0){
                parentNodes.add(node);
            }
        }

        for (CategoryTreeVO parentNode : parentNodes){
            findChildren(parentNode, list);
        }
        return parentNodes;
    }

    private void findChildren(CategoryTreeVO parentNode, List<CategoryTreeVO> allNodes){
        for (CategoryTreeVO node : allNodes){
            if (parentNode.getId().equals(node.getParentId())){
                if (parentNode.getChildren() == null){
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(node);
                findChildren(node, allNodes);
            }
        }
    }

    private void setProductIdAndTime(ShProductParam shProductParam,boolean isFirst,boolean isDesc){
        if (!isFirst){
            Long productId = repository.getProductIdBySn(shProductParam.getLastSn());
            shProductParam.setProductId(productId);
        }else{
            if (isDesc){
                shProductParam.setProductId(Long.MAX_VALUE);
                shProductParam.setLastCreatedAt(LocalDateTime.now());
            }else{
                shProductParam.setProductId(Long.MIN_VALUE);
                shProductParam.setLastCreatedAt(LocalDateTime.MIN);
            }
        }
    }
}
