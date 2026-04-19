package com.ruoyi.web.controller.smartcommunity;

import com.ruoyi.common.R;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.CommunityProperty;
import com.ruoyi.domain.JavaCourseEnrollment;
import com.ruoyi.service.IJavaCourseEnrollmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import nl.basjes.parse.useragent.yauaa.shaded.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/signup")
public class JavaCourseEnrollmentController {
    @Autowired
    private IJavaCourseEnrollmentService javaCourseEnrollmentService;
    @PostMapping("/submit")
    public Map<String, Object> submit(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 接收前端参数并封装为实体
            JavaCourseEnrollment javaCourseEnrollment = new JavaCourseEnrollment();
            javaCourseEnrollment.setStudentName(params.get("name"));// 前端传 userName
            javaCourseEnrollment.setPhoneNumber(params.get("phone"));
            javaCourseEnrollment.setIntendedClass(params.get("classIndex"));
            // 前端传 className
            boolean success = javaCourseEnrollmentService.addJavaCourse(javaCourseEnrollment);

            if (success) {
                result.put("code", 200);
                result.put("msg", "报名成功！");
                result.put("data", true);
            } else {
                result.put("code", 500);
                result.put("msg", "报名失败");
                result.put("data", false);
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "系统异常：" + e.getMessage());
            result.put("data", false);
        }
        return result;
    }
}
