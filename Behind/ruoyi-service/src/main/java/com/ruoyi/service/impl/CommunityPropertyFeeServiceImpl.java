package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityPropertyFee;
import com.ruoyi.mapper.CommunityPropertyFeeMapper;
import com.ruoyi.service.ICommunityPropertyFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityPropertyFeeServiceImpl implements ICommunityPropertyFeeService {
    @Autowired
    private CommunityPropertyFeeMapper communityPropertyFeeMapper;

    @Override
    public List<CommunityPropertyFee> selectCommunityPropertyFeeList(CommunityPropertyFee communityPropertyFee) {
        return communityPropertyFeeMapper.selectCommunityPropertyFeeList(communityPropertyFee);
    }

    @Override
    public CommunityPropertyFee selectCommunityPropertyFeeById(Long propertyFeeId) {
        return communityPropertyFeeMapper.selectCommunityPropertyFeeById(propertyFeeId);
    }

    @Override
    public int insertCommunityPropertyFee(CommunityPropertyFee communityPropertyFee) {
        return communityPropertyFeeMapper.insertCommunityPropertyFee(communityPropertyFee);
    }

    @Override
    public int updateCommunityPropertyFee(CommunityPropertyFee communityPropertyFee) {
        return communityPropertyFeeMapper.updateCommunityPropertyFee(communityPropertyFee);
    }

    @Override
    public int deleteCommunityPropertyFeeByIds(Long[] propertyFeeIds) {
        return communityPropertyFeeMapper.deleteCommunityPropertyFeeByIds(propertyFeeIds);
    }
}
