package com.ruoyi.web.controller.appointments;

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
import com.ruoyi.appointments.domain .CommunityReservation;
import com.ruoyi.appointments.service.ICommunityReservationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小区预约管理Controller
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@RequestMapping("/appointments/appointments")
public class CommunityReservationController1 extends BaseController
{
    @Autowired
    private ICommunityReservationService communityReservationService;

    /**
     * 查询小区预约管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CommunityReservation communityReservation)
    {
        startPage();
        List<CommunityReservation> list = communityReservationService.selectCommunityReservationList(communityReservation);
        return getDataTable(list);
    }

    /**
     * 导出小区预约管理列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityReservation communityReservation)
    {
        List<CommunityReservation> list = communityReservationService.selectCommunityReservationList(communityReservation);
        ExcelUtil<CommunityReservation> util = new ExcelUtil<CommunityReservation>(CommunityReservation.class);
        util.exportExcel(response, list, "小区预约管理数据");
    }

    /**
     * 获取小区预约管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityReservationService.selectCommunityReservationById(id));
    }

    /**
     * 新增小区预约管理
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityReservation communityReservation)
    {
        return toAjax(communityReservationService.insertCommunityReservation(communityReservation));
    }

    /**
     * 修改小区预约管理
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityReservation communityReservation)
    {
        return toAjax(communityReservationService.updateCommunityReservation(communityReservation));
    }

    /**
     * 删除小区预约管理
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communityReservationService.deleteCommunityReservationByIds(ids));
    }
}
