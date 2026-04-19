package com.ruoyi.community.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityVisitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "业主ID")
    private Long ownerId;

    @Excel(name = "业主姓名")
    private String ownerName;

    @Excel(name = "访客姓名")
    private String visitorName;

    @Excel(name = "访客电话")
    private String visitorPhone;

    @Excel(name = "访客身份证号")
    private String visitorIdCard;

    @Excel(name = "来访事由")
    private String visitReason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预计来访时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date expectedVisitTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际来访时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date actualVisitTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "离开时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date leaveTime;

    @Excel(name = "状态", readConverterExp = "0=待来访,1=已到访,2=已离开,3=已取消")
    private String status;

    @Excel(name = "邀请码")
    private String inviteCode;

    @Excel(name = "小区ID")
    private Long communityId;

    @Excel(name = "楼栋")
    private String building;

    @Excel(name = "单元")
    private String unit;

    @Excel(name = "房间号")
    private String room;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }

    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }

    public void setVisitorName(String visitorName) 
    {
        this.visitorName = visitorName;
    }

    public String getVisitorName() 
    {
        return visitorName;
    }

    public void setVisitorPhone(String visitorPhone) 
    {
        this.visitorPhone = visitorPhone;
    }

    public String getVisitorPhone() 
    {
        return visitorPhone;
    }

    public void setVisitorIdCard(String visitorIdCard) 
    {
        this.visitorIdCard = visitorIdCard;
    }

    public String getVisitorIdCard() 
    {
        return visitorIdCard;
    }

    public void setVisitReason(String visitReason) 
    {
        this.visitReason = visitReason;
    }

    public String getVisitReason() 
    {
        return visitReason;
    }

    public void setExpectedVisitTime(Date expectedVisitTime) 
    {
        this.expectedVisitTime = expectedVisitTime;
    }

    public Date getExpectedVisitTime() 
    {
        return expectedVisitTime;
    }

    public void setActualVisitTime(Date actualVisitTime) 
    {
        this.actualVisitTime = actualVisitTime;
    }

    public Date getActualVisitTime() 
    {
        return actualVisitTime;
    }

    public void setLeaveTime(Date leaveTime) 
    {
        this.leaveTime = leaveTime;
    }

    public Date getLeaveTime() 
    {
        return leaveTime;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setInviteCode(String inviteCode) 
    {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() 
    {
        return inviteCode;
    }

    public void setCommunityId(Long communityId) 
    {
        this.communityId = communityId;
    }

    public Long getCommunityId() 
    {
        return communityId;
    }

    public void setBuilding(String building) 
    {
        this.building = building;
    }

    public String getBuilding() 
    {
        return building;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public void setRoom(String room) 
    {
        this.room = room;
    }

    public String getRoom() 
    {
        return room;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ownerId", getOwnerId())
            .append("ownerName", getOwnerName())
            .append("visitorName", getVisitorName())
            .append("visitorPhone", getVisitorPhone())
            .append("visitorIdCard", getVisitorIdCard())
            .append("visitReason", getVisitReason())
            .append("expectedVisitTime", getExpectedVisitTime())
            .append("actualVisitTime", getActualVisitTime())
            .append("leaveTime", getLeaveTime())
            .append("status", getStatus())
            .append("inviteCode", getInviteCode())
            .append("communityId", getCommunityId())
            .append("building", getBuilding())
            .append("unit", getUnit())
            .append("room", getRoom())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
