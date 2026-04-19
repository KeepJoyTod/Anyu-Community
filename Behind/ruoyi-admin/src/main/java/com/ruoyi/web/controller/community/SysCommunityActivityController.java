package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.community.domain.SysCommunityActivity;
import com.ruoyi.community.mapper.SysCommunityActivityMapper;
import com.ruoyi.community.service.ICommunityPointsService;
import com.ruoyi.community.service.ISysCommunityActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community/activity")
public class SysCommunityActivityController extends BaseController {
    @Autowired
    private SysCommunityActivityMapper sysCommunityActivityMapper;
    @Autowired
    private ISysCommunityActivityService sysCommunityActivityService;
    @Autowired
    private ICommunityPointsService communityPointsService;

    // 报名接口：仅需活动ID + 人数+1 + 积分奖励
    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysCommunityActivity activity) {
        // 只校验活动ID是否为空
        if (activity.getActivityId() == null) {
            return AjaxResult.error("活动ID不能为空！");
        }
        
        try {
            // 获取当前登录用户ID
            Long userId = SecurityUtils.getUserId();
            
            // 调用Service执行人数+1
            boolean success = sysCommunityActivityService.addParticipantCount(activity.getActivityId());
            if (success) {
                // 为用户添加50积分
                communityPointsService.addPoints(userId, 50);
                return AjaxResult.success("报名成功！已获得50积分");
            } else {
                return AjaxResult.error("报名失败，活动不存在或名额已满！");
            }
        } catch (Exception e) {
            return AjaxResult.error("请先登录后再报名！");
        }
    }


    @GetMapping("/detail/{activityId}")
    public AjaxResult getActivityDetail(@PathVariable Long activityId) {
        SysCommunityActivity activity = sysCommunityActivityService.selectActivityById(activityId);
        return activity != null ? AjaxResult.success(activity) : AjaxResult.error("活动不存在！");
    }
    @GetMapping("/list")
    public AjaxResult list() {
        List<SysCommunityActivity> list = sysCommunityActivityService.selectAllActivity();
        return AjaxResult.success(list);
    }

    @PostMapping("/cancelRegister")
    public AjaxResult cancelRegister(@RequestBody Map<String, Object> params) {
        Long activityId = Long.parseLong(params.get("activityId").toString());

        try {
            // 获取当前登录用户ID
            Long userId = SecurityUtils.getUserId();
            
            // 人数 -1
            sysCommunityActivityService.cancelRegister(activityId);
            
            // 扣除用户50积分
            communityPointsService.deductPoints(userId, 50, "取消报名扣除积分");

            return AjaxResult.success("取消报名成功！已扣除50积分");
        } catch (Exception e) {
            return AjaxResult.error("请先登录后再操作！");
        }
    }
}