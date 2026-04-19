package com.ruoyi.mapper;


import com.ruoyi.domain.CommunityUser;

public interface CommunityUserMapper
{
    public CommunityUser selectCommunityUserByPhone(String phone);

    public CommunityUser selectCommunityUserById(Long id);

    public boolean updateCommunityUser(CommunityUser communityUser);

}
