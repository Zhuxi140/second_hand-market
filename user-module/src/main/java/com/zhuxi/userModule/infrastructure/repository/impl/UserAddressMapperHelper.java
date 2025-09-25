package com.zhuxi.userModule.infrastructure.repository.impl;


import com.zhuxi.common.constant.CommonMessage;
import com.zhuxi.common.constant.TransactionMessage;
import com.zhuxi.common.exception.TransactionalException;
import com.zhuxi.userModule.infrastructure.mapper.UserAddressMapper;
import com.zhuxi.userModule.interfaces.dto.address.AdsBaseUpdate;
import com.zhuxi.userModule.interfaces.dto.address.AdsLocationUpdate;
import com.zhuxi.userModule.interfaces.dto.address.AdsRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.address.AdsUpdate;
import com.zhuxi.userModule.interfaces.vo.address.UserAddressVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhuxi
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserAddressMapperHelper {
    
    private final UserAddressMapper userAddressMapper;

    public Long getUserId(String userSn){
        Long userId = userAddressMapper.getUserId(userSn);
        if (userId == null){
            log.error("userSn:{}-\ngetUserId-case:{}",userSn,CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new TransactionalException(TransactionMessage.USER_DATA_ERROR);
        }
        return userId;
    }


    //获取用户地址总数
    public void checkAddressCount(Long userId){
        int count = userAddressMapper.getAddressCount(userId);
        if (count > 20){
            throw new TransactionalException(TransactionMessage.ADDRESS_COUNT_ERROR);
        }
    }
    
    // 插入地址
    public void insert(AdsRegisterDTO userAddress, String userSn) {
        int insert = userAddressMapper.insert(userAddress);
        if (insert <= 0){
            log.error("userSn:{}-\ninsert-case:{}",userSn, CommonMessage.DATABASE_INSERT_EXCEPTION);
            throw new TransactionalException(TransactionMessage.ADD_ADDRESS_ERROR);
        }
    }

    // 根据用户ID查询默认地址ID
    public Long getAddressDefaultId(Long userId){
        return userAddressMapper.getAddressDefaultId(userId);
    }

    //取消默认地址
    public void cancelDefault(Long addressId){
        int update = userAddressMapper.cancelDefault(addressId);
        if (update <= 0){
            log.error("addressId:{}-\ncancelDefault-case:{}",addressId,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new TransactionalException(TransactionMessage.CANCEL_DEFAULT_ADDRESS_ERROR);
        }
    }

     //根据ID查询地址号
    public String selectAddressSnById(Long id) {
        String addressSn = userAddressMapper.selectAddressSnById(id);
        if (addressSn == null){
            log.error("id:{}-\nselectAddressSnById-case:{}",id,CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new TransactionalException(TransactionMessage.USER_DATA_ERROR);
        }
        return addressSn;
    }
    


    //删除地址
    public void deleteAddress(String addressSn,Long userId) {
        int result = userAddressMapper.deleteById(addressSn, userId);
        if (result <= 0){
            log.error("addressSn:{}-\ndeleteAddress-case:{}",addressSn,CommonMessage.DATABASE_DELETE_EXCEPTION);
            throw new TransactionalException(TransactionMessage.DELETE_ADDRESS_ERROR);
        }
    }

    public void updateAds(AdsUpdate update, String addressSn){
        int result = userAddressMapper.updateAds(update,addressSn);
        if (result <= 0){
            log.error("addressSn:{}-\nupdateAds-case:{}",addressSn,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new TransactionalException(TransactionMessage.UPDATE_ADDRESS_ERROR);
        }
    }
    
    // 修改地址基础信息
    public void updateBase(AdsBaseUpdate update, String addressSn) {
        int result = userAddressMapper.updateBase(update,addressSn);
        if (result <= 0){
            log.error("addressSn:{},---update-case:{}",addressSn,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new TransactionalException(TransactionMessage.UPDATE_ADDRESS_ERROR);
        }
    }

    // 修改地址位置信息
    public void updateLocation(AdsLocationUpdate update, String addressSn) {
        int result = userAddressMapper.updateLocation(update,addressSn);
        if (result <= 0){
            log.error("addressSn:{},---updateLocation-case:{}",addressSn,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new TransactionalException(TransactionMessage.UPDATE_ADDRESS_ERROR);
        }
    }

    // 获取地址列表
    public List<UserAddressVO> gerListAddress(Long userId) {
        List<UserAddressVO> list = userAddressMapper.gerListAddress(userId);
        if (list == null){
            log.error("userSn:{}-\ngerListAddress-case:{}",userId,CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new TransactionalException(TransactionMessage.USER_DATA_ERROR);
        }
        return list;
    }
    

}
