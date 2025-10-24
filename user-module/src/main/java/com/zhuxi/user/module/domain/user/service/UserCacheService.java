package com.zhuxi.user.module.domain.user.service;

import com.zhuxi.common.shared.enums.Role;

/**
 * @author zhuxi
 */

public interface UserCacheService {

    void saveBlockList(String token, Role role,long expire);


}
