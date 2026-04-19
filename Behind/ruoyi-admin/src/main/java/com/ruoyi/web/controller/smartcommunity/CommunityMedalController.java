package com.ruoyi.web.controller.smartcommunity;

import com.ruoyi.common.R;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.ICommunityMedalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 勋章表 前端控制器
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@RestController
@RequestMapping("/medal")
public class CommunityMedalController {
    @Autowired
    private ICommunityMedalService communityMedalService;

    @GetMapping("/hall")
    public AjaxResult myHall(@RequestParam("username") String username) {
Integer getMyHall = communityMedalService.getMyHallByName(username);
return AjaxResult.success(getMyHall);
    }
}
