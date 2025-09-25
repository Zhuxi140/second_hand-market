package com.zhuxi.userModule.application.service;


import com.zhuxi.common.exception.TransactionalException;
import com.zhuxi.common.result.Result;
import com.zhuxi.userModule.infrastructure.repository.impl.UserAddressMapperHelper;
import com.zhuxi.userModule.domain.address.service.UserAddressService;
import com.zhuxi.userModule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.userModule.interfaces.vo.address.UserAddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    
    private final UserAddressMapperHelper userAddressMapperHelper;
    private final ConcurrentMap<String, WeakReference<Object>> adsLock = new ConcurrentHashMap<>();


/*
*
     * 插入地址
     *待完善:  验证jwt中userSn与参数 userSn的一致性 并发等
*/
    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public Result<String> insertAddress(AdsRegisterDTO address, String userSn) {

        Long userId = userAddressMapperHelper.getUserId(userSn);
        userAddressMapperHelper.checkAddressCount(userId);

        // 若用户指定为默认地址，则取消其他已有的默认地址 否则设置为非默认地址
        if (address.getIsDefault() == 1){
            Long addressDefaultId = userAddressMapperHelper.getAddressDefaultId(userId);
            if (addressDefaultId != null){
                userAddressMapperHelper.cancelDefault(addressDefaultId);
            }
        }else{
            address.setIsDefault(0);
        }
        // 生成地址唯一标识 addressSn
        address.setAddressSn(UUID.randomUUID().toString());

        address.setUserId(userId);
        // 插入地址
        userAddressMapperHelper.insert(address,userSn);
        String addressSn = userAddressMapperHelper.selectAddressSnById(address.getId());

        return Result.success("success",addressSn);
    }

/*
*
     * 删除地址
*/
    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public Result<String> deleteBySn(String addressSn,String userSn) {
        Long userId = userAddressMapperHelper.getUserId(userSn);
        userAddressMapperHelper.deleteAddress(addressSn,userId);
        return Result.success("success");
    }
    
/*
*
     * 更新地址
*/
    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public Result<String> updateAds(AdsUpdate update, String addressSn, String userSn) {
        Object lock = getLock(userSn);
        synchronized ( lock) {
            userAddressMapperHelper.updateAds(update, addressSn);
        }
        return Result.success("success");
    }

/*
*
     * 获取用户地址列表
*/
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<List<UserAddressVO>> getListAddress(String userSn) {

        Long userId = userAddressMapperHelper.getUserId(userSn);
        List<UserAddressVO> list = userAddressMapperHelper.gerListAddress(userId);

        return Result.success("success",list);
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
