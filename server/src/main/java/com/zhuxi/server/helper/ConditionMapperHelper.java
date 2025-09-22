package com.zhuxi.server.helper;

import com.zhuxi.common.constant.CommonMessage;
import com.zhuxi.common.constant.TransactionMessage;
import com.zhuxi.common.exception.TransactionalException;
import com.zhuxi.pojo.VO.Condition.ConditionSdVO;
import com.zhuxi.server.mapper.ConditionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConditionMapperHelper {

    private final ConditionMapper conditionMapper;

    public List<ConditionSdVO> getListConditions(Integer isSecond){
        List<ConditionSdVO> listConditions = conditionMapper.getListConditions(isSecond);
        if (listConditions == null){
            log.error("getListConditions-case:{}", CommonMessage.DATABASE_SELECT_EXCEPTION);
            throw new TransactionalException(TransactionMessage.USER_DATA_ERROR);
        }
        return listConditions;
    }
}
