package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityWaterFee;
import com.ruoyi.smartcommunity.mapper.CommunityWaterFeeMapper;
import com.ruoyi.service.ICommunityWaterFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 水费表 服务实现类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
//@Service("smartCommunityWaterFeeServiceImpl")
public class CommunityWaterFeeServiceImpl implements ICommunityWaterFeeService {
    
    @Autowired
    private CommunityWaterFeeMapper communityWaterFeeMapper;

    @Override
    public CommunityWaterFee getWaterBalance(Long userId) {
        List<CommunityWaterFee> list = communityWaterFeeMapper.selectCommunityWaterFeeByUserId(userId);
        if (list != null && !list.isEmpty()) {
            // 返回最新的水费信息
            return list.get(0);
        }
        // 如果没有记录，返回默认值
        CommunityWaterFee defaultFee = new CommunityWaterFee();
        defaultFee.setUserId(userId);
        defaultFee.setBalance(BigDecimal.ZERO);
        defaultFee.setSurplusWater(0.0);
        return defaultFee;
    }

    @Override
    public boolean rechargeWater(Long userId, BigDecimal amount, String payMethod) {
        List<CommunityWaterFee> list = communityWaterFeeMapper.selectCommunityWaterFeeByUserId(userId);
        if (list != null && !list.isEmpty()) {
            CommunityWaterFee fee = list.get(0);
            fee.setBalance(fee.getBalance() != null ? fee.getBalance().add(amount) : amount);
            fee.setUpdateTime(LocalDateTime.now());
            return communityWaterFeeMapper.updateCommunityWaterFee(fee) > 0;
        } else {
            CommunityWaterFee fee = new CommunityWaterFee();
            fee.setUserId(userId);
            fee.setBalance(amount);
            fee.setSurplusWater(0.0);
            fee.setFeeStatus("paid");
            fee.setCreateTime(LocalDateTime.now());
            fee.setUpdateTime(LocalDateTime.now());
            return communityWaterFeeMapper.insertCommunityWaterFee(fee) > 0;
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
    public List<CommunityWaterFee> selectCommunityWaterFeeList(CommunityWaterFee communityWaterFee) {
        return communityWaterFeeMapper.selectCommunityWaterFeeListByQuery(communityWaterFee);
    }

    @Override
    public int updateCommunityWaterFee(CommunityWaterFee communityWaterFee) {
        return communityWaterFeeMapper.updateCommunityWaterFee(communityWaterFee);
    }

    @Override
    public int insertCommunityWaterFee(CommunityWaterFee communityWaterFee) {
        return communityWaterFeeMapper.insertCommunityWaterFee(communityWaterFee);
    }

    @Override
    public int deleteCommunityWaterFeeByIds(Long[] waterIds) {
        return communityWaterFeeMapper.deleteCommunityWaterFeeByIds(waterIds);
    }
}
