package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Java课程报名信息表
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("java_course_enrollment")
public class JavaCourseEnrollment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 意向班级
     */
    private String intendedClass;

    /**
     * 报名创建时间
     */
    private LocalDateTime createTime;

    /**
     * 信息更新时间
     */
    private LocalDateTime updateTime;
}
