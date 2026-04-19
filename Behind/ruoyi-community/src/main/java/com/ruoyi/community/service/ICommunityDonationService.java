package com.ruoyi.community.service;

import com.ruoyi.community.domain.CommunityDonation;

import java.math.BigDecimal;
import java.util.List;

public interface ICommunityDonationService {
    List<CommunityDonation> selectDonationList();

    /**
     * 更新捐赠项目的已筹金额和捐赠人数
     */
    int updateDonationAmountAndCount(Long id, BigDecimal amount);
}