package com.ruoyi.service.impl;

import com.ruoyi.domain.WaterAccount;
import com.ruoyi.domain.WaterRechargeRecord;
import com.ruoyi.mapper.WaterAccountMapper;
import com.ruoyi.mapper.WaterRechargeRecordMapper;
import com.ruoyi.service.WaterAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class WaterAccountServiceImpl implements WaterAccountService {

    @Autowired
    private WaterRechargeRecordMapper rechargeRecordMapper;
    @Autowired
    private WaterAccountMapper waterAccountMapper;
    // 假设水费单价为 2.5元/吨
    private static final BigDecimal WATER_PRICE_PER_TON = new BigDecimal("2.50");

    @Override
    public WaterAccount getBalance(Long userId) {
        WaterAccount account = waterAccountMapper.selectWaterAccountById(userId);
        // 如果账户不存在，初始化一个
        if (account == null) {
            account = new WaterAccount();
            account.setUserId(userId);
            account.setBalance(BigDecimal.ZERO);
            account.setWaterAmount(BigDecimal.ZERO);
            account.setCreateTime(LocalDateTime.now());
            account.setUpdateTime(LocalDateTime.now());
            waterAccountMapper.insert(account);
        }

        return account;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recharge(Long userId, BigDecimal amount, String payMethod) {
        // 计算可以购买的水量 = 充值金额 / 单价
        BigDecimal waterAdded = amount.divide(WATER_PRICE_PER_TON, 2, RoundingMode.HALF_UP);

        // 1. 记录充值流水
        WaterRechargeRecord record = new WaterRechargeRecord();
        record.setUserId(userId);
        record.setAmount(amount);
        record.setWaterAdded(waterAdded);
        record.setPayMethod(payMethod);
        record.setStatus(1); // 假设直接支付成功
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        rechargeRecordMapper.insert(record);

        // 2. 更新账户余额和可用水量
        WaterAccount account = getBalance(userId);
        account.setBalance(account.getBalance().add(amount));
        account.setWaterAmount(account.getWaterAmount().add(waterAdded));
        account.setUpdateTime(LocalDateTime.now());

        return waterAccountMapper.updateById(account) > 0;
    }
}
