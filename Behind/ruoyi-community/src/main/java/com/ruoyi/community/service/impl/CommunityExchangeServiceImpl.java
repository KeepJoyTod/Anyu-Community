package com.ruoyi.community.service.impl;

import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.community.domain.*;
import com.ruoyi.community.mapper.*;
import com.ruoyi.community.service.ICommunityExchangeService;
import com.ruoyi.community.service.ICommunityPointsService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Service
public class CommunityExchangeServiceImpl implements ICommunityExchangeService {

    private static final int POINTS_TO_YUAN_RATIO = 100;

    @Autowired
    private ICommunityPointsService pointsService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private CommunityWaterFeeMapper waterFeeMapper;
    @Autowired
    private CommunityElectricityFeeMapper electricityFeeMapper;
    @Autowired
    private CommunityPaymentMapper paymentMapper;
    @Autowired
    private CommunityPointsExchangeMapper pointsExchangeMapper;

    @Override
    @Transactional
    public boolean exchangePoints(Long userId, String billType, Integer pointsUsed, Double amount) {
        BigDecimal amountOffset = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP);
        String userName = null;

        // 获取用户信息
        SysUser user = userMapper.selectUserById(userId);
        if (user == null) return false;
        userName = user.getNickName();

        // 检查积分是否足够
        CommunityPoints points = pointsService.getPointsByUserId(userId);
        if (points == null || points.getAvailablePoints() < pointsUsed) return false;

        // 扣除积分
        boolean deductSuccess = pointsService.deductPoints(userId, pointsUsed, "兑换" + billType + "费用");
        if (!deductSuccess) return false;

        // 根据类型更新相应的费用表余额
        Long billId = null;
        switch (billType) {
            case "water":
                // 先尝试获取用户的水费记录
                CommunityWaterFee existingWater = waterFeeMapper.selectLatestByUserId(userId);
                if (existingWater != null) {
                    // 如果存在记录，使用其ID
                    billId = existingWater.getWaterId();
                    // 尝试更新余额
                    waterFeeMapper.updateBalanceByUserId(userId, amountOffset);
                } else {
                    // 如果不存在记录，创建一条新记录
                    CommunityWaterFee waterFee = new CommunityWaterFee();
                    waterFee.setUserId(userId);
                    waterFee.setUserName(userName);
                    waterFee.setUserRoom("" + user.getUserId());
                    waterFee.setReadMonth(new SimpleDateFormat("yyyy-MM").format(new Date()));
                    waterFee.setLastReading(BigDecimal.ZERO);
                    waterFee.setCurrentReading(BigDecimal.ZERO);
                    waterFee.setUsedAmount(BigDecimal.ZERO);
                    waterFee.setUnitPrice(BigDecimal.valueOf(1.0));
                    waterFee.setTotalFee(BigDecimal.ZERO);
                    waterFee.setBalance(amountOffset);
                    waterFee.setFeeStatus("paid");
                    waterFee.setDueDate(new Date());
                    waterFee.setPaymentTime(new Date());
                    waterFee.setPaymentNo(IdUtils.fastUUID());
                    waterFee.setOverdueAmount(BigDecimal.ZERO);
                    waterFee.setReader("系统");
                    waterFee.setReadTime(new Date());
                    waterFee.setRemark("积分兑换水费");
                    waterFeeMapper.insertWaterFee(waterFee);
                    billId = waterFee.getWaterId();
                }
                break;
            case "electricity":
                // 先尝试获取用户的电费记录
                CommunityElectricityFee existingElectricity = electricityFeeMapper.selectLatestByUserId(userId);
                if (existingElectricity != null) {
                    // 如果存在记录，使用其ID
                    billId = existingElectricity.getElectricityId();
                    // 尝试更新余额
                    electricityFeeMapper.updateBalanceByUserId(userId, amountOffset);
                } else {
                    // 如果不存在记录，创建一条新记录
                    CommunityElectricityFee electricityFee = new CommunityElectricityFee();
                    electricityFee.setUserId(userId);
                    electricityFee.setUserName(userName);
                    electricityFee.setUserRoom("" + user.getUserId());
                    electricityFee.setReadMonth(new SimpleDateFormat("yyyy-MM").format(new Date()));
                    electricityFee.setLastReading(BigDecimal.ZERO);
                    electricityFee.setCurrentReading(BigDecimal.ZERO);
                    electricityFee.setUsedAmount(BigDecimal.ZERO);
                    electricityFee.setUnitPrice(BigDecimal.valueOf(1.0));
                    electricityFee.setTotalFee(BigDecimal.ZERO);
                    electricityFee.setBalance(amountOffset);
                    electricityFee.setFeeStatus("paid");
                    electricityFee.setDueDate(new Date());
                    electricityFee.setPaymentTime(new Date());
                    electricityFee.setPaymentNo(IdUtils.fastUUID());
                    electricityFee.setOverdueAmount(BigDecimal.ZERO);
                    electricityFee.setReader("系统");
                    electricityFee.setReadTime(new Date());
                    electricityFee.setRemark("积分兑换电费");
                    electricityFeeMapper.insertElectricityFee(electricityFee);
                    billId = electricityFee.getElectricityId();
                }
                break;
            default:
                return false;
        }
        
