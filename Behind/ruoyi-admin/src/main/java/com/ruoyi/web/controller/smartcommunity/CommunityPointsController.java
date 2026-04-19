package com.ruoyi.web.controller.smartcommunity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.R;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.domain.CommunityPoints;
import com.ruoyi.service.ICommunityPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 积分表 前端控制器
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@RestController
@RequestMapping("/point")
public class CommunityPointsController {
    @Autowired
    private ICommunityPointsService communityPointsService;

    @GetMapping("/myPoints")
    public AjaxResult myPoints(@RequestParam("username") String username) {
       Integer getMyPoints = communityPointsService.getMyPointsByName(username);
        System.err.println(getMyPoints);
        return AjaxResult.success(getMyPoints);
    }
}
