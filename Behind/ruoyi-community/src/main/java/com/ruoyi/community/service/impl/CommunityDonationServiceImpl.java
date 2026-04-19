package com.ruoyi.community.service.impl;

import com.ruoyi.community.domain.CommunityDonation;
import com.ruoyi.community.mapper.CommunityDonationMapper;
import com.ruoyi.community.service.ICommunityDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunityDonationServiceImpl implements ICommunityDonationService {
    @Autowired
    private CommunityDonationMapper donationMapper;

    @Override
    public List<CommunityDonation> selectDonationList() {

        return donationMapper.selectDonationList();
    }

    @Override
    public int updateDonationAmountAndCount(Long id, BigDecimal amount) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("amount", amount);
        return donationMapper.updateDonationAmountAndCount(params);
    }
}