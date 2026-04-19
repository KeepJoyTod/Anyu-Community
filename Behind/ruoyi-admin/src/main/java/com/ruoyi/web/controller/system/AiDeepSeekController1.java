package com.ruoyi.web.controller.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController  // disabled to avoid duplicate bean conflict
@RequestMapping("/ai")
public class AiDeepSeekController1 {

    @Value("${ai.deepseek.api-key}")
    private String apiKey;

    @Value("${ai.deepseek.base-url}")
    private String baseUrl;

    @Value("${ai.deepseek.model}")
    private String model;

    private ObjectMapper objectMapper = new ObjectMapper();

    // 可联网搜索的 AI 对话接口
    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> param) {
        try {
            String userMessage = param.get("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return "请输入问题";
            }

            // 1. 联网搜索
            String searchResult = webSearch(userMessage);

            // 2. 组合提示词
            String prompt = ""
                    + "请根据以下搜索结果回答问题：\n"
                    + searchResult
                    + "\n用户问题：" + userMessage
                    + "\n请直接给出答案，不要多余描述";

            // 3. 调用 DeepSeek
            String aiResponse = callDeepSeek(prompt);

            return aiResponse;
        } catch (Exception e) {
            return "联网搜索服务异常：" + e.getMessage();
        }
    }

    // 联网搜索
    private String webSearch(String question) throws Exception {
        String keyword = URLEncoder.encode(question, "UTF-8");
        String url = "https://www.bing.com/search?q=" + keyword;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0");

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "UTF-8");
            Document doc = Jsoup.parse(html);
            String text = doc.select("p").text();
            return text.length() > 800 ? text.substring(0, 800) : text;
        }
    }

    // 调用 DeepSeek
    private String callDeepSeek(String prompt) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl);

        httpPost.setHeader("Authorization", "Bearer " + apiKey);
        httpPost.setHeader("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("temperature", 0.3);

        Map<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", prompt);
        requestBody.put("messages", new Map[]{msg});

        String json = objectMapper.writeValueAsString(requestBody);
        httpPost.setEntity(new StringEntity(json, "UTF-8"));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (result == null || result.trim().isEmpty()) {
                return "AI 接口返回空响应，请检查 API Key 或网络连接";
            }
            Map<String, Object> resMap = objectMapper.readValue(result, Map.class);

            List<?> choices = (List<?>) resMap.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<?, ?> choice = (Map<?, ?>) choices.get(0);
                Map<?, ?> message = (Map<?, ?>) choice.get("message");
                return message.get("content").toString();
            }
            return "AI 没有返回结果";
        }
    }
}