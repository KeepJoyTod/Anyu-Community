package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityWaterFee;
import com.ruoyi.domain.WaterAccount;
import com.ruoyi.domain.WaterRechargeRecord;
import com.ruoyi.mapper.CommunityWaterFeeMapper;
import com.ruoyi.mapper.WaterAccountMapper;
import com.ruoyi.mapper.WaterRechargeRecordMapper;
import com.ruoyi.service.WaterAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class WaterAccountServiceImpl implements WaterAccountService {

    @Autowired
    private WaterRechargeRecordMapper rechargeRecordMapper;
    @Autowired
    private WaterAccountMapper waterAccountMapper;
    @Autowired
    private CommunityWaterFeeMapper communityWaterFeeMapper;
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

        boolean updated = waterAccountMapper.updateById(account) > 0;
        if (updated) {
            savePaidFeeRecord(userId, amount, waterAdded, payMethod, account);
        }
        return updated;
    }

    private void savePaidFeeRecord(Long userId, BigDecimal amount, BigDecimal waterAdded, String payMethod, WaterAccount account) {
        LocalDateTime now = LocalDateTime.now();
        CommunityWaterFee fee = new CommunityWaterFee();
        fee.setUserId(userId);
        fee.setUserName("user-" + userId);
        fee.setUserRoom(String.valueOf(userId));
        fee.setReadMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
        fee.setLastReading(BigDecimal.ZERO);
        fee.setCurrentReading(waterAdded);
        fee.setUsedAmount(waterAdded);
        fee.setUnitPrice(WATER_PRICE_PER_TON);
        fee.setTotalFee(amount);
        fee.setBalance(account.getBalance());
        fee.setFeeStatus("paid");
        fee.setDueDate(now);
        fee.setPaymentTime(now);
        fee.setPaymentNo("W" + UUID.randomUUID().toString().replace("-", ""));
        fee.setOverdueAmount(BigDecimal.ZERO);
        fee.setReader("system");
        fee.setReadTime(now);
        fee.setCreateTime(now);
        fee.setUpdateTime(now);
        fee.setRemark("recharge:" + payMethod);
        communityWaterFeeMapper.insertCommunityWaterFee(fee);
    }
}
