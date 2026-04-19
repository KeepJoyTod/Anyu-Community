package com.ruoyi.mapper;

import com.ruoyi.domain.WaterAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WaterAccountMapper {
    WaterAccount selectWaterAccountById(Long userId);
    int insert(WaterAccount waterAccount);
    int updateById(WaterAccount waterAccount);
}
