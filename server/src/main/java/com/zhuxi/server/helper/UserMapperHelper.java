package com.zhuxi.server.helper;

import com.zhuxi.common.constant.CommonMessage;
import com.zhuxi.common.constant.TransactionMessage;
import com.zhuxi.common.exception.TransactionalException;
import com.zhuxi.pojo.DTO.User.UserRegisterDTO;
import com.zhuxi.pojo.DTO.User.UserUpdateInfoDTO;
import com.zhuxi.pojo.VO.User.UserViewVO;
import com.zhuxi.pojo.entity.User;
import com.zhuxi.server.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class UserMapperHelper {
    
    private final UserMapper userMapper;

    /**
     * 注册用户
     */
    public void register(UserRegisterDTO user) {
        try {
            int register = userMapper.register(user);
            if (register < 1){
                log.error("用户注册失败-: username:{}  cases: {}", user.getUsername(), CommonMessage.DATABASE_INSERT_EXCEPTION);
                throw new TransactionalException(TransactionMessage.REGISTER_ERROR);
            }
        }catch (DuplicateKeyException e){
            throw new TransactionalException(TransactionMessage.PHONE_IS_EXIST);
        }
    }

    /**
     * 判断用户名是否已存在
     */
    public int checkUsernameExist(String  username){
        Integer i = userMapper.checkUsernameExist(username);
        if (i != null && i > 0){
            return 1;
        }
        return 0;
    }

    /**
     * 通过用户名获取用户
     */
    public User getUserByUsername(String username) {
        return userMapper.getPasswordByUsername(username);
    }

    public void UpdatePw(String userSn, String newPw){
        int result = userMapper.UpdatePw(userSn, newPw);
        if (result < 1){
            log.error("用户密码更新失败-: userSn:{}\n cases: {}", userSn,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new TransactionalException(TransactionMessage.UPDATE_PW_ERROR);
        }
    }


    /**
     * 通过userSn获取密码
     */
    public String getPWByUserSn(String userSn){
        String pw = userMapper.getPWByUserSn(userSn);
        if (pw ==  null){
            throw new TransactionalException(TransactionMessage.USER_DATA_ERROR);
        }
        return pw;
    }

    /**
     * 获取用户信息
     */
    public UserViewVO getUserInfo(String userSn){
        UserViewVO userInfo = userMapper.getUserInfo(userSn);
        if (userInfo == null){
            throw new TransactionalException(TransactionMessage.USER_DATA_ERROR);
        }
        return userInfo;
    }

    /**
     * 更新用户
     */
    public void updateInfo(UserUpdateInfoDTO user, String userSn) {
        int result = userMapper.updateInfo(user, userSn);
        if (result < 1){
            log.error("用户信息更新失败-: userSn:{}\n cases: {}", userSn,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new TransactionalException(TransactionMessage.UPDATE_INFO_ERROR);
        }
    }

}
