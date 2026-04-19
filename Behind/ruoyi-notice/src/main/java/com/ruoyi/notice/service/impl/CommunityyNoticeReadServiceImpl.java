package com.ruoyi.notice.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.notice.mapper.CommunityyNoticeReadMapper;
import com.ruoyi.notice.domain.CommunityyNoticeRead;
import com.ruoyi.notice.service.ICommunityyNoticeReadService;

/**
 * 通知阅读记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@Service
public class CommunityyNoticeReadServiceImpl implements ICommunityyNoticeReadService
{
    @Autowired
    private CommunityyNoticeReadMapper communityyNoticeReadMapper;

    /**
     * 查询通知阅读记录
     * 
     * @param id 通知阅读记录主键
     * @return 通知阅读记录
     */
    @Override
    public CommunityyNoticeRead selectCommunityNoticeReadById(Long id)
    {
        return communityyNoticeReadMapper.selectCommunityNoticeReadById(id);
    }

    /**
     * 查询通知阅读记录列表
     * 
     * @param communityyNoticeRead 通知阅读记录
     * @return 通知阅读记录
     */
    @Override
    public List<CommunityyNoticeRead> selectCommunityNoticeReadList(CommunityyNoticeRead communityyNoticeRead)
    {
        return communityyNoticeReadMapper.selectCommunityNoticeReadList(communityyNoticeRead);
    }

    /**
     * 新增通知阅读记录
     * 
     * @param communityyNoticeRead 通知阅读记录
     * @return 结果
     */
    @Override
    public int insertCommunityNoticeRead(CommunityyNoticeRead communityyNoticeRead)
    {
        communityyNoticeRead.setCreateTime(DateUtils.getNowDate());
        return communityyNoticeReadMapper.insertCommunityNoticeRead(communityyNoticeRead);
    }

    /**
     * 修改通知阅读记录
     * 
     * @param communityyNoticeRead 通知阅读记录
     * @return 结果
     */
    @Override
    public int updateCommunityNoticeRead(CommunityyNoticeRead communityyNoticeRead)
    {
        communityyNoticeRead.setUpdateTime(DateUtils.getNowDate());
        return communityyNoticeReadMapper.updateCommunityNoticeRead(communityyNoticeRead);
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
        return communityyNoticeReadMapper.deleteCommunityNoticeReadByIds(ids);
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
        return communityyNoticeReadMapper.deleteCommunityNoticeReadById(id);
    }
}
