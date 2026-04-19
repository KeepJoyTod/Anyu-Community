package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CommunityTakeoutMapper;
import com.ruoyi.system.domain.CommunityTakeout;
import com.ruoyi.system.service.ICommunityTakeoutService;

/**
 * 小区外卖订单Service业务层处理
 * 
 * @author dengdeng
 * @date 2026-03-20
 */
@Service
public class CommunityTakeoutServiceImpl implements ICommunityTakeoutService 
{
    @Autowired
    private CommunityTakeoutMapper communityTakeoutMapper;

    /**
     * 查询小区外卖订单
     * 
     * @param id 小区外卖订单主键
     * @return 小区外卖订单
     */
    @Override
    public CommunityTakeout selectCommunityTakeoutById(Long id)
    {
        return communityTakeoutMapper.selectCommunityTakeoutById(id);
    }

    /**
     * 查询小区外卖订单列表
     * 
     * @param communityTakeout 小区外卖订单
     * @return 小区外卖订单
     */
    @Override
    public List<CommunityTakeout> selectCommunityTakeoutList(CommunityTakeout communityTakeout)
    {
        return communityTakeoutMapper.selectCommunityTakeoutList(communityTakeout);
    }

    /**
     * 新增小区外卖订单
     * 
     * @param communityTakeout 小区外卖订单
     * @return 结果
     */
    @Override
    public int insertCommunityTakeout(CommunityTakeout communityTakeout)
    {
        communityTakeout.setCreateTime(DateUtils.getNowDate());
        return communityTakeoutMapper.insertCommunityTakeout(communityTakeout);
    }

    /**
     * 修改小区外卖订单
     * 
     * @param communityTakeout 小区外卖订单
     * @return 结果
     */
    @Override
    public int updateCommunityTakeout(CommunityTakeout communityTakeout)
    {
        communityTakeout.setUpdateTime(DateUtils.getNowDate());
        return communityTakeoutMapper.updateCommunityTakeout(communityTakeout);
    }

    /**
     * 批量删除小区外卖订单
     * 
     * @param ids 需要删除的小区外卖订单主键
     * @return 结果
     */
    @Override
    public int deleteCommunityTakeoutByIds(Long[] ids)
    {
        return communityTakeoutMapper.deleteCommunityTakeoutByIds(ids);
    }

    /**
     * 删除小区外卖订单信息
     * 
     * @param id 小区外卖订单主键
     * @return 结果
     */
    @Override
    public int deleteCommunityTakeoutById(Long id)
    {
        return communityTakeoutMapper.deleteCommunityTakeoutById(id);
    }
}
