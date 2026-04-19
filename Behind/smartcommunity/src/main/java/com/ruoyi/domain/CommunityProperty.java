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
 * 物业信息表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Getter
@Setter
@ToString
@TableName("community_property")
public class CommunityProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物业ID（主键）
     */
    @TableId(value = "property_id", type = IdType.AUTO)
    private Long propertyId;

    /**
     * 物业名称
     */
    private String propertyName;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 物业办公地址
     */
    private String propertyAddress;

    /**
     * 服务范围
     */
    private String serviceScope;

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
