package com.ruoyi.community.service;

import com.ruoyi.community.domain.CommunityPoints;

public interface ICommunityPointsService {
    CommunityPoints getPointsByUserId(Long userId);
    boolean deductPoints(Long userId, Integer points, String remark);
    boolean addPoints(Long userId, Integer points);
    
    /**
     * 添加积分（带备注）
     * @param userId 用户ID
     * @param points 积分数量
     * @param remark 备注说明
     * @return 是否成功
     */
    boolean addPoints(Long userId, Integer points, String remark);
}