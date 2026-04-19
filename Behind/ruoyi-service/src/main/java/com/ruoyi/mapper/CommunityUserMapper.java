package com.ruoyi.mapper;


import com.ruoyi.domain.CommunityUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommunityUserMapper
{
    public CommunityUser selectCommunityUserByPhone(String phone);

    public CommunityUser selectCommunityUserById(Long id);

    public int updateCommunityUser(CommunityUser communityUser);
}
