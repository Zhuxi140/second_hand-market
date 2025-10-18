package com.zhuxi.productmodule.domain.model;

import com.zhuxi.productmodule.domain.enums.IsDraft;
import com.zhuxi.productmodule.domain.enums.ProductStatus;
import com.zhuxi.productmodule.domain.objectValue.*;
import com.zhuxi.productmodule.interfaces.dto.PublishSHDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author zhuxi
 */
@Getter
@NoArgsConstructor
public class Product {

    private Long id;
    private ProductSn productSn;
    private Long sellerId;
    private Long shopId;
    private Title title;
    private String description;
    private Long categoryId;
    private Price price;
    private Integer conditionId;
    private ProductStatus status;
    private Location location;
    private Integer viewCount;
    private HostScore hostScore;
    private IsDraft isDraft;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Product(Long id, ProductSn productSn, Long sellerId, Long shopId, Title title, String description, Long categoryId, Price price, Integer conditionId, ProductStatus status, Location location, Integer viewCount, HostScore hostScore, IsDraft isDraft, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.productSn = productSn;
        this.sellerId = sellerId;
        this.shopId = shopId;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.conditionId = conditionId;
        this.status = status;
        this.location = location;
        this.viewCount = viewCount;
        this.hostScore = hostScore;
        this.isDraft = isDraft;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // 发布二手商品
    public void publish(PublishSHDTO  sh,Long sellerId){
        this.title = new Title(sh.getTitle());
        this.description = sh.getDescription();
        this.categoryId = sh.getCategoryId();
        this.price = new Price(sh.getPrice());
        this.conditionId = sh.getConditionId();
        this.location = new Location(sh.getLocation());
        this.modifyToDraft(IsDraft.getByCode(sh.getIsDraft()));
        this.productSn = new ProductSn(UUID.randomUUID().toString());
        this.sellerId = sellerId;
    }

    // 更新草稿状态
    public void modifyToDraft(IsDraft draft){
        if (draft == IsDraft.ENABLE){
            this.isDraft = IsDraft.ENABLE;
            this.status = ProductStatus.NO_PUBLISH;
        }else if(draft == IsDraft.DISABLE){
            this.isDraft = IsDraft.DISABLE;
            this.status = ProductStatus.SALES;
        }
    }
}
