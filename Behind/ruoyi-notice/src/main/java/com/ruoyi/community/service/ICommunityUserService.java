package com.ruoyi.community.service;

import com.ruoyi.community.domain.CommunityUser;

public interface ICommunityUserService 
{
    public CommunityUser selectCommunityUserByPhone(String phone);

    public CommunityUser selectCommunityUserById(Long id);

    public int updateCommunityUser(CommunityUser communityUser);

    String getMyHouse(String username);
}
