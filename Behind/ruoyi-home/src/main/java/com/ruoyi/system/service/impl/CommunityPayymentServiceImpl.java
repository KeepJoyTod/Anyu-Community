package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CommunityPayymentMapper;
import com.ruoyi.system.domain.CommunityPayyment;
import com.ruoyi.system.service.ICommunityPayymentService;

/**
 * 小区缴费管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@Service
public class CommunityPayymentServiceImpl implements ICommunityPayymentService
{
    @Autowired
    private CommunityPayymentMapper communityPayymentMapper;

    /**
     * 查询小区缴费管理
     * 
     * @param id 小区缴费管理主键
     * @return 小区缴费管理
     */
    @Override
    public CommunityPayyment selectCommunityPaymentById(Long id)
    {
        return communityPayymentMapper.selectCommunityPaymentById(id);
    }

    /**
     * 查询小区缴费管理列表
     * 
     * @param communityPayyment 小区缴费管理
     * @return 小区缴费管理
     */
    @Override
    public List<CommunityPayyment> selectCommunityPaymentList(CommunityPayyment communityPayyment)
    {
        return communityPayymentMapper.selectCommunityPaymentList(communityPayyment);
    }

    /**
     * 新增小区缴费管理
     * 
     * @param communityPayyment 小区缴费管理
     * @return 结果
     */
    @Override
    public int insertCommunityPayment(CommunityPayyment communityPayyment)
    {
        communityPayyment.setCreateTime(DateUtils.getNowDate());
        return communityPayymentMapper.insertCommunityPayment(communityPayyment);
    }

    /**
     * 修改小区缴费管理
     * 
     * @param communityPayyment 小区缴费管理
     * @return 结果
     */
    @Override
    public int updateCommunityPayment(CommunityPayyment communityPayyment)
    {
        communityPayyment.setUpdateTime(DateUtils.getNowDate());
        return communityPayymentMapper.updateCommunityPayment(communityPayyment);
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
        return communityPayymentMapper.deleteCommunityPaymentByIds(ids);
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
        return communityPayymentMapper.deleteCommunityPaymentById(id);
    }

    @Override
    public Double getMoney() {
        Double money = communityPayymentMapper.havaMoney();
        return money;
    }
}
