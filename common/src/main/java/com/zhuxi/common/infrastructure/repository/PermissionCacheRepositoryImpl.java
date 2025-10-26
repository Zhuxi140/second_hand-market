package com.zhuxi.common.infrastructure.repository;

import com.zhuxi.common.interfaces.result.PermissionInfoOne;
import com.zhuxi.common.shared.constant.BusinessMessage;
import com.zhuxi.common.shared.constant.CommonMessage;
import com.zhuxi.common.domain.repository.PermissionCacheRepository;
import com.zhuxi.common.shared.exception.BusinessException;
import com.zhuxi.common.infrastructure.mapper.CacheMapper;
import com.zhuxi.common.interfaces.result.PermissionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuxi
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PermissionCacheRepositoryImpl implements PermissionCacheRepository {

    private final CacheMapper mapper;

    @Override
    public Long getUserId(String userSn) {
        Long userId = mapper.getUserId(userSn);
        if (userId == null){
            log.error("userSn:{}-\ngetUserId-case:{}",userSn, CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new BusinessException(BusinessMessage.USER_DATA_ERROR);
        }
        return userId;
    }

    @Override
    public List<String> getBanedPermissionInfo(Long userId) {
        return mapper.getBanedPermissionInfo(userId);
    }

    @Override
    public List<PermissionInfoOne> getRolePermissionInfo(Long userId) {
        return mapper.getRolePermissionInfo(userId);
    }
}
