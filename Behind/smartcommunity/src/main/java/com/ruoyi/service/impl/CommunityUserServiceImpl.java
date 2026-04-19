package com.ruoyi.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.domain.CommunityUser;
import com.ruoyi.mapper.CommunityUserMapper;
import com.ruoyi.service.ICommunityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("smartCommunityUserServiceImpl")
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
    public boolean updateCommunityUser(CommunityUser communityUser)
    {
        return communityUserMapper.updateCommunityUser(communityUser);
    }



}
