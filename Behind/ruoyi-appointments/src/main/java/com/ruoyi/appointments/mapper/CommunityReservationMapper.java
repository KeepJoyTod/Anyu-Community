package com.ruoyi.appointments.mapper;

import java.util.List;
import com.ruoyi.appointments.domain.CommunityReservation;

/**
 * 小区预约管理Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface CommunityReservationMapper 
{
    /**
     * 查询小区预约管理
     * 
     * @param id 小区预约管理主键
     * @return 小区预约管理
     */
    public CommunityReservation selectCommunityReservationById(Long id);

    /**
     * 查询小区预约管理列表
     * 
     * @param communityReservation 小区预约管理
     * @return 小区预约管理集合
     */
    public List<CommunityReservation> selectCommunityReservationList(CommunityReservation communityReservation);

    /**
     * 新增小区预约管理
     * 
     * @param communityReservation 小区预约管理
     * @return 结果
     */
    public int insertCommunityReservation(CommunityReservation communityReservation);

    /**
     * 修改小区预约管理
     * 
     * @param communityReservation 小区预约管理
     * @return 结果
     */
    public int updateCommunityReservation(CommunityReservation communityReservation);

    /**
     * 删除小区预约管理
     * 
     * @param id 小区预约管理主键
     * @return 结果
     */
    public int deleteCommunityReservationById(Long id);

    /**
     * 批量删除小区预约管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunityReservationByIds(Long[] ids);
}
