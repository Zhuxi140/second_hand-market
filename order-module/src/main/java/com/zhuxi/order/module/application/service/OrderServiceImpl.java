package com.zhuxi.order.module.application.service;

import com.zhuxi.common.domain.service.CommonCacheService;
import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.business.BusinessException;
import com.zhuxi.common.shared.utils.JackSonUtils;
import com.zhuxi.order.module.domain.objectValue.OrderDetail;
import com.zhuxi.order.module.domain.objectValue.ProductInfo;
import com.zhuxi.order.module.domain.objectValue.ProductSnapShot;
import com.zhuxi.order.module.domain.service.OrderRepository;
import com.zhuxi.order.module.domain.service.OrderService;
import com.zhuxi.order.module.interfaces.dto.CancelOrderDTO;
import com.zhuxi.order.module.interfaces.dto.CreateOrderDTO;
import com.zhuxi.order.module.interfaces.dto.UpdateOrderStatusDTO;
import com.zhuxi.order.module.interfaces.param.CreateOrderParam;
import com.zhuxi.order.module.interfaces.vo.OrderDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.zhuxi.order.module.domain.module.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author zhuxi
 */
@Service
@RequiredArgsConstructor
@Slf4j(topic = "OrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final CommonCacheService commonCache;
    private final CacheKeyProperties properties;

    /**
     * 创建订单
     */
    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public String create(CreateOrderDTO dto,String userSn){
        if (userSn == null || userSn.isBlank()){
            log.error("createOrder-userSn is empty or null; userSn:{}",userSn);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }

        String productSn = dto.getProductSn();

        // 查询买家、卖家、商品、地址ID
        Long buyerId = commonCache.getUserIdBySn(userSn);
        if (buyerId == null){
            buyerId = repository.getUserIdBySn(userSn);
            if (buyerId == null){
                log.error("createOrder-userId is null; userSn:{}",userSn);
                throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
            }
        }

        String sellerSn = dto.getSellerSn();
        Long sellerId = commonCache.getUserIdBySn(sellerSn);
        if (sellerId == null){
            sellerId = repository.getUserIdBySn(sellerSn);
            if (sellerId == null){
                log.error("createOrder-sellerId is null; sellerSn:{}",sellerSn);
                throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
            }
        }

        Long productId = (Long) commonCache.getHashOneValue(properties.getShProductKey() + productSn, "id");
        if (productId == null){
            productId = repository.getProductIdBySn(dto.getProductSn());
            if (productId == null){
                log.error("createOrder-productId is null; productSn:{}",dto.getProductSn());
                throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
            }
        }

        String skuSn = dto.getSkuSn();
        ProductInfo productInfo = repository.getProductInAndSpec(productId,skuSn);

        Long addressId = repository.getAddressIdBySn(dto.getAddressSn());
        // 计算订单总金额（单价 * 数量，数量默认1）
        BigDecimal price = productInfo.getPrice();
        BigDecimal amount = price.multiply(BigDecimal.valueOf(dto.getQuantity() == null ? 1 : dto.getQuantity()));

        CreateOrderParam param = new CreateOrderParam(buyerId, amount, addressId);

        ProductSnapShot productSnapShot = ProductSnapShot.create(dto.isSh(),skuSn,sellerSn,
                productInfo.getTitle(),productInfo.getDescription(),productInfo.getSpecifications(),
                productInfo.getImageUrl(),price,dto.getQuantity(),
                amount);


        String json = JackSonUtils.toJson(productSnapShot);
        OrderDetail.create(productId,skuId,sellerId, dto.getQuantity(), price,json);

        Order order = Order.create(orderDetail, param);

        boolean insert = repository.insert(order);
        String orderSn = order.getOrderSn();
        if (!insert){
            log.error("createOrder-insert order fail; orderSn:{}",orderSn);
            throw new BusinessException(BusinessMessage.CREATE_ORDER_ERROR);
        }

        return orderSn;
    }

    /**
     * 订单详情
     * @param orderSn 订单编号
     * @return 订单详情视图
     */
    @Transactional(readOnly = true)
    @Override
    public OrderDetailVO detail(String orderSn){
        return repository.getDetail(orderSn);
    }

    /**
     * 更新订单状态
     * @param orderSn 订单编号
     * @param dto 状态与备注
     */
    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public void updateStatus(String orderSn, UpdateOrderStatusDTO dto){
        repository.updateStatus(orderSn, dto.getUserStatus(), dto.getRemark());
    }

    /**
     * 用户订单列表
     * @param userSn 用户编号
     * @param page 页码
     * @param size 每页数量
     * @return 订单列表
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrderDetailVO> list(String userSn, int page, int size){
        int offset = (page - 1) * size;
        return repository.getListByUserSn(userSn, size, offset);
    }

    /**
     * 取消订单
     * 统一写状态为9（取消）
     * @param orderSn 订单编号
     * @param dto 取消原因
     */
    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public void cancel(String orderSn, CancelOrderDTO dto){
        repository.updateStatus(orderSn, 9, dto.getReason());
    }
}
