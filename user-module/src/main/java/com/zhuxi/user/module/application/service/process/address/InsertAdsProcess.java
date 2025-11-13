package com.zhuxi.user.module.application.service.process.address;

import com.zhuxi.user.module.domain.address.model.UserAddress;
import com.zhuxi.user.module.domain.address.repository.UserAddressRepository;
import com.zhuxi.user.module.interfaces.dto.address.AdsRegisterDTO;
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
@Slf4j(topic = "InsertAdsProcess")
public class InsertAdsProcess {

    private final UserAddressRepository repository;


    /**
     * 检查地址数量
     *
     * @param userSn 用户编号
     * @return  地址领域对象
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public UserAddress checkAddressCount(String userSn){
        UserAddress address = repository.getUserIdBySn(userSn);
        Long userId = address.getUserId();

        //检查地址数量是否上限
        repository.checkAddressCount(userId);

        return address;
    }

    /**
     * 插入地址
     *
     * @param address  地址领域对象
     * @param register 地址注册信息DTO
     * @return  地址编号
     */
    @Transactional(rollbackFor = Exception.class)
    public String insertAds(UserAddress address, AdsRegisterDTO register){
            // 若用户指定为默认地址，则取消其他已有的默认地址 否则设置为非默认地址
            if (register.getIsDefault() == 1) {
                Long addressDeId = repository.getDefaultId(address.getUserId());
                if (addressDeId != null) {
                    repository.cancelDefault(addressDeId);
                }
            } else {
                register.setIsDefault(0);
            }
        String addressSn = address.register(register);
        // 写入
        repository.save(address);
        return addressSn;
    }

}
