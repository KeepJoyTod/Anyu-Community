package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("packages")
public class PackageEntity {
    private Long id;
    private String company;
    @TableField("express_icon")
    private String expressIcon;
    @TableField("pickup_code")
    private String pickupCode;
    @TableField("tracking_no")
    private String trackingNo;
    @TableField("arrival_time")
    private LocalDateTime arrivalTime;
    private Integer status;
}

