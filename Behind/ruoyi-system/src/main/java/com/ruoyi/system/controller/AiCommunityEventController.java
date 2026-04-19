package com.ruoyi.system.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.AiCommunityEvent;
import com.ruoyi.system.service.IAiCommunityEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/system/ai-event")
public class AiCommunityEventController extends BaseController {

    @Autowired
    private IAiCommunityEventService service;

    @GetMapping("/list")
    public TableDataInfo list(AiCommunityEvent event) {
        startPage();
        List<AiCommunityEvent> list = service.selectList(event);
        return getDataTable(list);
    }

    @PutMapping("/status/{id}/{status}")
    public Object update(@PathVariable Long id, @PathVariable String status) {
        return service.updateStatus(id, status);
    }
}