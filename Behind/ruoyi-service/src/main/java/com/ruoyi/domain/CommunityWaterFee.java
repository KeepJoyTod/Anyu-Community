package com.ruoyi.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CommunityWaterFee {
    private Long waterId;
    
    private Long userId;
    
    private String userName;
    
    private String userRoom;
    
    private String readMonth;
    
    private BigDecimal lastReading;
    
    private BigDecimal currentReading;
    
    private Double surplusWater;
    
    private BigDecimal usedAmount;
    
    private BigDecimal unitPrice;
    
    private BigDecimal totalFee;
    
    private BigDecimal balance;
    
    private String feeStatus;
    
    private LocalDateTime dueDate;
    
    private LocalDateTime paymentTime;
    
    private String paymentNo;
    
    private BigDecimal overdueAmount;
    
    private String reader;
    
    private LocalDateTime readTime;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    private String remark;
}