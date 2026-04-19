package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CommunityStationMapper;
import com.ruoyi.system.domain.CommunityStation;
import com.ruoyi.system.service.ICommunityStationService;

/**
 * 小区驿站服务Service业务层处理
 * 
 * @author dengdeng
 * @date 2026-03-18
 */
@Service
public class CommunityStationServiceImpl implements ICommunityStationService 
{
    @Autowired
    private CommunityStationMapper communityStationMapper;

    /**
     * 查询小区驿站服务
     * 
     * @param id 小区驿站服务主键
     * @return 小区驿站服务
     */
    @Override
    public CommunityStation selectCommunityStationById(Long id)
    {
        return communityStationMapper.selectCommunityStationById(id);
    }

    /**
     * 查询小区驿站服务列表
     * 
     * @param communityStation 小区驿站服务
     * @return 小区驿站服务
     */
    @Override
    public List<CommunityStation> selectCommunityStationList(CommunityStation communityStation)
    {
        return communityStationMapper.selectCommunityStationList(communityStation);
    }

    /**
     * 新增小区驿站服务
     * 
     * @param communityStation 小区驿站服务
     * @return 结果
     */
    @Override
    public int insertCommunityStation(CommunityStation communityStation)
    {
        communityStation.setCreateTime(DateUtils.getNowDate());
        return communityStationMapper.insertCommunityStation(communityStation);
    }

    /**
     * 修改小区驿站服务
     * 
     * @param communityStation 小区驿站服务
     * @return 结果
     */
    @Override
    public int updateCommunityStation(CommunityStation communityStation)
    {
        communityStation.setUpdateTime(DateUtils.getNowDate());
        return communityStationMapper.updateCommunityStation(communityStation);
    }

    /**
     * 批量删除小区驿站服务
     * 
     * @param ids 需要删除的小区驿站服务主键
     * @return 结果
     */
    @Override
    public int deleteCommunityStationByIds(Long[] ids)
    {
        return communityStationMapper.deleteCommunityStationByIds(ids);
    }

    /**
     * 删除小区驿站服务信息
     * 
     * @param id 小区驿站服务主键
     * @return 结果
     */
    @Override
    public int deleteCommunityStationById(Long id)
    {
        return communityStationMapper.deleteCommunityStationById(id);
    }
}
