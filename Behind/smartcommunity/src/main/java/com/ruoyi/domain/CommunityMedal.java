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
 * 勋章表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_medal")
public class CommunityMedal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 勋章ID（主键）
     */
    @TableId(value = "medal_id", type = IdType.AUTO)
    private Long medalId;

    /**
     * 勋章名称（如：爱心志愿者、社区榜样）
     */
    private String medalName;

    /**
     * 勋章描述
     */
    private String medalDesc;

    /**
     * 获取所需积分
     */
    private Integer requiredPoints;

    /**
     * 勋章图标地址
     */
    private String medalIcon;

    /**
     * 勋章等级（1-5级）
     */
    private Integer medalLevel;

    /**
     * 勋章状态（0：停用，1：启用）
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
