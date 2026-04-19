package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.domain.JavaCourseEnrollment;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 * Java课程报名信息表 Mapper 接口
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface JavaCourseEnrollmentMapper extends BaseMapper<JavaCourseEnrollment> {

    @Insert("INSERT INTO java_course_enrollment( student_name, phone_number, intended_class)" +
            "VALUES(#{studentName}, #{phoneNumber}, #{intendedClass})")
    int insertJavaClass(JavaCourseEnrollment javaCourseEnrollment);
}
