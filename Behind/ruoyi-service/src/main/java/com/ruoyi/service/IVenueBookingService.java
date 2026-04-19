package com.ruoyi.service;

import com.ruoyi.domain.VenueBooking;

import java.util.List;

/**
 * 场地预约服务接口
 * 
 * @author ruoyi
 */
public interface IVenueBookingService
{
    /**
     * 创建场地预约
     * 
     * @param venueBooking 预约信息
     * @return 是否创建成功
     */
    boolean createBooking(VenueBooking venueBooking);

    /**
     * 获取用户的场地预约列表
     * 
     * @param userId 用户ID
     * @return 预约列表
     */
    List<VenueBooking> getBookingsByUserId(Long userId);

    /**
     * 获取所有场地预约列表
     * 
     * @return 预约列表
     */
    List<VenueBooking> listBookings();

    /**
     * 根据ID获取场地预约详情
     * 
     * @param id 预约ID
     * @return 预约详情
     */
    VenueBooking getBookingById(Long id);
}
