package com.zhuxi.userModule.application.service;


import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.userModule.domain.address.model.UserAddress;
import com.zhuxi.userModule.domain.address.repository.UserAddressRepository;
import com.zhuxi.userModule.domain.address.service.UserAddressService;
import com.zhuxi.userModule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.userModule.interfaces.vo.address.UserAddressVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    
    private final UserAddressRepository repository;
    private final ConcurrentMap<String, WeakReference<Object>> adsLock = new ConcurrentHashMap<>();


/*
*
     * 插入地址
     *待完善:  验证jwt中userSn与参数 userSn的一致性 并发等
*/
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public String insertAddress(AdsRegisterDTO register, String userSn) {

        UserAddress address = repository.getUserIdBySn(userSn);
        Long userId = address.getUserId();

        //检查地址数量是否上限
        repository.checkAddressCount(userId);

        // 若用户指定为默认地址，则取消其他已有的默认地址 否则设置为非默认地址
        if (register.getIsDefault() == 1){
            Long addressDefaultId = repository.getDefaultId(userId);
            if (addressDefaultId != null){
                repository.cancelDefault(addressDefaultId);
            }
        }else{
            register.setIsDefault(0);
        }

        String addressSn = address.register(register);
        // 写入
        repository.save(address);

        return addressSn;
    }

/*
*
     * 删除地址
*/
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void deleteBySn(String addressSn,String userSn) {
        repository.deleteAddress(addressSn);
    }
    
/*
*
     * 更新地址
*/
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateAds(AdsUpdate update, String addressSn, String userSn) {
        Object lock = getLock(userSn);
        synchronized ( lock) {
            UserAddress address = repository.getAddressIdBySn(addressSn);
            address.update(update);
            repository.save(address);
        }
    }

/*
*
     * 获取用户地址列表
*/
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<UserAddressVO> getListAddress(String userSn) {
        UserAddress address = repository.getUserIdBySn(userSn);
        return repository.gerListAddress(address.getUserId());
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
