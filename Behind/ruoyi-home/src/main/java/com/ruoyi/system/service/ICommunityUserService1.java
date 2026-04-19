package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CommunityUser;

/**
 * 社区住户信息Service接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface ICommunityUserService1 
{
    /**
     * 查询社区住户信息
     * 
     * @param id 社区住户信息主键
     * @return 社区住户信息
     */
    public CommunityUser selectCommunityUserById(String id);

    /**
     * 查询社区住户信息列表
     * 
     * @param communityUser 社区住户信息
     * @return 社区住户信息集合
     */
    public List<CommunityUser> selectCommunityUserList(CommunityUser communityUser);

    /**
     * 新增社区住户信息
     * 
     * @param communityUser 社区住户信息
     * @return 结果
     */
    public int insertCommunityUser(CommunityUser communityUser);

    /**
     * 修改社区住户信息
     * 
     * @param communityUser 社区住户信息
     * @return 结果
     */
    public int updateCommunityUser(CommunityUser communityUser);

    /**
     * 批量删除社区住户信息
     * 
     * @param ids 需要删除的社区住户信息主键集合
     * @return 结果
     */
    public int deleteCommunityUserByIds(String[] ids);

    /**
     * 删除社区住户信息信息
     * 
     * @param id 社区住户信息主键
     * @return 结果
     */
    public int deleteCommunityUserById(String id);

    Integer getUserNum();

    Integer getUserCount1();

    Integer getUserCount2();

    Integer getUserCount3();

    Integer getUserCount4();

    Integer getUserCount5();
}
