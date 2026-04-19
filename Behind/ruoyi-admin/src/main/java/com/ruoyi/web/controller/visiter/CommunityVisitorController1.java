package com.ruoyi.web.controller.visiter;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.visiter.domain.CommunityVisitor;
import com.ruoyi.visiter.service.ICommunityVisitorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小区访客管理Controller
 * 
 * @author ruoyi
 * @date 2026-03-29
 */
// @RestController  // disabled to avoid duplicate bean conflict
@RequestMapping("/visiter/visi")
public class CommunityVisitorController1 extends BaseController
{
    @Autowired
    private ICommunityVisitorService communityVisitorService;

    /**
     * 查询小区访客管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CommunityVisitor communityVisitor)
    {
        startPage();
        List<CommunityVisitor> list = communityVisitorService.selectCommunityVisitorList(communityVisitor);
        return getDataTable(list);
    }

    /**
     * 导出小区访客管理列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityVisitor communityVisitor)
    {
        List<CommunityVisitor> list = communityVisitorService.selectCommunityVisitorList(communityVisitor);
        ExcelUtil<CommunityVisitor> util = new ExcelUtil<CommunityVisitor>(CommunityVisitor.class);
        util.exportExcel(response, list, "小区访客管理数据");
    }

    /**
     * 获取小区访客管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityVisitorService.selectCommunityVisitorById(id));
    }

    /**
     * 新增小区访客管理
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityVisitor communityVisitor)
    {
        return toAjax(communityVisitorService.insertCommunityVisitor(communityVisitor));
    }

    /**
     * 修改小区访客管理
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityVisitor communityVisitor)
    {
        return toAjax(communityVisitorService.updateCommunityVisitor(communityVisitor));
    }

    /**
     * 删除小区访客管理
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communityVisitorService.deleteCommunityVisitorByIds(ids));
    }
}
