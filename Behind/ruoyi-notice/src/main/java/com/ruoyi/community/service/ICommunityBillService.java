package com.ruoyi.community.service;

import java.util.List;
import com.ruoyi.community.domain.CommunityBill;

public interface ICommunityBillService 
{
    public CommunityBill selectCommunityBillById(Long id);

    public List<CommunityBill> selectCommunityBillList(CommunityBill communityBill);

    public int insertCommunityBill(CommunityBill communityBill);

    public int updateCommunityBill(CommunityBill communityBill);

    public int deleteCommunityBillByIds(Long[] ids);

    public int deleteCommunityBillById(Long id);
}
