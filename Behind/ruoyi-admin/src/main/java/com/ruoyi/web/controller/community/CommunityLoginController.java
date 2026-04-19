package com.ruoyi.web.controller.community;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysRegisterService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

@RestController("communityLoginController")
@RequestMapping("/home/auth")
public class CommunityLoginController extends BaseController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        if (StringUtils.isEmpty(loginBody.getUsername()))
        {
            return error("用户账号不能为空");
        }
        if (StringUtils.isEmpty(loginBody.getPassword()))
        {
            return error("密码不能为空");
        }
        if (StringUtils.isEmpty(loginBody.getCode()))
        {
            return error("验证码不能为空");
        }

        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(),
                loginBody.getCode(), loginBody.getUuid());
        LoginUser loginUser = tokenService.getLoginUserByToken(token);
        if (loginUser == null)
        {
            return error("登录失败，请重试");
        }

        SysUser user = loginUser.getUser();

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", user.getUserId());
        data.put("username", user.getUserName());
        data.put("nickname", user.getNickName());
        data.put("email", user.getEmail());
        data.put("phone", user.getPhonenumber());
        data.put("avatar", user.getAvatar());
        data.put("sex", user.getSex());

        return success(data);
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody Map<String, String> params)
    {
        String username = params.get("username");
        String password = params.get("password");
        String phonenumber = params.get("phonenumber");
        String nickName = params.get("nickName");
        String email = params.get("email");
        String code = params.get("code");
        String uuid = params.get("uuid");

        if (username == null || username.isEmpty())
        {
            return error("用户账号不能为空");
        }
        if (password == null || password.isEmpty())
        {
            return error("密码不能为空");
        }
        if (phonenumber == null || phonenumber.isEmpty())
        {
            return error("手机号码不能为空");
        }
        if (nickName == null || nickName.isEmpty())
        {
            return error("昵称不能为空");
        }
        if (code == null || code.isEmpty())
        {
            return error("验证码不能为空");
        }

        RegisterBody registerBody = new RegisterBody();
        registerBody.setUsername(username);
        registerBody.setPassword(password);
        registerBody.setCode(code);
        registerBody.setUuid(uuid);

        String msg = registerService.register(registerBody);
        
        if (StringUtils.isEmpty(msg))
        {
            SysUser sysUser = userService.selectUserByUserName(username);
            if (sysUser != null)
            {
                sysUser.setPhonenumber(phonenumber);
                sysUser.setNickName(nickName);
                if (StringUtils.isNotEmpty(email))
                {
                    sysUser.setEmail(email);
                }
                sysUser.setSex("0");
                sysUser.setStatus("0");
                userService.updateUser(sysUser);
            }
            
            return success("注册成功");
        }
        else
        {
            return error(msg);
        }
    }
}
