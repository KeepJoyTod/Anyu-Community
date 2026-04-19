package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.AiCommunityEvent;
import java.util.List;

public interface AiCommunityEventMapper {
    List<AiCommunityEvent> selectList(AiCommunityEvent event);
    int insertAiCommunityEvent(AiCommunityEvent event);
    int updateAiCommunityEvent(AiCommunityEvent event);
    AiCommunityEvent selectById(Long id);
}