package com.ruoyi.web.controller.complaint;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.complaint1.domain.CommunityComplaint;
import com.ruoyi.complaint1.service.ICommunityComplaintService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小区投诉管理Controller
 *
 * @author xvxv
 * @date 2026-03-19
 */
@RestController
@RequestMapping("/complaint/complaint")
public class CommunityComplaintController extends BaseController
{
    @Autowired
    private ICommunityComplaintService communityComplaintService;

    /**
     * 查询小区投诉管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CommunityComplaint communityComplaint)
    {
        startPage();
        List<CommunityComplaint> list = communityComplaintService.selectCommunityComplaintList(communityComplaint);
        return getDataTable(list);
    }

    /**
     * 导出小区投诉管理列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityComplaint communityComplaint)
    {
        List<CommunityComplaint> list = communityComplaintService.selectCommunityComplaintList(communityComplaint);
        ExcelUtil<CommunityComplaint> util = new ExcelUtil<CommunityComplaint>(CommunityComplaint.class);
        util.exportExcel(response, list, "小区投诉管理数据");
    }

    /**
     * 获取小区投诉管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityComplaintService.selectCommunityComplaintById(id));
    }

    /**
     * 新增小区投诉管理
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityComplaint communityComplaint)
    {
        return toAjax(communityComplaintService.insertCommunityComplaint(communityComplaint));
    }

    /**
     * 修改小区投诉管理
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityComplaint communityComplaint)
    {
        return toAjax(communityComplaintService.updateCommunityComplaint(communityComplaint));
    }

    /**
     * 删除小区投诉管理
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communityComplaintService.deleteCommunityComplaintByIds(ids));
    }
}
