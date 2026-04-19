package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityWaterFee;
import com.ruoyi.mapper.CommunityWaterFeeMapper;
import com.ruoyi.service.ICommunityWaterFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service("primaryCommunityWaterFeeService")
@Transactional
public class CommunityWaterFeeServiceImpl implements ICommunityWaterFeeService {
    
    @Autowired
    private CommunityWaterFeeMapper communityWaterFeeMapper;
    
    private Logger logger = LoggerFactory.getLogger(CommunityWaterFeeServiceImpl.class);
    
    @Override
    public CommunityWaterFee getWaterBalance(Long userId) {
        try {
            // 查询用户的水费信息
            List<CommunityWaterFee> waterFees = communityWaterFeeMapper.selectCommunityWaterFeeByUserId(userId);
            if (waterFees != null && !waterFees.isEmpty()) {
                // 返回最新的水费信息
                return waterFees.get(0);
            }
            // 如果没有记录，返回默认值
            CommunityWaterFee defaultFee = new CommunityWaterFee();
            defaultFee.setUserId(userId);
            defaultFee.setBalance(BigDecimal.ZERO);
            defaultFee.setSurplusWater(0.0);
            return defaultFee;
        } catch (Exception e) {
            logger.error("获取水费余额失败", e);
            throw new RuntimeException("获取水费余额失败", e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rechargeWater(Long userId, BigDecimal amount, String payMethod) {
        try {
            // 查询用户的水费信息
            List<CommunityWaterFee> waterFees = communityWaterFeeMapper.selectCommunityWaterFeeByUserId(userId);
            CommunityWaterFee waterFee;
            
            if (waterFees != null && !waterFees.isEmpty()) {
                // 更新现有记录
                waterFee = waterFees.get(0);
                // 计算新的余额
                BigDecimal newBalance = waterFee.getBalance().add(amount);
                // 计算增加的水量（保留两位小数）
                double waterAdded = amount.divide(new BigDecimal("3.5"), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                // 更新余额和剩余水量
                waterFee.setBalance(newBalance);
                double currentWater = waterFee.getSurplusWater();
                double newSurplusWater = currentWater + waterAdded;
                // 确保剩余水量保留两位小数
                newSurplusWater = Math.round(newSurplusWater * 100.0) / 100.0;
                waterFee.setSurplusWater(newSurplusWater);
                waterFee.setUpdateTime(LocalDateTime.now());
                // 执行更新
                int updateResult = communityWaterFeeMapper.updateCommunityWaterFee(waterFee);
                logger.info("水费充值更新结果: {}", updateResult);
                return updateResult > 0;
            } else {
                // 创建新记录
                waterFee = new CommunityWaterFee();
                waterFee.setUserId(userId);
                waterFee.setBalance(amount);
                // 计算水量（保留两位小数）
                double waterAdded = amount.divide(new BigDecimal("3.5"), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                waterFee.setSurplusWater(waterAdded);
                waterFee.setFeeStatus("paid");
                waterFee.setCreateTime(LocalDateTime.now());
                waterFee.setUpdateTime(LocalDateTime.now());
                // 执行插入
                int insertResult = communityWaterFeeMapper.insertCommunityWaterFee(waterFee);
                logger.info("水费充值插入结果: {}", insertResult);
                return insertResult > 0;
            }
        } catch (Exception e) {
            logger.error("水费充值失败", e);
            throw new RuntimeException("水费充值失败", e);
        }
    }
    
    @Override
    public CommunityWaterFee selectCommunityWaterFeeById(Long waterId) {
        return communityWaterFeeMapper.selectCommunityWaterFeeById(waterId);
    }
    
    @Override
    public List<CommunityWaterFee> selectCommunityWaterFeeByUserId(Long userId) {
        return communityWaterFeeMapper.selectCommunityWaterFeeByUserId(userId);
    }
    
    @Override
    public List<CommunityWaterFee> selectCommunityWaterFeeList() {
        return communityWaterFeeMapper.selectCommunityWaterFeeList();
    }
    
    @Override
    public int updateCommunityWaterFee(CommunityWaterFee communityWaterFee) {
        return communityWaterFeeMapper.updateCommunityWaterFee(communityWaterFee);
    }
    
    @Override
    public int insertCommunityWaterFee(CommunityWaterFee communityWaterFee) {
        return communityWaterFeeMapper.insertCommunityWaterFee(communityWaterFee);
    }
}