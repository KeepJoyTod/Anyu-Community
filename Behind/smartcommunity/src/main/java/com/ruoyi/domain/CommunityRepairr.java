package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 维修表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_repair")
public class CommunityRepairr implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 维修ID（主键）
     */
    @TableId(value = "repair_id", type = IdType.AUTO)
    private Long repairId;

    /**
     * 维修人员ID（关联sys_user表id）
     */
    private Long repairmanId;

    /**
     * 维修人员姓名（冗余）
     */
    private String repairmanName;

    /**
     * 维修人员电话（冗余）
     */
    private String repairmanPhone;

    /**
     * 派单时间
     */
    private LocalDateTime assignTime;

    /**
     * 到达现场时间
     */
    private LocalDateTime arriveTime;

    /**
     * 维修完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 维修费用
     */
    private BigDecimal repairCost;

    /**
     * 费用说明
     */
    private String costDesc;

    /**
     * 维修结果
     */
    private String repairResult;

    /**
     * 业主评分（1-5分）
     */
    private Integer userRating;

    /**
     * 业主反馈
     */
    private String userFeedback;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}
