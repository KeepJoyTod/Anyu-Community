package com.ruoyi.web.controller.smartcommunity;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.domain.CommunityUser;
import com.ruoyi.service.ICommunityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

// @RestController("communityLoginController")  // disabled to avoid duplicate bean conflict
@RequestMapping("/home/auth")
@Anonymous
public class CommunityLoginController extends BaseController
{
    @Autowired
    private ICommunityUserService communityUserService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String, String> params)
    {
        String phone = params.get("phone");
        String password = params.get("password");

        if (phone == null || phone.isEmpty())
        {
            return error("手机号码不能为空");
        }
        if (password == null || password.isEmpty())
        {
            return error("密码不能为空");
        }

        CommunityUser user = communityUserService.selectCommunityUserByPhone(phone);
        if (user == null)
        {
            return error("用户不存在");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty())
        {
            return error("请先设置密码");
        }

        if (!password.equals(user.getPassword()))
        {
            return error("密码错误");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("phone", user.getPhone());
        data.put("name", user.getName());
        data.put("specificAddress", user.getSpecificAddress());

        return success(data);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody CommunityUser user)
    {
        if (user.getPhone() == null || user.getPhone().isEmpty())
        {
            return error("手机号码不能为空");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty())
        {
            return error("密码不能为空");
        }

        CommunityUser existUser = communityUserService.selectCommunityUserByPhone(user.getPhone());
        if (existUser != null)
        {
            return error("该手机号已注册");
        }

        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));

        return success("注册成功");
    }
}
