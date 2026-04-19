package com.ruoyi.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ElectricityWaterAccount {
    private Long id;
    
    private Long userId;
    
    // 电费相关字段
    private BigDecimal balance;
    private BigDecimal electricityAmount;
    
    // 水费相关字段
    private BigDecimal waterBalance;
    private BigDecimal waterAmount;
    
    // 充值记录相关字段
    private BigDecimal amount;
    private BigDecimal electricityAdded;
    private BigDecimal waterFeeAmount;
    private BigDecimal waterAdded;
    private String payMethod;
    private Integer status; // 0-pending, 1-success, 2-fail
    
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}