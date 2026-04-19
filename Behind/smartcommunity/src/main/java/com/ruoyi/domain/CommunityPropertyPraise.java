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
 * 物业表扬表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_property_praise")
public class CommunityPropertyPraise implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表扬ID（主键）
     */
    @TableId(value = "praise_id", type = IdType.AUTO)
    private Long praiseId;

    /**
     * 关联物业ID（关联community_property表property_id）
     */
    private Long propertyId;

    /**
     * 表扬人ID（关联community_user表id）
     */
    private Long userId;

    /**
     * 表扬人姓名（冗余）
     */
    private String userName;

    /**
     * 表扬标题
     */
    private String praiseTitle;

    /**
     * 表扬内容
     */
    private String praiseContent;

    /**
     * 表扬时间
     */
    private LocalDateTime praiseTime;

    /**
     * 是否公开（0：否，1：是）
     */
    private String isPublic;

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
