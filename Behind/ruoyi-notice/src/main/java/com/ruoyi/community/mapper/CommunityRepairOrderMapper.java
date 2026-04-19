package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityRepairOrder;
import org.apache.ibatis.annotations.Param;

public interface CommunityRepairOrderMapper 
{
    public CommunityRepairOrder selectCommunityRepairOrderById(Long orderId);

    public List<CommunityRepairOrder> selectCommunityRepairOrderList(CommunityRepairOrder communityRepairOrder);

    public int insertCommunityRepairOrder(CommunityRepairOrder communityRepairOrder);

    public int updateCommunityRepairOrder(CommunityRepairOrder communityRepairOrder);

    public int deleteCommunityRepairOrderById(Long orderId);

    public int deleteCommunityRepairOrderByIds(Long[] orderIds);
}
