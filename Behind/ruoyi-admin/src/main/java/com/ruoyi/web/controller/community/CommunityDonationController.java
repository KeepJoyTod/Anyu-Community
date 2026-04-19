package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.community.domain.CommunityDonation;
import com.ruoyi.community.domain.CommunityDonationRecord;
import com.ruoyi.community.domain.DonationRequest;
import com.ruoyi.community.service.ICommunityDonationService;
import com.ruoyi.community.service.ICommunityDonationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

//爱心捐赠
@RestController
@RequestMapping("/community/donation")
public class CommunityDonationController {

    @Autowired
    private ICommunityDonationService donationService;

    @Autowired
    private ICommunityDonationRecordService donationRecordService;

//    获取捐赠项目列表

    @GetMapping("/list")
    public AjaxResult getDonationList() {
        List<CommunityDonation> donations = donationService.selectDonationList();
        return AjaxResult.success(donations);
    }

//    获取捐赠记录列表

    @GetMapping("/records")
    public AjaxResult getDonationRecords() {
        List<CommunityDonationRecord> records = donationRecordService.selectDonationRecordList();
        return AjaxResult.success(records);
    }

//    根据捐赠项目ID获取捐赠记录

    @GetMapping("/records/{donationId}")
    public AjaxResult getDonationRecordsByDonationId(@PathVariable Long donationId) {
        List<CommunityDonationRecord> records = donationRecordService.selectDonationRecordByDonationId(donationId);
        return AjaxResult.success(records);
    }

//    创建捐赠记录

    @PostMapping("/create")
    public AjaxResult create(@RequestBody DonationRequest request) {

        // 创建捐赠记录
        CommunityDonationRecord record = new CommunityDonationRecord();
        record.setDonationId(request.getDonationId());
        record.setDonorName(request.getDonorName());
        record.setDonorPhone(request.getDonorPhone());
        record.setAmount(new BigDecimal(request.getAmount()));
        record.setMessage(request.getMessage());
        record.setPayType(request.getPayType());
        record.setStatus("success"); // 模拟支付成功
        // 保存捐赠记录
        donationRecordService.insertDonationRecord(record);
        // 更新捐赠项目的已筹金额和捐赠人数
        donationService.updateDonationAmountAndCount(request.getDonationId(), new BigDecimal(request.getAmount()));

        return AjaxResult.success("捐赠成功");
    }
}