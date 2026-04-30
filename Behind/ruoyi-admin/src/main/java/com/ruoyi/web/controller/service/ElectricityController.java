package com.ruoyi.web.controller.service;

import com.ruoyi.common.Result;
import com.ruoyi.domain.CommunityElectricityFee;
import com.ruoyi.domain.ElectricityAccount;
import com.ruoyi.mapper.CommunityElectricityFeeMapper;
import com.ruoyi.service.ElectricityAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/electricity")
@CrossOrigin
public class ElectricityController {

    @Autowired
    private ElectricityAccountService electricityAccountService;

    @Autowired
    private CommunityElectricityFeeMapper communityElectricityFeeMapper;

    @GetMapping("/balance")
    public Result<Map<String, Object>> getBalance() {
        Long userId = 1L;
        ElectricityAccount account = electricityAccountService.getBalance(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", account.getBalance());
        data.put("electricityAmount", account.getElectricityAmount());
        return Result.success(data);
    }

    @PostMapping("/recharge")
    public Result<String> recharge(@RequestBody Map<String, Object> params) {
        Long userId = 1L;
        if (params.get("amount") == null || params.get("payMethod") == null) {
            return Result.error(400, "missing amount or payMethod");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(params.get("amount").toString());
        } catch (NumberFormatException e) {
            return Result.error(400, "invalid amount");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error(400, "amount must be positive");
        }

        boolean success = electricityAccountService.recharge(userId, amount, params.get("payMethod").toString());
        return success ? Result.success("success") : Result.error("recharge failed");
    }

    @GetMapping("/bill/list")
    public Result<List<CommunityElectricityFee>> getElectricityBillList() {
        Long userId = 1L;
        return Result.success(communityElectricityFeeMapper.selectByUserId(userId));
    }

    @GetMapping("/bill/detail/{electricityId}")
    public Result<CommunityElectricityFee> getElectricityBillDetail(@PathVariable Long electricityId) {
        CommunityElectricityFee electricityFee = communityElectricityFeeMapper.selectById(electricityId);
        if (electricityFee == null) {
            return Result.error(404, "electricity bill not found");
        }
        return Result.success(electricityFee);
    }
}
