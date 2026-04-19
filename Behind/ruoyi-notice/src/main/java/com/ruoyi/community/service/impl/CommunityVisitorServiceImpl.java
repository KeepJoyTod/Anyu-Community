package com.ruoyi.community.service.impl;

import java.util.List;
import java.util.UUID;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityVisitorMapper;
import com.ruoyi.community.domain.CommunityVisitor;
import com.ruoyi.community.service.ICommunityVisitorService;

@Service("noticeCommunityVisitorServiceImpl")
public class CommunityVisitorServiceImpl implements ICommunityVisitorService 
{
    @Autowired
    private CommunityVisitorMapper communityVisitorMapper;

    @Override
    public CommunityVisitor selectCommunityVisitorById(Long id)
    {
        return communityVisitorMapper.selectCommunityVisitorById(id);
    }

    @Override
    public List<CommunityVisitor> selectCommunityVisitorList(CommunityVisitor communityVisitor)
    {
        return communityVisitorMapper.selectCommunityVisitorList(communityVisitor);
    }

    @Override
    public int insertCommunityVisitor(CommunityVisitor communityVisitor)
    {
        communityVisitor.setCreateTime(DateUtils.getNowDate());
        communityVisitor.setStatus("0");
        String inviteCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        communityVisitor.setInviteCode(inviteCode);
        return communityVisitorMapper.insertCommunityVisitor(communityVisitor);
    }

    @Override
    public int updateCommunityVisitor(CommunityVisitor communityVisitor)
    {
        communityVisitor.setUpdateTime(DateUtils.getNowDate());
        return communityVisitorMapper.updateCommunityVisitor(communityVisitor);
    }

    @Override
    public int deleteCommunityVisitorByIds(Long[] ids)
    {
        return communityVisitorMapper.deleteCommunityVisitorByIds(ids);
    }

    @Override
    public int deleteCommunityVisitorById(Long id)
    {
        return communityVisitorMapper.deleteCommunityVisitorById(id);
    }
}
