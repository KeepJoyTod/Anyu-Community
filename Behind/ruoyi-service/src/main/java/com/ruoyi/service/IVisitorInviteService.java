package com.ruoyi.service;

import com.ruoyi.domain.VisitorInvite;

import java.util.List;

/**
 * 访客邀请Service接口
 *
 * @author ruoyi
 */
public interface IVisitorInviteService {
    /**
     * 根据ID查询访客邀请
     * @param id 主键ID
     * @return 访客邀请信息
     */
    VisitorInvite selectVisitorInviteById(Long id);

    /**
     * 查询最近的访客邀请列表
     * @param limit 条数限制
     * @return 访客邀请集合
     */
    List<VisitorInvite> selectRecentVisitorInvite(Integer limit);

    /**
     * 新增访客邀请
     * @param visitorInvite 访客邀请信息
     * @return 影响行数
     */
    int insertVisitorInvite(VisitorInvite visitorInvite);

    /**
     * 访客签入操作
     * @param id 主键ID
     * @return 影响行数
     */
    int signInVisitor(Long id);
}