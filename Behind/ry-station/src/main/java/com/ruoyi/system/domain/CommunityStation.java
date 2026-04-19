package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小区驿站服务对象 community_station
 * 
 * @author dengdeng
 * @date 2026-03-18
 */
public class CommunityStation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 快递单号（唯一） */
    @Excel(name = "快递单号", readConverterExp = "唯=一")
    private String expressNo;

    /** 业主ID（关联sys_user表id） */
    @Excel(name = "业主ID", readConverterExp = "关=联sys_user表id")
    private Long userId;

    /** 业主姓名（冗余） */
    @Excel(name = "业主姓名", readConverterExp = "冗=余")
    private String userName;

    /** 业主手机号（冗余） */
    @Excel(name = "业主手机号", readConverterExp = "冗=余")
    private String userPhone;

    /** 业主房号（冗余） */
    @Excel(name = "业主房号", readConverterExp = "冗=余")
    private String userRoom;

    /** 快递公司（sf:顺丰, yto:圆通, zto:中通, sto:申通, yunda:韵达） */
    @Excel(name = "快递公司", readConverterExp = "s=f:顺丰,,y=to:圆通,,z=to:中通,,s=to:申通,,y=unda:韵达")
    private String expressCompany;

    /** 驿站状态（in_storage:已入库, picked:已取件, overdue:逾期未取） */
    @Excel(name = "驿站状态", readConverterExp = "i=n_storage:已入库,,p=icked:已取件,,o=verdue:逾期未取")
    private String stationStatus;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date storageTime;

    /** 取件时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "取件时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pickTime;

    /** 驿站操作员 */
    @Excel(name = "驿站操作员")
    private String operator;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setExpressNo(String expressNo) 
    {
        this.expressNo = expressNo;
    }

    public String getExpressNo() 
    {
        return expressNo;
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

    public void setUserRoom(String userRoom) 
    {
        this.userRoom = userRoom;
    }

    public String getUserRoom() 
    {
        return userRoom;
    }

    public void setExpressCompany(String expressCompany) 
    {
        this.expressCompany = expressCompany;
    }

    public String getExpressCompany() 
    {
        return expressCompany;
    }

    public void setStationStatus(String stationStatus) 
    {
        this.stationStatus = stationStatus;
    }

    public String getStationStatus() 
    {
        return stationStatus;
    }

    public void setStorageTime(Date storageTime) 
    {
        this.storageTime = storageTime;
    }

    public Date getStorageTime() 
    {
        return storageTime;
    }

    public void setPickTime(Date pickTime) 
    {
        this.pickTime = pickTime;
    }

    public Date getPickTime() 
    {
        return pickTime;
    }

    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("expressNo", getExpressNo())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userPhone", getUserPhone())
            .append("userRoom", getUserRoom())
            .append("expressCompany", getExpressCompany())
            .append("stationStatus", getStationStatus())
            .append("storageTime", getStorageTime())
            .append("pickTime", getPickTime())
            .append("operator", getOperator())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
