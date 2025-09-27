package com.zhuxi.userModule.infrastructure.repository.impl;

import com.zhuxi.common.constant.CommonMessage;
import com.zhuxi.common.constant.BusinessMessage;
import com.zhuxi.common.exception.BusinessException;
import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.domain.user.repository.UserRepository;
import com.zhuxi.userModule.infrastructure.mapper.UserMapper;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public void save(User user){
        if (user.getId() == null){
            int save = userMapper.save(user);
            if (save < 1){
                log.error("save()-: username:{}  cases: {}", user.getUsername(), CommonMessage.DATABASE_INSERT_EXCEPTION);
                throw new BusinessException(BusinessMessage.REGISTER_ERROR);
            }
        }else{
            int update = userMapper.update(user);
            if (update < 1){
                log.error("update-: userSn:{}\n cases: {}", user.getUserSn(),CommonMessage.DATABASE_UPDATE_EXCEPTION);
                throw new BusinessException(BusinessMessage.UPDATE_INFO_ERROR);
            }
        }
    }

    //判断用户名是否已存在
    @Override
    public int checkUsernameExist(String  username){
        Integer i = userMapper.checkUsernameExist(username);
        if (i != null && i > 0){
            return 1;
        }
        return 0;
    }

    //通过用户名获取用户
    @Override
    public User getLoginByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        if (user == null){
            throw new BusinessException(BusinessMessage.USERNAME_OR_PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public void updatePw(Long id, String newPw){
        int result = userMapper.updatePw(id, newPw);
        if (result < 1){
            log.error("用户密码更新失败-: id:{}\n cases: {}", id,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new BusinessException(BusinessMessage.UPDATE_PW_ERROR);
        }
    }

    //获取用户信息
    @Override
    public UserViewVO getUserInfo(String userSn){
        UserViewVO userInfo = userMapper.getUserInfo(userSn);
        if (userInfo == null){
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return userInfo;
    }



    @Override
    public User getISBySn(String userSn) {
        User user = userMapper.getISBySn(userSn);
        if (user == null){
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return user;
    }
}
