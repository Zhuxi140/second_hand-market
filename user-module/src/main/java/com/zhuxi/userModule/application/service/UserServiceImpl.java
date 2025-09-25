package com.zhuxi.userModule.application.service;


import com.zhuxi.common.constant.TransactionMessage;
import com.zhuxi.common.exception.TransactionalException;
import com.zhuxi.common.result.Result;
import com.zhuxi.common.utils.BCryptUtils;
import com.zhuxi.userModule.domain.user.enums.Role;
import com.zhuxi.userModule.domain.user.model.User;
import com.zhuxi.userModule.domain.user.service.UserService;
import com.zhuxi.userModule.infrastructure.repository.impl.UserMapperHelper;
import com.zhuxi.userModule.interfaces.dto.user.UserLoginDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserRegisterDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdateInfoDTO;
import com.zhuxi.userModule.interfaces.dto.user.UserUpdatePwDTO;
import com.zhuxi.userModule.interfaces.vo.user.UserLoginVO;
import com.zhuxi.userModule.interfaces.vo.user.UserRegisterVO;
import com.zhuxi.userModule.interfaces.vo.user.UserViewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhuxi
 */
@Service
@RequiredArgsConstructor
// 待完善接口 -- 修改手机号、头像上传处理逻辑等
public class UserServiceImpl implements UserService {
    
    private final UserMapperHelper userMapperHelper;
    private final BCryptUtils bCryptUtils;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    /**
     * 注册用户
     * 待完善: userSn的生成机制
    */
    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public Result<UserRegisterVO> register(UserRegisterDTO user) {

        //检查用户名是否存在
        int exist = userMapperHelper.checkUsernameExist(user.getUsername());
        if (exist == 1){
            throw new TransactionalException(TransactionMessage.USERNAME_IS_EXIST);
        }

        //存在则继续执行
        String nickname = user.getNickname();
        if(nickname == null || nickname.isBlank()){
            nickname = "换换" + RANDOM.nextInt(10000);
            user.setNickname(nickname);
        }
        //密码加密
        user.setPassword((bCryptUtils.hashCode(user.getPassword())));
        String userSn = UUID.randomUUID().toString();
        user.setUserSn(userSn);
        //注册
        userMapperHelper.register(user);

        UserRegisterVO vo = new UserRegisterVO(
                userSn,
                nickname,
                Role.user,
                "https://c-ssl.dtstatic.com/uploads/blog/202505/01/bYSaV04yULoBnL1.thumb.300_0.jpg_webp",
                "sdadsa",
                "dassad",
                1656845462L
        );
        return Result.success("注册成功",vo);
    }

/**
     * 登录
     */

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<UserLoginVO> login(UserLoginDTO login) {

        User user = userMapperHelper.getUserByUsername(login.getUsername());
        String hashPassword = user.getPassword();
        boolean outCome;
        try {
            outCome = bCryptUtils.checkPw(
                    hashPassword == null ? virtualHash.hash.get(RANDOM.nextInt(virtualHash.hash.size())) : login.getPassword(),
                    hashPassword
            );
        }catch (NullPointerException e){
            throw new TransactionalException(TransactionMessage.USERNAME_OR_PASSWORD_ERROR);
        }

        if (outCome){
            UserLoginVO vo = new UserLoginVO(
                    user.getUserSn(),
                    user.getNickname(),
                    user.getRole(),
                    user.getAvatar(),
                    "asdasdas",
                    "dsadasdsa",
                    123456L
            );
            return Result.success(TransactionMessage.LOGIN_SUCCESS,vo);
        }
        return Result.fail(TransactionMessage.USERNAME_OR_PASSWORD_ERROR);
    }

/*
*
     * 登出
     * 待完善 : 登出逻辑(redis)

*/

    @Override
    public Result<String> logout() {
        return null;
    }

/**
     * 更新用户资料
     * 待完善: 核心逻辑 、 检查userSn与 token中的标识 一致性
     */

    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public Result<String> updateInfo(UserUpdateInfoDTO user, String userSn) {
        userMapperHelper.updateInfo(user,userSn);
        return Result.success("success");
    }
/*
*
     * 获取用户信息
     * 待完善: 检查userSn与 token中的标识 一致性
     */

    @Override
    @Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
    public Result<UserViewVO> getUserInfo(String userSn) {
        UserViewVO view = userMapperHelper.getUserInfo(userSn);
        return Result.success("success",view);
    }


/*
*
     * 修改密码
     * 待完善: 检查userSn与 token中的标识一致性 、 改密后应强制拉黑token 使其重新登陆

*/

    @Override
    @Transactional(rollbackFor = TransactionalException.class)
    public Result<String> updatePassword(UserUpdatePwDTO updatePw, String userSn) {

        String pw = userMapperHelper.getPWByUserSn(userSn);
        boolean result = bCryptUtils.checkPw(updatePw.getOldPassword(), pw);
        if (!result){
            throw new TransactionalException(TransactionMessage.USER_OLD_PASSWORD);
        }

        String newHash = bCryptUtils.hashCode(updatePw.getNewPassword());
        userMapperHelper.UpdatePw(userSn,newHash);

        return Result.success("success");
    }


    private static class virtualHash{
        public static List<String> hash = List.of(
            "$2a$10$o0d2OaDqaXmnhJDw4934Guo17bVhKafy5PnwwkySttZJd1JiEBJbC",
            "$2a$10$zQSBxnXcy.Xh1kG1YFWwSO.VLm1DBeX1VB8yxjmHOKz5KUASWPWx.",
            "$2a$10$Yj9Jfl.sJXZiUjuWu94etePa9u.ZPYJBLEDPP8yNt4YHO/CIkyIxq",
            "$2a$10$jzjviFMYJA8COLaCg6Boieh0e1.Q.Y1AhPlomT8RC25sc1IuB6qQ.",
            "$2a$10$GG6r.eAdUNqxslQSAOHyTOZDk/qUCB3RLCUAzSriYfZkNOIqrASeC",
            "$2a$10$C8PO6p4iq8rnJhV0sYpNNO9WLj96hSp5tmTEPQfLtyf9hS4e/or3W"
    );
    }
}
