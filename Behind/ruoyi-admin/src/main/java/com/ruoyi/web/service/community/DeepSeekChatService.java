package com.ruoyi.web.service.community;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;

/**
 * DeepSeek 对话（小区助手），密钥仅配置在服务端。
 */
@Service
public class DeepSeekChatService
{
    private static final Logger log = LoggerFactory.getLogger(DeepSeekChatService.class);

    private static final String CHAT_PATH = "/v1/chat/completions";

    private static final String SYSTEM_PROMPT = "你是智慧小区「阳光家园」的物业助手。请使用简体中文回答，语气友好、简洁、分点说明（必要时）。"
            + "若用户询问报修、缴费、访客邀请、手机开门、联系物业等，请结合常见办理步骤说明，并提示可在本小程序首页或服务页找到对应入口。";

    @Value("${ai.deepseek.api-key:}")
    private String apiKey;

    @Value("${ai.deepseek.base-url:https://api.deepseek.com}")
    private String baseUrl;

    @Value("${ai.deepseek.model:deepseek-chat}")
    private String model;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    /**
     * 单轮对话
     */
    public String chat(String userMessage)
    {
        if (StringUtils.isEmpty(apiKey))
        {
            throw new ServiceException("未配置 DeepSeek：请在服务端设置环境变量 DEEPSEEK_API_KEY（注意是下划线，不要空格），或在 application.yml 中配置 deepseek.api-key");
        }
        String trimmed = StringUtils.trim(userMessage);
        if (StringUtils.isEmpty(trimmed))
        {
            throw new ServiceException("消息不能为空");
        }
        if (trimmed.length() > 4000)
        {
            trimmed = trimmed.substring(0, 4000);
        }

        JSONObject body = new JSONObject();
        body.put("model", model);
        body.put("temperature", 0.6);
        body.put("max_tokens", 1024);

        JSONArray messages = new JSONArray();
        messages.add(new JSONObject().fluentPut("role", "system").fluentPut("content", SYSTEM_PROMPT));
        messages.add(new JSONObject().fluentPut("role", "user").fluentPut("content", trimmed));
        body.put("messages", messages);

        String url = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) + CHAT_PATH : baseUrl + CHAT_PATH;
        String json = body.toJSONString();

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(90))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String rspBody = response.body();
            if (response.statusCode() < 200 || response.statusCode() >= 300)
            {
                log.warn("DeepSeek HTTP {} body: {}", response.statusCode(), rspBody);
                throw new ServiceException(parseErrorMessage(rspBody, "DeepSeek 请求失败"));
            }

            JSONObject root = JSON.parseObject(rspBody);
            if (root == null)
            {
                throw new ServiceException("DeepSeek 返回为空");
            }
            if (root.containsKey("error"))
            {
                JSONObject err = root.getJSONObject("error");
                String msg = err != null ? err.getString("message") : "未知错误";
                throw new ServiceException(StringUtils.isNotEmpty(msg) ? msg : "DeepSeek 返回错误");
            }

            JSONArray choices = root.getJSONArray("choices");
            if (choices == null || choices.isEmpty())
            {
                throw new ServiceException("DeepSeek 未返回内容");
            }
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            String content = message != null ? message.getString("content") : null;
            if (StringUtils.isEmpty(content))
            {
                throw new ServiceException("DeepSeek 回复为空");
            }
            return content.trim();
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error("调用 DeepSeek 失败", e);
            throw new ServiceException("调用智能助手失败，请稍后重试");
        }
    }

    /**
     * 多轮对话（OpenAI 格式 role: user/assistant/system）
     */
    public String chatMessages(List<JSONObject> clientMessages)
    {
        if (StringUtils.isEmpty(apiKey))
        {
            throw new ServiceException("未配置 DeepSeek：请在服务端设置环境变量 DEEPSEEK_API_KEY（注意是下划线，不要空格），或在 application.yml 中配置 deepseek.api-key");
        }
        if (clientMessages == null || clientMessages.isEmpty())
        {
            throw new ServiceException("消息不能为空");
        }

        List<JSONObject> capped = new ArrayList<>();
        int start = Math.max(0, clientMessages.size() - 12);
        for (int i = start; i < clientMessages.size(); i++)
        {
            capped.add(clientMessages.get(i));
        }

        JSONObject body = new JSONObject();
        body.put("model", model);
        body.put("temperature", 0.6);
        body.put("max_tokens", 1024);

        JSONArray messages = new JSONArray();
        messages.add(new JSONObject().fluentPut("role", "system").fluentPut("content", SYSTEM_PROMPT));
        for (JSONObject m : capped)
        {
            String role = m.getString("role");
            String content = m.getString("content");
            if (StringUtils.isEmpty(content))
            {
                continue;
            }
            if ("user".equals(role))
            {
                messages.add(new JSONObject().fluentPut("role", "user").fluentPut("content", truncate(content, 4000)));
            }
            else if ("assistant".equals(role))
            {
                messages.add(new JSONObject().fluentPut("role", "assistant").fluentPut("content", truncate(content, 4000)));
            }
        }
        if (messages.size() <= 1)
        {
            throw new ServiceException("消息不能为空");
        }

        body.put("messages", messages);
        String url = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) + CHAT_PATH : baseUrl + CHAT_PATH;

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(90))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(body.toJSONString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String rspBody = response.body();
            if (response.statusCode() < 200 || response.statusCode() >= 300)
            {
                log.warn("DeepSeek HTTP {} body: {}", response.statusCode(), rspBody);
                throw new ServiceException(parseErrorMessage(rspBody, "DeepSeek 请求失败"));
            }

            JSONObject root = JSON.parseObject(rspBody);
            if (root != null && root.containsKey("error"))
            {
                JSONObject err = root.getJSONObject("error");
                String msg = err != null ? err.getString("message") : "未知错误";
                throw new ServiceException(StringUtils.isNotEmpty(msg) ? msg : "DeepSeek 返回错误");
            }
            JSONArray choices = root != null ? root.getJSONArray("choices") : null;
            if (choices == null || choices.isEmpty())
            {
                throw new ServiceException("DeepSeek 未返回内容");
            }
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            String content = message != null ? message.getString("content") : null;
            if (StringUtils.isEmpty(content))
            {
                throw new ServiceException("DeepSeek 回复为空");
            }
            return content.trim();
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error("调用 DeepSeek 失败", e);
            throw new ServiceException("调用智能助手失败，请稍后重试");
        }
    }

    private static String truncate(String s, int max)
    {
        if (s == null || s.length() <= max)
        {
            return s;
        }
        return s.substring(0, max);
    }

    private static String parseErrorMessage(String rspBody, String defaultMsg)
    {
        try
        {
            JSONObject root = JSON.parseObject(rspBody);
            if (root != null && root.containsKey("error"))
            {
                Object err = root.get("error");
                if (err instanceof JSONObject)
                {
                    String m = ((JSONObject) err).getString("message");
                    if (StringUtils.isNotEmpty(m))
                    {
                        return m;
                    }
                }
                else if (err instanceof String)
                {
                    return (String) err;
                }
            }
        }
        catch (Exception ignored)
        {
        }
        return defaultMsg;
    }
}
