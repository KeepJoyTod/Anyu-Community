package com.ruoyi.community.service;

import java.util.List;
import com.ruoyi.community.domain.CommunityNotice;

public interface ICommunityNoticeService1
{
    public List<CommunityNotice> selectCommunityNoticeList(CommunityNotice communityNotice);

    public CommunityNotice selectCommunityNoticeById(Long noticeId);

    public int insertCommunityNotice(CommunityNotice communityNotice);

    public int updateCommunityNotice(CommunityNotice communityNotice);

    public int deleteCommunityNoticeById(Long noticeId);

    public int deleteCommunityNoticeByIds(Long[] noticeIds);
}
