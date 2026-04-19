package com.ruoyi.community.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.community.mapper.CommunityBillMapper;
import com.ruoyi.community.domain.CommunityBill;
import com.ruoyi.community.service.ICommunityBillService;

@Service
public class CommunityBillServiceImpl implements ICommunityBillService 
{
    @Autowired
    private CommunityBillMapper communityBillMapper;

    @Override
    public CommunityBill selectCommunityBillById(Long id)
    {
        return communityBillMapper.selectCommunityBillById(id);
    }

    @Override
    public List<CommunityBill> selectCommunityBillList(CommunityBill communityBill)
    {
        return communityBillMapper.selectCommunityBillList(communityBill);
    }

    @Override
    public int insertCommunityBill(CommunityBill communityBill)
    {
        communityBill.setCreateTime(DateUtils.getNowDate());
        return communityBillMapper.insertCommunityBill(communityBill);
    }

    @Override
    public int updateCommunityBill(CommunityBill communityBill)
    {
        communityBill.setUpdateTime(DateUtils.getNowDate());
        return communityBillMapper.updateCommunityBill(communityBill);
    }

    @Override
    public int deleteCommunityBillByIds(Long[] ids)
    {
        return communityBillMapper.deleteCommunityBillByIds(ids);
    }

    @Override
    public int deleteCommunityBillById(Long id)
    {
        return communityBillMapper.deleteCommunityBillById(id);
    }
}
