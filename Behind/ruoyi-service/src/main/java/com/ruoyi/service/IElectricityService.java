package com.ruoyi.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.CommunityElectricityFee;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 电费管理Service接口
 * 
 * @author ruoyi
 */
@Service
public interface IElectricityService {

    /**
     * 获取电费余额
     * 
     * @return 电费余额信息
     */
    AjaxResult getElectricityBalance();

    /**
     * 创建电费充值订单
     * 
     * @param amount 充值金额
     * @param payMethod 支付方式
     * @return 充值结果
     */
    AjaxResult createElectricityOrder(BigDecimal amount, String payMethod);

    /**
     * 获取电费账单列表
     * 
     * @return 电费账单列表
     */
    List<CommunityElectricityFee> getElectricityBillList();

    /**
     * 获取电费账单详情
     * 
     * @param electricityId 电费ID
     * @return 电费账单详情
     */
    CommunityElectricityFee getElectricityBillDetail(Long electricityId);
}
