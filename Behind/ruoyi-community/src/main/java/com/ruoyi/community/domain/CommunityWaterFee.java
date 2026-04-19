package com.ruoyi.community.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class CommunityWaterFee extends BaseEntity {
    private long serialVersionUID = 1L;

    private Long waterId;
    private Long userId;
    private String userName;
    private String userRoom;
    private String readMonth;
    private BigDecimal lastReading;
    private BigDecimal currentReading;
    private BigDecimal usedAmount;
    private BigDecimal unitPrice;
    private BigDecimal totalFee;
    private BigDecimal balance;
    private String feeStatus;
    private Date dueDate;
    private Date paymentTime;
    private String paymentNo;
    private BigDecimal overdueAmount;
    private String reader;
    private Date readTime;

    // getters and setters
    public Long getWaterId() { return waterId; }
    public void setWaterId(Long waterId) { this.waterId = waterId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserRoom() { return userRoom; }
    public void setUserRoom(String userRoom) { this.userRoom = userRoom; }
    public String getReadMonth() { return readMonth; }
    public void setReadMonth(String readMonth) { this.readMonth = readMonth; }
    public BigDecimal getLastReading() { return lastReading; }
    public void setLastReading(BigDecimal lastReading) { this.lastReading = lastReading; }
    public BigDecimal getCurrentReading() { return currentReading; }
    public void setCurrentReading(BigDecimal currentReading) { this.currentReading = currentReading; }
    public BigDecimal getUsedAmount() { return usedAmount; }
    public void setUsedAmount(BigDecimal usedAmount) { this.usedAmount = usedAmount; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getTotalFee() { return totalFee; }
    public void setTotalFee(BigDecimal totalFee) { this.totalFee = totalFee; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getFeeStatus() { return feeStatus; }
    public void setFeeStatus(String feeStatus) { this.feeStatus = feeStatus; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getPaymentTime() { return paymentTime; }
    public void setPaymentTime(Date paymentTime) { this.paymentTime = paymentTime; }
    public String getPaymentNo() { return paymentNo; }
    public void setPaymentNo(String paymentNo) { this.paymentNo = paymentNo; }
    public BigDecimal getOverdueAmount() { return overdueAmount; }
    public void setOverdueAmount(BigDecimal overdueAmount) { this.overdueAmount = overdueAmount; }
    public String getReader() { return reader; }
    public void setReader(String reader) { this.reader = reader; }
    public Date getReadTime() { return readTime; }
    public void setReadTime(Date readTime) { this.readTime = readTime; }
}