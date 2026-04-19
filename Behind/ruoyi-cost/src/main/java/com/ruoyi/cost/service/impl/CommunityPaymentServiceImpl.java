package com.ruoyi.cost.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cost.mapper.CommunityPaymentMapper;
import com.ruoyi.cost.domain.CommunityPayment;
import com.ruoyi.cost.service.ICommunityPaymentService;

/**
 * 小区缴费管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-21
 */
@Service
public class CommunityPaymentServiceImpl implements ICommunityPaymentService 
{
    @Autowired
    private CommunityPaymentMapper communityPaymentMapper;

    /**
     * 查询小区缴费管理
     * 
     * @param id 小区缴费管理主键
     * @return 小区缴费管理
     */
    @Override
    public CommunityPayment selectCommunityPaymentById(Long id)
    {
        return communityPaymentMapper.selectCommunityPaymentById(id);
    }

    /**
     * 查询小区缴费管理列表
     * 
     * @param communityPayment 小区缴费管理
     * @return 小区缴费管理
     */
    @Override
    public List<CommunityPayment> selectCommunityPaymentList(CommunityPayment communityPayment)
    {
        return communityPaymentMapper.selectCommunityPaymentList(communityPayment);
    }

    /**
     * 新增小区缴费管理
     * 
     * @param communityPayment 小区缴费管理
     * @return 结果
     */
    @Override
    public int insertCommunityPayment(CommunityPayment communityPayment)
    {
        communityPayment.setCreateTime(DateUtils.getNowDate());
        return communityPaymentMapper.insertCommunityPayment(communityPayment);
    }

    /**
     * 修改小区缴费管理
     * 
     * @param communityPayment 小区缴费管理
     * @return 结果
     */
    @Override
    public int updateCommunityPayment(CommunityPayment communityPayment)
    {
        communityPayment.setUpdateTime(DateUtils.getNowDate());
        return communityPaymentMapper.updateCommunityPayment(communityPayment);
    }

    /**
     * 批量删除小区缴费管理
     * 
     * @param ids 需要删除的小区缴费管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityPaymentByIds(Long[] ids)
    {
        return communityPaymentMapper.deleteCommunityPaymentByIds(ids);
    }

    /**
     * 删除小区缴费管理信息
     * 
     * @param id 小区缴费管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityPaymentById(Long id)
    {
        return communityPaymentMapper.deleteCommunityPaymentById(id);
    }
}
