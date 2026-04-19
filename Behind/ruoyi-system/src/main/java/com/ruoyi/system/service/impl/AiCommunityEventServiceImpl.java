package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.AiCommunityEvent;
import com.ruoyi.system.mapper.AiCommunityEventMapper;
import com.ruoyi.system.service.IAiCommunityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiCommunityEventServiceImpl implements IAiCommunityEventService {

    @Autowired
    private AiCommunityEventMapper mapper;

    @Override
    public List<AiCommunityEvent> selectList(AiCommunityEvent event) {
        return mapper.selectList(event);
    }

    @Override
    public int insertAiCommunityEvent(AiCommunityEvent event) {
        return mapper.insertAiCommunityEvent(event);
    }

    @Override
    public int updateStatus(Long id, String status) {
        AiCommunityEvent event = new AiCommunityEvent();
        event.setId(id);
        event.setStatus(status);
        return mapper.updateAiCommunityEvent(event);
    }
}