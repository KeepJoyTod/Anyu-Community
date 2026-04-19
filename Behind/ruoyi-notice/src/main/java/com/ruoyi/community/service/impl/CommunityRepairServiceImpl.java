package com.ruoyi.community.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityRepairMapper;
import com.ruoyi.community.domain.CommunityRepair;
import com.ruoyi.community.service.ICommunityRepairService;

@Service
public class CommunityRepairServiceImpl implements ICommunityRepairService 
{
    @Autowired
    private CommunityRepairMapper communityRepairMapper;

    @Override
    public CommunityRepair selectCommunityRepairById(Long repairId)
    {
        return communityRepairMapper.selectCommunityRepairById(repairId);
    }

    @Override
    public List<CommunityRepair> selectCommunityRepairList(CommunityRepair communityRepair)
    {
        return communityRepairMapper.selectCommunityRepairList(communityRepair);
    }

    @Override
    public int insertCommunityRepair(CommunityRepair communityRepair)
    {
        return communityRepairMapper.insertCommunityRepair(communityRepair);
    }

    @Override
    public int updateCommunityRepair(CommunityRepair communityRepair)
    {
        return communityRepairMapper.updateCommunityRepair(communityRepair);
    }

    @Override
    public int deleteCommunityRepairByIds(Long[] repairIds)
    {
        return communityRepairMapper.deleteCommunityRepairByIds(repairIds);
    }

    @Override
    public int deleteCommunityRepairById(Long repairId)
    {
        return communityRepairMapper.deleteCommunityRepairById(repairId);
    }
}
