package com.ruoyi.web.controller.wuyefei;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.CommunityPropertyFee;
import com.ruoyi.service.ICommunityPropertyFeeService;
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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wuyefei/wu")
public class CommunityPropertyFeeController extends BaseController {
    @Autowired
    private ICommunityPropertyFeeService communityPropertyFeeService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityPropertyFee communityPropertyFee) {
        startPage();
        List<CommunityPropertyFee> list = communityPropertyFeeService.selectCommunityPropertyFeeList(communityPropertyFee);
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityPropertyFee communityPropertyFee) {
        List<CommunityPropertyFee> list = communityPropertyFeeService.selectCommunityPropertyFeeList(communityPropertyFee);
        ExcelUtil<CommunityPropertyFee> util = new ExcelUtil<>(CommunityPropertyFee.class);
        util.exportExcel(response, list, "property_fee");
    }

    @GetMapping("/{propertyFeeId}")
    public AjaxResult getInfo(@PathVariable Long propertyFeeId) {
        return success(communityPropertyFeeService.selectCommunityPropertyFeeById(propertyFeeId));
    }

    @Log(title = "property_fee", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunityPropertyFee communityPropertyFee) {
        Date now = new Date();
        communityPropertyFee.setCreateTime(now);
        communityPropertyFee.setUpdateTime(now);
        if (communityPropertyFee.getFeeStatus() == null || communityPropertyFee.getFeeStatus().isEmpty()) {
            communityPropertyFee.setFeeStatus("unpaid");
        }
        return toAjax(communityPropertyFeeService.insertCommunityPropertyFee(communityPropertyFee));
    }

    @Log(title = "property_fee", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityPropertyFee communityPropertyFee) {
        communityPropertyFee.setUpdateTime(new Date());
        return toAjax(communityPropertyFeeService.updateCommunityPropertyFee(communityPropertyFee));
    }

    @Log(title = "property_fee", businessType = BusinessType.DELETE)
    @DeleteMapping("/{propertyFeeIds}")
    public AjaxResult remove(@PathVariable Long[] propertyFeeIds) {
        return toAjax(communityPropertyFeeService.deleteCommunityPropertyFeeByIds(propertyFeeIds));
    }
}
