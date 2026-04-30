package com.ruoyi.web.controller.service;

import com.ruoyi.common.Result;
import com.ruoyi.domain.CommunityWaterFee;
import com.ruoyi.domain.WaterAccount;
import com.ruoyi.service.ICommunityWaterFeeService;
import com.ruoyi.service.WaterAccountService;
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
@RequestMapping("/water")
@CrossOrigin
public class WaterController {

    @Autowired
    private WaterAccountService waterAccountService;

    @Autowired
    private ICommunityWaterFeeService communityWaterFeeService;

    @GetMapping("/balance")
    public Result<Map<String, Object>> getBalance() {
        Long userId = 1L;
        WaterAccount account = waterAccountService.getBalance(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", account.getBalance());
        data.put("waterAmount", account.getWaterAmount());
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

        boolean success = waterAccountService.recharge(userId, amount, params.get("payMethod").toString());
        return success ? Result.success("success") : Result.error("recharge failed");
    }

    @GetMapping("/bill/list")
    public Result<List<CommunityWaterFee>> getWaterBillList() {
        Long userId = 1L;
        return Result.success(communityWaterFeeService.selectCommunityWaterFeeByUserId(userId));
    }

    @GetMapping("/bill/detail/{waterId}")
    public Result<CommunityWaterFee> getWaterBillDetail(@PathVariable Long waterId) {
        CommunityWaterFee waterFee = communityWaterFeeService.selectCommunityWaterFeeById(waterId);
        if (waterFee == null) {
            return Result.error(404, "water bill not found");
        }
        return Result.success(waterFee);
    }
}
