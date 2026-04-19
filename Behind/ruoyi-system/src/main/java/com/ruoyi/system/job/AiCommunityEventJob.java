package com.ruoyi.system.job;

import com.ruoyi.system.domain.AiCommunityEvent;
import com.ruoyi.system.service.AiSearchService;
import com.ruoyi.system.service.IAiCommunityEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AiCommunityEventJob {

    private static final Logger log = LoggerFactory.getLogger(AiCommunityEventJob.class);

    @Autowired
    private AiSearchService aiSearchService;

    @Autowired
    private IAiCommunityEventService eventService;

    // 每小时执行一次
    // 启动后立刻执行一次，方便演示
    @Scheduled(initialDelay = 3000, fixedDelay = Long.MAX_VALUE)
//    @Scheduled(cron = "0 0 * * * ?")
    public void scan() {
        try {
            AiCommunityEvent event = aiSearchService.searchAndAnalyze();
            event.setStatus("0");
            eventService.insertAiCommunityEvent(event);
            log.info("AI自动发现社区事件：{}", event.getEventTitle());
        } catch (Exception e) {
            log.warn("AI社区事件扫描失败（网络不可用或API Key未配置）: {}", e.getMessage());
        }
    }
}