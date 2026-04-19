package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 场地预约表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_space_reservation")
public class CommunitySpaceReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 预约ID（主键）
     */
    @TableId(value = "reservation_id", type = IdType.AUTO)
    private Long reservationId;

    /**
     * 场地名称（如：社区礼堂、健身房、会议室）
     */
    private String spaceName;

    /**
     * 预约人ID（关联community_user表id）
     */
    private Long userId;

    /**
     * 预约人姓名（冗余）
     */
    private String userName;

    /**
     * 预约人电话（冗余）
     */
    private String userPhone;

    /**
     * 预约日期
     */
    private LocalDate reserveDate;

    /**
     * 使用开始时间
     */
    private LocalTime startTime;

    /**
     * 使用结束时间
     */
    private LocalTime endTime;

    /**
     * 预约用途
     */
    private String reservationPurpose;

    /**
     * 预约状态（pending:待审核, approved:已通过, rejected:已拒绝, completed:已完成, cancelled:已取消）
     */
    private String reservationStatus;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 审核人ID（关联sys_user表id）
     */
    private Long approverId;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;

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
