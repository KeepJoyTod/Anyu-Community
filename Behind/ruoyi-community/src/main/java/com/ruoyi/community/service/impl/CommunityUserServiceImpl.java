package com.ruoyi.community.service.impl;

import com.ruoyi.community.domain.CommunityUser;
import com.ruoyi.community.mapper.CommunityUserMapper;
import com.ruoyi.community.service.ICommunityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("communityUserService")
public class CommunityUserServiceImpl implements ICommunityUserService 
{
    @Autowired
    private CommunityUserMapper communityUserMapper;

    @Override
    public CommunityUser selectCommunityUserByPhone(String phone)
    {
        return communityUserMapper.selectCommunityUserByPhone(phone);
    }

    @Override
    public CommunityUser selectCommunityUserById(Long id)
    {
        return communityUserMapper.selectCommunityUserById(id);
    }

    @Override
    public int updateCommunityUser(CommunityUser communityUser)
    {
        return communityUserMapper.updateCommunityUser(communityUser);
    }
}
