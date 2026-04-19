package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityDonation;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CommunityDonationMapper {

    List<CommunityDonation> selectDonationList();
    
    /**
     * 更新捐赠项目的已筹金额和捐赠人数
     */
    int updateDonationAmountAndCount(java.util.Map<String, Object> params);
}

