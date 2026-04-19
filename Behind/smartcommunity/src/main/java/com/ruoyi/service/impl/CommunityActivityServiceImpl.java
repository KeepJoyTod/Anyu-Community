package com.ruoyi.service.impl;

import com.ruoyi.mapper.CommunityRepairrMapper;
import com.ruoyi.domain.CommunityActivity;
import com.ruoyi.mapper.CommunityActivityMapper;
import com.ruoyi.service.ICommunityActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 社区活动表 服务实现类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Service
public class CommunityActivityServiceImpl extends ServiceImpl<CommunityActivityMapper, CommunityActivity> implements ICommunityActivityService {
@Autowired
private CommunityRepairrMapper communityRepairMapper;
    @Override
    public List<String> getRepairList() {
List<String>  repairList =communityRepairMapper.selectGetRepairList();
return repairList;
    }
}
