package com.ruoyi.service;

import com.ruoyi.domain.RepairOrder;

import java.util.List;

/**
 * 维修订单服务接口
 */
public interface IRepairOrderService {

    /**
     * 根据ID查询维修订单
     * @param orderId 订单ID
     * @return 维修订单
     */
    RepairOrder selectRepairOrderById(Long orderId);

    /**
     * 新增维修订单
     * @param repairOrder 维修订单
     * @return 结果
     */
    int insertRepairOrder(RepairOrder repairOrder);

    /**
     * 修改维修订单
     * @param repairOrder 维修订单
     * @return 结果
     */
    int updateRepairOrder(RepairOrder repairOrder);

    /**
     * 删除维修订单
     * @param orderId 订单ID
     * @return 结果
     */
    int deleteRepairOrderById(Long orderId);

    /**
     * 取消维修订单
     * @param orderId 订单ID
     * @return 结果
     */
    int cancelRepairOrder(Long orderId);

    /**
     * 获取待处理的维修订单列表
     * @return 维修订单列表
     */
    List<RepairOrder> selectPendingRepairOrders();
}