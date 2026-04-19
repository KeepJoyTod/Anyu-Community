package com.ruoyi.community.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityRepairOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "单据ID")
    private Long orderId;

    @Excel(name = "关联维修ID")
    private Long repairId;

    @Excel(name = "报修人ID")
    private Long userId;

    @Excel(name = "报修人姓名")
    private String userName;

    @Excel(name = "报修人房号")
    private String userRoom;

    @Excel(name = "报修人电话")
    private String userPhone;

    @Excel(name = "报修类型", readConverterExp = "water=水电维修,furniture=家电维修,decoration=装修维修,other=其他")
    private String repairType;

    @Excel(name = "维修地址")
    private String repairAddress;

    @Excel(name = "故障描述")
    private String repairDesc;

    @Excel(name = "故障图片")
    private String repairImages;

    @Excel(name = "单据状态", readConverterExp = "pending=待派单,assigned=已派单,processing=维修中,completed=已完成,cancelled=已取消")
    private String orderStatus;

    private CommunityRepair repair;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setRepairId(Long repairId) 
    {
        this.repairId = repairId;
    }

    public Long getRepairId() 
    {
        return repairId;
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

    public void setUserPhone(String userPhone) 
    {
        this.userPhone = userPhone;
    }

    public String getUserPhone() 
    {
        return userPhone;
    }

    public void setRepairType(String repairType) 
    {
        this.repairType = repairType;
    }

    public String getRepairType() 
    {
        return repairType;
    }

    public void setRepairAddress(String repairAddress) 
    {
        this.repairAddress = repairAddress;
    }

    public String getRepairAddress() 
    {
        return repairAddress;
    }

    public void setRepairDesc(String repairDesc) 
    {
        this.repairDesc = repairDesc;
    }

    public String getRepairDesc() 
    {
        return repairDesc;
    }

    public void setRepairImages(String repairImages) 
    {
        this.repairImages = repairImages;
    }

    public String getRepairImages() 
    {
        return repairImages;
    }

    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }

    public CommunityRepair getRepair() 
    {
        return repair;
    }

    public void setRepair(CommunityRepair repair) 
    {
        this.repair = repair;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("repairId", getRepairId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userRoom", getUserRoom())
            .append("userPhone", getUserPhone())
            .append("repairType", getRepairType())
            .append("repairAddress", getRepairAddress())
            .append("repairDesc", getRepairDesc())
            .append("repairImages", getRepairImages())
            .append("orderStatus", getOrderStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
