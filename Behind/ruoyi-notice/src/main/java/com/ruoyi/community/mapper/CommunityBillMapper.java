package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityBill;

public interface CommunityBillMapper 
{
    public CommunityBill selectCommunityBillById(Long id);

    public List<CommunityBill> selectCommunityBillList(CommunityBill communityBill);

    public int insertCommunityBill(CommunityBill communityBill);

    public int updateCommunityBill(CommunityBill communityBill);

    public int deleteCommunityBillById(Long id);

    public int deleteCommunityBillByIds(Long[] ids);
}
