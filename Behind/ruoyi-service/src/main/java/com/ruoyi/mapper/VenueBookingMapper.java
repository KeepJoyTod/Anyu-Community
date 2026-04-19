package com.ruoyi.mapper;

import com.ruoyi.domain.VenueBooking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 场地预约Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface VenueBookingMapper {
    int insert(VenueBooking venueBooking);
    List<VenueBooking> selectList();
    List<VenueBooking> selectByUserId(Long userId);
    VenueBooking selectById(Long id);
}