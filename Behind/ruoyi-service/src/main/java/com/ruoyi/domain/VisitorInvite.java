package com.ruoyi.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 访客邀请实体类（适配community_visitor表 + RuoYi框架Date类型）
 *
 * @author ruoyi
 */
@Data
public class VisitorInvite extends BaseEntity {
    private static long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 业主ID */
    private Long ownerId;

    /** 业主姓名 */
    private String ownerName;

    /** 访客姓名（对应前端name字段） */
    private String visitorName;

    /** 访客电话（对应前端phone字段） */
    private String visitorPhone;

    /** 访客身份证号 */
    private String visitorIdCard;

    /** 来访事由（对应前端reason字段） */
    private String visitReason;

    /** 预计来访时间 */
    private Date expectedVisitTime;
    
    /** 前端传递的预计来访时间字符串 */
    private String expectedVisitTimeStr;

    /** 实际来访时间（签入时间） */
    private Date actualVisitTime;

    /** 离开时间 */
    private Date leaveTime;

    /** 状态(0:待来访,1:已到访,2:已离开,3:已取消) */
    private String status;

    /** 邀请码 */
    private String inviteCode;

    /** 小区ID */
    private Long communityId;

    /** 楼栋ID */
    private String buildingId;

    /** 单元 */
    private String unit;

    /** 房间号 */
    private String room;

    /** 前端传递的日期字符串 */
    private String date;

    // 【兼容前端】原有前端用name/phone/reason/visitTime，适配前端传参
    public String getName() {
        return this.visitorName;
    }
    public void setName(String name) {
        this.visitorName = name;
    }
    public String getPhone() {
        return this.visitorPhone;
    }
    public void setPhone(String phone) {
        this.visitorPhone = phone;
    }
    public String getReason() {
        return this.visitReason;
    }
    public void setReason(String reason) {
        this.visitReason = reason;
    }
    public Date getVisitTime() {
        return this.expectedVisitTime;
    }
    public void setVisitTime(Date visitTime) {
        this.expectedVisitTime = visitTime;
    }
    public String getDate() {
        if (this.expectedVisitTime != null) {
            // 将Date类型的expectedVisitTime转换为字符串格式
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(this.expectedVisitTime);
        }
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getExpectedVisitTimeStr() {
        if (this.expectedVisitTime != null) {
            // 将Date类型的expectedVisitTime转换为字符串格式
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(this.expectedVisitTime);
        }
        return this.expectedVisitTimeStr;
    }
    public void setExpectedVisitTimeStr(String expectedVisitTimeStr) {
        this.expectedVisitTimeStr = expectedVisitTimeStr;
    }
}