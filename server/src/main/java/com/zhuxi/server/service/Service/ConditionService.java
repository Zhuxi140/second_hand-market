package com.zhuxi.server.service.Service;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.VO.Condition.ConditionSdVO;

import java.util.List;

public interface ConditionService {

    Result<List<ConditionSdVO>> getListConditions(Integer isSecond);
}
