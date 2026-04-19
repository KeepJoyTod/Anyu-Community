package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CommunityNoticeMapper;
import com.ruoyi.system.domain.CommunityNotice1;
import com.ruoyi.system.service.ICommunityNoticeService;

/**
 * 小区通知管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
// @Service  // disabled to avoid duplicate bean conflict
public class CommunityNoticeServiceImpl1 implements ICommunityNoticeService 
{
    @Autowired
    private CommunityNoticeMapper communityNoticeMapper;

    /**
     * 查询小区通知管理
     * 
     * @param id 小区通知管理主键
     * @return 小区通知管理
     */
    @Override
    public CommunityNotice1 selectCommunityNoticeById(Long id)
    {
        return communityNoticeMapper.selectCommunityNoticeById(id);
    }

    /**
     * 查询小区通知管理列表
     * 
     * @param communityNotice 小区通知管理
     * @return 小区通知管理
     */
    @Override
    public List<CommunityNotice1> selectCommunityNoticeList(CommunityNotice1 communityNotice)
    {
        return communityNoticeMapper.selectCommunityNoticeList(communityNotice);
    }

    /**
     * 新增小区通知管理
     * 
     * @param communityNotice 小区通知管理
     * @return 结果
     */
    @Override
    public int insertCommunityNotice(CommunityNotice1 communityNotice)
    {
        communityNotice.setCreateTime(DateUtils.getNowDate());
        return communityNoticeMapper.insertCommunityNotice(communityNotice);
    }

    /**
     * 修改小区通知管理
     * 
     * @param communityNotice 小区通知管理
     * @return 结果
     */
    @Override
    public int updateCommunityNotice(CommunityNotice1 communityNotice)
    {
        communityNotice.setUpdateTime(DateUtils.getNowDate());
        return communityNoticeMapper.updateCommunityNotice(communityNotice);
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
        return communityNoticeMapper.deleteCommunityNoticeByIds(ids);
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
        return communityNoticeMapper.deleteCommunityNoticeById(id);
    }

    @Override
    public Integer getNoticeNum() {
        Integer noticeNum = communityNoticeMapper.haveNoticeNum();
        return noticeNum;
    }
}
