package com.ruoyi.web.controller.service;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * 通知控制器
 */
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 获取通知列表
     */
    @GetMapping
    public AjaxResult list(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "100") int size) {
        return AjaxResult.success(noticeService.listNotices(page, size));
    }

    /**
     * 获取通知详情
     */
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        Map<String, Object> notice = noticeService.getNoticeDetail(id);
        if (notice == null) {
            return AjaxResult.error(404, "通知不存在");
        }
        return AjaxResult.success(notice);
    }

    /**
     * 标记通知为已读
     */
    @PutMapping("/{id}/read")
    public AjaxResult markRead(@PathVariable Long id) {
        boolean result = noticeService.markNoticeRead(id);
        if (!result) {
            return AjaxResult.error(404, "通知不存在");
        }
        return AjaxResult.success(Collections.singletonMap("updated", 1));
    }

    /**
     * 标记所有通知为已读
     */
    @PutMapping("/read-all")
    public AjaxResult markAllRead() {
        int updated = noticeService.markAllNoticesRead();
        return AjaxResult.success(Collections.singletonMap("updated", updated));
    }
}

