package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityMedal;
import com.ruoyi.mapper.CommunityMedalMapper;
import com.ruoyi.service.ICommunityMedalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 勋章表 服务实现类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Service
public class CommunityMedalServiceImpl extends ServiceImpl<CommunityMedalMapper, CommunityMedal> implements ICommunityMedalService {
    @Autowired
    private CommunityMedalMapper communityMedalMapper;

    @Override
    public Integer getMyHallByName(String username) {
        Integer getMyHallByName = communityMedalMapper.getMyHallByName(username);
        return getMyHallByName;
    }
}