        // 再次确保 billId 不为 null
        if (billId == null) {
            // 如果 billId 仍然为 null，根据类型创建一条新记录
            if ("water".equals(billType)) {
                CommunityWaterFee waterFee = new CommunityWaterFee();
                waterFee.setUserId(userId);
                waterFee.setUserName(userName);
                waterFee.setUserRoom("" + user.getUserId());
                waterFee.setReadMonth(new SimpleDateFormat("yyyy-MM").format(new Date()));
                waterFee.setLastReading(BigDecimal.ZERO);
                waterFee.setCurrentReading(BigDecimal.ZERO);
                waterFee.setUsedAmount(BigDecimal.ZERO);
                waterFee.setUnitPrice(BigDecimal.valueOf(1.0));
                waterFee.setTotalFee(BigDecimal.ZERO);
                waterFee.setBalance(amountOffset);
                waterFee.setFeeStatus("paid");
                waterFee.setDueDate(new Date());
                waterFee.setPaymentTime(new Date());
                waterFee.setPaymentNo(IdUtils.fastUUID());
                waterFee.setOverdueAmount(BigDecimal.ZERO);
                waterFee.setReader("系统");
                waterFee.setReadTime(new Date());
                waterFee.setRemark("积分兑换水费");
                waterFeeMapper.insertWaterFee(waterFee);
                billId = waterFee.getWaterId();
            } else if ("electricity".equals(billType)) {
                CommunityElectricityFee electricityFee = new CommunityElectricityFee();
                electricityFee.setUserId(userId);
                electricityFee.setUserName(userName);
                electricityFee.setUserRoom("" + user.getUserId());
                electricityFee.setReadMonth(new SimpleDateFormat("yyyy-MM").format(new Date()));
                electricityFee.setLastReading(BigDecimal.ZERO);
                electricityFee.setCurrentReading(BigDecimal.ZERO);
                electricityFee.setUsedAmount(BigDecimal.ZERO);
                electricityFee.setUnitPrice(BigDecimal.valueOf(1.0));
                electricityFee.setTotalFee(BigDecimal.ZERO);
                electricityFee.setBalance(amountOffset);
                electricityFee.setFeeStatus("paid");
                electricityFee.setDueDate(new Date());
                electricityFee.setPaymentTime(new Date());
                electricityFee.setPaymentNo(IdUtils.fastUUID());
                electricityFee.setOverdueAmount(BigDecimal.ZERO);
                electricityFee.setReader("系统");
                electricityFee.setReadTime(new Date());
                electricityFee.setRemark("积分兑换电费");
                electricityFeeMapper.insertElectricityFee(electricityFee);
                billId = electricityFee.getElectricityId();
            }
        }



        // 记录积分兑换
        CommunityPointsExchange exchange = new CommunityPointsExchange();
        exchange.setUserId(userId);
        exchange.setUserName(userName);
        exchange.setBillType(billType);
        exchange.setBillId(billId);
        exchange.setPointsUsed(pointsUsed);
        exchange.setAmountOffset(amountOffset);
        exchange.setExchangeTime(new Date());
        exchange.setRemark("积分兑换" + ("water".equals(billType) ? "水费" : "电费"));
        pointsExchangeMapper.insertPointsExchange(exchange);

