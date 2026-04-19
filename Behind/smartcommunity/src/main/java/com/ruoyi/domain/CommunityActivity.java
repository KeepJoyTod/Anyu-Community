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
 * 社区活动表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_activity")
public class CommunityActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动ID（主键）
     */
    @TableId(value = "activity_id", type = IdType.AUTO)
    private Long activityId;

    /**
     * 活动标题
     */
    private String activityTitle;

    /**
     * 活动分类（volunteer:志愿服务, good_deed:好人好事, daily_share:日常分享）
     */
    private String activityType;

    /**
     * 活动内容
     */
    private String activityContent;

    /**
     * 活动时间
     */
    private LocalDateTime activityTime;

    /**
     * 活动地点
     */
    private String activityLocation;

    /**
     * 发布人ID（关联community_user表id）
     */
    private Long publisherId;

    /**
     * 发布人姓名（冗余）
     */
    private String publisherName;

    /**
     * 报名开始时间
     */
    private LocalDateTime signupStartTime;

    /**
     * 报名结束时间
     */
    private LocalDateTime signupEndTime;

    /**
     * 最大参与人数（0表示无限制）
     */
    private Integer maxParticipants;

    /**
     * 当前报名人数
     */
    private Integer currentParticipants;

    /**
     * 活动状态（draft:草稿, published:已发布, ongoing:进行中, completed:已结束, cancelled:已取消）
     */
    private String status;

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
