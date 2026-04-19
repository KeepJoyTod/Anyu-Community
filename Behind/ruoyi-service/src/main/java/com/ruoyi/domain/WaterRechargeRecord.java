package com.ruoyi.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WaterRechargeRecord {
    private Long id;
    
    private Long userId;
    
    private BigDecimal amount;
    
    private BigDecimal waterAdded;
    
    private String payMethod;
    
    private Integer status; // 0-pending, 1-success, 2-fail
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
