package com.ruoyi.service.impl;

import com.ruoyi.domain.VenueBooking;
import com.ruoyi.mapper.VenueBookingMapper;
import com.ruoyi.service.IVenueBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VenueBookingServiceImpl implements IVenueBookingService {

    @Autowired
    private VenueBookingMapper venueBookingMapper;

    @Override
    public boolean createBooking(VenueBooking venueBooking) {
        if (venueBooking.getStatus() == null || venueBooking.getStatus().isEmpty()) {
            venueBooking.setStatus("\u5f85\u4f7f\u7528");
        }
        LocalDateTime now = LocalDateTime.now();
        venueBooking.setCreateTime(now);
        venueBooking.setUpdateTime(now);
        return venueBookingMapper.insert(venueBooking) > 0;
    }

    @Override
    public List<VenueBooking> getBookingsByUserId(Long userId) {
        return venueBookingMapper.selectByUserId(userId);
    }

    @Override
    public List<VenueBooking> listBookings() {
        return venueBookingMapper.selectList();
    }

    @Override
    public List<VenueBooking> listBookings(VenueBooking venueBooking) {
        return venueBookingMapper.selectListByQuery(venueBooking);
    }

    @Override
    public VenueBooking getBookingById(Long id) {
        return venueBookingMapper.selectById(id);
    }

    @Override
    public int updateBooking(VenueBooking venueBooking) {
        venueBooking.setUpdateTime(LocalDateTime.now());
        return venueBookingMapper.updateById(venueBooking);
    }

    @Override
    public int deleteBookingsByIds(Long[] ids) {
        return venueBookingMapper.deleteByIds(ids);
    }
}
