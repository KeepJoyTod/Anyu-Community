package com.ruoyi.community.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


public class SysCommunityActivity extends BaseEntity
{
    private  long serialVersionUID = 1L;

    /** 活动ID */
    private Long activityId;

    /** 活动标题 */
    @Excel(name = "活动标题")
    private String activityTitle;

    /** 活动类型 */
    @Excel(name = "活动类型")
    private String activityType;

    /** 活动内容 */
    @Excel(name = "活动内容")
    private String activityContent;

    /** 活动时间 */
    @Excel(name = "活动时间")
    private Date activityTime;

    /** 活动地点 */
    @Excel(name = "活动地点")
    private String activityLocation;

    /** 发布人ID */
    @Excel(name = "发布人ID")
    private Long publisherId;

    /** 发布人姓名 */
    @Excel(name = "发布人姓名")
    private String publisherName;

    /** 报名开始时间 */
    @Excel(name = "报名开始时间")
    private Date signupStartTime;

    /** 报名结束时间 */
    @Excel(name = "报名结束时间")
    private Date signupEndTime;

    /** 最大参与人数 */
    @Excel(name = "最大参与人数")
    private Integer maxParticipants;

    /** 当前参与人数 */
    @Excel(name = "当前参与人数")
    private Integer currentParticipants;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    // 报名人信息（前端传参用）
    private String userName;
    private String userPhone;

    // 完整GET/SET方法
    public Long getActivityId() {return activityId;}
    public void setActivityId(Long activityId) {this.activityId = activityId;}
    public String getActivityTitle() {return activityTitle;}
    public void setActivityTitle(String activityTitle) {this.activityTitle = activityTitle;}
    public String getActivityType() {return activityType;}
    public void setActivityType(String activityType) {this.activityType = activityType;}
    public String getActivityContent() {return activityContent;}
    public void setActivityContent(String activityContent) {this.activityContent = activityContent;}
    public Date getActivityTime() {return activityTime;}
    public void setActivityTime(Date activityTime) {this.activityTime = activityTime;}
    public String getActivityLocation() {return activityLocation;}
    public void setActivityLocation(String activityLocation) {this.activityLocation = activityLocation;}
    public Long getPublisherId() {return publisherId;}
    public void setPublisherId(Long publisherId) {this.publisherId = publisherId;}
    public String getPublisherName() {return publisherName;}
    public void setPublisherName(String publisherName) {this.publisherName = publisherName;}
    public Date getSignupStartTime() {return signupStartTime;}
    public void setSignupStartTime(Date signupStartTime) {this.signupStartTime = signupStartTime;}
    public Date getSignupEndTime() {return signupEndTime;}
    public void setSignupEndTime(Date signupEndTime) {this.signupEndTime = signupEndTime;}
    public Integer getMaxParticipants() {return maxParticipants;}
    public void setMaxParticipants(Integer maxParticipants) {this.maxParticipants = maxParticipants;}
    public Integer getCurrentParticipants() {return currentParticipants;}
    public void setCurrentParticipants(Integer currentParticipants) {this.currentParticipants = currentParticipants;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public String getRemark() {return remark;}
    public void setRemark(String remark) {this.remark = remark;}
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public String getUserPhone() {return userPhone;}
    public void setUserPhone(String userPhone) {this.userPhone = userPhone;}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("activityId", getActivityId())
                .append("activityTitle", getActivityTitle())
                .append("activityType", getActivityType())
                .append("activityContent", getActivityContent())
                .append("activityTime", getActivityTime())
                .append("activityLocation", getActivityLocation())
                .append("publisherId", getPublisherId())
                .append("publisherName", getPublisherName())
                .append("signupStartTime", getSignupStartTime())
                .append("signupEndTime", getSignupEndTime())
                .append("maxParticipants", getMaxParticipants())
                .append("currentParticipants", getCurrentParticipants())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}