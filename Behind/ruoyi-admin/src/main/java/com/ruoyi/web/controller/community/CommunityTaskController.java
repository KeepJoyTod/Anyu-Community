package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.community.domain.CommunityTask;
import com.ruoyi.community.service.ICommunityTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 积分任务中心Controller
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
@RestController
@RequestMapping("/community/task")
public class CommunityTaskController extends BaseController
{
    @Autowired
    private ICommunityTaskService taskService;

    /**
     * 获取任务列表（带用户完成状态）
     */
    @GetMapping("/list")
    public AjaxResult list()
    {
        try {
            Long userId = SecurityUtils.getUserId();
            List<CommunityTask> tasks = taskService.getAllTasksWithStatus(userId);
            return AjaxResult.success(tasks);
        } catch (Exception e) {
            // 未登录返回空列表
            return AjaxResult.success("请先登录", null);
        }
    }

    /**
     * 获取分类任务列表
     */
    @GetMapping("/category")
    public AjaxResult category()
    {
        try {
            Long userId = SecurityUtils.getUserId();
            Map<String, List<CommunityTask>> tasks = taskService.getTasksByCategory(userId);
            return AjaxResult.success(tasks);
        } catch (Exception e) {
            return AjaxResult.success("请先登录", null);
        }
    }

    /**
     * 签到
     */
    @PostMapping("/signin")
    public AjaxResult signin()
    {
        try {
            Long userId = SecurityUtils.getUserId();
            Map<String, Object> result = taskService.signin(userId);
            
            if ((Boolean) result.get("success")) {
                return AjaxResult.success(result.get("msg").toString(), result);
            } else {
                return AjaxResult.error(result.get("msg").toString());
            }
        } catch (Exception e) {
            return AjaxResult.error("请先登录");
        }
    }

    /**
     * 检查今日是否已签到
     */
    @GetMapping("/hasSignin")
    public AjaxResult hasSignin()
    {
        try {
            Long userId = SecurityUtils.getUserId();
            boolean hasSignin = taskService.hasSigninToday(userId);
            int consecutiveDays = taskService.getConsecutiveDays(userId);
            
            Map<String, Object> result = Map.of(
                "hasSignin", hasSignin,
                "consecutiveDays", consecutiveDays
            );
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.success("请先登录", null);
        }
    }

    /**
     * 完成任务
     */
    @PostMapping("/complete/{taskAction}")
    public AjaxResult complete(@PathVariable String taskAction)
    {
        try {
            Long userId = SecurityUtils.getUserId();
            Map<String, Object> result = taskService.completeTask(userId, taskAction);
            
            if ((Boolean) result.get("success")) {
                return AjaxResult.success(result.get("msg").toString(), result);
            } else {
                return AjaxResult.error(result.get("msg").toString());
            }
        } catch (Exception e) {
            return AjaxResult.error("请先登录");
        }
    }
}
