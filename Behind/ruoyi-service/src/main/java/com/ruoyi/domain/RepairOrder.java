package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepairOrder {
    @TableId(type = IdType.AUTO)
    private Long orderId;

    private Long repairId;
    private Long userId;
    private String userName;
    private String userRoom;
    private String userPhone;
    private String repairType;
    private String repairAddress;
    private String repairDesc;
    private String repairImages;
    private String orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;
}

