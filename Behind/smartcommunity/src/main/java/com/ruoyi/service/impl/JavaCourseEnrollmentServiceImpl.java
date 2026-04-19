package com.ruoyi.service.impl;

import com.ruoyi.domain.JavaCourseEnrollment;
import com.ruoyi.mapper.JavaCourseEnrollmentMapper;
import com.ruoyi.service.IJavaCourseEnrollmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Java课程报名信息表 服务实现类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Service
public class JavaCourseEnrollmentServiceImpl extends ServiceImpl<JavaCourseEnrollmentMapper, JavaCourseEnrollment> implements IJavaCourseEnrollmentService {
@Autowired
private JavaCourseEnrollmentMapper javaCourseEnrollmentMapper;
    @Override
    public boolean addJavaCourse(JavaCourseEnrollment javaCourseEnrollment) {
        int rows =javaCourseEnrollmentMapper.insertJavaClass(javaCourseEnrollment);
        return rows > 0;
    }
}

