package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CommunityyyReservationMapper;
import com.ruoyi.system.domain.CommunityyReservation;
import com.ruoyi.system.service.ICommunityReservationnService;

/**
 * 小区预约管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@Service
public class CommunityReservationnServiceImpl implements ICommunityReservationnService
{
    @Autowired
    private CommunityyyReservationMapper communityyyReservationMapper;

    /**
     * 查询小区预约管理
     * 
     * @param id 小区预约管理主键
     * @return 小区预约管理
     */
    @Override
    public CommunityyReservation selectCommunityReservationById(Long id)
    {
        return communityyyReservationMapper.selectCommunityReservationById(id);
    }

    /**
     * 查询小区预约管理列表
     * 
     * @param communityyReservation 小区预约管理
     * @return 小区预约管理
     */
    @Override
    public List<CommunityyReservation> selectCommunityReservationList(CommunityyReservation communityyReservation)
    {
        return communityyyReservationMapper.selectCommunityReservationList(communityyReservation);
    }

    /**
     * 新增小区预约管理
     * 
     * @param communityyReservation 小区预约管理
     * @return 结果
     */
    @Override
    public int insertCommunityReservation(CommunityyReservation communityyReservation)
    {
        communityyReservation.setCreateTime(DateUtils.getNowDate());
        return communityyyReservationMapper.insertCommunityReservation(communityyReservation);
    }

    /**
     * 修改小区预约管理
     * 
     * @param communityyReservation 小区预约管理
     * @return 结果
     */
    @Override
    public int updateCommunityReservation(CommunityyReservation communityyReservation)
    {
        communityyReservation.setUpdateTime(DateUtils.getNowDate());
        return communityyyReservationMapper.updateCommunityReservation(communityyReservation);
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
        return communityyyReservationMapper.deleteCommunityReservationByIds(ids);
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
        return communityyyReservationMapper.deleteCommunityReservationById(id);
    }

    @Override
    public Integer getReservationNum() {
        Integer reservationNum= communityyyReservationMapper.haveNum();
        return reservationNum;
    }
}
