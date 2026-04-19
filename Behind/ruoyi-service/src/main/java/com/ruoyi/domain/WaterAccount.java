package com.ruoyi.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WaterAccount {
    private Long id;
    
    private Long userId;
    
    private BigDecimal balance;
    
    private BigDecimal waterAmount;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
