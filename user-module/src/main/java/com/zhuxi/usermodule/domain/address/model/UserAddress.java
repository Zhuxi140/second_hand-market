package com.zhuxi.usermodule.domain.address.model;

import com.zhuxi.usermodule.domain.address.ValueObject.PostalAddress;
import com.zhuxi.usermodule.domain.address.ValueObject.Recipient;
import com.zhuxi.usermodule.domain.address.enums.IsDefault;
import com.zhuxi.usermodule.domain.user.valueObject.Phone;
import com.zhuxi.usermodule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.usermodule.interfaces.dto.address.AdsUpdate;
import lombok.Getter;

import java.util.UUID;

/**
 * @author zhuxi
 */
@Getter
public class UserAddress {
    private Long id;
    private String addressSn;
    private Long userId;
    private Recipient recipient;
    private PostalAddress allAddress;
    private String label;
    private IsDefault isDefault;

    private UserAddress(Long id, String addressSn, Long userId, Recipient recipient, PostalAddress allAddress, String label, IsDefault isDefault) {
        this.id = id;
        this.addressSn = addressSn;
        this.userId = userId;
        this.recipient = recipient;
        this.allAddress = allAddress;
        this.label = label;
        this.isDefault = isDefault;
    }

    public UserAddress() {}

    // 注册地址
    public String register(AdsRegisterDTO register){
        // 生成地址唯一标识 addressSn
        String addressSn = UUID.randomUUID().toString();
        this.addressSn = addressSn;
        this.label = register.getLabel();
        this.recipient = new Recipient(register.getConsignee(),register.getSex(),new Phone(register.getPhone()));
        this.allAddress = new PostalAddress(register.getProvinceCode(),register.getProvinceName(),register.getCityCode(),
                register.getCityName(),register.getDistrictCode(),register.getDistrictName(),register.getDetail());
        this.isDefault = IsDefault.getByCode(register.getIsDefault());
        return addressSn;
    }

    // 修改地址
    public void update(AdsUpdate update,IsDefault isDefault){
        this.label = update.getLabel();
        this.recipient = new Recipient(update.getConsignee(),update.getSex(),new Phone(update.getPhone()));
        this.allAddress = new PostalAddress(update.getProvinceCode(),update.getProvinceName(),update.getCityCode(),
                update.getCityName(),update.getDistrictCode(),update.getDistrictName(),update.getDetail());
        this.isDefault = isDefault;
    }
}
