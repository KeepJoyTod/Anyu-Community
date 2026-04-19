package com.ruoyi.community.service.impl;

import java.util.*;
import com.ruoyi.community.domain.*;
import com.ruoyi.community.mapper.CommunityTaskMapper;
import com.ruoyi.community.service.ICommunityTaskService;
import com.ruoyi.community.service.ICommunityPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 积分任务Service实现类
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
@Service
public class CommunityTaskServiceImpl implements ICommunityTaskService
{
    @Autowired
    private CommunityTaskMapper taskMapper;

    @Autowired
    private ICommunityPointsService pointsService;

    @Override
    public List<CommunityTask> getAllTasksWithStatus(Long userId) {
        List<CommunityTask> tasks = taskMapper.selectAllTasks();
        
        for (CommunityTask task : tasks) {
            // 查询用户今日完成次数
            Integer todayTimes = taskMapper.selectTodayCompleteTimes(userId, task.getTaskId());
            if (todayTimes == null) {
                todayTimes = 0;
            }
            
            // 查询用户任务记录
            CommunityUserTask userTask = taskMapper.selectUserTask(userId, task.getTaskId());
            
            task.setTodayCompleteTimes(todayTimes);
            task.setTotalCompleteTimes(userTask != null ? userTask.getTotalTimes() : 0);
            task.setCompleted(todayTimes >= task.getMaxTimes());
        }
        
        return tasks;
    }

    @Override
    public Map<String, List<CommunityTask>> getTasksByCategory(Long userId) {
        List<CommunityTask> allTasks = getAllTasksWithStatus(userId);
        Map<String, List<CommunityTask>> result = new HashMap<>();
        
        result.put("daily", new ArrayList<>());
        result.put("special", new ArrayList<>());
        
        for (CommunityTask task : allTasks) {
            if ("daily".equals(task.getTaskType())) {
                result.get("daily").add(task);
            } else if ("special".equals(task.getTaskType())) {
                result.get("special").add(task);
            }
        }
        
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> completeTask(Long userId, String taskAction) {
        Map<String, Object> result = new HashMap<>();
        
        // 查询任务
        CommunityTask task = taskMapper.selectTaskByAction(taskAction);
        if (task == null) {
            result.put("success", false);
            result.put("msg", "任务不存在");
            return result;
        }
        
        // 检查今日是否已完成
        Integer todayTimes = taskMapper.selectTodayCompleteTimes(userId, task.getTaskId());
        if (todayTimes == null) {
            todayTimes = 0;
        }
        
        if (todayTimes >= task.getMaxTimes()) {
            result.put("success", false);
            result.put("msg", "今日任务已完成");
            return result;
        }
        
        // 查询或创建用户任务记录
        CommunityUserTask userTask = taskMapper.selectUserTask(userId, task.getTaskId());
        Date now = new Date();
        
        if (userTask == null) {
            userTask = new CommunityUserTask();
            userTask.setUserId(userId);
            userTask.setTaskId(task.getTaskId());
            userTask.setCompleteTimes(1);
            userTask.setTotalTimes(1);
            userTask.setLastCompleteTime(now);
            taskMapper.insertUserTask(userTask);
        } else {
            // 检查是否是新的一天
            boolean isNewDay = !isSameDay(userTask.getLastCompleteTime(), now);
            if (isNewDay) {
                userTask.setCompleteTimes(1);
            } else {
                userTask.setCompleteTimes(userTask.getCompleteTimes() + 1);
            }
            userTask.setTotalTimes(userTask.getTotalTimes() + 1);
            userTask.setLastCompleteTime(now);
            taskMapper.updateUserTask(userTask);
        }
        
        // 发放积分
        pointsService.addPoints(userId, task.getPointsReward(), "完成任务: " + task.getTaskName());
        
        result.put("success", true);
        result.put("msg", "任务完成，获得 " + task.getPointsReward() + " 积分");
        result.put("points", task.getPointsReward());
        result.put("taskName", task.getTaskName());
        
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> signin(Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        // 检查今日是否已签到
        if (hasSigninToday(userId)) {
            result.put("success", false);
            result.put("msg", "今日已签到");
            return result;
        }
        
        // 查询上次签到记录
        CommunitySigninRecord lastRecord = taskMapper.selectLastSigninRecord(userId);
        int consecutiveDays = 1;
        
        if (lastRecord != null) {
            // 检查是否是连续签到
            Date lastDate = lastRecord.getSigninDate();
            Date today = new Date();
            
            if (isConsecutiveDay(lastDate, today)) {
                consecutiveDays = lastRecord.getConsecutiveDays() + 1;
            }
        }
        
        // 计算签到积分（基础5分，连续签到有加成）
        int basePoints = 5;
        int bonusPoints = Math.min((consecutiveDays - 1) * 2, 20); // 最多加20分
        int totalPoints = basePoints + bonusPoints;
        
        // 插入签到记录
        CommunitySigninRecord record = new CommunitySigninRecord();
        record.setUserId(userId);
        record.setSigninDate(new Date());
        record.setConsecutiveDays(consecutiveDays);
        record.setPointsEarned(totalPoints);
        taskMapper.insertSigninRecord(record);
        
        // 发放积分
        pointsService.addPoints(userId, totalPoints, "每日签到，连续 " + consecutiveDays + " 天");
        
        // 完成签到任务
        completeTask(userId, "signin");
        
        result.put("success", true);
        result.put("msg", "签到成功，获得 " + totalPoints + " 积分");
        result.put("points", totalPoints);
        result.put("consecutiveDays", consecutiveDays);
        
        return result;
    }

    @Override
    public boolean hasSigninToday(Long userId) {
        Integer count = taskMapper.selectTodaySignin(userId);
        return count != null && count > 0;
    }

    @Override
    public int getConsecutiveDays(Long userId) {
        CommunitySigninRecord lastRecord = taskMapper.selectLastSigninRecord(userId);
        if (lastRecord == null) {
            return 0;
        }
        
        // 检查上次签到是否是昨天或今天
        Date lastDate = lastRecord.getSigninDate();
        Date today = new Date();
        
        if (isSameDay(lastDate, today) || isConsecutiveDay(lastDate, today)) {
            return lastRecord.getConsecutiveDays();
        }
        
        return 0;
    }

    /**
     * 判断两个日期是否是同一天
     */
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断是否是连续的一天（昨天）
     */
    private boolean isConsecutiveDay(Date lastDate, Date today) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(lastDate);
        cal2.setTime(today);
        
        // 将时间设为0点，只比较日期
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        
        long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        long oneDay = 24 * 60 * 60 * 1000;
        
        return diff == oneDay;
    }
}
