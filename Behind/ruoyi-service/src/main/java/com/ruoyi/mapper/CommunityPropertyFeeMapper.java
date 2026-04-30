package com.ruoyi.mapper;

import com.ruoyi.domain.CommunityPropertyFee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityPropertyFeeMapper {
    List<CommunityPropertyFee> selectCommunityPropertyFeeList(CommunityPropertyFee communityPropertyFee);

    CommunityPropertyFee selectCommunityPropertyFeeById(Long propertyFeeId);

    int insertCommunityPropertyFee(CommunityPropertyFee communityPropertyFee);

    int updateCommunityPropertyFee(CommunityPropertyFee communityPropertyFee);

    int deleteCommunityPropertyFeeByIds(Long[] propertyFeeIds);
}
