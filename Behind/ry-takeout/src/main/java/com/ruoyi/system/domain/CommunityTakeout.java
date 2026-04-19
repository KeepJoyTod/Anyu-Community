package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小区外卖订单对象 community_takeout
 * 
 * @author dengdeng
 * @date 2026-03-20
 */
public class CommunityTakeout extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID（关联sys_user表user_id） */
    @Excel(name = "用户ID", readConverterExp = "关=联sys_user表user_id")
    private Long userId;

    /** 用户姓名（冗余，关联sys_user表nick_name） */
    @Excel(name = "用户姓名", readConverterExp = "冗=余，关联sys_user表nick_name")
    private String userName;

    /** 用户手机号（冗余，关联sys_user表phonenumber） */
    @Excel(name = "用户手机号", readConverterExp = "冗=余，关联sys_user表phonenumber")
    private String userPhone;

    /** 用户房号（冗余，与社区其他表保持一致） */
    @Excel(name = "用户房号", readConverterExp = "冗=余，与社区其他表保持一致")
    private String userRoom;

    /** 订单号（唯一） */
    @Excel(name = "订单号", readConverterExp = "唯=一")
    private String orderNo;

    /** 商家名称 */
    @Excel(name = "商家名称")
    private String shopName;

    /** 商家电话 */
    @Excel(name = "商家电话")
    private String shopPhone;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private BigDecimal totalAmount;

    /** 配送费 */
    @Excel(name = "配送费")
    private BigDecimal deliveryFee;

    /** 实付金额 */
    @Excel(name = "实付金额")
    private BigDecimal actualAmount;

    /** 订单状态（pending:待支付, paid:已支付, preparing:备餐中, delivering:配送中, completed:已完成, cancelled:已取消, refunding:退款中） */
    @Excel(name = "订单状态", readConverterExp = "p=ending:待支付,,p=aid:已支付,,p=reparing:备餐中,,d=elivering:配送中,,c=ompleted:已完成,,c=ancelled:已取消,,r=efunding:退款中")
    private String orderStatus;

    /** 支付方式（wechat:微信, alipay:支付宝, card:刷卡） */
    @Excel(name = "支付方式", readConverterExp = "w=echat:微信,,a=lipay:支付宝,,c=ard:刷卡")
    private String paymentMethod;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;

    /** 预计送达时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计送达时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

    /** 实际送达时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际送达时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualDeliveryTime;

    /** 配送地址 */
    @Excel(name = "配送地址")
    private String deliveryAddress;

    /** 取消原因 */
    @Excel(name = "取消原因")
    private String cancelReason;

    /** 关联预约ID（关联community_reservation表id，可选） */
    @Excel(name = "关联预约ID", readConverterExp = "关=联community_reservation表id，可选")
    private Long reservationId;

    /** 关联投诉ID（关联community_complaint表id，可选） */
    @Excel(name = "关联投诉ID", readConverterExp = "关=联community_complaint表id，可选")
    private Long complaintId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setUserPhone(String userPhone) 
    {
        this.userPhone = userPhone;
    }

    public String getUserPhone() 
    {
        return userPhone;
    }

    public void setUserRoom(String userRoom) 
    {
        this.userRoom = userRoom;
    }

    public String getUserRoom() 
    {
        return userRoom;
    }

    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    public void setShopName(String shopName) 
    {
        this.shopName = shopName;
    }

    public String getShopName() 
    {
        return shopName;
    }

    public void setShopPhone(String shopPhone) 
    {
        this.shopPhone = shopPhone;
    }

    public String getShopPhone() 
    {
        return shopPhone;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) 
    {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getDeliveryFee() 
    {
        return deliveryFee;
    }

    public void setActualAmount(BigDecimal actualAmount) 
    {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getActualAmount() 
    {
        return actualAmount;
    }

    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }

    public void setPaymentMethod(String paymentMethod) 
    {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() 
    {
        return paymentMethod;
    }

    public void setPaymentTime(Date paymentTime) 
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime() 
    {
        return paymentTime;
    }

    public void setDeliveryTime(Date deliveryTime) 
    {
        this.deliveryTime = deliveryTime;
    }

    public Date getDeliveryTime() 
    {
        return deliveryTime;
    }

    public void setActualDeliveryTime(Date actualDeliveryTime) 
    {
        this.actualDeliveryTime = actualDeliveryTime;
    }

    public Date getActualDeliveryTime() 
    {
        return actualDeliveryTime;
    }

    public void setDeliveryAddress(String deliveryAddress) 
    {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() 
    {
        return deliveryAddress;
    }

    public void setCancelReason(String cancelReason) 
    {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason() 
    {
        return cancelReason;
    }

    public void setReservationId(Long reservationId) 
    {
        this.reservationId = reservationId;
    }

    public Long getReservationId() 
    {
        return reservationId;
    }

    public void setComplaintId(Long complaintId) 
    {
        this.complaintId = complaintId;
    }

    public Long getComplaintId() 
    {
        return complaintId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userPhone", getUserPhone())
            .append("userRoom", getUserRoom())
            .append("orderNo", getOrderNo())
            .append("shopName", getShopName())
            .append("shopPhone", getShopPhone())
            .append("totalAmount", getTotalAmount())
            .append("deliveryFee", getDeliveryFee())
            .append("actualAmount", getActualAmount())
            .append("orderStatus", getOrderStatus())
            .append("paymentMethod", getPaymentMethod())
            .append("paymentTime", getPaymentTime())
            .append("deliveryTime", getDeliveryTime())
            .append("actualDeliveryTime", getActualDeliveryTime())
            .append("deliveryAddress", getDeliveryAddress())
            .append("remark", getRemark())
            .append("cancelReason", getCancelReason())
            .append("reservationId", getReservationId())
            .append("complaintId", getComplaintId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
