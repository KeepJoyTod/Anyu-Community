package com.ruoyi.visiter.service;

import java.util.List;
import com.ruoyi.visiter.domain.CommunityVisitor;

/**
 * 小区访客管理Service接口
 * 
 * @author ruoyi
 * @date 2026-03-29
 */
public interface ICommunityVisitorService 
{
    /**
     * 查询小区访客管理
     * 
     * @param id 小区访客管理主键
     * @return 小区访客管理
     */
    public CommunityVisitor selectCommunityVisitorById(Long id);

    /**
     * 查询小区访客管理列表
     * 
     * @param communityVisitor 小区访客管理
     * @return 小区访客管理集合
     */
    public List<CommunityVisitor> selectCommunityVisitorList(CommunityVisitor communityVisitor);

    /**
     * 新增小区访客管理
     * 
     * @param communityVisitor 小区访客管理
     * @return 结果
     */
    public int insertCommunityVisitor(CommunityVisitor communityVisitor);

    /**
     * 修改小区访客管理
     * 
     * @param communityVisitor 小区访客管理
     * @return 结果
     */
    public int updateCommunityVisitor(CommunityVisitor communityVisitor);

    /**
     * 批量删除小区访客管理
     * 
     * @param ids 需要删除的小区访客管理主键集合
     * @return 结果
     */
    public int deleteCommunityVisitorByIds(Long[] ids);

    /**
     * 删除小区访客管理信息
     * 
     * @param id 小区访客管理主键
     * @return 结果
     */
    public int deleteCommunityVisitorById(Long id);
}
