package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.community.domain.CommunityPoints;
import com.ruoyi.community.service.ICommunityPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序端积分控制器
 * 使用CommunityUser的ID查询积分
 */
@RestController("communityPointsController2")
@RequestMapping("/home/points")
public class CommunityPointsController2 extends BaseController {


    @Autowired
    private ICommunityPointsService pointsService;

    /**
     * 根据用户ID获取积分信息
     * @param userId 社区用户ID
     * @return 积分信息
     */
    @GetMapping("/getPoints/{userId}")
    public AjaxResult getPoints(@PathVariable("userId") Long userId) {
        CommunityPoints points = pointsService.getPointsByUserId(userId);
        Map<String, Object> data = new HashMap<>();
        if (points != null) {
            data.put("totalPoints", points.getTotalPoints());
            data.put("availablePoints", points.getAvailablePoints());
            data.put("frozenPoints", points.getFrozenPoints());
            data.put("userId", points.getUserId());
        } else {
            data.put("totalPoints", 0);
            data.put("availablePoints", 0);
            data.put("frozenPoints", 0);
            data.put("userId", userId);
        }
        return success(data);
    }

    /**
     * 更新用户积分（增加积分）
     * @param userId 用户ID
     * @param param 包含points字段
     * @return 结果
     */
    @PostMapping("/addPoints/{userId}")
    public AjaxResult addPoints(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> param) {
        Integer points = Integer.valueOf(param.get("points").toString());
        boolean success = pointsService.addPoints(userId, points);
        if (success) {
            return success("积分增加成功");
        } else {
            return error("积分增加失败");
        }
    }

    /**
     * 扣除用户积分
     * @param userId 用户ID
     * @param param 包含points字段
     * @return 结果
     */
    @PostMapping("/deductPoints/{userId}")
    public AjaxResult deductPoints(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> param) {
        Integer points = Integer.valueOf(param.get("points").toString());
        String remark = param.get("remark") != null ? param.get("remark").toString() : "";
        boolean success = pointsService.deductPoints(userId, points, remark);
        if (success) {
            return success("积分扣除成功");
        } else {
            return error("积分扣除失败，可能积分不足");
        }
    }
    /**
     * 添加积分
     */
    @PostMapping("/add")
    public AjaxResult addPoints(@RequestBody Map<String, Object> params) {
        try {
            Long userId = SecurityUtils.getUserId();
            Integer points = (Integer) params.get("points");
            String reason = (String) params.get("reason");

            if (points == null || points <= 0) {
                return AjaxResult.error("积分数量必须大于0");
            }

            boolean success = pointsService.addPoints(userId, points, reason);
            if (success) {
                return AjaxResult.success("积分添加成功");
            } else {
                return AjaxResult.error("积分添加失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("请先登录");
        }
    }
}
