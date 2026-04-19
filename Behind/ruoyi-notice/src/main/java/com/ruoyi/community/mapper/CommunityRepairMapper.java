package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityRepair;
import org.apache.ibatis.annotations.Param;

public interface CommunityRepairMapper 
{
    public CommunityRepair selectCommunityRepairById(Long repairId);

    public List<CommunityRepair> selectCommunityRepairList(CommunityRepair communityRepair);

    public int insertCommunityRepair(CommunityRepair communityRepair);

    public int updateCommunityRepair(CommunityRepair communityRepair);

    public int deleteCommunityRepairById(Long repairId);

    public int deleteCommunityRepairByIds(Long[] repairIds);
}
