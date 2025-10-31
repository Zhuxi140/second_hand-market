package com.zhuxi.user.module.infrastructure.repository.impl;

import com.zhuxi.common.shared.constant.CommonMessage;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.AuthMessage;
import com.zhuxi.common.shared.enums.Role;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.repository.UserRepository;
import com.zhuxi.user.module.domain.user.model.RefreshToken;
import com.zhuxi.user.module.infrastructure.mapper.UserMapper;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author zhuxi
 */
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

    @Override
    public void saveRole(Long userId, int roleId) {
        int i = userMapper.saveRole(userId, roleId);
        if (i < 1){
            log.error("saveRole()-error: userId:{},roleId:{}\n cases: {}", userId,roleId,CommonMessage.DATABASE_INSERT_EXCEPTION);
            throw new BusinessException(BusinessMessage.REGISTER_ERROR);
        }
    }

    @Override
    public void checkRoleExist(int roleId) {
        Integer i = userMapper.checkRoleExist(roleId);
        if (i == null || i <= 0){
            log.error("checkRoleExist-error: roleId:{}\n cases: {}", roleId,CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.ROLE_NOT_EXIST);
        }
    }

    @Override
    public Role getRole(Long userId) {
        return userMapper.getRole(userId);
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
    public User getUserIdStatusBySn(String userSn) {
        User user = userMapper.getUserIdStatusBySn(userSn);
        if (user == null){
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return user;
    }

    @Override
    public User getUserIdStatusPasswordBySn(String userSn) {
        User user = userMapper.getUserIdStatusPasswordBySn(userSn);
        if (user == null){
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return user;
    }

    @Override
    public Long getUserId(String userSn) {
        Long userId = userMapper.getUserId(userSn);
        if (userId == null){
            log.error("getUserId-error:userSn:{}-\ngetUserId-case:{}",userSn,CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return userId;
    }


    @Override
    public void saveToken(RefreshToken token) {
        int result = userMapper.saveToken(token);
        if (result < 1){
            log.error("saveToken-error-: id:{}\n cases: {}", token.getId(),CommonMessage.DATABASE_INSERT_EXCEPTION);
            throw new BusinessException(AuthMessage.OTHER_TOKEN_ERROR);
        }
    }

    @Override
    public RefreshToken getTokenByUserId(Long userId) {
        return userMapper.getTokenByUserId(userId);
    }

    @Override
    public Long checkFreshTokenExist(Long userId) {
        return userMapper.checkFreshTokenExist(userId);
    }

    @Override
    public void deleteToken(Long tokenId) {
        int i = userMapper.deleteToken(tokenId);
        if (i != 1){
            log.error("deleteToken-error: tokenId:{} case:{}", tokenId,CommonMessage.DATABASE_UPDATE_EXCEPTION);
            throw new BusinessException(AuthMessage.LOGIN_INVALID);
        }
    }

}
