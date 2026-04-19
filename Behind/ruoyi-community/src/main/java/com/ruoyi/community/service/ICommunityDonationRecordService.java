package com.ruoyi.community.service;

import com.ruoyi.community.domain.CommunityDonationRecord;

import java.util.List;

public interface ICommunityDonationRecordService {
    /**
     * 插入捐赠记录
     */
    int insertDonationRecord(CommunityDonationRecord record);

    /**
     * 查询所有捐赠记录
     */
    List<CommunityDonationRecord> selectDonationRecordList();

    /**
     * 根据捐赠项目ID查询捐赠记录
     */
    List<CommunityDonationRecord> selectDonationRecordByDonationId(Long donationId);
}