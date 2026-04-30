package com.ruoyi.web.controller.dianfeiguanli;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.CommunityElectricityFee;
import com.ruoyi.mapper.CommunityElectricityFeeMapper;
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
@RequestMapping("/dianfeiguanli/dianfeiguanli")
public class CommunityElectricityFeeAdminController extends BaseController {
    @Autowired
    private CommunityElectricityFeeMapper communityElectricityFeeMapper;

    @GetMapping("/list")
    public TableDataInfo list(CommunityElectricityFee communityElectricityFee) {
        startPage();
        List<CommunityElectricityFee> list = communityElectricityFeeMapper.selectCommunityElectricityFeeList(communityElectricityFee);
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityElectricityFee communityElectricityFee) {
        List<CommunityElectricityFee> list = communityElectricityFeeMapper.selectCommunityElectricityFeeList(communityElectricityFee);
        ExcelUtil<CommunityElectricityFee> util = new ExcelUtil<>(CommunityElectricityFee.class);
        util.exportExcel(response, list, "electricity_fee");
    }

    @GetMapping("/{electricityId}")
    public AjaxResult getInfo(@PathVariable Long electricityId) {
        return success(communityElectricityFeeMapper.selectById(electricityId));
    }

    @Log(title = "electricity_fee", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunityElectricityFee communityElectricityFee) {
        LocalDateTime now = LocalDateTime.now();
        communityElectricityFee.setCreateTime(now);
        communityElectricityFee.setUpdateTime(now);
        if (communityElectricityFee.getFeeStatus() == null || communityElectricityFee.getFeeStatus().isEmpty()) {
            communityElectricityFee.setFeeStatus("unpaid");
        }
        return toAjax(communityElectricityFeeMapper.insert(communityElectricityFee));
    }

    @Log(title = "electricity_fee", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityElectricityFee communityElectricityFee) {
        communityElectricityFee.setUpdateTime(LocalDateTime.now());
        return toAjax(communityElectricityFeeMapper.updateById(communityElectricityFee));
    }

    @Log(title = "electricity_fee", businessType = BusinessType.DELETE)
    @DeleteMapping("/{electricityIds}")
    public AjaxResult remove(@PathVariable Long[] electricityIds) {
        return toAjax(communityElectricityFeeMapper.deleteCommunityElectricityFeeByIds(electricityIds));
    }
}
