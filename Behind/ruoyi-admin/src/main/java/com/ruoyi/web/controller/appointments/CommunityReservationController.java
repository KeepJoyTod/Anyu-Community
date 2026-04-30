package com.ruoyi.web.controller.appointments;

import com.ruoyi.appointments.domain.CommunityReservation;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.VenueBooking;
import com.ruoyi.service.IVenueBookingService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments/appointments")
public class CommunityReservationController extends BaseController {
    @Autowired
    private IVenueBookingService venueBookingService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityReservation communityReservation) {
        startPage();
        List<CommunityReservation> list = venueBookingService.listBookings(toBookingQuery(communityReservation))
                .stream()
                .map(this::toReservation)
                .collect(Collectors.toList());
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityReservation communityReservation) {
        List<CommunityReservation> list = venueBookingService.listBookings(toBookingQuery(communityReservation))
                .stream()
                .map(this::toReservation)
                .collect(Collectors.toList());
        ExcelUtil<CommunityReservation> util = new ExcelUtil<>(CommunityReservation.class);
        util.exportExcel(response, list, "venue_booking");
    }

    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        VenueBooking booking = venueBookingService.getBookingById(id);
        return success(booking == null ? null : toReservation(booking));
    }

    @Log(title = "venue_booking", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunityReservation communityReservation) {
        return toAjax(venueBookingService.createBooking(toBooking(communityReservation)));
    }

    @Log(title = "venue_booking", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityReservation communityReservation) {
        return toAjax(venueBookingService.updateBooking(toBooking(communityReservation)));
    }

    @Log(title = "venue_booking", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(venueBookingService.deleteBookingsByIds(ids));
    }

    private VenueBooking toBookingQuery(CommunityReservation reservation) {
        VenueBooking booking = new VenueBooking();
        if (reservation == null) {
            return booking;
        }
        booking.setId(reservation.getId());
        booking.setUserId(reservation.getUserId());
        booking.setUserName(reservation.getUserName());
        booking.setUserPhone(reservation.getUserPhone());
        booking.setSpaceName(firstNonBlank(reservation.getServiceAddress(), reservation.getReservationType()));
        booking.setReserveDate(toLocalDate(reservation.getReservationTime()));
        booking.setStatus(reservation.getReservationStatus());
        booking.setRejectReason(reservation.getRejectReason());
        return booking;
    }

    private VenueBooking toBooking(CommunityReservation reservation) {
        VenueBooking booking = toBookingQuery(reservation);
        booking.setReservationPurpose(reservation.getRemark());
        booking.setRemark(reservation.getRemark());
        if (booking.getStatus() == null || booking.getStatus().isEmpty()) {
            booking.setStatus("\u5f85\u4f7f\u7528");
        }
        return booking;
    }

    private CommunityReservation toReservation(VenueBooking booking) {
        CommunityReservation reservation = new CommunityReservation();
        reservation.setId(booking.getId());
        reservation.setUserId(booking.getUserId());
        reservation.setUserName(booking.getUserName());
        reservation.setUserPhone(booking.getUserPhone());
        reservation.setReservationType(booking.getSpaceName());
        reservation.setReservationTime(toDate(booking.getReserveDate()));
        reservation.setReservationStatus(booking.getStatus());
        reservation.setServiceAddress(booking.getSpaceName());
        reservation.setRejectReason(booking.getRejectReason());
        reservation.setRemark(firstNonBlank(booking.getReservationPurpose(), booking.getRemark()));
        reservation.setCreateTime(toDate(booking.getCreateTime()));
        reservation.setUpdateTime(toDate(booking.getUpdateTime()));
        return reservation;
    }

    private LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date toDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private String firstNonBlank(String first, String second) {
        return first != null && !first.isEmpty() ? first : second;
    }
}
