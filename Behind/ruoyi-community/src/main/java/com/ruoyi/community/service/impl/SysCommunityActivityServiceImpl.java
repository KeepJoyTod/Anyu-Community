package com.ruoyi.community.service.impl;

import com.ruoyi.community.domain.SysCommunityActivity;
import com.ruoyi.community.mapper.SysCommunityActivityMapper;
import com.ruoyi.community.service.ISysCommunityActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysCommunityActivityServiceImpl implements ISysCommunityActivityService {

    @Autowired
    private SysCommunityActivityMapper sysCommunityActivityMapper;


    @Override
    public boolean activityRegister(SysCommunityActivity activity) {
        return false;
    }

    @Override
    public SysCommunityActivity selectActivityById(Long activityId) {
        return sysCommunityActivityMapper.selectActivityById(activityId);
    }

    // 核心：实现人数+1 + 名额校验
    @Override
    @Transactional
    public boolean addParticipantCount(Long activityId) {
        // 1. 查询活动是否存在
        SysCommunityActivity activity = selectActivityById(activityId);
        if (activity == null) return false;

        // 2. 校验名额是否已满（max=0表示无限制）
        Integer max = activity.getMaxParticipants();
        Integer current = activity.getCurrentParticipants();
        if (max != null && max > 0 && current != null && current >= max) {
            return false;
        }

        // 3. 执行人数+1（兼容current为null的情况）
        return sysCommunityActivityMapper.updateParticipantCount(activityId) > 0;
    }

    @Override
    public List<SysCommunityActivity> selectAllActivity() {

        return sysCommunityActivityMapper.selectAllActivity();
    }

    @Override
    public void cancelRegister(Long activityId) {
        sysCommunityActivityMapper.cancelRegister(activityId);
    }
}