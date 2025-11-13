package com.zhuxi.user.module.application.service;


import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.user.module.application.service.process.address.InsertAdsProcess;
import com.zhuxi.user.module.domain.address.enums.IsDefault;
import com.zhuxi.user.module.domain.address.model.UserAddress;
import com.zhuxi.user.module.domain.address.repository.UserAddressRepository;
import com.zhuxi.user.module.domain.address.service.UserAddressCacheService;
import com.zhuxi.user.module.domain.address.service.UserAddressService;
import com.zhuxi.user.module.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.user.module.interfaces.dto.address.AdsUpdate;
import com.zhuxi.user.module.interfaces.vo.address.UserAddressVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zhuxi
 */
@Slf4j(topic = "UserAddressServiceImpl")
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    
    private final UserAddressRepository repository;
    private final ConcurrentMap<String, WeakReference<Object>> adsLock = new ConcurrentHashMap<>();
    private final InsertAdsProcess insertAdsProcess;
    private final UserAddressCacheService cache;

    // 添加地址
    @Override
    public String insertAddress(AdsRegisterDTO register, String userSn) {
        // 检查地址数量
        UserAddress userAddress = insertAdsProcess.checkAddressCount(userSn);

        // 原子性检查是否需处理默认地址并插入地址
        String addressSn = null;
        Object lock = getLock(userSn);
        synchronized(lock){
            insertAdsProcess.insertAds(userAddress, register);
        }
        return addressSn;
    }

    // 删除地址
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void deleteBySn(String addressSn,String userSn) {
        // 检查地址id有效性
        Long addressId = repository.checkIdEffective(addressSn);
        // 删除地址
        repository.deleteAddress(addressId);
    }
    
    /*
    * 更新地址
    */
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateAds(AdsUpdate update, String addressSn, String userSn) {
        Object lock = getLock(userSn);
        synchronized (lock) {
            // 获取地址id 以及 是否为默认地址
            UserAddress address = repository.getAdIdOrDefaultBySn(addressSn);

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

    // 获取地址列表
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<UserAddressVO> getListAddress(String userSn) {

        List<UserAddressVO> info = cache.getInfo(userSn);
        if (info == null) {
            // 未命中
            UserAddress address = repository.getUserIdBySn(userSn);
            List<UserAddressVO> userAddressVos = repository.gerListAddress(address.getUserId());
            // 异步缓存信息
            CompletableFuture.runAsync(() -> cache.saveInfo(userSn, userAddressVos));
            return userAddressVos;
        }
        return info;
    }

    private Object getLock(String userSn){
        adsLock.entrySet().removeIf(entry->{
            WeakReference<Object> ref = entry.getValue();
            return ref != null && ref.get() == null;
        });

        return adsLock.compute(userSn, (key, ref) -> {
            if (ref != null){
                Object lock = ref.get();
                if (lock != null){
                    return ref;
                }
            }
            return new WeakReference<>(new Object());
        }).get();
    }
}
