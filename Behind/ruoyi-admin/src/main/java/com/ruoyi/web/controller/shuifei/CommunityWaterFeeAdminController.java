package com.ruoyi.web.controller.shuifei;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.CommunityWaterFee;
import com.ruoyi.service.ICommunityWaterFeeService;
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
@RequestMapping("/shuifei/shuifei")
public class CommunityWaterFeeAdminController extends BaseController {
    @Autowired
    private ICommunityWaterFeeService communityWaterFeeService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityWaterFee communityWaterFee) {
        startPage();
        List<CommunityWaterFee> list = communityWaterFeeService.selectCommunityWaterFeeList(communityWaterFee);
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityWaterFee communityWaterFee) {
        List<CommunityWaterFee> list = communityWaterFeeService.selectCommunityWaterFeeList(communityWaterFee);
        ExcelUtil<CommunityWaterFee> util = new ExcelUtil<>(CommunityWaterFee.class);
        util.exportExcel(response, list, "water_fee");
    }

    @GetMapping("/{waterId}")
    public AjaxResult getInfo(@PathVariable Long waterId) {
        return success(communityWaterFeeService.selectCommunityWaterFeeById(waterId));
    }

    @Log(title = "water_fee", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunityWaterFee communityWaterFee) {
        LocalDateTime now = LocalDateTime.now();
        communityWaterFee.setCreateTime(now);
        communityWaterFee.setUpdateTime(now);
        if (communityWaterFee.getFeeStatus() == null || communityWaterFee.getFeeStatus().isEmpty()) {
            communityWaterFee.setFeeStatus("unpaid");
        }
        return toAjax(communityWaterFeeService.insertCommunityWaterFee(communityWaterFee));
    }

    @Log(title = "water_fee", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityWaterFee communityWaterFee) {
        communityWaterFee.setUpdateTime(LocalDateTime.now());
        return toAjax(communityWaterFeeService.updateCommunityWaterFee(communityWaterFee));
    }

    @Log(title = "water_fee", businessType = BusinessType.DELETE)
    @DeleteMapping("/{waterIds}")
    public AjaxResult remove(@PathVariable Long[] waterIds) {
        return toAjax(communityWaterFeeService.deleteCommunityWaterFeeByIds(waterIds));
    }
}
