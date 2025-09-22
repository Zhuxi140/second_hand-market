package com.zhuxi.server.controller;

import com.zhuxi.common.result.Result;
import com.zhuxi.pojo.VO.Condition.ConditionSdVO;
import com.zhuxi.server.service.Service.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/condition")
@RequiredArgsConstructor
public class ConditionController {
    private final ConditionService conditionService;

    @GetMapping("/getLis/secondHand")
    public Result<List<ConditionSdVO>> getListConditions()
    {
        return conditionService.getListConditions(1);
    }

    @GetMapping("/getLis/new")
    public Result<List<ConditionSdVO>> getListConditionsNew(){
        return conditionService.getListConditions(0);
    }
}
