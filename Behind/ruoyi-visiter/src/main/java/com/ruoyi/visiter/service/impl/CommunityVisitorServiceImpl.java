package com.ruoyi.visiter.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.visiter.mapper.CommunityVisitorMapper;
import com.ruoyi.visiter.domain.CommunityVisitor;
import com.ruoyi.visiter.service.ICommunityVisitorService;

/**
 * 小区访客管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-29
 */
@Service
public class CommunityVisitorServiceImpl implements ICommunityVisitorService 
{
    @Autowired
    private CommunityVisitorMapper communityVisitorMapper;

    /**
     * 查询小区访客管理
     * 
     * @param id 小区访客管理主键
     * @return 小区访客管理
     */
    @Override
    public CommunityVisitor selectCommunityVisitorById(Long id)
    {
        return communityVisitorMapper.selectCommunityVisitorById(id);
    }

    /**
     * 查询小区访客管理列表
     * 
     * @param communityVisitor 小区访客管理
     * @return 小区访客管理
     */
    @Override
    public List<CommunityVisitor> selectCommunityVisitorList(CommunityVisitor communityVisitor)
    {
        return communityVisitorMapper.selectCommunityVisitorList(communityVisitor);
    }

    /**
     * 新增小区访客管理
     * 
     * @param communityVisitor 小区访客管理
     * @return 结果
     */
    @Override
    public int insertCommunityVisitor(CommunityVisitor communityVisitor)
    {
        communityVisitor.setCreateTime(DateUtils.getNowDate());
        return communityVisitorMapper.insertCommunityVisitor(communityVisitor);
    }

    /**
     * 修改小区访客管理
     * 
     * @param communityVisitor 小区访客管理
     * @return 结果
     */
    @Override
    public int updateCommunityVisitor(CommunityVisitor communityVisitor)
    {
        communityVisitor.setUpdateTime(DateUtils.getNowDate());
        return communityVisitorMapper.updateCommunityVisitor(communityVisitor);
    }

    /**
     * 批量删除小区访客管理
     * 
     * @param ids 需要删除的小区访客管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityVisitorByIds(Long[] ids)
    {
        return communityVisitorMapper.deleteCommunityVisitorByIds(ids);
    }

    /**
     * 删除小区访客管理信息
     * 
     * @param id 小区访客管理主键
     * @return 结果
     */
    @Override
    public int deleteCommunityVisitorById(Long id)
    {
        return communityVisitorMapper.deleteCommunityVisitorById(id);
    }
}
