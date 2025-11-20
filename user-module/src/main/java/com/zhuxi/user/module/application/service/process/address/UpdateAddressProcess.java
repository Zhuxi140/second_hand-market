package com.zhuxi.user.module.application.service.process.address;

import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.user.module.application.service.UserAddressServiceImpl;
import com.zhuxi.user.module.domain.address.enums.IsDefault;
import com.zhuxi.user.module.domain.address.model.UserAddress;
import com.zhuxi.user.module.domain.address.repository.UserAddressRepository;
import com.zhuxi.user.module.interfaces.dto.address.AdsUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
@Slf4j(topic = "UpdateAddressProcess")
public class UpdateAddressProcess {
    private final UserAddressRepository repository;

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public UserAddress getAddress(String addressSn){
        return repository.getAdIdOrDefaultBySn(addressSn);
    }

    @Transactional(rollbackFor = BusinessException.class)
    public void updateAddress(AdsUpdate update,UserAddress address){
        // 是否已为默认地址 或 存在已有的默认地址
        IsDefault upIsDefault = IsDefault.getByCode(update.getIsDefault());
        if (upIsDefault == IsDefault.ENABLE && address.getIsDefault() == IsDefault.ENABLE){
            throw new BusinessException(BusinessMessage.ALREADY_DEFAULT_ADDRESS);
        }else{
            Long addressDeId = repository.getDefaultId(address.getUserId());
            if (addressDeId != null){
                repository.cancelDefault(addressDeId);
            }
        }
        // 修改
        address.update(update, upIsDefault);
        // 写入
        repository.save(address);
    }
}
