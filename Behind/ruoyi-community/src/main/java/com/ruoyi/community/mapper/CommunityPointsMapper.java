package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityPoints;
import org.apache.ibatis.annotations.Param;

public interface CommunityPointsMapper {
    CommunityPoints selectPointsByUserId(Long userId);
    int updateAvailablePoints(@Param("userId") Long userId, @Param("points") Integer points);
    int insertPoints(@Param("userId") Long userId, @Param("points") Integer points);
}