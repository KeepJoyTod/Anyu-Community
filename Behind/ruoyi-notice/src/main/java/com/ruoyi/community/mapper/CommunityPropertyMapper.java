package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityProperty;

public interface CommunityPropertyMapper 
{
    public CommunityProperty selectCommunityProperty();

    public int insertCommunityProperty(CommunityProperty communityProperty);

    public int updateCommunityProperty(CommunityProperty communityProperty);
}
