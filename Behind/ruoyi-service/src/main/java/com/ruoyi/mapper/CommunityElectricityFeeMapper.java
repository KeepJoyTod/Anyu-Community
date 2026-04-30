package com.ruoyi.mapper;

import com.ruoyi.domain.CommunityElectricityFee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 电费管理Mapper接口
 * 
 * @author ruoyi
 */
@Mapper
public interface CommunityElectricityFeeMapper {
    /**
     * 查询电费账单列表
     */
    List<CommunityElectricityFee> selectCommunityElectricityFeeList(CommunityElectricityFee communityElectricityFee);

    
    /**
     * 根据用户ID查询电费账单列表
     * 
     * @param userId 用户ID
     * @return 电费账单列表
     */
    List<CommunityElectricityFee> selectByUserId(Long userId);
    
    /**
     * 根据缴费状态查询电费账单列表
     * 
     * @param feeStatus 缴费状态
     * @return 电费账单列表
     */
    List<CommunityElectricityFee> selectByFeeStatus(String feeStatus);
    
    /**
     * 根据月份查询电费账单
     * 
     * @param readMonth 月份
     * @return 电费账单列表
     */
    List<CommunityElectricityFee> selectByReadMonth(String readMonth);
    
    /**
     * 根据ID更新电费账单
     * 
     * @param communityElectricityFee 电费账单
     * @return 影响行数
     */
    int updateById(CommunityElectricityFee communityElectricityFee);
    
    /**
     * 插入电费账单
     * 
     * @param communityElectricityFee 电费账单
     * @return 影响行数
     */
    int insert(CommunityElectricityFee communityElectricityFee);
    
    /**
     * 根据ID查询电费账单
     * 
     * @param electricityId 电费ID
     * @return 电费账单
     */
    CommunityElectricityFee selectById(Long electricityId);

    int deleteCommunityElectricityFeeByIds(Long[] electricityIds);
    
}
