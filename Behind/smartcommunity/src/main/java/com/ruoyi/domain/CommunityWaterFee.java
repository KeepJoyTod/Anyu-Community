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
 * 水费表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_water_fee")
public class CommunityWaterFee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 水费ID（主键）
     */
    @TableId(value = "water_id", type = IdType.AUTO)
    private Long waterId;

    /**
     * 业主ID（关联community_user表id）
     */
    private Long userId;

    /**
     * 业主姓名（冗余）
     */
    private String userName;

    /**
     * 业主房号（冗余）
     */
    private String userRoom;

    /**
     * 计费月份（格式：YYYY-MM）
     */
    private String readMonth;

    /**
     * 上次抄表数（吨）
     */
    private BigDecimal lastReading;

    /**
     * 本次抄表数（吨）
     */
    private BigDecimal currentReading;

    /**
     * 剩余水量（吨）
     */
    private Double surplusWater;

    /**
     * 实际用水量（吨）
     */
    private BigDecimal usedAmount;

    /**
     * 水费单价（元/吨）
     */
    private BigDecimal unitPrice;

    /**
     * 水费总额（元）
     */
    private BigDecimal totalFee;

    /**
     * 账户余额（元）
     */
    private BigDecimal balance;

    /**
     * 缴费状态（unpaid:未缴费, paid:已缴费, overdue:逾期）
     */
    private String feeStatus;

    /**
     * 缴费截止日期
     */
    private LocalDateTime dueDate;

    /**
     * 缴费时间
     */
    private LocalDateTime paymentTime;

    /**
     * 缴费订单号
     */
    private String paymentNo;

    /**
     * 逾期滞纳金（元）
     */
    private BigDecimal overdueAmount;

    /**
     * 抄表员
     */
    private String reader;

    /**
     * 抄表时间
     */
    private LocalDateTime readTime;

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
