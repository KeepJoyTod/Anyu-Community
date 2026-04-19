package com.ruoyi.cost.mapper;

import java.util.List;
import com.ruoyi.cost.domain.CommunityPayment;

/**
 * 小区缴费管理Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-21
 */
public interface CommunityPaymentMapper 
{
    /**
     * 查询小区缴费管理
     * 
     * @param id 小区缴费管理主键
     * @return 小区缴费管理
     */
    public CommunityPayment selectCommunityPaymentById(Long id);

    /**
     * 查询小区缴费管理列表
     * 
     * @param communityPayment 小区缴费管理
     * @return 小区缴费管理集合
     */
    public List<CommunityPayment> selectCommunityPaymentList(CommunityPayment communityPayment);

    /**
     * 新增小区缴费管理
     * 
     * @param communityPayment 小区缴费管理
     * @return 结果
     */
    public int insertCommunityPayment(CommunityPayment communityPayment);

    /**
     * 修改小区缴费管理
     * 
     * @param communityPayment 小区缴费管理
     * @return 结果
     */
    public int updateCommunityPayment(CommunityPayment communityPayment);

    /**
     * 删除小区缴费管理
     * 
     * @param id 小区缴费管理主键
     * @return 结果
     */
    public int deleteCommunityPaymentById(Long id);

    /**
     * 批量删除小区缴费管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunityPaymentByIds(Long[] ids);
}
