package com.zhuxi.product.module.domain.model;

import com.zhuxi.product.module.domain.enums.IsDraft;
import com.zhuxi.product.module.domain.enums.ProductStatus;
import com.zhuxi.product.module.domain.objectValue.*;
import com.zhuxi.product.module.interfaces.dto.PublishSHDTO;
import com.zhuxi.product.module.interfaces.dto.UpdateProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
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
    private String categoryName;
    private Price price;
    private Integer conditionId;
    private String conditionName;
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

    /**
     * 发布商品
     * @param sh 发布二手商品信息
     * @param sellerId 卖家id
     */
    public void publish(PublishSHDTO  sh, Long sellerId, List<String> otherNames){
        this.title = new Title(sh.getTitle());
        this.description = sh.getDescription();
        this.categoryId = sh.getCategoryId();
        this.categoryName = otherNames.get(0);
        this.price = new Price(sh.getPrice());
        this.conditionId = sh.getConditionId();
        this.conditionName = otherNames.get(1);
        this.location = new Location(sh.getLocation());
        this.modifyToDraft(IsDraft.getByCode(sh.getIsDraft()));
        String string = UUID.randomUUID().toString();
        String sn = string.replace("-", "");
        this.productSn = new ProductSn(sn);
        this.sellerId = sellerId;
    }

    /**
     * 修改商品信息
     * @param update 修改商品信息DTO
     */
    public void modify(UpdateProductDTO update,Long productId){
        this.id = productId;
        this.sellerId = update.getSellerId();
        this.title = new Title(update.getTitle());
        this.description = update.getDescription();
        this.categoryId = update.getCategoryId();
        this.price = new Price(update.getPrice());
        this.conditionId = update.getConditionId();
        this.status = ProductStatus.getByCode(update.getStatus());
        this.location = new Location(update.getLocation());
        this.modifyToDraft(update.getIsDraft());
    }

    /**
     * 修改为商品为草稿状态
     * @param draft 草稿状态
     */
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
