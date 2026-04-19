package com.ruoyi.service;

import com.ruoyi.domain.CommunityWaterFee;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 水费表 服务类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface ICommunityWaterFeeService {

    CommunityWaterFee getWaterBalance(Long userId);

    boolean rechargeWater(Long userId, BigDecimal amount, String payMethod);

    CommunityWaterFee selectCommunityWaterFeeById(Long waterId);

    List<CommunityWaterFee> selectCommunityWaterFeeByUserId(Long userId);

    List<CommunityWaterFee> selectCommunityWaterFeeList();

    int updateCommunityWaterFee(CommunityWaterFee communityWaterFee);

    int insertCommunityWaterFee(CommunityWaterFee communityWaterFee);
}
