package com.ruoyi.notice.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.notice.mapper.CommunityyNoticeMapper;
import com.ruoyi.notice.domain.CommunityyNotice;
import com.ruoyi.notice.service.ICommunityyNoticeService;

/**
 * 小区通知管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@Service
public class CommunityyNoticeServiceImpl implements ICommunityyNoticeService
{
    @Autowired
    private CommunityyNoticeMapper communityyNoticeMapper;

    /**
     * 查询小区通知管理
     * 
     * @param id 小区通知管理主键
     * @return 小区通知管理
     */
    @Override
    public CommunityyNotice selectCommunityNoticeById(Long id)
    {
        return communityyNoticeMapper.selectCommunityNoticeById(id);
    }

    /**
     * 查询小区通知管理列表
     * 
     * @param communityyNotice 小区通知管理
     * @return 小区通知管理
     */
    @Override
    public List<CommunityyNotice> selectCommunityNoticeList(CommunityyNotice communityyNotice)
    {
        return communityyNoticeMapper.selectCommunityNoticeList(communityyNotice);
    }

    /**
     * 新增小区通知管理
     * 
     * @param communityyNotice 小区通知管理
     * @return 结果
     */
    @Override
    public int insertCommunityNotice(CommunityyNotice communityyNotice)
    {
        communityyNotice.setCreateTime(DateUtils.getNowDate());
        return communityyNoticeMapper.insertCommunityNotice(communityyNotice);
    }

    /**
     * 修改小区通知管理
     * 
     * @param communityyNotice 小区通知管理
     * @return 结果
     */
    @Override
    public int updateCommunityNotice(CommunityyNotice communityyNotice)
    {
        communityyNotice.setUpdateTime(DateUtils.getNowDate());
        return communityyNoticeMapper.updateCommunityNotice(communityyNotice);
    }

    /**
     * 批量删除小区通知管理
     * 
     * @param ids 需要删除的小区通知管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityNoticeByIds(Long[] ids)
    {
        return communityyNoticeMapper.deleteCommunityNoticeByIds(ids);
    }

    /**
     * 删除小区通知管理信息
     * 
     * @param id 小区通知管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityNoticeById(Long id)
    {
        return communityyNoticeMapper.deleteCommunityNoticeById(id);
    }
}
