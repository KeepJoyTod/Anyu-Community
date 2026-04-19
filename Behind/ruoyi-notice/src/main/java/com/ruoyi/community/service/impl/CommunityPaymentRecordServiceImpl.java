package com.ruoyi.community.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityPaymentRecordMapper;
import com.ruoyi.community.domain.CommunityPaymentRecord;
import com.ruoyi.community.service.ICommunityPaymentRecordService;

@Service
public class CommunityPaymentRecordServiceImpl implements ICommunityPaymentRecordService 
{
    @Autowired
    private CommunityPaymentRecordMapper communityPaymentRecordMapper;

    @Override
    public CommunityPaymentRecord selectCommunityPaymentRecordById(Long id)
    {
        return communityPaymentRecordMapper.selectCommunityPaymentRecordById(id);
    }

    @Override
    public List<CommunityPaymentRecord> selectCommunityPaymentRecordList(CommunityPaymentRecord communityPaymentRecord)
    {
        return communityPaymentRecordMapper.selectCommunityPaymentRecordList(communityPaymentRecord);
    }

    @Override
    public int insertCommunityPaymentRecord(CommunityPaymentRecord communityPaymentRecord)
    {
        communityPaymentRecord.setCreateTime(DateUtils.getNowDate());
        return communityPaymentRecordMapper.insertCommunityPaymentRecord(communityPaymentRecord);
    }

    @Override
    public int updateCommunityPaymentRecord(CommunityPaymentRecord communityPaymentRecord)
    {
        communityPaymentRecord.setUpdateTime(DateUtils.getNowDate());
        return communityPaymentRecordMapper.updateCommunityPaymentRecord(communityPaymentRecord);
    }

    @Override
    public int deleteCommunityPaymentRecordByIds(Long[] ids)
    {
        return communityPaymentRecordMapper.deleteCommunityPaymentRecordByIds(ids);
    }

    @Override
    public int deleteCommunityPaymentRecordById(Long id)
    {
        return communityPaymentRecordMapper.deleteCommunityPaymentRecordById(id);
    }
}
