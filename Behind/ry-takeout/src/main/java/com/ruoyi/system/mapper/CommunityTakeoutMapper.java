package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.CommunityTakeout;

/**
 * 小区外卖订单Mapper接口
 * 
 * @author dengdeng
 * @date 2026-03-20
 */
public interface CommunityTakeoutMapper 
{
    /**
     * 查询小区外卖订单
     * 
     * @param id 小区外卖订单主键
     * @return 小区外卖订单
     */
    public CommunityTakeout selectCommunityTakeoutById(Long id);

    /**
     * 查询小区外卖订单列表
     * 
     * @param communityTakeout 小区外卖订单
     * @return 小区外卖订单集合
     */
    public List<CommunityTakeout> selectCommunityTakeoutList(CommunityTakeout communityTakeout);

    /**
     * 新增小区外卖订单
     * 
     * @param communityTakeout 小区外卖订单
     * @return 结果
     */
    public int insertCommunityTakeout(CommunityTakeout communityTakeout);

    /**
     * 修改小区外卖订单
     * 
     * @param communityTakeout 小区外卖订单
     * @return 结果
     */
    public int updateCommunityTakeout(CommunityTakeout communityTakeout);

    /**
     * 删除小区外卖订单
     * 
     * @param id 小区外卖订单主键
     * @return 结果
     */
    public int deleteCommunityTakeoutById(Long id);

    /**
     * 批量删除小区外卖订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunityTakeoutByIds(Long[] ids);
}
