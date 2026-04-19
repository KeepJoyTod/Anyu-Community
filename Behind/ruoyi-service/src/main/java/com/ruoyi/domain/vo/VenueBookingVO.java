package com.ruoyi.domain.vo;

import com.ruoyi.domain.Venue;
import com.ruoyi.domain.VenueBooking;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 场地预约VO类，包含场地信息和预约信息
 */
@Data
public class VenueBookingVO extends VenueBooking {
    // 场地信息
    private Long venueId;
    private String venueName;
    private String venueIcon;
    private String venueDescription;
    private BigDecimal venuePrice;
    private LocalDateTime venueCreatedAt;
    private LocalDateTime venueUpdatedAt;

    // 构造方法
    public VenueBookingVO() {
    }

    // 从Venue和VenueBooking构造VO
    public VenueBookingVO(Venue venue, VenueBooking booking) {
        if (booking != null) {
            setId(booking.getId());
            setSpaceName(booking.getSpaceName());
            setUserId(booking.getUserId());
            setUserName(booking.getUserName());
            setUserPhone(booking.getUserPhone());
            setReserveDate(booking.getReserveDate());
            setStartTime(booking.getStartTime());
            setEndTime(booking.getEndTime());
            setReservationPurpose(booking.getReservationPurpose());
            setStatus(booking.getStatus());
            setRejectReason(booking.getRejectReason());
            setApproverId(booking.getApproverId());
            setApproveTime(booking.getApproveTime());
            setCreateTime(booking.getCreateTime());
            setUpdateTime(booking.getUpdateTime());
            setRemark(booking.getRemark());
        }

        if (venue != null) {
            this.venueId = venue.getId();
            this.venueName = venue.getName();
            this.venueIcon = venue.getIcon();
            this.venueDescription = venue.getDesc();
            this.venuePrice = venue.getPrice();
            this.venueCreatedAt = venue.getCreatedAt();
            this.venueUpdatedAt = venue.getUpdatedAt();
        }
    }

    // getter和setter方法
    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
    public String getVenueIcon() { return venueIcon; }
    public void setVenueIcon(String venueIcon) { this.venueIcon = venueIcon; }
    public String getVenueDescription() { return venueDescription; }
    public void setVenueDescription(String venueDescription) { this.venueDescription = venueDescription; }
    public BigDecimal getVenuePrice() { return venuePrice; }
    public void setVenuePrice(BigDecimal venuePrice) { this.venuePrice = venuePrice; }
    public LocalDateTime getVenueCreatedAt() { return venueCreatedAt; }
    public void setVenueCreatedAt(LocalDateTime venueCreatedAt) { this.venueCreatedAt = venueCreatedAt; }
    public LocalDateTime getVenueUpdatedAt() { return venueUpdatedAt; }
    public void setVenueUpdatedAt(LocalDateTime venueUpdatedAt) { this.venueUpdatedAt = venueUpdatedAt; }
}
