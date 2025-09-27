package com.zhuxi.userModule.domain.address.model;

import com.zhuxi.userModule.domain.address.ValueObject.PostalAddress;
import com.zhuxi.userModule.domain.address.ValueObject.Recipient;
import com.zhuxi.userModule.domain.user.valueObject.Phone;
import com.zhuxi.userModule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserAddress {
    private Long id;
    private String addressSn;
    private Long userId;
    private Recipient recipient;
    private PostalAddress allAddress;
    private String label;
    private Integer isDefault;

    private UserAddress(Long id, String addressSn, Long userId, Recipient recipient, PostalAddress allAddress, String label, Integer isDefault) {
        this.id = id;
        this.addressSn = addressSn;
        this.userId = userId;
        this.recipient = recipient;
        this.allAddress = allAddress;
        this.label = label;
        this.isDefault = isDefault;
    }

    public UserAddress() {}

    public String register(AdsRegisterDTO register){
        // 生成地址唯一标识 addressSn
        String addressSn = UUID.randomUUID().toString();
        this.addressSn = addressSn;
        this.label = register.getLabel();
        this.recipient = new Recipient(register.getConsignee(),register.getSex(),new Phone(register.getPhone()));
        this.allAddress = new PostalAddress(register.getProvinceCode(),register.getProvinceName(),register.getCityCode(),
                register.getCityName(),register.getDistrictCode(),register.getDistrictName(),register.getDetail());
        this.isDefault = register.getIsDefault();
        return addressSn;
    }

    public void update(AdsUpdate update){
        this.label = update.getLabel();
        this.recipient = new Recipient(update.getConsignee(),update.getSex(),new Phone(update.getPhone()));
        this.allAddress = new PostalAddress(update.getProvinceCode(),update.getProvinceName(),update.getCityCode(),
                update.getCityName(),update.getDistrictCode(),update.getDistrictName(),update.getDetail());
        this.isDefault = update.getIsDefault();
    }
}
