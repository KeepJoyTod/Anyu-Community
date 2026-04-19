package com.ruoyi.community.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityPaymentRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "账单ID")
    private Long billId;

    @Excel(name = "业主ID")
    private Long ownerId;

    @Excel(name = "业主姓名")
    private String ownerName;

    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    @Excel(name = "支付方式", readConverterExp = "1=微信,2=支付宝,3=银行卡,4=现金")
    private String payMethod;

    @Excel(name = "支付流水号")
    private String transactionNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    @Excel(name = "状态", readConverterExp = "0=待支付,1=支付成功,2=支付失败,3=已退款")
    private String status;

    @Excel(name = "小区ID")
    private Long communityId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBillId(Long billId) 
    {
        this.billId = billId;
    }

    public Long getBillId() 
    {
        return billId;
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

    public void setPayAmount(BigDecimal payAmount) 
    {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() 
    {
        return payAmount;
    }

    public void setPayMethod(String payMethod) 
    {
        this.payMethod = payMethod;
    }

    public String getPayMethod() 
    {
        return payMethod;
    }

    public void setTransactionNo(String transactionNo) 
    {
        this.transactionNo = transactionNo;
    }

    public String getTransactionNo() 
    {
        return transactionNo;
    }

    public void setPayTime(Date payTime) 
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billId", getBillId())
            .append("ownerId", getOwnerId())
            .append("ownerName", getOwnerName())
            .append("payAmount", getPayAmount())
            .append("payMethod", getPayMethod())
            .append("transactionNo", getTransactionNo())
            .append("payTime", getPayTime())
            .append("status", getStatus())
            .append("communityId", getCommunityId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
