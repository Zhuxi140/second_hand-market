package com.zhuxi.server.mapper;

import com.zhuxi.pojo.VO.Condition.ConditionSdVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConditionMapper {

    @Select("SELECT id,name FROM product_condition WHERE is_second = #{isSecond}")
    List<ConditionSdVO> getListConditions(Integer isSecond);

}
