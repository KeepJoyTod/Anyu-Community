package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.SysCommunityActivity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SysCommunityActivityMapper {

    SysCommunityActivity selectActivityById(@Param("activityId") Long activityId);

    // 核心：修正SQL，确保字段名和表名完全匹配

    int updateParticipantCount(@Param("activityId") Long activityId);

    List<SysCommunityActivity> selectAllActivity();

    void cancelRegister(Long activityId);
}