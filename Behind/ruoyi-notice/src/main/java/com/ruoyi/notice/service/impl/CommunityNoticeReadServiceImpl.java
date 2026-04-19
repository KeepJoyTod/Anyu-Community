package com.ruoyi.notice.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.notice.mapper.CommunityNoticeReadMapper;
import com.ruoyi.notice.domain.CommunityNoticeRead;
import com.ruoyi.notice.service.ICommunityNoticeReadService;

/**
 * 通知阅读记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@Service
public class CommunityNoticeReadServiceImpl implements ICommunityNoticeReadService 
{
    @Autowired
    private CommunityNoticeReadMapper communityNoticeReadMapper;

    /**
     * 查询通知阅读记录
     * 
     * @param id 通知阅读记录主键
     * @return 通知阅读记录
     */
    @Override
    public CommunityNoticeRead selectCommunityNoticeReadById(Long id)
    {
        return communityNoticeReadMapper.selectCommunityNoticeReadById(id);
    }

    /**
     * 查询通知阅读记录列表
     * 
     * @param communityNoticeRead 通知阅读记录
     * @return 通知阅读记录
     */
    @Override
    public List<CommunityNoticeRead> selectCommunityNoticeReadList(CommunityNoticeRead communityNoticeRead)
    {
        return communityNoticeReadMapper.selectCommunityNoticeReadList(communityNoticeRead);
    }

    /**
     * 新增通知阅读记录
     * 
     * @param communityNoticeRead 通知阅读记录
     * @return 结果
     */
    @Override
    public int insertCommunityNoticeRead(CommunityNoticeRead communityNoticeRead)
    {
        communityNoticeRead.setCreateTime(DateUtils.getNowDate());
        return communityNoticeReadMapper.insertCommunityNoticeRead(communityNoticeRead);
    }

    /**
     * 修改通知阅读记录
     * 
     * @param communityNoticeRead 通知阅读记录
     * @return 结果
     */
    @Override
    public int updateCommunityNoticeRead(CommunityNoticeRead communityNoticeRead)
    {
        communityNoticeRead.setUpdateTime(DateUtils.getNowDate());
        return communityNoticeReadMapper.updateCommunityNoticeRead(communityNoticeRead);
    }

    /**
     * 批量删除通知阅读记录
     * 
     * @param ids 需要删除的通知阅读记录主键
     * @return 结果
     */
    @Override
    public int deleteCommunityNoticeReadByIds(Long[] ids)
    {
        return communityNoticeReadMapper.deleteCommunityNoticeReadByIds(ids);
    }

    /**
     * 删除通知阅读记录信息
     * 
     * @param id 通知阅读记录主键
     * @return 结果
     */
    @Override
    public int deleteCommunityNoticeReadById(Long id)
    {
        return communityNoticeReadMapper.deleteCommunityNoticeReadById(id);
    }
}
