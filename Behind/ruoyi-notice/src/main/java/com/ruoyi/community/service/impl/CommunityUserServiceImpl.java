package com.ruoyi.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.notice.mapper.CommunityUserMapper;
import com.ruoyi.community.domain.CommunityUser;
import com.ruoyi.community.service.ICommunityUserService;

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

    @Override
    public String getMyHouse(String username) {
        String communityUser = communityUserMapper.selectMyHouse(username);
        return communityUser;
    }
}
