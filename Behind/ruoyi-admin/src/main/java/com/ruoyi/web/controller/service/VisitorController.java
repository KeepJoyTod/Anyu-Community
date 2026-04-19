package com.ruoyi.web.controller.service;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.VisitorInvite;
import com.ruoyi.service.IVisitorInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 访客邀请前端控制器
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController extends BaseController {

    @Autowired
    private IVisitorInviteService visitorInviteService;

    /**
     * 新增访客邀请
     */
    @PostMapping("/invite")
    public AjaxResult add(@RequestBody VisitorInvite visitorInvite) {
        // 处理日期转换，前端发送的是字符串日期，需要转换为Date对象
        try {
            if (visitorInvite.getDate() != null && !visitorInvite.getDate().isEmpty()) {
                // 前端发送的是字符串日期，如"2026-03-29"，需要转换为Date对象
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(visitorInvite.getDate());
                visitorInvite.setExpectedVisitTime(date);
            } else if (visitorInvite.getExpectedVisitTimeStr() != null && !visitorInvite.getExpectedVisitTimeStr().isEmpty()) {
                // 处理前端发送的expectedVisitTimeStr字段
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(visitorInvite.getExpectedVisitTimeStr());
                visitorInvite.setExpectedVisitTime(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toAjax(visitorInviteService.insertVisitorInvite(visitorInvite));
    }

    /**
     * 查询最近的访客邀请列表
     */
    @GetMapping("/recent")
    public AjaxResult recent(@RequestParam(defaultValue = "3") Integer limit) {
        List<VisitorInvite> list = visitorInviteService.selectRecentVisitorInvite(limit);
        return success(list);
    }

    /**
     * 访客签入接口
     */
    @PutMapping("/signin/{id}")
    public AjaxResult signIn(@PathVariable Long id) {
        int rows = visitorInviteService.signInVisitor(id);
        if (rows > 0) {
            return success("签入成功");
        }
        return error("签入失败，该访客已签入或不存在");
    }
}