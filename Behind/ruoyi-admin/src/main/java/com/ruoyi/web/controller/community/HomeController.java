package com.ruoyi.web.controller.community;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jakarta.annotation.Resource;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.Producer;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.sign.Base64;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.service.ISysConfigService;

@RestController("homeController")
@RequestMapping("/home")
public class HomeController extends BaseController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ISysConfigService configService;

    @GetMapping("/focus")
    public AjaxResult getFocus()
    {
        Map<String, Object> focus = new HashMap<>();
        focus.put("title", "今日焦点");
        focus.put("content", "2026年度文明社区评选展示 - 共建美好家园");
        return success(focus);
    }

    @GetMapping("/data")
    public AjaxResult getHomeData()
    {
        Map<String, Object> data = new HashMap<>();
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("greeting", "早上好");
        userInfo.put("nickname", "智慧业主");
        userInfo.put("communityName", "阳光家园社区");
        data.put("userInfo", userInfo);
        
        return success(data);
    }

    @GetMapping("/captchaImage")
    public AjaxResult getCode() throws IOException
    {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return ajax;
        }

        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String captchaType = RuoYiConfig.getCaptchaType();
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    @GetMapping("/captchaText")
    public AjaxResult getTextCaptcha()
    {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return ajax;
        }

        // 生成4位数字验证码
        String code = String.format("%04d", (int)(Math.random() * 10000));
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        
        ajax.put("uuid", uuid);
        ajax.put("code", code);
        ajax.put("type", "text");
        return ajax;
    }

    @PostMapping("/sendEmailCaptcha")
    public AjaxResult sendEmailCaptcha(@RequestParam String email)
    {
        AjaxResult ajax = AjaxResult.success();
        
        // 生成6位数字验证码
        String code = String.format("%06d", (int)(Math.random() * 1000000));
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        
        // 存储验证码到Redis
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        
        // 这里应该调用邮件服务发送验证码，现在只是模拟
        // emailService.sendCaptchaEmail(email, code);
        System.out.println("发送邮件验证码到 " + email + ": " + code);
        
        ajax.put("uuid", uuid);
        ajax.put("message", "验证码已发送到邮箱");
        ajax.put("type", "email");
        return ajax;
    }

    @PostMapping("/sendSmsCaptcha")
    public AjaxResult sendSmsCaptcha(@RequestParam String phone)
    {
        AjaxResult ajax = AjaxResult.success();
        
        // 生成6位数字验证码
        String code = String.format("%06d", (int)(Math.random() * 1000000));
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        
        // 存储验证码到Redis
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        
        // 这里应该调用短信服务发送验证码，现在只是模拟
        // smsService.sendCaptchaSms(phone, code);
        System.out.println("发送短信验证码到 " + phone + ": " + code);
        
        ajax.put("uuid", uuid);
        ajax.put("message", "验证码已发送到手机");
        ajax.put("type", "sms");
        return ajax;
    }

    @GetMapping("/questionCaptcha")
    public AjaxResult getQuestionCaptcha(@RequestParam(required = false) String username)
    {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return ajax;
        }

        // 基于用户名生成简单问题
        String[] questions = {
            "1+1=?",
            "2+2=?", 
            "3+3=?",
            "5-2=?",
            "10-5=?"
        };
        
        String[] answers = {"2", "4", "6", "3", "5"};
        
        int index = (username != null ? Math.abs(username.hashCode()) : 0) % questions.length;
        String question = questions[index];
        String answer = answers[index];
        
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        
        redisCache.setCacheObject(verifyKey, answer, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        
        ajax.put("uuid", uuid);
        ajax.put("question", question);
        ajax.put("type", "question");
        return ajax;
    }
}
