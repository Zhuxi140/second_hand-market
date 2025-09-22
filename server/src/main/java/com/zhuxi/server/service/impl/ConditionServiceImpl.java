package com.zhuxi.server.service.impl;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.VO.Condition.ConditionSdVO;
import com.zhuxi.server.helper.ConditionMapperHelper;
import com.zhuxi.server.service.Service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {

    private final ConditionMapperHelper conditionMapperHelper;
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<List<ConditionSdVO>> getListConditions(Integer isSecond) {
        List<ConditionSdVO> listConditions = conditionMapperHelper.getListConditions(isSecond);
        return Result.success("success",listConditions);
    }
}
