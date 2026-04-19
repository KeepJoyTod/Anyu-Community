package com.ruoyi.web.controller.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.service.community.DeepSeekChatService;

/**
 * 小区助手（DeepSeek），需登录后访问。
 */
@RestController
@RequestMapping("/home/assistant")
public class CommunityAssistantController extends BaseController
{
    @Autowired
    private DeepSeekChatService deepSeekChatService;

    /**
     * 对话：支持单条 message，或 messages 多轮（OpenAI 角色：user/assistant）
     */
    @PostMapping("/chat")
    public AjaxResult chat(@RequestBody JSONObject body)
    {
        JSONArray messages = body.getJSONArray("messages");
        if (messages != null && !messages.isEmpty())
        {
            List<JSONObject> list = messages.toJavaList(JSONObject.class);
            String reply = deepSeekChatService.chatMessages(list);
            Map<String, Object> data = new HashMap<>();
            data.put("reply", reply);
            return success(data);
        }

        String message = body.getString("message");
        if (StringUtils.isEmpty(message))
        {
            return error("消息不能为空");
        }
        String reply = deepSeekChatService.chat(message);
        Map<String, Object> data = new HashMap<>();
        data.put("reply", reply);
        return success(data);
    }
}
