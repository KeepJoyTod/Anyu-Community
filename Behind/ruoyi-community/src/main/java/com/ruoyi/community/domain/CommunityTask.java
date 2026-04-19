package com.ruoyi.community.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积分任务定义对象 community_task
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
public class CommunityTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long taskId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String taskDesc;

    /** 任务类型: daily-日常任务, special-专属任务 */
    @Excel(name = "任务类型", readConverterExp = "daily=日常任务,special=专属任务")
    private String taskType;

    /** 任务图标 */
    private String taskIcon;

    /** 积分奖励 */
    @Excel(name = "积分奖励")
    private Integer pointsReward;

    /** 任务动作标识 */
    private String taskAction;

    /** 每日最大完成次数 */
    private Integer maxTimes;

    /** 排序 */
    private Integer sortOrder;

    /** 状态: 0-启用, 1-禁用 */
    private String status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    // 非数据库字段 - 用户任务状态
    /** 是否已完成 */
    private Boolean completed;

    /** 今日完成次数 */
    private Integer todayCompleteTimes;

    /** 总完成次数 */
    private Integer totalCompleteTimes;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskIcon() {
        return taskIcon;
    }

    public void setTaskIcon(String taskIcon) {
        this.taskIcon = taskIcon;
    }

    public Integer getPointsReward() {
        return pointsReward;
    }

    public void setPointsReward(Integer pointsReward) {
        this.pointsReward = pointsReward;
    }

    public String getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(String taskAction) {
        this.taskAction = taskAction;
    }

    public Integer getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(Integer maxTimes) {
        this.maxTimes = maxTimes;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getTodayCompleteTimes() {
        return todayCompleteTimes;
    }

    public void setTodayCompleteTimes(Integer todayCompleteTimes) {
        this.todayCompleteTimes = todayCompleteTimes;
    }

    public Integer getTotalCompleteTimes() {
        return totalCompleteTimes;
    }

    public void setTotalCompleteTimes(Integer totalCompleteTimes) {
        this.totalCompleteTimes = totalCompleteTimes;
    }
}
