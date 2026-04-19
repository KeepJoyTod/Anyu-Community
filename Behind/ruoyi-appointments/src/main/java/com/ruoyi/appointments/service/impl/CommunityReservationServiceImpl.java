package com.ruoyi.appointments.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.appointments.mapper.CommunityReservationMapper;
import com.ruoyi.appointments.domain.CommunityReservation;
import com.ruoyi.appointments.service.ICommunityReservationService;

/**
 * 小区预约管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@Service
public class CommunityReservationServiceImpl implements ICommunityReservationService 
{
    @Autowired
    private CommunityReservationMapper communityReservationMapper;

    /**
     * 查询小区预约管理
     * 
     * @param id 小区预约管理主键
     * @return 小区预约管理
     */
    @Override
    public CommunityReservation selectCommunityReservationById(Long id)
    {
        return communityReservationMapper.selectCommunityReservationById(id);
    }

    /**
     * 查询小区预约管理列表
     * 
     * @param communityReservation 小区预约管理
     * @return 小区预约管理
     */
    @Override
    public List<CommunityReservation> selectCommunityReservationList(CommunityReservation communityReservation)
    {
        return communityReservationMapper.selectCommunityReservationList(communityReservation);
    }

    /**
     * 新增小区预约管理
     * 
     * @param communityReservation 小区预约管理
     * @return 结果
     */
    @Override
    public int insertCommunityReservation(CommunityReservation communityReservation)
    {
        communityReservation.setCreateTime(DateUtils.getNowDate());
        return communityReservationMapper.insertCommunityReservation(communityReservation);
    }

    /**
     * 修改小区预约管理
     * 
     * @param communityReservation 小区预约管理
     * @return 结果
     */
    @Override
    public int updateCommunityReservation(CommunityReservation communityReservation)
    {
        communityReservation.setUpdateTime(DateUtils.getNowDate());
        return communityReservationMapper.updateCommunityReservation(communityReservation);
    }

    /**
     * 批量删除小区预约管理
     * 
     * @param ids 需要删除的小区预约管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityReservationByIds(Long[] ids)
    {
        return communityReservationMapper.deleteCommunityReservationByIds(ids);
    }

    /**
     * 删除小区预约管理信息
     * 
     * @param id 小区预约管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityReservationById(Long id)
    {
        return communityReservationMapper.deleteCommunityReservationById(id);
    }
}
