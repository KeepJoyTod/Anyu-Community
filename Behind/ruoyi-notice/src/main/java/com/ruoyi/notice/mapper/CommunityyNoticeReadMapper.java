package com.ruoyi.notice.mapper;

import java.util.List;
import com.ruoyi.notice.domain.CommunityyNoticeRead;

/**
 * 通知阅读记录Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface CommunityyNoticeReadMapper
{
    /**
     * 查询通知阅读记录
     * 
     * @param id 通知阅读记录主键
     * @return 通知阅读记录
     */
    public CommunityyNoticeRead selectCommunityNoticeReadById(Long id);

    /**
     * 查询通知阅读记录列表
     * 
     * @param communityyNoticeRead 通知阅读记录
     * @return 通知阅读记录集合
     */
    public List<CommunityyNoticeRead> selectCommunityNoticeReadList(CommunityyNoticeRead communityyNoticeRead);

    /**
     * 新增通知阅读记录
     * 
     * @param communityyNoticeRead 通知阅读记录
     * @return 结果
     */
    public int insertCommunityNoticeRead(CommunityyNoticeRead communityyNoticeRead);

    /**
     * 修改通知阅读记录
     * 
     * @param communityyNoticeRead 通知阅读记录
     * @return 结果
     */
    public int updateCommunityNoticeRead(CommunityyNoticeRead communityyNoticeRead);

    /**
     * 删除通知阅读记录
     * 
     * @param id 通知阅读记录主键
     * @return 结果
     */
    public int deleteCommunityNoticeReadById(Long id);

    /**
     * 批量删除通知阅读记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunityNoticeReadByIds(Long[] ids);
}
