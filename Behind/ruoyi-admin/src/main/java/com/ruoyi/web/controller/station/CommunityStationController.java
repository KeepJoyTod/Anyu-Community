package com.ruoyi.web.controller.station;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.CommunityStation;
import com.ruoyi.system.service.ICommunityStationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小区驿站服务Controller
 * 
 * @author dengdeng
 * @date 2026-03-18
 */
@RestController
@RequestMapping("/station/station")
public class CommunityStationController extends BaseController
{
    @Autowired
    private ICommunityStationService communityStationService;

    /**
     * 查询小区驿站服务列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CommunityStation communityStation)
    {
        startPage();
        List<CommunityStation> list = communityStationService.selectCommunityStationList(communityStation);
        return getDataTable(list);
    }

    /**
     * 导出小区驿站服务列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityStation communityStation)
    {
        List<CommunityStation> list = communityStationService.selectCommunityStationList(communityStation);
        ExcelUtil<CommunityStation> util = new ExcelUtil<CommunityStation>(CommunityStation.class);
        util.exportExcel(response, list, "小区驿站服务数据");
    }

    /**
     * 获取小区驿站服务详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityStationService.selectCommunityStationById(id));
    }

    /**
     * 新增小区驿站服务
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityStation communityStation)
    {
        return toAjax(communityStationService.insertCommunityStation(communityStation));
    }

    /**
     * 修改小区驿站服务
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityStation communityStation)
    {
        return toAjax(communityStationService.updateCommunityStation(communityStation));
    }

    /**
     * 删除小区驿站服务
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communityStationService.deleteCommunityStationByIds(ids));
    }
}
