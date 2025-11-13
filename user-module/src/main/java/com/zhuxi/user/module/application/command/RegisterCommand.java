package com.zhuxi.user.module.application.command;

import com.zhuxi.common.infrastructure.properties.CacheKeyProperties;
import com.zhuxi.user.module.interfaces.dto.user.UserRegisterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuxi
 */
@AllArgsConstructor
@Getter
public class RegisterCommand {
    private UserRegisterDTO register;
    private CacheKeyProperties defaultProperties;
    private String hashPassword;
    private String nickName;
}
