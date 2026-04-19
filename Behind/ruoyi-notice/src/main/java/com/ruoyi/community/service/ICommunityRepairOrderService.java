package com.ruoyi.community.service;

import java.util.List;
import com.ruoyi.community.domain.CommunityRepairOrder;

public interface ICommunityRepairOrderService 
{
    public CommunityRepairOrder selectCommunityRepairOrderById(Long orderId);

    public List<CommunityRepairOrder> selectCommunityRepairOrderList(CommunityRepairOrder communityRepairOrder);

    public int insertCommunityRepairOrder(CommunityRepairOrder communityRepairOrder);

    public int updateCommunityRepairOrder(CommunityRepairOrder communityRepairOrder);

    public int deleteCommunityRepairOrderByIds(Long[] orderIds);

    public int deleteCommunityRepairOrderById(Long orderId);
}
