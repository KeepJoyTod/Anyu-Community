package com.ruoyi.web.controller.cost;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.cost.domain.CommunityPayment;
import com.ruoyi.cost.service.ICommunityPaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小区缴费管理Controller
 * 
 * @author ruoyi
 * @date 2026-03-21
 */
@RestController
@RequestMapping("/cost/cos")
public class CommunityPaymentController extends BaseController
{
    @Autowired
    private ICommunityPaymentService communityPaymentService;

    /**
     * 查询小区缴费管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CommunityPayment communityPayment)
    {
        startPage();
        List<CommunityPayment> list = communityPaymentService.selectCommunityPaymentList(communityPayment);
        return getDataTable(list);
    }

    /**
     * 导出小区缴费管理列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityPayment communityPayment)
    {
        List<CommunityPayment> list = communityPaymentService.selectCommunityPaymentList(communityPayment);
        ExcelUtil<CommunityPayment> util = new ExcelUtil<CommunityPayment>(CommunityPayment.class);
        util.exportExcel(response, list, "小区缴费管理数据");
    }

    /**
     * 获取小区缴费管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityPaymentService.selectCommunityPaymentById(id));
    }

    /**
     * 新增小区缴费管理
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityPayment communityPayment)
    {
        return toAjax(communityPaymentService.insertCommunityPayment(communityPayment));
    }

    /**
     * 修改小区缴费管理
     */
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityPayment communityPayment)
    {
        return toAjax(communityPaymentService.updateCommunityPayment(communityPayment));
    }

    /**
     * 删除小区缴费管理
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(communityPaymentService.deleteCommunityPaymentByIds(ids));
    }
}
