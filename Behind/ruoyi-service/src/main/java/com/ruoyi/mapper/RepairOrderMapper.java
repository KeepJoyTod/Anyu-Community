package com.ruoyi.mapper;

import com.ruoyi.domain.RepairOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepairOrderMapper {

    /**
     * 新增维修订单
     * @param repairOrder 维修订单
     * @return 结果
     */
    int insert(RepairOrder repairOrder);

    /**
     * 根据ID查询维修订单
     * @param orderId 订单ID
     * @return 维修订单
     */
    RepairOrder selectById(Long orderId);

    /**
     * 修改维修订单
     * @param repairOrder 维修订单
     * @return 结果
     */
    int updateById(RepairOrder repairOrder);

    /**
     * 删除维修订单
     * @param orderId 订单ID
     * @return 结果
     */
    int deleteById(Long orderId);

    /**
     * 查询待处理的维修订单列表
     * @return 维修订单列表
     */
    List<RepairOrder> selectPendingOrders();
}

