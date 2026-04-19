package com.ruoyi.community.service;

import java.util.List;
import com.ruoyi.community.domain.CommunityRepair;

public interface ICommunityRepairService 
{
    public CommunityRepair selectCommunityRepairById(Long repairId);

    public List<CommunityRepair> selectCommunityRepairList(CommunityRepair communityRepair);

    public int insertCommunityRepair(CommunityRepair communityRepair);

    public int updateCommunityRepair(CommunityRepair communityRepair);

    public int deleteCommunityRepairByIds(Long[] repairIds);

    public int deleteCommunityRepairById(Long repairId);
}
