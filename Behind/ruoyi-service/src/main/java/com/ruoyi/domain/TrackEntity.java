package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("package_track")
public class TrackEntity {
    private Long id;
    @TableField("package_id")
    private Long packageId;
    @TableField("time")
    private LocalDateTime trackTime;
    private String description;
}

