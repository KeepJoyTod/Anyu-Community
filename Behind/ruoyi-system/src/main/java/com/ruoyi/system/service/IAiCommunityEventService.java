package com.ruoyi.system.service;

import com.ruoyi.system.domain.AiCommunityEvent;
import java.util.List;

public interface IAiCommunityEventService {
    List<AiCommunityEvent> selectList(AiCommunityEvent event);
    int insertAiCommunityEvent(AiCommunityEvent event);
    int updateStatus(Long id, String status);
}