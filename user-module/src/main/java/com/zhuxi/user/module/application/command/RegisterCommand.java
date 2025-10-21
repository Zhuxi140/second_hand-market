package com.zhuxi.user.module.application.command;

import com.zhuxi.user.module.infrastructure.config.DefaultProperties;
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
    private DefaultProperties defaultProperties;
    private String hashPassword;
    private String nickName;
}
