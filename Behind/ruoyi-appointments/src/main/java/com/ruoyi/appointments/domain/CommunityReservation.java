package com.ruoyi.appointments.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小区预约管理对象 community_reservation
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public class CommunityReservation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 业主ID（关联sys_user表id） */
    @Excel(name = "业主ID", readConverterExp = "关=联sys_user表id")
    private Long userId;

    /** 业主姓名（冗余：查询频次高，不常修改） */
    @Excel(name = "业主姓名", readConverterExp = "冗=余：查询频次高，不常修改")
    private String userName;

    /** 业主手机号（冗余：查询频次高） */
    @Excel(name = "业主手机号", readConverterExp = "冗=余：查询频次高")
    private String userPhone;

    /** 预约类型（park:车位预约, repair:维修预约, gym:健身房预约） */
    @Excel(name = "预约类型", readConverterExp = "p=ark:车位预约,,r=epair:维修预约,,g=ym:健身房预约")
    private String reservationType;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reservationTime;

    /** 预约状态（pending:待处理, approved:已审核, rejected:已拒绝, completed:已完成, cancelled:已取消） */
    @Excel(name = "预约状态", readConverterExp = "p=ending:待处理,,a=pproved:已审核,,r=ejected:已拒绝,,c=ompleted:已完成,,c=ancelled:已取消")
    private String reservationStatus;

    /** 服务地址 */
    @Excel(name = "服务地址")
    private String serviceAddress;

    /** 拒绝原因 */
    @Excel(name = "拒绝原因")
    private String rejectReason;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setUserPhone(String userPhone) 
    {
        this.userPhone = userPhone;
    }

    public String getUserPhone() 
    {
        return userPhone;
    }

    public void setReservationType(String reservationType) 
    {
        this.reservationType = reservationType;
    }

    public String getReservationType() 
    {
        return reservationType;
    }

    public void setReservationTime(Date reservationTime) 
    {
        this.reservationTime = reservationTime;
    }

    public Date getReservationTime() 
    {
        return reservationTime;
    }

    public void setReservationStatus(String reservationStatus) 
    {
        this.reservationStatus = reservationStatus;
    }

    public String getReservationStatus() 
    {
        return reservationStatus;
    }

    public void setServiceAddress(String serviceAddress) 
    {
        this.serviceAddress = serviceAddress;
    }

    public String getServiceAddress() 
    {
        return serviceAddress;
    }

    public void setRejectReason(String rejectReason) 
    {
        this.rejectReason = rejectReason;
    }

    public String getRejectReason() 
    {
        return rejectReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userPhone", getUserPhone())
            .append("reservationType", getReservationType())
            .append("reservationTime", getReservationTime())
            .append("reservationStatus", getReservationStatus())
            .append("serviceAddress", getServiceAddress())
            .append("rejectReason", getRejectReason())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
