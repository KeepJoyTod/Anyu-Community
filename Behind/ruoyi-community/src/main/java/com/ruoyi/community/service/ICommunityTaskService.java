package com.ruoyi.community.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.community.domain.CommunityTask;

/**
 * 积分任务Service接口
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
public interface ICommunityTaskService 
{
    /**
     * 获取所有任务（带用户完成状态）
     * 
     * @param userId 用户ID
     * @return 任务列表
     */
    public List<CommunityTask> getAllTasksWithStatus(Long userId);

    /**
     * 获取任务分类列表
     * 
     * @param userId 用户ID
     * @return 按类型分组的任务
     */
    public Map<String, List<CommunityTask>> getTasksByCategory(Long userId);

    /**
     * 完成任务
     * 
     * @param userId 用户ID
     * @param taskAction 任务动作
     * @return 结果信息
     */
    public Map<String, Object> completeTask(Long userId, String taskAction);

    /**
     * 签到
     * 
     * @param userId 用户ID
     * @return 签到结果
     */
    public Map<String, Object> signin(Long userId);

    /**
     * 检查用户今日是否已签到
     * 
     * @param userId 用户ID
     * @return true-已签到
     */
    public boolean hasSigninToday(Long userId);

    /**
     * 获取用户连续签到天数
     * 
     * @param userId 用户ID
     * @return 连续签到天数
     */
    public int getConsecutiveDays(Long userId);
}
