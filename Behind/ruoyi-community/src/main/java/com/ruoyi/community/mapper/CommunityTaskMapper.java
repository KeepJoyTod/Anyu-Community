package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityTask;
import com.ruoyi.community.domain.CommunityUserTask;
import com.ruoyi.community.domain.CommunitySigninRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 积分任务Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
public interface CommunityTaskMapper 
{
    /**
     * 查询所有启用的任务
     */
    public List<CommunityTask> selectAllTasks();

    /**
     * 根据任务类型查询任务
     */
    public List<CommunityTask> selectTasksByType(@Param("taskType") String taskType);

    /**
     * 根据任务动作查询任务
     */
    public CommunityTask selectTaskByAction(@Param("taskAction") String taskAction);

    /**
     * 查询用户的任务完成记录
     */
    public CommunityUserTask selectUserTask(@Param("userId") Long userId, @Param("taskId") Long taskId);

    /**
     * 插入用户任务记录
     */
    public int insertUserTask(CommunityUserTask userTask);

    /**
     * 更新用户任务记录
     */
    public int updateUserTask(CommunityUserTask userTask);

    /**
     * 查询用户今日任务完成次数
     */
    public Integer selectTodayCompleteTimes(@Param("userId") Long userId, @Param("taskId") Long taskId);

    /**
     * 插入签到记录
     */
    public int insertSigninRecord(CommunitySigninRecord signinRecord);

    /**
     * 查询用户最近签到记录
     */
    public CommunitySigninRecord selectLastSigninRecord(@Param("userId") Long userId);

    /**
     * 查询用户今日是否已签到
     */
    public Integer selectTodaySignin(@Param("userId") Long userId);
}
