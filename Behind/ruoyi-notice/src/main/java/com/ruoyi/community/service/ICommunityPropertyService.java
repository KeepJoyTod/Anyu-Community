package com.ruoyi.community.service;

import com.ruoyi.community.domain.CommunityProperty;

public interface ICommunityPropertyService 
{
    public CommunityProperty selectCommunityProperty();

    public int insertCommunityProperty(CommunityProperty communityProperty);

    public int updateCommunityProperty(CommunityProperty communityProperty);
}
