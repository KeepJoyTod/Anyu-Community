package com.ruoyi.system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeepSeekUtil {

    @Value("${ai.deepseek.api-key}")
    private String apiKey;

    @Value("${ai.deepseek.base-url}")
    private String baseUrl;

    @Value("${ai.deepseek.model:deepseek-chat}")
    private String model;

    public String sendChat(String prompt) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        String chatUrl = baseUrl.endsWith("/") ? baseUrl + "chat/completions" : baseUrl + "/chat/completions";
        HttpPost post = new HttpPost(chatUrl);
        post.setHeader("Authorization","Bearer "+apiKey);
        post.setHeader("Content-Type","application/json");

        Map<String,Object> body = new HashMap<>();
        body.put("model", model);
        body.put("temperature",0.1);

        Map<String,String> msg = new HashMap<>();
        msg.put("role","user");
        msg.put("content",prompt);
        body.put("messages",new Map[]{msg});

        String json = new ObjectMapper().writeValueAsString(body);
        post.setEntity(new StringEntity(json,"UTF-8"));

        try(CloseableHttpResponse resp = client.execute(post)){
            String res = EntityUtils.toString(resp.getEntity());
            if (res == null || res.trim().isEmpty()) {
                throw new RuntimeException("DeepSeek API returned empty response");
            }
            Map map = new ObjectMapper().readValue(res,Map.class);
            if (map.get("choices") == null) {
                throw new RuntimeException("DeepSeek API error: " + res);
            }
            List list = (List) map.get("choices");
            Map m = (Map) list.get(0);
            Map message = (Map) m.get("message");
            return message.get("content").toString();
        }
    }
}