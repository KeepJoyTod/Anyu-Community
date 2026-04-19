package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityNotice;

public interface CommunityNoticeMapper 
{
    public List<CommunityNotice> selectCommunityNoticeList(CommunityNotice communityNotice);

    public CommunityNotice selectCommunityNoticeById(Long noticeId);

    public int insertCommunityNotice(CommunityNotice communityNotice);

    public int updateCommunityNotice(CommunityNotice communityNotice);

    public int deleteCommunityNoticeById(Long noticeId);

    public int deleteCommunityNoticeByIds(Long[] noticeIds);
}
