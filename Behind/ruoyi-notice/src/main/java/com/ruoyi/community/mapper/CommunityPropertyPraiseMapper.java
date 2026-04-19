package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityPropertyPraise;

public interface CommunityPropertyPraiseMapper 
{
    public List<CommunityPropertyPraise> selectCommunityPropertyPraiseList(CommunityPropertyPraise communityPropertyPraise);

    public CommunityPropertyPraise selectCommunityPropertyPraiseById(Long id);

    public int insertCommunityPropertyPraise(CommunityPropertyPraise communityPropertyPraise);

    public int updateCommunityPropertyPraise(CommunityPropertyPraise communityPropertyPraise);

    public int deleteCommunityPropertyPraiseById(Long id);
}
