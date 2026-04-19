package com.ruoyi.community.service;

import java.util.List;
import com.ruoyi.community.domain.CommunityPaymentRecord;

public interface ICommunityPaymentRecordService 
{
    public CommunityPaymentRecord selectCommunityPaymentRecordById(Long id);

    public List<CommunityPaymentRecord> selectCommunityPaymentRecordList(CommunityPaymentRecord communityPaymentRecord);

    public int insertCommunityPaymentRecord(CommunityPaymentRecord communityPaymentRecord);

    public int updateCommunityPaymentRecord(CommunityPaymentRecord communityPaymentRecord);

    public int deleteCommunityPaymentRecordByIds(Long[] ids);

    public int deleteCommunityPaymentRecordById(Long id);
}
