package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.community.domain.CommunitySurvey;
import com.ruoyi.community.service.ICommunitySurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 满意度调查Controller
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
@RestController
@RequestMapping("/community/survey")
public class CommunitySurveyController
{
    @Autowired
    private ICommunitySurveyService communitySurveyService;

    /**
     * 提交满意度调查
     */
    @PostMapping("/submit")
    public AjaxResult submitSurvey(@RequestBody CommunitySurvey communitySurvey)
    {
        try {
            // 设置用户ID
            Long userId = SecurityUtils.getUserId();
            communitySurvey.setUserId(userId);

            // 保存满意度调查
            int result = communitySurveyService.insertCommunitySurvey(communitySurvey);
            if (result > 0) {
                return AjaxResult.success("提交成功");
            } else {
                return AjaxResult.error("提交失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("提交失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户的满意度调查
     */
    @PostMapping("/user")
    public AjaxResult getUserSurvey()
    {
        try {
            Long userId = SecurityUtils.getUserId();
            CommunitySurvey communitySurvey = new CommunitySurvey();
            communitySurvey.setUserId(userId);
            return AjaxResult.success(communitySurveyService.selectCommunitySurveyList(communitySurvey));
        } catch (Exception e) {
            return AjaxResult.error("查询失败: " + e.getMessage());
        }
    }
}
