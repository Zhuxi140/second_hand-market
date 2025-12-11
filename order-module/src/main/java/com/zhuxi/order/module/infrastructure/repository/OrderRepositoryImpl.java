package com.zhuxi.order.module.infrastructure.repository;

import com.zhuxi.common.shared.constant.CommonMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.order.module.domain.module.Order;
import com.zhuxi.order.module.domain.objectValue.ProductInfo;
import com.zhuxi.order.module.domain.service.OrderRepository;
import com.zhuxi.order.module.infrastructure.mapper.OrderMapper;
import com.zhuxi.order.module.interfaces.vo.OrderDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhuxi
 */
@Repository
@RequiredArgsConstructor
@Slf4j(topic = "OrderRepositoryImpl")
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper mapper;

    @Override
    public boolean insert(Order order) {

        int result1 = mapper.insertOrder(order);
        int result2 = mapper.insertOrderDetail(order.getOrderDetail());
        return result1 > 0 && result2 > 0;
    }

    /**
     * 根据用户编号获取用户ID
     */
    @Override
    public Long getUserIdBySn(String sn) { return mapper.selectUserIdBySn(sn); }

    /**
     * 根据商品编号获取商品ID
     */
    @Override
    public Long getProductIdBySn(String sn) { return mapper.selectProductIdBySn(sn); }


    /**
     * 根据地址编号获取地址ID
     */
    @Override
    public Long getAddressIdBySn(String sn) { return mapper.selectAddressIdBySn(sn); }

    @Override
    public ProductInfo getProductInAndSpec(Long productId, String skuSn) {
        if (skuSn == null || skuSn.isBlank()){
            return mapper.getProductInAndSpec(productId,);
        }
        Long specId = mapper.getSpecIdBySkuSn(skuSn);
        if (specId == null){
            log.error("getProductInAndSpec-specId is null; skuSn:{}",skuSn);
            throw new BusinessException(CommonMessage.DATABASE_SELECT_EXCEPTION);
        }
    }

    /**
     * 获取订单详情视图
     */
    @Override
    public OrderDetailVO getDetail(String orderSn) { return mapper.selectDetail(orderSn); }

    /**
     * 更新订单状态与备注
     */
    @Override
    public void updateStatus(String orderSn, Integer status, String remark) { mapper.updateStatus(orderSn, status, remark); }

    /**
     * 根据用户编号获取其关联订单列表
     */
    @Override
    public List<OrderDetailVO> getListByUserSn(String userSn, int limit, int offset) { return mapper.selectListByUserSn(userSn, limit, offset); }
}
