package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityPaymentRecord;

public interface CommunityPaymentRecordMapper 
{
    public CommunityPaymentRecord selectCommunityPaymentRecordById(Long id);

    public List<CommunityPaymentRecord> selectCommunityPaymentRecordList(CommunityPaymentRecord communityPaymentRecord);

    public int insertCommunityPaymentRecord(CommunityPaymentRecord communityPaymentRecord);

    public int updateCommunityPaymentRecord(CommunityPaymentRecord communityPaymentRecord);

    public int deleteCommunityPaymentRecordById(Long id);

    public int deleteCommunityPaymentRecordByIds(Long[] ids);
}
