package com.ruoyi.community.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityPropertyPraiseMapper;
import com.ruoyi.community.domain.CommunityPropertyPraise;
import com.ruoyi.community.service.ICommunityPropertyPraiseService;

@Service("homeCommunityPropertyPraiseService")
public class CommunityPropertyPraiseServiceImpl implements ICommunityPropertyPraiseService 
{
    @Autowired
    private CommunityPropertyPraiseMapper communityPropertyPraiseMapper;

    @Override
    public List<CommunityPropertyPraise> selectCommunityPropertyPraiseList(CommunityPropertyPraise communityPropertyPraise)
    {
        return communityPropertyPraiseMapper.selectCommunityPropertyPraiseList(communityPropertyPraise);
    }

    @Override
    public CommunityPropertyPraise selectCommunityPropertyPraiseById(Long id)
    {
        return communityPropertyPraiseMapper.selectCommunityPropertyPraiseById(id);
    }

    @Override
    public int insertCommunityPropertyPraise(CommunityPropertyPraise communityPropertyPraise)
    {
        return communityPropertyPraiseMapper.insertCommunityPropertyPraise(communityPropertyPraise);
    }

    @Override
    public int updateCommunityPropertyPraise(CommunityPropertyPraise communityPropertyPraise)
    {
        return communityPropertyPraiseMapper.updateCommunityPropertyPraise(communityPropertyPraise);
    }

    @Override
    public int deleteCommunityPropertyPraiseById(Long id)
    {
        return communityPropertyPraiseMapper.deleteCommunityPropertyPraiseById(id);
    }
}
