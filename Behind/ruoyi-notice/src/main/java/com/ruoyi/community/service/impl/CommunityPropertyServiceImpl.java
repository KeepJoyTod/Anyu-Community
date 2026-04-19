package com.ruoyi.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityPropertyMapper;
import com.ruoyi.community.domain.CommunityProperty;
import com.ruoyi.community.service.ICommunityPropertyService;

@Service("homeCommunityPropertyService")
public class CommunityPropertyServiceImpl implements ICommunityPropertyService 
{
    @Autowired
    private CommunityPropertyMapper communityPropertyMapper;

    @Override
    public CommunityProperty selectCommunityProperty()
    {
        return communityPropertyMapper.selectCommunityProperty();
    }

    @Override
    public int insertCommunityProperty(CommunityProperty communityProperty)
    {
        return communityPropertyMapper.insertCommunityProperty(communityProperty);
    }

    @Override
    public int updateCommunityProperty(CommunityProperty communityProperty)
    {
        return communityPropertyMapper.updateCommunityProperty(communityProperty);
    }
}
