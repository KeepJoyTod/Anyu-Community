package com.ruoyi.notice.mapper;

import java.util.List;
import com.ruoyi.notice.domain.CommunityNoticeRead;

/**
 * 通知阅读记录Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface CommunityNoticeReadMapper 
{
    /**
     * 查询通知阅读记录
     * 
     * @param id 通知阅读记录主键
     * @return 通知阅读记录
     */
    public CommunityNoticeRead selectCommunityNoticeReadById(Long id);

    /**
     * 查询通知阅读记录列表
     * 
     * @param communityNoticeRead 通知阅读记录
     * @return 通知阅读记录集合
     */
    public List<CommunityNoticeRead> selectCommunityNoticeReadList(CommunityNoticeRead communityNoticeRead);

    /**
     * 新增通知阅读记录
     * 
     * @param communityNoticeRead 通知阅读记录
     * @return 结果
     */
    public int insertCommunityNoticeRead(CommunityNoticeRead communityNoticeRead);

    /**
     * 修改通知阅读记录
     * 
     * @param communityNoticeRead 通知阅读记录
     * @return 结果
     */
    public int updateCommunityNoticeRead(CommunityNoticeRead communityNoticeRead);

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
