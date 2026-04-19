package com.ruoyi.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RepairDetail {
    private Long repairId; // 维修ID (主键)
    private Long repairmanId; // 维修人员ID (关联sys_user表id)
    private String repairmanName; // 维修人员姓名 (冗余)
    private String repairmanPhone; // 维修人员电话 (冗余)
    private LocalDateTime assignTime; // 派单时间
    private LocalDateTime arriveTime; // 到达现场时间
    private LocalDateTime completeTime; // 维修完成时间
    private BigDecimal repairCost; // 维修费用
    private String costDesc; // 费用说明
    private String repairResult; // 维修结果
    private Integer userRating; // 业主评分 (1-5分)
    private String userFeedback; // 业主反馈
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String remark; // 备注
}