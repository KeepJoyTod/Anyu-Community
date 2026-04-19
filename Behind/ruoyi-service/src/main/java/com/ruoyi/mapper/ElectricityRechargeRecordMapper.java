package com.ruoyi.mapper;

import com.ruoyi.domain.ElectricityRechargeRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ElectricityRechargeRecordMapper {
    int insert(ElectricityRechargeRecord record);
}