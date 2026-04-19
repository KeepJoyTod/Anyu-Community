package com.ruoyi.service;

import com.ruoyi.domain.CommunityWaterFee;

import java.math.BigDecimal;
import java.util.List;

/**
 * 水费服务接口
 */
public interface ICommunityWaterFeeService {
    
    /**
     * 获取用户水费余额
     * @param userId 用户ID
     * @return 水费信息
     */
    CommunityWaterFee getWaterBalance(Long userId);
    
    /**
     * 充值水费
     * @param userId 用户ID
     * @param amount 充值金额
     * @param payMethod 支付方式
     * @return 充值结果
     */
    boolean rechargeWater(Long userId, BigDecimal amount, String payMethod);
    
    /**
     * 根据ID查询水费信息
     * @param waterId 水费ID
     * @return 水费信息
     */
    CommunityWaterFee selectCommunityWaterFeeById(Long waterId);
    
    /**
     * 根据用户ID查询水费信息
     * @param userId 用户ID
     * @return 水费信息列表
     */
    List<CommunityWaterFee> selectCommunityWaterFeeByUserId(Long userId);
    
    /**
     * 查询水费信息列表
     * @return 水费信息列表
     */
    List<CommunityWaterFee> selectCommunityWaterFeeList();
    
    /**
     * 更新水费信息
     * @param communityWaterFee 水费信息
     * @return 更新结果
     */
    int updateCommunityWaterFee(CommunityWaterFee communityWaterFee);
    
    /**
     * 插入水费信息
     * @param communityWaterFee 水费信息
     * @return 插入结果
     */
    int insertCommunityWaterFee(CommunityWaterFee communityWaterFee);
}
