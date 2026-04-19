package com.ruoyi.web.controller.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.domain.Venue;
import com.ruoyi.domain.VenueBooking;
import com.ruoyi.service.IVenueBookingService;
import com.ruoyi.service.IVenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 场地预约控制器
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/venue")
@CrossOrigin
public class VenueController
{
    @Autowired
    private IVenueService venueService;
    
    @Autowired
    private IVenueBookingService venueBookingService;

    /**
     * 获取场地列表
     */
    @GetMapping("/list")
    public AjaxResult listVenues() {
        List<Venue> venues = venueService.listVenues();
        return AjaxResult.success(venues);
    }

    /**
     * 创建场地预约
     */
    @PostMapping("/booking")
    public AjaxResult createBooking(@RequestBody VenueBooking venueBooking) {
        boolean success = venueBookingService.createBooking(venueBooking);
        if (success) {
            return AjaxResult.success("预约提交成功，等待审核");
        } else {
            return AjaxResult.error("预约提交失败，请重试");
        }
    }

    /**
     * 获取场地预约列表
     */
    @GetMapping("/bookings")
    public AjaxResult getBookings() {
        // 暂时使用固定用户ID 1，实际项目中应该从登录信息中获取
        List<VenueBooking> bookings = venueBookingService.getBookingsByUserId(1L);
        return AjaxResult.success(bookings);
    }

    /**
     * 获取场地预约详情
     */
    @GetMapping("/booking/{id}")
    public AjaxResult getBookingDetail(@PathVariable Long id) {
        VenueBooking booking = venueBookingService.getBookingById(id);
        if (booking != null) {
            return AjaxResult.success(booking);
        } else {
            return AjaxResult.error("预约不存在");
        }
    }
}

