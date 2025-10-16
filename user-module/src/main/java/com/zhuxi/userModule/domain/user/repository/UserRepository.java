package com.zhuxi.userModule.domain.user.repository;

import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.domain.user.valueObject.RefreshToken;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;

public interface UserRepository {

    void save(User user);

    int checkUsernameExist(String  username);

    User getLoginByUsername(String username);

    void updatePw(Long id, String newPw);

    UserViewVO getUserInfo(String userSn);

    User getISBySn(String userSn);

    Long getUserId(String userSn);

    void saveToken(RefreshToken refreshToken);

    RefreshToken getTokenByUserId(Long userId);



}
