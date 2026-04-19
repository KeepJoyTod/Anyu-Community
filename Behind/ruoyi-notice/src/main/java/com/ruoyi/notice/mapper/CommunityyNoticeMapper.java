package com.ruoyi.notice.mapper;

import java.util.List;
import com.ruoyi.notice.domain.CommunityyNotice;

/**
 * 小区通知管理Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface CommunityyNoticeMapper
{
    /**
     * 查询小区通知管理
     * 
     * @param id 小区通知管理主键
     * @return 小区通知管理
     */
    public CommunityyNotice selectCommunityNoticeById(Long id);

    /**
     * 查询小区通知管理列表
     * 
     * @param communityyNotice 小区通知管理
     * @return 小区通知管理集合
     */
    public List<CommunityyNotice> selectCommunityNoticeList(CommunityyNotice communityyNotice);

    /**
     * 新增小区通知管理
     * 
     * @param communityyNotice 小区通知管理
     * @return 结果
     */
    public int insertCommunityNotice(CommunityyNotice communityyNotice);

    /**
     * 修改小区通知管理
     * 
     * @param communityyNotice 小区通知管理
     * @return 结果
     */
    public int updateCommunityNotice(CommunityyNotice communityyNotice);

    /**
     * 删除小区通知管理
     * 
     * @param id 小区通知管理主键
     * @return 结果
     */
    public int deleteCommunityNoticeById(Long id);

    /**
     * 批量删除小区通知管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunityNoticeByIds(Long[] ids);
}
