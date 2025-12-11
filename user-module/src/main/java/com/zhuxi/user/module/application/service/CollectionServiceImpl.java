package com.zhuxi.user.module.application.service;

import com.zhuxi.user.module.domain.collection.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import com.zhuxi.user.module.domain.collection.service.CollectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 收藏应用服务
 * 负责处理用户收藏/取消收藏/查询收藏列表的用例
 */
@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository repository;

    /**
     * 收藏商品
     * @param userSn 用户编号
     * @param productSn 商品编号
     */
    @Transactional
    @Override
    public void add(String userSn, String productSn){
        // 根据编号查询ID并写入收藏关系
        Long userId = repository.getUserIdBySn(userSn);
        Long productId = repository.getProductIdBySn(productSn);
        repository.save(userId, productId);
    }

    /**
     * 取消收藏
     * @param userSn 用户编号
     * @param productSn 商品编号
     */
    @Transactional
    @Override
    public void del(String userSn, String productSn){
        Long userId = repository.getUserIdBySn(userSn);
        Long productId = repository.getProductIdBySn(productSn);
        repository.delete(userId, productId);
    }

    /**
     * 收藏列表
     * @param userSn 用户编号
     * @param page 页码
     * @param size 每页数量
     * @return 收藏的商品简要信息
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String,Object>> list(String userSn, int page, int size){
        Long userId = repository.getUserIdBySn(userSn);
        int offset = (page - 1) * size;
        return repository.getList(userId, size, offset);
    }
}
