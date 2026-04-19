package com.ruoyi.community.service;

import com.ruoyi.community.domain.SysCommunityActivity;

import java.util.List;

public interface ISysCommunityActivityService {

    boolean activityRegister(SysCommunityActivity activity);
    SysCommunityActivity selectActivityById(Long activityId);

    // 新增：报名人数+1的方法
    boolean addParticipantCount(Long activityId);

    List<SysCommunityActivity> selectAllActivity();

    void cancelRegister(Long activityId);
}