package com.ruoyi.service.impl;

import com.ruoyi.domain.RepairOrder;
import com.ruoyi.mapper.RepairOrderMapper;
import com.ruoyi.service.IRepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维修订单服务实现类
 */
@Service
public class RepairOrderServiceImpl implements IRepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Override
    public List<RepairOrder> selectRepairOrderList(RepairOrder repairOrder) {
        return repairOrderMapper.selectRepairOrderList(repairOrder);
    }

    @Override
    public RepairOrder selectRepairOrderById(Long orderId) {
        return repairOrderMapper.selectById(orderId);
    }

    @Override
    public int insertRepairOrder(RepairOrder repairOrder) {
        return repairOrderMapper.insert(repairOrder);
    }

    @Override
    public int updateRepairOrder(RepairOrder repairOrder) {
        return repairOrderMapper.updateById(repairOrder);
    }

    @Override
    public int deleteRepairOrderById(Long orderId) {
        return repairOrderMapper.deleteById(orderId);
    }

    @Override
    public int deleteRepairOrderByIds(Long[] orderIds) {
        return repairOrderMapper.deleteByIds(orderIds);
    }

    @Override
    public int cancelRepairOrder(Long orderId) {
        RepairOrder repairOrder = repairOrderMapper.selectById(orderId);
        if (repairOrder != null && "pending".equals(repairOrder.getOrderStatus())) {
            repairOrder.setOrderStatus("canceled");
            return repairOrderMapper.updateById(repairOrder);
        }
        return 0;
    }

    @Override
    public List<RepairOrder> selectPendingRepairOrders() {
        return repairOrderMapper.selectPendingOrders();
    }
}
