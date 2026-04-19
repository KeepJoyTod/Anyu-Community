package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CommunityyReservation;

/**
 * 小区预约管理Service接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface ICommunityReservationnService
{
    /**
     * 查询小区预约管理
     * 
     * @param id 小区预约管理主键
     * @return 小区预约管理
     */
    public CommunityyReservation selectCommunityReservationById(Long id);

    /**
     * 查询小区预约管理列表
     * 
     * @param communityyReservation 小区预约管理
     * @return 小区预约管理集合
     */
    public List<CommunityyReservation> selectCommunityReservationList(CommunityyReservation communityyReservation);

    /**
     * 新增小区预约管理
     * 
     * @param communityyReservation 小区预约管理
     * @return 结果
     */
    public int insertCommunityReservation(CommunityyReservation communityyReservation);

    /**
     * 修改小区预约管理
     * 
     * @param communityyReservation 小区预约管理
     * @return 结果
     */
    public int updateCommunityReservation(CommunityyReservation communityyReservation);

    /**
     * 批量删除小区预约管理
     * 
     * @param ids 需要删除的小区预约管理主键集合
     * @return 结果
     */
    public int deleteCommunityReservationByIds(Long[] ids);

    /**
     * 删除小区预约管理信息
     * 
     * @param id 小区预约管理主键
     * @return 结果
     */
    public int deleteCommunityReservationById(Long id);

    Integer getReservationNum();
}
