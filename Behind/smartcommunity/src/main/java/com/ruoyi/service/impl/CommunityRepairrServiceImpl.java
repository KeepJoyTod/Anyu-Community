package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityRepairr;
import com.ruoyi.mapper.CommunityRepairrMapper;
import com.ruoyi.service.ICommunityRepairrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 维修表 服务实现类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Service
public class CommunityRepairrServiceImpl extends ServiceImpl<CommunityRepairrMapper, CommunityRepairr> implements ICommunityRepairrService {
@Autowired
private CommunityRepairrMapper communityRepairrMapper;
    @Override
    public List<String> getList() {
List<String> getList=    communityRepairrMapper.selectGetRepairList();
return getList;
    }
}
