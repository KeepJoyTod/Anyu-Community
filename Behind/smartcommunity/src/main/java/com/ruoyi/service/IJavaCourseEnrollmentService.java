package com.ruoyi.service;

import com.ruoyi.domain.JavaCourseEnrollment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * Java课程报名信息表 服务类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface IJavaCourseEnrollmentService extends IService<JavaCourseEnrollment> {
    boolean addJavaCourse(JavaCourseEnrollment javaCourseEnrollment);

}
