package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 生活缴费账单表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_bill")
public class CommunityBilll implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业主ID
     */
    private Long ownerId;

    /**
     * 业主姓名
     */
    private String ownerName;

    /**
     * 账单类型（1:物业费,2:水费,3:电费,4:燃气费,5:停车费,6:其他）
     */
    private String billType;

    /**
     * 账单编号
     */
    private String billNo;

    /**
     * 账单金额
     */
    private BigDecimal billAmount;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 欠费金额
     */
    private BigDecimal arrearsAmount;

    /**
     * 账单日期
     */
    private LocalDate billDate;

    /**
     * 缴费截止日期
     */
    private LocalDate dueDate;

    /**
     * 状态（0:未支付,1:部分支付,2:已支付）
     */
    private String status;

    /**
     * 小区ID
     */
    private Long communityId;

    /**
     * 楼栋
     */
    private String building;

    /**
     * 单元
     */
    private String unit;

    /**
     * 房间号
     */
    private String room;

    /**
     * 用量（水电气等）
     */
    private BigDecimal usageAmount;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 上期读数
     */
    private BigDecimal lastReading;

    /**
     * 本期读数
     */
    private BigDecimal currentReading;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}
