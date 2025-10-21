package com.zhuxi.user.module.domain.user.repository;

import com.zhuxi.user.module.domain.user.model.User;
import com.zhuxi.user.module.domain.user.valueObject.RefreshToken;
import com.zhuxi.user.module.interfaces.vo.user.UserViewVO;

public interface UserRepository {

    void save(User user);

    int checkUsernameExist(String  username);

    User getLoginByUsername(String username);

    void updatePw(Long id, String newPw);

    UserViewVO getUserInfo(String userSn);

    User getUserIdRoleStatusBySn(String userSn);

    Long getUserId(String userSn);

    void saveToken(RefreshToken refreshToken);

    RefreshToken getTokenByUserId(Long userId);



}
