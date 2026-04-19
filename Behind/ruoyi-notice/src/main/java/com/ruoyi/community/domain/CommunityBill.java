package com.ruoyi.community.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityBill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "业主ID")
    private Long ownerId;

    @Excel(name = "业主姓名")
    private String ownerName;

    @Excel(name = "账单类型", readConverterExp = "1=物业费,2=水费,3=电费,4=燃气费,5=停车费,6=其他")
    private String billType;

    @Excel(name = "账单编号")
    private String billNo;

    @Excel(name = "账单金额")
    private BigDecimal billAmount;

    @Excel(name = "已付金额")
    private BigDecimal paidAmount;

    @Excel(name = "欠费金额")
    private BigDecimal arrearsAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "账单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缴费截止日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    @Excel(name = "状态", readConverterExp = "0=未支付,1=部分支付,2=已支付")
    private String status;

    @Excel(name = "小区ID")
    private Long communityId;

    @Excel(name = "楼栋")
    private String building;

    @Excel(name = "单元")
    private String unit;

    @Excel(name = "房间号")
    private String room;

    @Excel(name = "用量")
    private BigDecimal usageAmount;

    @Excel(name = "单价")
    private BigDecimal unitPrice;

    @Excel(name = "上期读数")
    private BigDecimal lastReading;

    @Excel(name = "本期读数")
    private BigDecimal currentReading;

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

    public void setBillType(String billType) 
    {
        this.billType = billType;
    }

    public String getBillType() 
    {
        return billType;
    }

    public void setBillNo(String billNo) 
    {
        this.billNo = billNo;
    }

    public String getBillNo() 
    {
        return billNo;
    }

    public void setBillAmount(BigDecimal billAmount) 
    {
        this.billAmount = billAmount;
    }

    public BigDecimal getBillAmount() 
    {
        return billAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) 
    {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getPaidAmount() 
    {
        return paidAmount;
    }

    public void setArrearsAmount(BigDecimal arrearsAmount) 
    {
        this.arrearsAmount = arrearsAmount;
    }

    public BigDecimal getArrearsAmount() 
    {
        return arrearsAmount;
    }

    public void setBillDate(Date billDate) 
    {
        this.billDate = billDate;
    }

    public Date getBillDate() 
    {
        return billDate;
    }

    public void setDueDate(Date dueDate) 
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate() 
    {
        return dueDate;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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

    public void setUsageAmount(BigDecimal usageAmount) 
    {
        this.usageAmount = usageAmount;
    }

    public BigDecimal getUsageAmount() 
    {
        return usageAmount;
    }

    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }

    public void setLastReading(BigDecimal lastReading) 
    {
        this.lastReading = lastReading;
    }

    public BigDecimal getLastReading() 
    {
        return lastReading;
    }

    public void setCurrentReading(BigDecimal currentReading) 
    {
        this.currentReading = currentReading;
    }

    public BigDecimal getCurrentReading() 
    {
        return currentReading;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ownerId", getOwnerId())
            .append("ownerName", getOwnerName())
            .append("billType", getBillType())
            .append("billNo", getBillNo())
            .append("billAmount", getBillAmount())
            .append("paidAmount", getPaidAmount())
            .append("arrearsAmount", getArrearsAmount())
            .append("billDate", getBillDate())
            .append("dueDate", getDueDate())
            .append("status", getStatus())
            .append("communityId", getCommunityId())
            .append("building", getBuilding())
            .append("unit", getUnit())
            .append("room", getRoom())
            .append("usageAmount", getUsageAmount())
            .append("unitPrice", getUnitPrice())
            .append("lastReading", getLastReading())
            .append("currentReading", getCurrentReading())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
