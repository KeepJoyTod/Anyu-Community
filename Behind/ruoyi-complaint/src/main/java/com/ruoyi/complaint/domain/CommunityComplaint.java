package com.ruoyi.complaint.domain;//package com.ruoyi.complaint.domain;
//
//import java.util.Date;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import org.apache.commons.lang3.builder.ToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//import com.ruoyi.common.annotation.Excel;
//import com.ruoyi.common.core.domain.BaseEntity;
//
///**
// * 小区投诉管理对象 community_complaint
// *
// * @author xvxv
// * @date 2026-03-19
// */
//public class CommunityComplaint extends BaseEntity
//{
//    private static final long serialVersionUID = 1L;
//
//    /** 主键ID */
//    private Long id;
//
//    /** 投诉人ID（关联sys_user表id） */
//    @Excel(name = "投诉人ID", readConverterExp = "关=联sys_user表id")
//    private Long userId;
//
//    /** 投诉人姓名（冗余） */
//    @Excel(name = "投诉人姓名", readConverterExp = "冗=余")
//    private String userName;
//
//    /** 投诉人手机号（冗余） */
//    @Excel(name = "投诉人手机号", readConverterExp = "冗=余")
//    private String userPhone;
//
//    /** 投诉标题 */
//    @Excel(name = "投诉标题")
//    private String complaintTitle;
//
//    /** 投诉内容 */
//    @Excel(name = "投诉内容")
//    private String complaintContent;
//
//    /** 投诉类型（environment:环境问题, security:安全问题, service:服务问题, noise:噪音问题） */
//    @Excel(name = "投诉类型", readConverterExp = "e=nvironment:环境问题,,s=ecurity:安全问题,,s=ervice:服务问题,,n=oise:噪音问题")
//    private String complaintType;
//
//    /** 投诉状态（pending:待处理, processing:处理中, resolved:已解决, rejected:已驳回） */
//    @Excel(name = "投诉状态", readConverterExp = "p=ending:待处理,,p=rocessing:处理中,,r=esolved:已解决,,r=ejected:已驳回")
//    private String complaintStatus;
//
//    /** 处理人ID（关联sys_user表id） */
//    @Excel(name = "处理人ID", readConverterExp = "关=联sys_user表id")
//    private Long handleUserId;
//
//    /** 处理人姓名（冗余） */
//    @Excel(name = "处理人姓名", readConverterExp = "冗=余")
//    private String handleUserName;
//
//    /** 处理结果 */
//    @Excel(name = "处理结果")
//    private String handleResult;
//
//    /** 处理时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
//    private Date handleTime;
//
//    public void setId(Long id)
//    {
//        this.id = id;
//    }
//
//    public Long getId()
//    {
//        return id;
//    }
//
//    public void setUserId(Long userId)
//    {
//        this.userId = userId;
//    }
//
//    public Long getUserId()
//    {
//        return userId;
//    }
//
//    public void setUserName(String userName)
//    {
//        this.userName = userName;
//    }
//
//    public String getUserName()
//    {
//        return userName;
//    }
//
//    public void setUserPhone(String userPhone)
//    {
//        this.userPhone = userPhone;
//    }
//
//    public String getUserPhone()
//    {
//        return userPhone;
//    }
//
//    public void setComplaintTitle(String complaintTitle)
//    {
//        this.complaintTitle = complaintTitle;
//    }
//
//    public String getComplaintTitle()
//    {
//        return complaintTitle;
//    }
//
//    public void setComplaintContent(String complaintContent)
//    {
//        this.complaintContent = complaintContent;
//    }
//
//    public String getComplaintContent()
//    {
//        return complaintContent;
//    }
//
//    public void setComplaintType(String complaintType)
//    {
//        this.complaintType = complaintType;
//    }
//
//    public String getComplaintType()
//    {
//        return complaintType;
//    }
//
//    public void setComplaintStatus(String complaintStatus)
//    {
//        this.complaintStatus = complaintStatus;
//    }
//
//    public String getComplaintStatus()
//    {
//        return complaintStatus;
//    }
//
//    public void setHandleUserId(Long handleUserId)
//    {
//        this.handleUserId = handleUserId;
//    }
//
//    public Long getHandleUserId()
//    {
//        return handleUserId;
//    }
//
//    public void setHandleUserName(String handleUserName)
//    {
//        this.handleUserName = handleUserName;
//    }
//
//    public String getHandleUserName()
//    {
//        return handleUserName;
//    }
//
//    public void setHandleResult(String handleResult)
//    {
//        this.handleResult = handleResult;
//    }
//
//    public String getHandleResult()
//    {
//        return handleResult;
//    }
//
//    public void setHandleTime(Date handleTime)
//    {
//        this.handleTime = handleTime;
//    }
//
//    public Date getHandleTime()
//    {
//        return handleTime;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("id", getId())
//            .append("userId", getUserId())
//            .append("userName", getUserName())
//            .append("userPhone", getUserPhone())
//            .append("complaintTitle", getComplaintTitle())
//            .append("complaintContent", getComplaintContent())
//            .append("complaintType", getComplaintType())
//            .append("complaintStatus", getComplaintStatus())
//            .append("handleUserId", getHandleUserId())
//            .append("handleUserName", getHandleUserName())
//            .append("handleResult", getHandleResult())
//            .append("handleTime", getHandleTime())
//            .append("createBy", getCreateBy())
//            .append("createTime", getCreateTime())
//            .append("updateBy", getUpdateBy())
//            .append("updateTime", getUpdateTime())
//            .append("remark", getRemark())
//            .toString();
//    }
//}
