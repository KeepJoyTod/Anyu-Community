package com.ruoyi.service;


import com.ruoyi.domain.CommunityUser;


public interface ICommunityUserService
{
    public CommunityUser selectCommunityUserByPhone(String phone);

    public CommunityUser selectCommunityUserById(Long id);

    public int updateCommunityUser(CommunityUser communityUser);
}
