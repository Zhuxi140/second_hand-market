package com.zhuxi.product.module.application.service;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.shared.exception.PersistenceException;
import com.zhuxi.product.module.domain.enums.IsDraft;
import com.zhuxi.product.module.domain.model.Product;
import com.zhuxi.product.module.domain.repository.ProductRepository;
import com.zhuxi.product.module.domain.service.ProductService;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
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

/**
 * @author zhuxi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CategoryTreeVO> getCategoryList(int limit, int offset) {
        List<CategoryVO> list = repository.getCategoryList(limit, offset);

        // 构建树形结构
        List<CategoryTreeVO> treeVO = list.stream()
                .map(CategoryTreeVO::new)
                .toList();

        return buildTree(treeVO);
    }


    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String publishSh(PublishSHDTO sh, String userSn) {
        Product product = new Product();
        product.publish(sh,1L);
        try{
            repository.save(product);
        }catch (PersistenceException e){
            saveDraft( product);
            throw new BusinessException(BusinessMessage.SAVE_PRODUCT_ERROR_SAVE_DRAFT);
        }
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
    public ProductDetailVO getProductDetail(String productId) {
        return repository.getProductDetail(productId);
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
