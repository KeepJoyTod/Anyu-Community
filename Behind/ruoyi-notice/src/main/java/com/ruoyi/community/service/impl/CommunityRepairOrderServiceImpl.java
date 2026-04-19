package com.ruoyi.community.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityRepairOrderMapper;
import com.ruoyi.community.domain.CommunityRepairOrder;
import com.ruoyi.community.service.ICommunityRepairOrderService;

@Service
public class CommunityRepairOrderServiceImpl implements ICommunityRepairOrderService 
{
    @Autowired
    private CommunityRepairOrderMapper communityRepairOrderMapper;

    @Override
    public CommunityRepairOrder selectCommunityRepairOrderById(Long orderId)
    {
        return communityRepairOrderMapper.selectCommunityRepairOrderById(orderId);
    }

    @Override
    public List<CommunityRepairOrder> selectCommunityRepairOrderList(CommunityRepairOrder communityRepairOrder)
    {
        return communityRepairOrderMapper.selectCommunityRepairOrderList(communityRepairOrder);
    }

    @Override
    public int insertCommunityRepairOrder(CommunityRepairOrder communityRepairOrder)
    {
        return communityRepairOrderMapper.insertCommunityRepairOrder(communityRepairOrder);
    }

    @Override
    public int updateCommunityRepairOrder(CommunityRepairOrder communityRepairOrder)
    {
        return communityRepairOrderMapper.updateCommunityRepairOrder(communityRepairOrder);
    }

    @Override
    public int deleteCommunityRepairOrderByIds(Long[] orderIds)
    {
        return communityRepairOrderMapper.deleteCommunityRepairOrderByIds(orderIds);
    }

    @Override
    public int deleteCommunityRepairOrderById(Long orderId)
    {
        return communityRepairOrderMapper.deleteCommunityRepairOrderById(orderId);
    }
}
