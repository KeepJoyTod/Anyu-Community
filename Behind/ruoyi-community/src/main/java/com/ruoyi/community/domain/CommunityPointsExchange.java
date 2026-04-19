package com.ruoyi.community.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class CommunityPointsExchange extends BaseEntity {
    private long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "用户姓名")
    private String userName;

    @Excel(name = "账单类型")
    private String billType;

    @Excel(name = "账单ID")
    private Long billId;

    @Excel(name = "使用积分")
    private Integer pointsUsed;

    @Excel(name = "抵扣金额")
    private BigDecimal amountOffset;

    @Excel(name = "兑换时间")
    private Date exchangeTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getBillType() { return billType; }
    public void setBillType(String billType) { this.billType = billType; }
    public Long getBillId() { return billId; }
    public void setBillId(Long billId) { this.billId = billId; }
    public Integer getPointsUsed() { return pointsUsed; }
    public void setPointsUsed(Integer pointsUsed) { this.pointsUsed = pointsUsed; }
    public BigDecimal getAmountOffset() { return amountOffset; }
    public void setAmountOffset(BigDecimal amountOffset) { this.amountOffset = amountOffset; }
    public Date getExchangeTime() { return exchangeTime; }
    public void setExchangeTime(Date exchangeTime) { this.exchangeTime = exchangeTime; }
}