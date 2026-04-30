package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityElectricityFee;
import com.ruoyi.domain.ElectricityAccount;
import com.ruoyi.domain.ElectricityRechargeRecord;
import com.ruoyi.mapper.CommunityElectricityFeeMapper;
import com.ruoyi.mapper.ElectricityAccountMapper;
import com.ruoyi.mapper.ElectricityRechargeRecordMapper;
import com.ruoyi.service.ElectricityAccountService;
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
public class ElectricityAccountServiceImpl implements ElectricityAccountService {

    @Autowired
    private ElectricityRechargeRecordMapper rechargeRecordMapper;

    @Autowired
    private ElectricityAccountMapper electricityAccountMapper;

    @Autowired
    private CommunityElectricityFeeMapper communityElectricityFeeMapper;

    // 假设电费单价为 0.54元/度
    private static final BigDecimal ELECTRICITY_PRICE_PER_UNIT = new BigDecimal("0.54");

    @Override
    public ElectricityAccount getBalance(Long userId) {
        ElectricityAccount account = electricityAccountMapper.selectElectricityAccountByUserId(userId);
        
        // 如果账户不存在，初始化一个
        if (account == null) {
            account = new ElectricityAccount();
            account.setUserId(userId);
            account.setBalance(BigDecimal.ZERO);
            account.setElectricityAmount(BigDecimal.ZERO);
            account.setCreateTime(LocalDateTime.now());
            account.setUpdateTime(LocalDateTime.now());
            electricityAccountMapper.insert(account);
        }
        
        return account;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recharge(Long userId, BigDecimal amount, String payMethod) {
        // 计算可以购买的电量 = 充值金额 / 单价
        BigDecimal electricityAdded = amount.divide(ELECTRICITY_PRICE_PER_UNIT, 2, RoundingMode.HALF_UP);

        // 1. 记录充值流水
        ElectricityRechargeRecord record = new ElectricityRechargeRecord();
        record.setUserId(userId);
        record.setAmount(amount);
        record.setElectricityAdded(electricityAdded);
        record.setPayMethod(payMethod);
        record.setStatus(1); // 假设直接支付成功
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        rechargeRecordMapper.insert(record);

        // 2. 更新账户余额和可用电量
        ElectricityAccount account = getBalance(userId);
        account.setBalance(account.getBalance().add(amount));
        account.setElectricityAmount(account.getElectricityAmount().add(electricityAdded));
        account.setUpdateTime(LocalDateTime.now());
        
        boolean updated = electricityAccountMapper.updateById(account) > 0;
        if (updated) {
            savePaidFeeRecord(userId, amount, electricityAdded, payMethod, account);
        }
        return updated;
    }

    private void savePaidFeeRecord(Long userId, BigDecimal amount, BigDecimal electricityAdded, String payMethod, ElectricityAccount account) {
        LocalDateTime now = LocalDateTime.now();
        CommunityElectricityFee fee = new CommunityElectricityFee();
        fee.setUserId(userId);
        fee.setUserName("user-" + userId);
        fee.setUserRoom(String.valueOf(userId));
        fee.setReadMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
        fee.setLastReading(BigDecimal.ZERO);
        fee.setCurrentReading(electricityAdded);
        fee.setUsedAmount(electricityAdded);
        fee.setUnitPrice(ELECTRICITY_PRICE_PER_UNIT);
        fee.setTotalFee(amount);
        fee.setBalance(account.getBalance());
        fee.setSurplusElectricity(account.getElectricityAmount().doubleValue());
        fee.setFeeStatus("paid");
        fee.setDueDate(now);
        fee.setPaymentTime(now);
        fee.setPaymentNo("E" + UUID.randomUUID().toString().replace("-", ""));
        fee.setOverdueAmount(BigDecimal.ZERO);
        fee.setReader("system");
        fee.setReadTime(now);
        fee.setCreateTime(now);
        fee.setUpdateTime(now);
        fee.setRemark("recharge:" + payMethod);
        communityElectricityFeeMapper.insert(fee);
    }
}
