package com.ruoyi.web.controller.community;

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
import com.ruoyi.system.service.ISysUserService;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("communityLoginController")
@RequestMapping("/home/auth")
public class CommunityLoginController extends BaseController
{
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        if (loginBody == null)
        {
            return error("请求体不能为空");
        }
        if (!StringUtils.hasText(loginBody.getUsername()))
        {
            return error("用户名不能为空");
        }
        if (!StringUtils.hasText(loginBody.getPassword()))
        {
            return error("密码不能为空");
        }
        if (!StringUtils.hasText(loginBody.getCode()))
        {
            return error("验证码不能为空");
        }

        String token = loginService.login(
                StringUtils.trim(loginBody.getUsername()),
                loginBody.getPassword(),
                StringUtils.trim(loginBody.getCode()),
                StringUtils.trim(loginBody.getUuid()));
        LoginUser loginUser = tokenService.getLoginUserByToken(token);
        if (loginUser == null || loginUser.getUser() == null)
        {
            return error("登录失败，请重试");
        }

        SysUser user = loginUser.getUser();
        Map<String, Object> data = new LinkedHashMap<>();
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
    public AjaxResult register(@RequestBody CommunityRegisterRequest request)
    {
        if (request == null)
        {
            return error("请求体不能为空");
        }

        String username = StringUtils.trim(request.getUsername());
        String password = request.getPassword();
        String phone = StringUtils.trim(request.getPhonenumber());
        String nickName = StringUtils.trim(request.getNickName());
        String email = StringUtils.trim(request.getEmail());
        String code = StringUtils.trim(request.getCode());
        String uuid = StringUtils.trim(request.getUuid());

        if (!StringUtils.hasText(username))
        {
            return error("用户名不能为空");
        }
        if (!StringUtils.hasText(password))
        {
            return error("密码不能为空");
        }
        if (password.length() < 6 || password.length() > 20)
        {
            return error("密码长度需在 6 到 20 位之间");
        }
        if (!StringUtils.hasText(phone))
        {
            return error("手机号不能为空");
        }
        if (!PHONE_PATTERN.matcher(phone).matches())
        {
            return error("手机号格式不正确");
        }
        if (!StringUtils.hasText(nickName))
        {
            return error("昵称不能为空");
        }
        if (!StringUtils.hasText(code))
        {
            return error("验证码不能为空");
        }
        if (StringUtils.hasText(email) && !EMAIL_PATTERN.matcher(email).matches())
        {
            return error("邮箱格式不正确");
        }

        RegisterBody registerBody = new RegisterBody();
        registerBody.setUsername(username);
        registerBody.setPassword(password);
        registerBody.setCode(code);
        registerBody.setUuid(uuid);

        String registerMessage = registerService.register(registerBody);
        if (StringUtils.isNotEmpty(registerMessage))
        {
            return error(registerMessage);
        }

        SysUser sysUser = userService.selectUserByUserName(username);
        if (sysUser != null)
        {
            sysUser.setPhonenumber(phone);
            sysUser.setNickName(nickName);
            if (StringUtils.hasText(email))
            {
                sysUser.setEmail(email);
            }
            sysUser.setSex("0");
            sysUser.setStatus("0");
            sysUser.setUpdateBy(username);
            userService.updateUser(sysUser);
        }

        return success("注册成功");
    }

    public static class CommunityRegisterRequest extends RegisterBody
    {
        private String phonenumber;
        private String nickName;
        private String email;

        public String getPhonenumber()
        {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber)
        {
            this.phonenumber = phonenumber;
        }

        public String getNickName()
        {
            return nickName;
        }

        public void setNickName(String nickName)
        {
            this.nickName = nickName;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }
    }
}
