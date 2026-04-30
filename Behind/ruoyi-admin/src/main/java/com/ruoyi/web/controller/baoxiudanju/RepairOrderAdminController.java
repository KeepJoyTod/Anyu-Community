package com.ruoyi.web.controller.baoxiudanju;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.RepairOrder;
import com.ruoyi.service.IRepairOrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/baoxiudanju/baoxiudanju")
public class RepairOrderAdminController extends BaseController {
    @Autowired
    private IRepairOrderService repairOrderService;

    @GetMapping("/list")
    public TableDataInfo list(RepairOrder repairOrder) {
        startPage();
        List<RepairOrder> list = repairOrderService.selectRepairOrderList(repairOrder);
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, RepairOrder repairOrder) {
        List<RepairOrder> list = repairOrderService.selectRepairOrderList(repairOrder);
        ExcelUtil<RepairOrder> util = new ExcelUtil<>(RepairOrder.class);
        util.exportExcel(response, list, "repair_order");
    }

    @GetMapping("/{orderId}")
    public AjaxResult getInfo(@PathVariable Long orderId) {
        return success(repairOrderService.selectRepairOrderById(orderId));
    }

    @Log(title = "repair_order", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairOrder repairOrder) {
        LocalDateTime now = LocalDateTime.now();
        repairOrder.setCreateTime(now);
        repairOrder.setUpdateTime(now);
        if (repairOrder.getOrderStatus() == null || repairOrder.getOrderStatus().isEmpty()) {
            repairOrder.setOrderStatus("pending");
        }
        return toAjax(repairOrderService.insertRepairOrder(repairOrder));
    }

    @Log(title = "repair_order", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairOrder repairOrder) {
        repairOrder.setUpdateTime(LocalDateTime.now());
        return toAjax(repairOrderService.updateRepairOrder(repairOrder));
    }

    @Log(title = "repair_order", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(repairOrderService.deleteRepairOrderByIds(orderIds));
    }
}
