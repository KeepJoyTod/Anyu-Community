package com.ruoyi.web.controller.smartcommunity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.community.service.ICommunityUserService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Style;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物业信息表 前端控制器
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@RestController
@RequestMapping("/user")
public class CommunityPropertyController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ICommunityUserService communityUserService;
    //    查询我的页面用户信息
    @GetMapping("/user")
    public AjaxResult getUserByName(@RequestParam("username") String username) {
        System.err.println(username);
        SysUser user = sysUserService.getUserName(username);
        return AjaxResult.success(user);
    }

    //查询我的信息
    @GetMapping("/getByUsername")
    public AjaxResult getByUserName2(@RequestParam("username") String username) {
        SysUser sysuser = sysUserService.getByUserName2(username);
        return AjaxResult.success(sysuser);
    }
    //账户中心修改我的信息
    @PutMapping("updateAll")
    public AjaxResult updateAll(@RequestParam("username") String username , @RequestBody Map<String,String> params ){
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setPhonenumber(params.get("phonenumber"));
        String pwd = params.get("password");
        if (pwd != null && !pwd.isEmpty()) {
            sysUser.setPassword(pwd);
        }
        sysUserService.updateALLByName(sysUser);
        return AjaxResult.success();
    }
    @GetMapping("/house")
    public AjaxResult getMyHouse(@RequestParam("username") String username){
        String communityUser = communityUserService.getMyHouse(username);
        return AjaxResult.success(communityUser);

    }
}
