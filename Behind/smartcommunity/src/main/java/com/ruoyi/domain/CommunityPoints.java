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
 * 积分表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_points")
public class CommunityPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 积分ID（主键）
     */
    @TableId(value = "points_id", type = IdType.AUTO)
    private Long pointsId;

    /**
     * 业主ID（关联community_user表id）
     */
    private Long userId;

    /**
     * 总积分
     */
    private Integer totalPoints;

    /**
     * 可用积分
     */
    private Integer availablePoints;

    /**
     * 冻结积分
     */
    private Integer frozenPoints;

    /**
     * 关联勋章ID（关联community_medal表medal_id）
     */
    private Long medalId;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;
}
