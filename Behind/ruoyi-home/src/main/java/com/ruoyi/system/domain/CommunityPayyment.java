package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小区缴费管理对象 community_payment
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public class CommunityPayyment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 业主ID（关联sys_user表id） */
    @Excel(name = "业主ID", readConverterExp = "关=联sys_user表id")
    private Long userId;

    /** 业主姓名（冗余） */
    @Excel(name = "业主姓名", readConverterExp = "冗=余")
    private String userName;

    /** 业主房号（冗余：查询频次高） */
    @Excel(name = "业主房号", readConverterExp = "冗=余：查询频次高")
    private String userRoom;

    /** 缴费类型（property:物业费, water:水费, electricity:电费, gas:燃气费, parking:车位费） */
    @Excel(name = "缴费类型", readConverterExp = "p=roperty:物业费,,w=ater:水费,,e=lectricity:电费,,g=as:燃气费,,p=arking:车位费")
    private String paymentType;

    /** 缴费金额 */
    @Excel(name = "缴费金额")
    private BigDecimal paymentAmount;

    /** 缴费状态（unpaid:未缴费, paid:已缴费, overdue:逾期） */
    @Excel(name = "缴费状态", readConverterExp = "u=npaid:未缴费,,p=aid:已缴费,,o=verdue:逾期")
    private String paymentStatus;

    /** 缴费订单号（唯一） */
    @Excel(name = "缴费订单号", readConverterExp = "唯=一")
    private String paymentNo;

    /** 缴费时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缴费时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;

    /** 逾期滞纳金 */
    @Excel(name = "逾期滞纳金")
    private BigDecimal overdueAmount;

    /** 缴费截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缴费截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueTime;

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

    public void setUserRoom(String userRoom) 
    {
        this.userRoom = userRoom;
    }

    public String getUserRoom() 
    {
        return userRoom;
    }

    public void setPaymentType(String paymentType) 
    {
        this.paymentType = paymentType;
    }

    public String getPaymentType() 
    {
        return paymentType;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() 
    {
        return paymentAmount;
    }

    public void setPaymentStatus(String paymentStatus) 
    {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() 
    {
        return paymentStatus;
    }

    public void setPaymentNo(String paymentNo) 
    {
        this.paymentNo = paymentNo;
    }

    public String getPaymentNo() 
    {
        return paymentNo;
    }

    public void setPaymentTime(Date paymentTime) 
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime() 
    {
        return paymentTime;
    }

    public void setOverdueAmount(BigDecimal overdueAmount) 
    {
        this.overdueAmount = overdueAmount;
    }

    public BigDecimal getOverdueAmount() 
    {
        return overdueAmount;
    }

    public void setDueTime(Date dueTime) 
    {
        this.dueTime = dueTime;
    }

    public Date getDueTime() 
    {
        return dueTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userRoom", getUserRoom())
            .append("paymentType", getPaymentType())
            .append("paymentAmount", getPaymentAmount())
            .append("paymentStatus", getPaymentStatus())
            .append("paymentNo", getPaymentNo())
            .append("paymentTime", getPaymentTime())
            .append("overdueAmount", getOverdueAmount())
            .append("dueTime", getDueTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
