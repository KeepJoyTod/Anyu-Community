package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.CommunityStation;

/**
 * 小区驿站服务Mapper接口
 * 
 * @author dengdeng
 * @date 2026-03-18
 */
public interface CommunityStationMapper 
{
    /**
     * 查询小区驿站服务
     * 
     * @param id 小区驿站服务主键
     * @return 小区驿站服务
     */
    public CommunityStation selectCommunityStationById(Long id);

    /**
     * 查询小区驿站服务列表
     * 
     * @param communityStation 小区驿站服务
     * @return 小区驿站服务集合
     */
    public List<CommunityStation> selectCommunityStationList(CommunityStation communityStation);

    /**
     * 新增小区驿站服务
     * 
     * @param communityStation 小区驿站服务
     * @return 结果
     */
    public int insertCommunityStation(CommunityStation communityStation);

    /**
     * 修改小区驿站服务
     * 
     * @param communityStation 小区驿站服务
     * @return 结果
     */
    public int updateCommunityStation(CommunityStation communityStation);

    /**
     * 删除小区驿站服务
     * 
     * @param id 小区驿站服务主键
     * @return 结果
     */
    public int deleteCommunityStationById(Long id);

    /**
     * 批量删除小区驿站服务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunityStationByIds(Long[] ids);
}
