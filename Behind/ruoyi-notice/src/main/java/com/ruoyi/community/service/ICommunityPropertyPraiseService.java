package com.ruoyi.community.service;

import java.util.List;
import com.ruoyi.community.domain.CommunityPropertyPraise;

public interface ICommunityPropertyPraiseService 
{
    public List<CommunityPropertyPraise> selectCommunityPropertyPraiseList(CommunityPropertyPraise communityPropertyPraise);

    public CommunityPropertyPraise selectCommunityPropertyPraiseById(Long id);

    public int insertCommunityPropertyPraise(CommunityPropertyPraise communityPropertyPraise);

    public int updateCommunityPropertyPraise(CommunityPropertyPraise communityPropertyPraise);

    public int deleteCommunityPropertyPraiseById(Long id);
}
