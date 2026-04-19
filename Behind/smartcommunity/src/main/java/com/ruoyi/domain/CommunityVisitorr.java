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
 * 访客邀请表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_visitor")
public class CommunityVisitorr implements Serializable {

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
     * 访客姓名
     */
    private String visitorName;

    /**
     * 访客电话
     */
    private String visitorPhone;

    /**
     * 访客身份证号
     */
    private String visitorIdCard;

    /**
     * 来访事由
     */
    private String visitReason;

    /**
     * 预计来访时间
     */
    private LocalDateTime expectedVisitTime;

    /**
     * 实际来访时间
     */
    private LocalDateTime actualVisitTime;

    /**
     * 离开时间
     */
    private LocalDateTime leaveTime;

    /**
     * 状态（0:待来访,1:已到访,2:已离开,3:已取消）
     */
    private String status;

    /**
     * 邀请码
     */
    private String inviteCode;

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
