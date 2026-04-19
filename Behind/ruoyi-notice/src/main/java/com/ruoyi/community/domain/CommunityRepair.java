package com.ruoyi.community.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "维修ID")
    private Long repairId;

    @Excel(name = "维修人员ID")
    private Long repairmanId;

    @Excel(name = "维修人员姓名")
    private String repairmanName;

    @Excel(name = "维修人员电话")
    private String repairmanPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "派单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date assignTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "到达现场时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date arriveTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "维修完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    @Excel(name = "维修费用")
    private BigDecimal repairCost;

    @Excel(name = "费用说明")
    private String costDesc;

    @Excel(name = "维修结果")
    private String repairResult;

    @Excel(name = "业主评分")
    private Integer userRating;

    @Excel(name = "业主反馈")
    private String userFeedback;

    public void setRepairId(Long repairId) 
    {
        this.repairId = repairId;
    }

    public Long getRepairId() 
    {
        return repairId;
    }

    public void setRepairmanId(Long repairmanId) 
    {
        this.repairmanId = repairmanId;
    }

    public Long getRepairmanId() 
    {
        return repairmanId;
    }

    public void setRepairmanName(String repairmanName) 
    {
        this.repairmanName = repairmanName;
    }

    public String getRepairmanName() 
    {
        return repairmanName;
    }

    public void setRepairmanPhone(String repairmanPhone) 
    {
        this.repairmanPhone = repairmanPhone;
    }

    public String getRepairmanPhone() 
    {
        return repairmanPhone;
    }

    public void setAssignTime(Date assignTime) 
    {
        this.assignTime = assignTime;
    }

    public Date getAssignTime() 
    {
        return assignTime;
    }

    public void setArriveTime(Date arriveTime) 
    {
        this.arriveTime = arriveTime;
    }

    public Date getArriveTime() 
    {
        return arriveTime;
    }

    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() 
    {
        return completeTime;
    }

    public void setRepairCost(BigDecimal repairCost) 
    {
        this.repairCost = repairCost;
    }

    public BigDecimal getRepairCost() 
    {
        return repairCost;
    }

    public void setCostDesc(String costDesc) 
    {
        this.costDesc = costDesc;
    }

    public String getCostDesc() 
    {
        return costDesc;
    }

    public void setRepairResult(String repairResult) 
    {
        this.repairResult = repairResult;
    }

    public String getRepairResult() 
    {
        return repairResult;
    }

    public void setUserRating(Integer userRating) 
    {
        this.userRating = userRating;
    }

    public Integer getUserRating() 
    {
        return userRating;
    }

    public void setUserFeedback(String userFeedback) 
    {
        this.userFeedback = userFeedback;
    }

    public String getUserFeedback() 
    {
        return userFeedback;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("repairId", getRepairId())
            .append("repairmanId", getRepairmanId())
            .append("repairmanName", getRepairmanName())
            .append("repairmanPhone", getRepairmanPhone())
            .append("assignTime", getAssignTime())
            .append("arriveTime", getArriveTime())
            .append("completeTime", getCompleteTime())
            .append("repairCost", getRepairCost())
            .append("costDesc", getCostDesc())
            .append("repairResult", getRepairResult())
            .append("userRating", getUserRating())
            .append("userFeedback", getUserFeedback())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
