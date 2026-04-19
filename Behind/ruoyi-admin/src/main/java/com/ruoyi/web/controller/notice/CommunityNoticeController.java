package com.ruoyi.web.controller.notice;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.notice.domain.CommunityyNotice;
import com.ruoyi.notice.service.ICommunityyNoticeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小区通知管理Controller
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@RestController
@RequestMapping("/notice/notice")
public class CommunityNoticeController extends BaseController
{
    @Autowired
    private ICommunityyNoticeService communityNoticeService;

    /**
     * 查询小区通知管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CommunityyNotice communityyNotice)
    {
        startPage();
        List<CommunityyNotice> list = communityNoticeService.selectCommunityNoticeList(communityyNotice);
        return getDataTable(list);
    }

    /**
     * 导出小区通知管理列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityyNotice communityyNotice)
    {
        List<CommunityyNotice> list = communityNoticeService.selectCommunityNoticeList(communityyNotice);
        ExcelUtil<CommunityyNotice> util = new ExcelUtil<CommunityyNotice>(CommunityyNotice.class);
        util.exportExcel(response, list, "小区通知管理数据");
    }

    /**
     * 获取小区通知管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityNoticeService.selectCommunityNoticeById(id));
    }

    /**
     * 新增小区通知管理
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityyNotice communityyNotice)
    {
        return toAjax(communityNoticeService.insertCommunityNotice(communityyNotice));
    }

    /**
     * 修改小区通知管理
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityyNotice communityyNotice)
    {
        return toAjax(communityNoticeService.updateCommunityNotice(communityyNotice));
    }

    /**
     * 删除小区通知管理
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communityNoticeService.deleteCommunityNoticeByIds(ids));
    }
}
