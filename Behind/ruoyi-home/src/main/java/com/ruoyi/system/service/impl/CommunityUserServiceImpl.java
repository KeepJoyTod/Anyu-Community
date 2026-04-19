package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CommunityUserMapper;
import com.ruoyi.system.domain.CommunityUser1;
import com.ruoyi.system.service.ICommunityUserService;

/**
 * 社区住户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
@Service
public class CommunityUserServiceImpl implements ICommunityUserService 
{
    @Autowired
    private CommunityUserMapper communityUserMapper;

    /**
     * 查询社区住户信息
     * 
     * @param id 社区住户信息主键
     * @return 社区住户信息
     */
    @Override
    public CommunityUser1 selectCommunityUserById(String id)
    {
        return communityUserMapper.selectCommunityUserById(id);
    }

    /**
     * 查询社区住户信息列表
     * 
     * @param communityUser 社区住户信息
     * @return 社区住户信息
     */
    @Override
    public List<CommunityUser1> selectCommunityUserList(CommunityUser1 communityUser)
    {
        return communityUserMapper.selectCommunityUserList(communityUser);
    }

    /**
     * 新增社区住户信息
     * 
     * @param communityUser 社区住户信息
     * @return 结果
     */
    @Override
    public int insertCommunityUser(CommunityUser1 communityUser)
    {
        communityUser.setCreateTime(DateUtils.getNowDate());
        return communityUserMapper.insertCommunityUser(communityUser);
    }

    /**
     * 修改社区住户信息
     * 
     * @param communityUser 社区住户信息
     * @return 结果
     */
    @Override
    public int updateCommunityUser(CommunityUser1 communityUser)
    {
        communityUser.setUpdateTime(DateUtils.getNowDate());
        return communityUserMapper.updateCommunityUser(communityUser);
    }

    /**
     * 批量删除社区住户信息
     * 
     * @param ids 需要删除的社区住户信息主键
     * @return 结果
     */
    @Override
    public int deleteCommunityUserByIds(String[] ids)
    {
        return communityUserMapper.deleteCommunityUserByIds(ids);
    }

    /**
     * 删除社区住户信息信息
     * 
     * @param id 社区住户信息主键
     * @return 结果
     */
    @Override
    public int deleteCommunityUserById(String id)
    {
        return communityUserMapper.deleteCommunityUserById(id);
    }

    @Override
    public Integer getUserNum() {
        Integer userNum = communityUserMapper.havaUserNum();
        return userNum;
    }

    @Override
    public Integer getUserCount1() {
        Integer userCount1=communityUserMapper.haveUserCount1();
    return userCount1;
    }

    @Override
    public Integer getUserCount2() {
        Integer userCount2=communityUserMapper.haveUserCount2();
        return userCount2;
    }

    @Override
    public Integer getUserCount3() {
        Integer userCount3=communityUserMapper.haveUserCount3();
        return userCount3;
    }

    @Override
    public Integer getUserCount4() {
        Integer userCount4=communityUserMapper.haveUserCount4();
        return userCount4;
    }

    @Override
    public Integer getUserCount5() {
        Integer userCount5=communityUserMapper.haveUserCount5();
        return userCount5;
    }


}
