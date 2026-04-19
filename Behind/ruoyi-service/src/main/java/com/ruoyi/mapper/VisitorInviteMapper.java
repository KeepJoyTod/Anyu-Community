package com.ruoyi.mapper;

import com.ruoyi.domain.VisitorInvite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 访客邀请Mapper接口（适配community_visitor表）
 *
 * @author ruoyi
 */
@Mapper
public interface VisitorInviteMapper {
    /**
     * 根据ID查询访客邀请
     * @param id 主键ID
     * @return 访客邀请信息
     */
    VisitorInvite selectById(Long id);
    
    /**
     * 查询最近的访客邀请列表
     * @param limit 条数限制
     * @return 访客邀请集合
     */
    List<VisitorInvite> selectRecentVisitorInvite(@Param("limit") Integer limit);

    /**
     * 更新访客签入状态
     * @param id 主键ID
     * @return 影响行数
     */
    int updateVisitorSignInStatus(Long id);
    
    /**
     * 新增访客邀请
     * @param visitorInvite 访客邀请信息
     * @return 影响行数
     */
    int insert(VisitorInvite visitorInvite);
}