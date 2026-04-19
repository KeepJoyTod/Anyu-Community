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
 * 电费表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_electricity_fee")
public class CommunityElectricityFee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电费ID（主键）
     */
    @TableId(value = "electricity_id", type = IdType.AUTO)
    private Long electricityId;

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
     * 上次抄表数（度）
     */
    private BigDecimal lastReading;

    /**
     * 本次抄表数（度）
     */
    private BigDecimal currentReading;

    /**
     * 剩余电量（度）
     */
    private Double surplusElectricity;

    /**
     * 实际用电量（度）
     */
    private BigDecimal usedAmount;

    /**
     * 电费单价（元/度）
     */
    private BigDecimal unitPrice;

    /**
     * 账户余额（元）
     */
    private BigDecimal balance;

    /**
     * 电费总额（元）
     */
    private BigDecimal totalFee;

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
