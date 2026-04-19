package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 报修单据表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_repair_order")
public class CommunityRepairrOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报修单ID
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 维修ID
     */
    private Long repairId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户房间
     */
    private String userRoom;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 报修类型
     */
    private String repairType;

    /**
     * 报修地址
     */
    private String repairAddress;

    /**
     * 问题描述
     */
    private String repairDesc;

    /**
     * 报修图片
     */
    private String repairImages;

    /**
     * 订单状态
     */
    private String orderStatus;

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
