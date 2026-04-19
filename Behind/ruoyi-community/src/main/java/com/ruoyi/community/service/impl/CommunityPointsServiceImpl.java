package com.ruoyi.community.service.impl;

import com.ruoyi.community.domain.CommunityPoints;
import com.ruoyi.community.mapper.CommunityPointsMapper;
import com.ruoyi.community.service.ICommunityPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("communityPointsServiceImpl2")
public class CommunityPointsServiceImpl implements ICommunityPointsService {
    @Autowired
    private CommunityPointsMapper pointsMapper;

    @Override
    public CommunityPoints getPointsByUserId(Long userId) {
        return pointsMapper.selectPointsByUserId(userId);
    }

    @Override
    @Transactional
    public boolean deductPoints(Long userId, Integer points, String remark) {
        CommunityPoints cp = pointsMapper.selectPointsByUserId(userId);
        if (cp == null || cp.getAvailablePoints() < points) {
            return false;
        }
        int rows = pointsMapper.updateAvailablePoints(userId, -points);
        return rows > 0;
    }

    @Override
    @Transactional
    public boolean addPoints(Long userId, Integer points) {
        CommunityPoints cp = pointsMapper.selectPointsByUserId(userId);
        if (cp == null) {
            // 如果用户没有积分记录，创建一条新记录
            return pointsMapper.insertPoints(userId, points) > 0;
        }
        int rows = pointsMapper.updateAvailablePoints(userId, points);
        return rows > 0;
    }

    @Override
    @Transactional
    public boolean addPoints(Long userId, Integer points, String remark) {
        // 调用两参数方法添加积分
        boolean success = addPoints(userId, points);
        // 这里可以添加积分变动记录日志
        // 如果有积分记录表，可以在这里插入一条记录
        System.out.println("用户 " + userId + " 获得 " + points + " 积分，备注：" + remark);
        return success;
    }
}