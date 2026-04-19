package com.ruoyi.mapper;

import com.ruoyi.domain.WaterRechargeRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WaterRechargeRecordMapper {
    int insert(WaterRechargeRecord record);
}
