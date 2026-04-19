package com.ruoyi.community.service.impl;

import com.ruoyi.community.domain.CommunityDonationRecord;
import com.ruoyi.community.mapper.CommunityDonationRecordMapper;
import com.ruoyi.community.service.ICommunityDonationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityDonationRecordServiceImpl implements ICommunityDonationRecordService {
    @Autowired
    private CommunityDonationRecordMapper donationRecordMapper;

    @Override
    public int insertDonationRecord(CommunityDonationRecord record) {
        return donationRecordMapper.insertDonationRecord(record);
    }

    @Override
    public List<CommunityDonationRecord> selectDonationRecordList() {
        return donationRecordMapper.selectDonationRecordList();
    }

    @Override
    public List<CommunityDonationRecord> selectDonationRecordByDonationId(Long donationId) {
        return donationRecordMapper.selectDonationRecordByDonationId(donationId);
    }
}