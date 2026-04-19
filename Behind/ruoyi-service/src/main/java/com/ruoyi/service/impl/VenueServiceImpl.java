package com.ruoyi.service.impl;

import com.ruoyi.domain.Venue;
import com.ruoyi.mapper.VenueMapper;
import com.ruoyi.service.IVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地服务实现类
 *
 * @author ruoyi
 */
@Service
public class VenueServiceImpl implements IVenueService {
    
    @Autowired
    private VenueMapper venueMapper;
    
    @Override
    public List<Venue> listVenues() {
        // 使用实际的数据库操作获取场地列表
        return venueMapper.selectList();
    }
}