package com.zhuxi.user.module.application.service;


import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.user.module.application.service.process.address.InsertAdsProcess;
import com.zhuxi.user.module.application.service.process.address.UpdateAddressProcess;
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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuxi
 */
@Slf4j(topic = "UserAddressServiceImpl")
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    
    private final UserAddressRepository repository;
    private final InsertAdsProcess insertAdsProcess;
    private final UpdateAddressProcess updateAddressProcess;
    private final UserAddressCacheService cache;
    private final ConcurrentMap<String, WeakReference<ReentrantLock>> adsLock = new ConcurrentHashMap<>();
    private final ReferenceQueue<ReentrantLock> lockReferenceQueue = new ReferenceQueue<>();

    // 添加地址
    @Override
    public String insertAddress(AdsRegisterDTO register, String userSn) {
        ReentrantLock lock = getLock(userSn);
        try {
            if (!lock.tryLock(1, TimeUnit.SECONDS)){
                throw new BusinessException(BusinessMessage.TIMEOUT);
            }
            UserAddress userAddress = insertAdsProcess.checkAddressCount(userSn);
            return insertAdsProcess.insertAds(userAddress, register);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(BusinessMessage.ADD_ADDRESS_ERROR);
        } finally {
            lock.unlock();
        }
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
        ReentrantLock lock = getLock(userSn);
        try{
        if (!lock.tryLock(1, TimeUnit.SECONDS)) {
            throw new BusinessException(BusinessMessage.TIMEOUT);
        }
        UserAddress address = updateAddressProcess.getAddress(addressSn);
        updateAddressProcess.updateAddress(update,address);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
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

    private ReentrantLock getLock(String userSn){
        expungeStaleEntries();

        return adsLock.compute(userSn, (key, ref) -> {
            if (ref != null){
                ReentrantLock lock = ref.get();
                if (lock != null){
                    return ref;
                }
            }
            return new LockWeakReference(key,new ReentrantLock(),lockReferenceQueue);
        }).get();
    }

    private void expungeStaleEntries() {
        LockWeakReference ref;
        while ((ref = (LockWeakReference)lockReferenceQueue.poll()) != null) {
            adsLock.remove(ref.key,ref);
        }
    }

    private static class LockWeakReference extends WeakReference<ReentrantLock> {
        final String key;
        LockWeakReference(String key, ReentrantLock referent, ReferenceQueue<? super ReentrantLock> q) {
            super(referent, q);
            this.key = key;
        }
    }
}
