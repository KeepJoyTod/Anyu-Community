package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityDonationRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityDonationRecordMapper {
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
