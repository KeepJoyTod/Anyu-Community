package com.ruoyi.service;

import com.ruoyi.domain.WaterAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface WaterAccountService {
    
    /**
     * 获取用户余额
     * @param userId 用户ID
     * @return 账户信息
     */
    WaterAccount getBalance(Long userId);

    /**
     * 充值水费
     * @param userId 用户ID
     * @param amount 充值金额
     * @param payMethod 支付方式
     * @return 是否成功
     */
    boolean recharge(Long userId, BigDecimal amount, String payMethod);
}
