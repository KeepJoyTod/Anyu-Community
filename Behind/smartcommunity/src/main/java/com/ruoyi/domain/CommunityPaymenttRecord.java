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
 * 支付记录表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_payment_record")
public class CommunityPaymenttRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账单ID
     */
    private Long billId;

    /**
     * 业主ID
     */
    private Long ownerId;

    /**
     * 业主姓名
     */
    private String ownerName;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付方式（1:微信,2:支付宝,3:银行卡,4:现金）
     */
    private String payMethod;

    /**
     * 支付流水号
     */
    private String transactionNo;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 状态（0:待支付,1:支付成功,2:支付失败,3:已退款）
     */
    private String status;

    /**
     * 小区ID
     */
    private Long communityId;

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
