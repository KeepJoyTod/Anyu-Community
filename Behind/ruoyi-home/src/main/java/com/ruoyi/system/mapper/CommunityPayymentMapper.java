package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.CommunityPayyment;

/**
 * 小区缴费管理Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface CommunityPayymentMapper
{
    /**
     * 查询小区缴费管理
     * 
     * @param id 小区缴费管理主键
     * @return 小区缴费管理
     */
    public CommunityPayyment selectCommunityPaymentById(Long id);

    /**
     * 查询小区缴费管理列表
     * 
     * @param communityPayyment 小区缴费管理
     * @return 小区缴费管理集合
     */
    public List<CommunityPayyment> selectCommunityPaymentList(CommunityPayyment communityPayyment);

    /**
     * 新增小区缴费管理
     * 
     * @param communityPayyment 小区缴费管理
     * @return 结果
     */
    public int insertCommunityPayment(CommunityPayyment communityPayyment);

    /**
     * 修改小区缴费管理
     * 
     * @param communityPayyment 小区缴费管理
     * @return 结果
     */
    public int updateCommunityPayment(CommunityPayyment communityPayyment);

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

    Double havaMoney();
}
