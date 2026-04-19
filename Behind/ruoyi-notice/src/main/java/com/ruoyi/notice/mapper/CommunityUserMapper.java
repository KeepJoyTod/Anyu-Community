package com.ruoyi.notice.mapper;

import com.ruoyi.community.domain.CommunityUser;

public interface CommunityUserMapper 
{
    public CommunityUser selectCommunityUserByPhone(String phone);

    public CommunityUser selectCommunityUserById(Long id);

    public int updateCommunityUser(CommunityUser communityUser);

    String selectMyHouse(String username);
}
