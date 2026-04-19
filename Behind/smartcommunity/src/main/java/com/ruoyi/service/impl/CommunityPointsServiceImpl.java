package com.ruoyi.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.domain.CommunityPoints;
import com.ruoyi.mapper.CommunityPointsMapper;
import com.ruoyi.service.ICommunityPointsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 积分表 服务实现类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Service
public class CommunityPointsServiceImpl extends ServiceImpl<CommunityPointsMapper, CommunityPoints> implements ICommunityPointsService {
    @Autowired
    private  CommunityPointsMapper communityPointsMapper;


    @Override
    public Integer getMyPointsByName(String username) {
        Integer communityPoints = communityPointsMapper.getMyPointsByName(username);
        return communityPoints;
    }
}
