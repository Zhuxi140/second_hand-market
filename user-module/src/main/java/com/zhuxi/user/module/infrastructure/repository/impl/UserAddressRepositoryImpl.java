package com.zhuxi.user.module.infrastructure.repository.impl;

import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.constant.CommonMessage;
import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.user.module.domain.address.model.UserAddress;
import com.zhuxi.user.module.domain.address.repository.UserAddressRepository;
import com.zhuxi.user.module.infrastructure.mapper.UserAddressMapper;
import com.zhuxi.user.module.interfaces.vo.address.UserAddressVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuxi
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class UserAddressRepositoryImpl implements UserAddressRepository {

    private final UserAddressMapper userAddressMapper;

    @Override
    public UserAddress getUserIdBySn(String userSn){
        UserAddress address = userAddressMapper.getUserId(userSn);
        if (address == null){
            log.error("userSn:{}-\ngetUserId-case:{}",userSn, CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return address;
    }


    @Override
    public UserAddress getAdIdOrDefaultBySn(String addressSn){
        UserAddress address = userAddressMapper.getAdIdOrDefaultBySn(addressSn);
        if (address == null){
            log.error("addressSn:{}-\ngetAddressBySn-case:{}",addressSn, CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return address;
    }


    //获取用户地址总数
    @Override
    public void checkAddressCount(Long userId){
        int count = userAddressMapper.getAddressCount(userId);
        if (count > 20){
            throw new BusinessException(BusinessMessage.ADDRESS_COUNT_ERROR);
        }
    }

    @Override
    public void save(UserAddress address){
        if (address.getId() == null){
            int insert = userAddressMapper.insert(address);
            if (insert <= 0){
                log.error("userId:{}-\ninsert-case:{}",address.getUserId(), CommonMessage.DATABASE_INSERT_EXCEPTION);
                throw new BusinessException(BusinessMessage.ADD_ADDRESS_ERROR);
            }
        }else{
            int update = userAddressMapper.update(address);
            if (update <= 0){
                log.error("userId:{}-\nupdate-case:{}",address.getUserId(), CommonMessage.DATABASE_UPDATE_EXCEPTION);
                throw new BusinessException(BusinessMessage.UPDATE_ADDRESS_ERROR);
            }
        }
    }

    // 根据用户ID查询默认地址ID
    @Override
    public Long getDefaultId(Long userId){
        return userAddressMapper.getDefaultId(userId);
    }

    //取消默认地址
    @Override
    public void cancelDefault(Long addressId){
        int update = userAddressMapper.cancelDefault(addressId);
        if (update <= 0){
            log.error("addressId:{}-\ncancelDefault-case:{}",addressId,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new BusinessException(BusinessMessage.CANCEL_DEFAULT_ADDRESS_ERROR);
        }
    }


    //删除地址
    @Override
    public void deleteAddress(String addressSn) {
        int result = userAddressMapper.deleteBySn(addressSn);
        if (result <= 0){
            log.error("addressSn:{}-\ndeleteAddress-case:{}",addressSn,CommonMessage.DATABASE_DELETE_EXCEPTION);
            throw new BusinessException(BusinessMessage.DELETE_ADDRESS_ERROR);
        }
    }

    // 获取地址列表
    @Override
    public List<UserAddressVO> gerListAddress(Long userId) {
        List<UserAddressVO> list = userAddressMapper.gerListAddress(userId);
        if (list == null){
            log.error("userId:{}-\ngerListAddress-case:{}",userId,CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return list;
    }
}
