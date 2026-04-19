package com.ruoyi.notice.service;

import java.util.List;
import com.ruoyi.notice.domain.CommunityNotice;

/**
 * 小区通知管理Service接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface ICommunityNoticeService 
{
    /**
     * 查询小区通知管理
     * 
     * @param id 小区通知管理主键
     * @return 小区通知管理
     */
    public CommunityNotice selectCommunityNoticeById(Long id);

    /**
     * 查询小区通知管理列表
     * 
     * @param communityNotice 小区通知管理
     * @return 小区通知管理集合
     */
    public List<CommunityNotice> selectCommunityNoticeList(CommunityNotice communityNotice);

    /**
     * 新增小区通知管理
     * 
     * @param communityNotice 小区通知管理
     * @return 结果
     */
    public int insertCommunityNotice(CommunityNotice communityNotice);

    /**
     * 修改小区通知管理
     * 
     * @param communityNotice 小区通知管理
     * @return 结果
     */
    public int updateCommunityNotice(CommunityNotice communityNotice);

    /**
     * 批量删除小区通知管理
     * 
     * @param ids 需要删除的小区通知管理主键集合
     * @return 结果
     */
    public int deleteCommunityNoticeByIds(Long[] ids);

    /**
     * 删除小区通知管理信息
     * 
     * @param id 小区通知管理主键
     * @return 结果
     */
    public int deleteCommunityNoticeById(Long id);
}
