package com.zhuxi.productModule.domain.model;

import com.zhuxi.productModule.domain.objectValue.*;
import com.zhuxi.productModule.interfaces.dto.PublishSHDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private Integer status;
    private Location location;
    private Integer viewCount;
    private HostScore hostScore;
    private Integer isDraft;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Product(Long categoryId, Long id, ProductSn productSn, Long sellerId, Long shopId, Title title, String description, Price price, Integer conditionId, Integer status, Location location, Integer viewCount, HostScore hostScore, Integer isDraft, LocalDateTime createTime, LocalDateTime updateTime) {
        this.categoryId = categoryId;
        this.id = id;
        this.productSn = productSn;
        this.sellerId = sellerId;
        this.shopId = shopId;
        this.title = title;
        this.description = description;
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

    public void publish(PublishSHDTO  sh,Long sellerId){
        this.title = new Title(sh.getTitle());
        this.description = sh.getDescription();
        this.categoryId = sh.getCategoryId();
        this.price = new Price(sh.getPrice());
        this.conditionId = sh.getConditionId();
        this.location = new Location(sh.getLocation());
        this.isDraft = sh.getIsDraft();
        this.productSn = new ProductSn(UUID.randomUUID().toString());
        this.sellerId = sellerId;
    }
}
