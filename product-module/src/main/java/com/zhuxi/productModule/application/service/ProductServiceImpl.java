package com.zhuxi.productModule.application.service;

import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.common.exception.PersistenceException;
import com.zhuxi.productModule.domain.model.Product;
import com.zhuxi.productModule.domain.repository.ProductRepository;
import com.zhuxi.productModule.domain.service.ProductService;
import com.zhuxi.productModule.interfaces.dto.PublishSHDTO;
import com.zhuxi.productModule.interfaces.vo.CategoryTreeVO;
import com.zhuxi.productModule.interfaces.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxi
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    /**
     * 获取商品分类列表
     * @param limit  分页参数
     * @param offset 分页参数
     * @return 商品分类树形列表
     */
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

    /**
     * 发布二手商品
     * @param sh 商品发布参数
     * @param userSn 用户标识
     * @return 发布结果
     */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String publishSh(PublishSHDTO sh, String userSn) {

        try{
            Product product = new Product();
            product.publish(sh,1L);
        }catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }

        return "";
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
}
