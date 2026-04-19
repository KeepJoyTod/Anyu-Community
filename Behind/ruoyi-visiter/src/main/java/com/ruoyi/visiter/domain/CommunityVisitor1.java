package com.ruoyi.visiter.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小区访客管理对象 community_visitor
 * 
 * @author ruoyi
 * @date 2026-03-29
 */
public class CommunityVisitor1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 访客姓名 */
    @Excel(name = "访客姓名")
    private String visitorName;

    /** 访客身份证号（唯一） */
    @Excel(name = "访客身份证号", readConverterExp = "唯=一")
    private String visitorIdCard;

    /** 访客手机号 */
    @Excel(name = "访客手机号")
    private String visitorPhone;

    /** 被访业主ID（关联sys_user表id） */
    @Excel(name = "被访业主ID", readConverterExp = "关=联sys_user表id")
    private Long visitUserId;

    /** 被访业主姓名（冗余） */
    @Excel(name = "被访业主姓名", readConverterExp = "冗=余")
    private String visitUserName;

    /** 被访业主房号（冗余） */
    @Excel(name = "被访业主房号", readConverterExp = "冗=余")
    private String visitUserRoom;

    /** 访问类型（family:探亲, repair:维修, delivery:配送, other:其他） */
    @Excel(name = "访问类型", readConverterExp = "f=amily:探亲,,r=epair:维修,,d=elivery:配送,,o=ther:其他")
    private String visitType;

    /** 入住/进入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入住/进入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkInTime;

    /** 离开时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离开时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkOutTime;

    /** 访客状态（in_community:在小区内, out:已离开） */
    @Excel(name = "访客状态", readConverterExp = "i=n_community:在小区内,,o=ut:已离开")
    private String visitorStatus;

    /** 门岗操作员 */
    @Excel(name = "门岗操作员")
    private String gateOperator;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setVisitorName(String visitorName) 
    {
        this.visitorName = visitorName;
    }

    public String getVisitorName() 
    {
        return visitorName;
    }

    public void setVisitorIdCard(String visitorIdCard) 
    {
        this.visitorIdCard = visitorIdCard;
    }

    public String getVisitorIdCard() 
    {
        return visitorIdCard;
    }

    public void setVisitorPhone(String visitorPhone) 
    {
        this.visitorPhone = visitorPhone;
    }

    public String getVisitorPhone() 
    {
        return visitorPhone;
    }

    public void setVisitUserId(Long visitUserId) 
    {
        this.visitUserId = visitUserId;
    }

    public Long getVisitUserId() 
    {
        return visitUserId;
    }

    public void setVisitUserName(String visitUserName) 
    {
        this.visitUserName = visitUserName;
    }

    public String getVisitUserName() 
    {
        return visitUserName;
    }

    public void setVisitUserRoom(String visitUserRoom) 
    {
        this.visitUserRoom = visitUserRoom;
    }

    public String getVisitUserRoom() 
    {
        return visitUserRoom;
    }

    public void setVisitType(String visitType) 
    {
        this.visitType = visitType;
    }

    public String getVisitType() 
    {
        return visitType;
    }

    public void setCheckInTime(Date checkInTime) 
    {
        this.checkInTime = checkInTime;
    }

    public Date getCheckInTime() 
    {
        return checkInTime;
    }

    public void setCheckOutTime(Date checkOutTime) 
    {
        this.checkOutTime = checkOutTime;
    }

    public Date getCheckOutTime() 
    {
        return checkOutTime;
    }

    public void setVisitorStatus(String visitorStatus) 
    {
        this.visitorStatus = visitorStatus;
    }

    public String getVisitorStatus() 
    {
        return visitorStatus;
    }

    public void setGateOperator(String gateOperator) 
    {
        this.gateOperator = gateOperator;
    }

    public String getGateOperator() 
    {
        return gateOperator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("visitorName", getVisitorName())
            .append("visitorIdCard", getVisitorIdCard())
            .append("visitorPhone", getVisitorPhone())
            .append("visitUserId", getVisitUserId())
            .append("visitUserName", getVisitUserName())
            .append("visitUserRoom", getVisitUserRoom())
            .append("visitType", getVisitType())
            .append("checkInTime", getCheckInTime())
            .append("checkOutTime", getCheckOutTime())
            .append("visitorStatus", getVisitorStatus())
            .append("gateOperator", getGateOperator())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
