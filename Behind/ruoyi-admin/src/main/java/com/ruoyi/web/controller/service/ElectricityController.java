package com.ruoyi.web.controller.service;

import com.ruoyi.common.Result;
import com.ruoyi.domain.ElectricityAccount;
import com.ruoyi.service.ElectricityAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/electricity")
@CrossOrigin // 允许跨域请求，方便前端测试
public class ElectricityController {

    @Autowired
    private ElectricityAccountService electricityAccountService;

    /**
     * 获取余额
     * 对应前端: GET /electricity/balance
     */
    @GetMapping("/balance")
    public Result<Map<String, Object>> getBalance() {
        // 实际项目中 userId 从 Token 获取，这里模拟当前登录用户 userId = 1
        Long userId = 1L;

        ElectricityAccount account = electricityAccountService.getBalance(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("balance", account.getBalance());
        data.put("electricityAmount", account.getElectricityAmount());

        return Result.success(data);
    }

    /**
     * 电费充值
     * 对应前端: POST /electricity/recharge
     */
    @PostMapping("/recharge")
    public Result<String> recharge(@RequestBody Map<String, Object> params) {
        // 实际项目中 userId 从 Token 获取，这里模拟当前登录用户 userId = 1
        Long userId = 1L;

        // 从 Map 中获取参数并进行基础校验
        if (params.get("amount") == null || params.get("payMethod") == null) {
            return Result.error(400, "参数不完整");
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(params.get("amount").toString());
        } catch (NumberFormatException e) {
            return Result.error(400, "金额格式不正确");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.error(400, "充值金额必须大于0");
        }

        String payMethod = params.get("payMethod").toString();

        boolean success = electricityAccountService.recharge(userId, amount, payMethod);
        if (success) {
            return Result.success("充值成功");
        }
        return Result.error("充值失败");
    }
}