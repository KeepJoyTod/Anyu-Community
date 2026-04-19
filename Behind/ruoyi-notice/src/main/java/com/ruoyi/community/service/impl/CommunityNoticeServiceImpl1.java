package com.ruoyi.community.service.impl;

import java.util.List;

import com.ruoyi.community.service.ICommunityNoticeService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityNoticeMapper;
import com.ruoyi.community.domain.CommunityNotice;
//import com.ruoyi.community.service.ICommunityNoticeService;

@Service("homeCommunityNoticeService")
public class CommunityNoticeServiceImpl1 implements ICommunityNoticeService1
{
    @Autowired
    private CommunityNoticeMapper communityNoticeMapper;

    @Override
    public List<CommunityNotice> selectCommunityNoticeList(CommunityNotice communityNotice)
    {
        return communityNoticeMapper.selectCommunityNoticeList(communityNotice);
    }

    @Override
    public CommunityNotice selectCommunityNoticeById(Long noticeId)
    {
        return communityNoticeMapper.selectCommunityNoticeById(noticeId);
    }

    @Override
    public int insertCommunityNotice(CommunityNotice communityNotice)
    {
        return communityNoticeMapper.insertCommunityNotice(communityNotice);
    }

    @Override
    public int updateCommunityNotice(CommunityNotice communityNotice)
    {
        return communityNoticeMapper.updateCommunityNotice(communityNotice);
    }

    @Override
    public int deleteCommunityNoticeById(Long noticeId)
    {
        return communityNoticeMapper.deleteCommunityNoticeById(noticeId);
    }

    @Override
    public int deleteCommunityNoticeByIds(Long[] noticeIds)
    {
        return communityNoticeMapper.deleteCommunityNoticeByIds(noticeIds);
    }
}
