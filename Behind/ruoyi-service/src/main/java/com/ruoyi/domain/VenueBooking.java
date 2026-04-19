package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 场地预约实体类
 */
@TableName("venue_booking")
public class VenueBooking {
    @TableId(type = IdType.AUTO)
    @TableField("reservation_id")
    private Long id; // 预约ID (主键)
    private String spaceName; // 场地名称
    private Long userId; // 预约人ID
    private String userName; // 预约人姓名
    private String userPhone; // 预约人电话
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reserveDate; // 预约日期
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime; // 使用开始时间
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime; // 使用结束时间
    private String reservationPurpose; // 预约用途
    @TableField("reservation_status")
    private String status; // 预约状态
    private String rejectReason; // 拒绝原因
    private Long approverId; // 审核人ID
    private LocalDateTime approveTime; // 审核时间
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private String remark; // 备注

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSpaceName() { return spaceName; }
    public void setSpaceName(String spaceName) { this.spaceName = spaceName; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
    public LocalDate getReserveDate() { return reserveDate; }
    public void setReserveDate(LocalDate reserveDate) { this.reserveDate = reserveDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public String getReservationPurpose() { return reservationPurpose; }
    public void setReservationPurpose(String reservationPurpose) { this.reservationPurpose = reservationPurpose; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
    public Long getApproverId() { return approverId; }
    public void setApproverId(Long approverId) { this.approverId = approverId; }
    public LocalDateTime getApproveTime() { return approveTime; }
    public void setApproveTime(LocalDateTime approveTime) { this.approveTime = approveTime; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}