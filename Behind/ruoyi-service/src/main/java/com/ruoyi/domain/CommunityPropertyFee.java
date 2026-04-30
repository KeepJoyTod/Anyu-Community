package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommunityPropertyFee {
    private Long propertyFeeId;
    private Long userId;
    private String userName;
    private String userRoom;
    private String propertyMonth;
    private BigDecimal houseArea;
    private BigDecimal unitPrice;
    private BigDecimal totalFee;
    private String feeStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;
    private String paymentNo;
    private BigDecimal overdueAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String remark;
}
