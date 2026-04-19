package com.ruoyi.complaint1.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.complaint1.mapper.CommunityComplaintMapper;
import com.ruoyi.complaint1.domain.CommunityComplaint;
import com.ruoyi.complaint1.service.ICommunityComplaintService;

/**
 * 小区投诉管理Service业务层处理
 * 
 * @author xvxv
 * @date 2026-03-19
 */
@Service
public class CommunityComplaintServiceImpl implements ICommunityComplaintService 
{
    @Autowired
    private CommunityComplaintMapper communityComplaintMapper;

    /**
     * 查询小区投诉管理
     * 
     * @param id 小区投诉管理主键
     * @return 小区投诉管理
     */
    @Override
    public CommunityComplaint selectCommunityComplaintById(Long id)
    {
        return communityComplaintMapper.selectCommunityComplaintById(id);
    }

    /**
     * 查询小区投诉管理列表
     * 
     * @param communityComplaint 小区投诉管理
     * @return 小区投诉管理
     */
    @Override
    public List<CommunityComplaint> selectCommunityComplaintList(CommunityComplaint communityComplaint)
    {
        return communityComplaintMapper.selectCommunityComplaintList(communityComplaint);
    }

    /**
     * 新增小区投诉管理
     * 
     * @param communityComplaint 小区投诉管理
     * @return 结果
     */
    @Override
    public int insertCommunityComplaint(CommunityComplaint communityComplaint)
    {
        communityComplaint.setCreateTime(DateUtils.getNowDate());
        return communityComplaintMapper.insertCommunityComplaint(communityComplaint);
    }

    /**
     * 修改小区投诉管理
     * 
     * @param communityComplaint 小区投诉管理
     * @return 结果
     */
    @Override
    public int updateCommunityComplaint(CommunityComplaint communityComplaint)
    {
        communityComplaint.setUpdateTime(DateUtils.getNowDate());
        return communityComplaintMapper.updateCommunityComplaint(communityComplaint);
    }

    /**
     * 批量删除小区投诉管理
     * 
     * @param ids 需要删除的小区投诉管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityComplaintByIds(Long[] ids)
    {
        return communityComplaintMapper.deleteCommunityComplaintByIds(ids);
    }

    /**
     * 删除小区投诉管理信息
     * 
     * @param id 小区投诉管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityComplaintById(Long id)
    {
        return communityComplaintMapper.deleteCommunityComplaintById(id);
    }
}
