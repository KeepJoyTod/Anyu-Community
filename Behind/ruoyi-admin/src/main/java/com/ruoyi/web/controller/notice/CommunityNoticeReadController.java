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
import com.ruoyi.notice.domain.CommunityNoticeRead;
import com.ruoyi.notice.service.ICommunityNoticeReadService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 通知阅读记录Controller
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@RestController
@RequestMapping("/noticeRead/noticeRead")
public class CommunityNoticeReadController extends BaseController
{
    @Autowired
    private ICommunityNoticeReadService communityNoticeReadService;

    /**
     * 查询通知阅读记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CommunityNoticeRead communityNoticeRead)
    {
        startPage();
        List<CommunityNoticeRead> list = communityNoticeReadService.selectCommunityNoticeReadList(communityNoticeRead);
        return getDataTable(list);
    }

    /**
     * 导出通知阅读记录列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityNoticeRead communityNoticeRead)
    {
        List<CommunityNoticeRead> list = communityNoticeReadService.selectCommunityNoticeReadList(communityNoticeRead);
        ExcelUtil<CommunityNoticeRead> util = new ExcelUtil<CommunityNoticeRead>(CommunityNoticeRead.class);
        util.exportExcel(response, list, "通知阅读记录数据");
    }

    /**
     * 获取通知阅读记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityNoticeReadService.selectCommunityNoticeReadById(id));
    }

    /**
     * 新增通知阅读记录
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityNoticeRead communityNoticeRead)
    {
        return toAjax(communityNoticeReadService.insertCommunityNoticeRead(communityNoticeRead));
    }

    /**
     * 修改通知阅读记录
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityNoticeRead communityNoticeRead)
    {
        return toAjax(communityNoticeReadService.updateCommunityNoticeRead(communityNoticeRead));
    }

    /**
     * 删除通知阅读记录
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communityNoticeReadService.deleteCommunityNoticeReadByIds(ids));
    }
}
