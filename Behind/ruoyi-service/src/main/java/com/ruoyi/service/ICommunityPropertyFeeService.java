package com.ruoyi.service;

import com.ruoyi.domain.CommunityPropertyFee;

import java.util.List;

public interface ICommunityPropertyFeeService {
    List<CommunityPropertyFee> selectCommunityPropertyFeeList(CommunityPropertyFee communityPropertyFee);

    CommunityPropertyFee selectCommunityPropertyFeeById(Long propertyFeeId);

    int insertCommunityPropertyFee(CommunityPropertyFee communityPropertyFee);

    int updateCommunityPropertyFee(CommunityPropertyFee communityPropertyFee);

    int deleteCommunityPropertyFeeByIds(Long[] propertyFeeIds);
}
