package com.ruoyi.system.service;

import com.ruoyi.system.domain.AiCommunityEvent;
import com.ruoyi.system.utils.DeepSeekUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;

@Service
public class AiSearchService {

    @Autowired
    private DeepSeekUtil deepSeekUtil;

    public AiCommunityEvent searchAndAnalyze() throws Exception {
        String kw = URLEncoder.encode("本地天气预警 市政停水停电 社区安全通知","UTF-8");
        String url = "https://www.bing.com/search?q="+kw;

        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        String html;
        try (CloseableHttpResponse resp = HttpClients.createDefault().execute(get)) {
            html = EntityUtils.toString(resp.getEntity());
        }
        String plain = Jsoup.parse(html).select("p").text();
        String content = plain.length() <= 1000 ? plain : plain.substring(0, 1000);
        if (content.isEmpty()) {
            content = "（搜索结果为空，请根据常见社区场景生成示例事件）";
        }

        String prompt = """
            你是社区AI，请分析内容，只返回标准JSON，不要其他文字：
            {
                "eventTitle":"标题",
                "eventContent":"内容",
                "eventType":"weather/safety/policy/notice",
                "eventLevel":"normal/warning/urgent",
                "aiSuggest":"处理建议"
            }
            内容：%s
        """.formatted(content);

        String aiRes = deepSeekUtil.sendChat(prompt).trim();
        int jsonStart = aiRes.indexOf('{');
        int jsonEnd = aiRes.lastIndexOf('}');
        if (jsonStart >= 0 && jsonEnd > jsonStart) {
            aiRes = aiRes.substring(jsonStart, jsonEnd + 1);
        }
        return new com.fasterxml.jackson.databind.ObjectMapper()
                .readValue(aiRes, AiCommunityEvent.class);
    }
}