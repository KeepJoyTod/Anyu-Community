package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityUser;

public interface CommunityUserMapper 
{
    public CommunityUser selectCommunityUserByPhone(String phone);

    public CommunityUser selectCommunityUserById(Long id);

    public int updateCommunityUser(CommunityUser communityUser);
}