        return true;
    }

    @Override
    @Transactional
    public boolean exchangePoints(Long userId, String billType, Long billId, Integer pointsUsed) {
        BigDecimal amountOffset = BigDecimal.valueOf(pointsUsed)
                .divide(BigDecimal.valueOf(POINTS_TO_YUAN_RATIO), 2, RoundingMode.HALF_UP);
        BigDecimal billAmount = null;
        String paymentNo = IdUtils.fastUUID();
        String userName = null;

        switch (billType) {
            case "water":
                CommunityWaterFee water = waterFeeMapper.selectWaterFeeById(billId);
                if (water == null || !water.getUserId().equals(userId)) return false;
                if (!"unpaid".equals(water.getFeeStatus())) return false;
                billAmount = water.getTotalFee();
                userName = water.getUserName();
                if (amountOffset.compareTo(billAmount) < 0) return false;
                waterFeeMapper.updateStatusToPaid(billId, paymentNo);
                break;
            case "electricity":
                CommunityElectricityFee elec = electricityFeeMapper.selectElectricityFeeById(billId);
                if (elec == null || !elec.getUserId().equals(userId)) return false;
                if (!"unpaid".equals(elec.getFeeStatus())) return false;
                billAmount = elec.getTotalFee();
                userName = elec.getUserName();
                if (amountOffset.compareTo(billAmount) < 0) return false;
                electricityFeeMapper.updateStatusToPaid(billId, paymentNo);
                break;
            case "property":
                CommunityPayment payment = paymentMapper.selectPaymentById(billId);
                if (payment == null || !payment.getUserId().equals(userId)) return false;
                if (!"unpaid".equals(payment.getPaymentStatus())) return false;
                billAmount = payment.getPaymentAmount();
                userName = payment.getUserName();
                if (amountOffset.compareTo(billAmount) < 0) return false;
                paymentMapper.updateStatusToPaid(billId, paymentNo);
                break;
            default:
                return false;
        }

        boolean deductSuccess = pointsService.deductPoints(userId, pointsUsed, "兑换" + billType + "账单");
        if (!deductSuccess) return false;

        CommunityPointsExchange exchange = new CommunityPointsExchange();
        exchange.setUserId(userId);
        exchange.setUserName(userName);
        exchange.setBillType(billType);
        exchange.setBillId(billId);
        exchange.setPointsUsed(pointsUsed);
        exchange.setAmountOffset(amountOffset);
        exchange.setExchangeTime(new Date());
        pointsExchangeMapper.insertPointsExchange(exchange);

        return true;
    }

    @Override
    public List<Map<String, Object>> getUnpaidBillsByUserId(Long userId) {
        List<Map<String, Object>> bills = new ArrayList<>();

        waterFeeMapper.selectUnpaidByUserId(userId).forEach(water -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", water.getWaterId());
            item.put("type", "water");
            item.put("typeName", "水费");
            item.put("amount", water.getTotalFee());
            item.put("deadline", water.getDueDate());
            item.put("pointsNeeded", water.getTotalFee().multiply(new BigDecimal(POINTS_TO_YUAN_RATIO)).intValue());
            item.put("name", water.getUserRoom() + " - " + water.getReadMonth() + "水费");
            bills.add(item);
        });

        electricityFeeMapper.selectUnpaidByUserId(userId).forEach(elec -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", elec.getElectricityId());
            item.put("type", "electricity");
            item.put("typeName", "电费");
            item.put("amount", elec.getTotalFee());
            item.put("deadline", elec.getDueDate());
            item.put("pointsNeeded", elec.getTotalFee().multiply(new BigDecimal(POINTS_TO_YUAN_RATIO)).intValue());
            item.put("name", elec.getUserRoom() + " - " + elec.getReadMonth() + "电费");
            bills.add(item);
        });

        paymentMapper.selectUnpaidByUserId(userId).forEach(payment -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", payment.getId());
            item.put("type", "property");
            item.put("typeName", "物业费");
            item.put("amount", payment.getPaymentAmount());
            item.put("deadline", payment.getDueTime());
            item.put("pointsNeeded", payment.getPaymentAmount().multiply(new BigDecimal(POINTS_TO_YUAN_RATIO)).intValue());
            item.put("name", payment.getUserRoom() + " - 物业费账单");
            bills.add(item);
        });

        return bills;
    }

    @Override
    public List<Map<String, Object>> getExchangeRecordsByUserId(Long userId) {
        List<CommunityPointsExchange> records = pointsExchangeMapper.selectByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (CommunityPointsExchange record : records) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", record.getId());
            item.put("billType", record.getBillType());
            item.put("billTypeName", getBillTypeName(record.getBillType()));
            item.put("billId", record.getBillId());
            item.put("pointsUsed", record.getPointsUsed());
            item.put("amountOffset", record.getAmountOffset());
            item.put("exchangeTime", record.getExchangeTime());
            item.put("remark", record.getRemark());
            result.add(item);
        }

        return result;
    }

    private String getBillTypeName(String billType) {
        if (billType == null) {
            return "其他";
        }
        switch (billType) {
            case "water": return "水费";
            case "electricity": return "电费";
            case "property": return "物业费";
            default: return "其他";
        }
    }
}