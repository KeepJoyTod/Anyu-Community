package com.ruoyi.mapper;

import com.ruoyi.domain.ElectricityAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ElectricityAccountMapper {
    ElectricityAccount selectElectricityAccountByUserId(Long userId);
    int insert(ElectricityAccount electricityAccount);
    int updateById(ElectricityAccount electricityAccount);
}