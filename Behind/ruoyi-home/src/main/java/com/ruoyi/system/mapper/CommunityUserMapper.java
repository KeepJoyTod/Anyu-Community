package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.CommunityUser1;

/**
 * 社区住户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public interface CommunityUserMapper 
{
    /**
     * 查询社区住户信息
     * 
     * @param id 社区住户信息主键
     * @return 社区住户信息
     */
    public CommunityUser1 selectCommunityUserById(String id);

    /**
     * 查询社区住户信息列表
     * 
     * @param communityUser 社区住户信息
     * @return 社区住户信息集合
     */
    public List<CommunityUser1> selectCommunityUserList(CommunityUser1 communityUser);

    /**
     * 新增社区住户信息
     * 
     * @param communityUser 社区住户信息
     * @return 结果
     */
    public int insertCommunityUser(CommunityUser1 communityUser);

    /**
     * 修改社区住户信息
     * 
     * @param communityUser 社区住户信息
     * @return 结果
     */
    public int updateCommunityUser(CommunityUser1 communityUser);

    /**
     * 删除社区住户信息
     * 
     * @param id 社区住户信息主键
     * @return 结果
     */
    public int deleteCommunityUserById(String id);

    /**
     * 批量删除社区住户信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommunityUserByIds(String[] ids);

    Integer havaUserNum();

    Integer haveUserCount1();

    Integer haveUserCount2();

    Integer haveUserCount3();

    Integer haveUserCount4();

    Integer haveUserCount5();
}
