package com.ruoyi.service.impl;

import com.ruoyi.domain.VenueBooking;
import com.ruoyi.mapper.VenueBookingMapper;
import com.ruoyi.service.IVenueBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 场地预约服务实现类
 *
 * @author ruoyi
 */
@Service
public class VenueBookingServiceImpl implements IVenueBookingService {
    
    @Autowired
    private VenueBookingMapper venueBookingMapper;
    
    @Override
    public boolean createBooking(VenueBooking venueBooking) {
        // 设置默认预约状态为"待使用"
        venueBooking.setStatus("待使用");
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        venueBooking.setCreateTime(now);
        venueBooking.setUpdateTime(now);
        // 使用实际的数据库操作保存预约信息
        return venueBookingMapper.insert(venueBooking) > 0;
    }

    @Override
    public List<VenueBooking> getBookingsByUserId(Long userId) {
        // 使用实际的数据库操作获取用户的预约列表
        return venueBookingMapper.selectByUserId(userId);
    }

    @Override
    public List<VenueBooking> listBookings() {
        // 使用实际的数据库操作获取所有预约列表
        return venueBookingMapper.selectList();
    }

    @Override
    public VenueBooking getBookingById(Long id) {
        // 使用实际的数据库操作获取预约详情
        return venueBookingMapper.selectById(id);
    }
}