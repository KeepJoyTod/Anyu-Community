package com.ruoyi.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
public class CommunityComplaint extends BaseEntity
{
    private static long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String userName;
    private String userPhone;
    private String complaintTitle;
    private String complaintContent;
    private String complaintType;
    private String complaintStatus;
    private Long handleUserId;
    private String handleUserName;
    private String handleResult;
    private Date handleTime;
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Long getUserId()
    {
        return userId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public String getUserPhone()
    {
        return userPhone;
    }
    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }
    public String getComplaintTitle()
    {
        return complaintTitle;
    }
    public void setComplaintTitle(String complaintTitle)
    {
        this.complaintTitle = complaintTitle;
    }
    public String getComplaintContent()
    {
        return complaintContent;
    }
    public void setComplaintContent(String complaintContent)
    {
        this.complaintContent = complaintContent;
    }
    public String getComplaintType()
    {
        return complaintType;
    }
    public void setComplaintType(String complaintType)
    {
        this.complaintType = complaintType;
    }
    public String getComplaintStatus()
    {
        return complaintStatus;
    }
    public void setComplaintStatus(String complaintStatus)
    {
        this.complaintStatus = complaintStatus;
    }
    public Long getHandleUserId()
    {
        return handleUserId;
    }
    public void setHandleUserId(Long handleUserId)
    {
        this.handleUserId = handleUserId;
    }
    public String getHandleUserName()
    {
        return handleUserName;
    }
    public void setHandleUserName(String handleUserName)
    {
        this.handleUserName = handleUserName;
    }
    public String getHandleResult()
    {
        return handleResult;
    }
    public void setHandleResult(String handleResult)
    {
        this.handleResult = handleResult;
    }
    public Date getHandleTime()
    {
        return handleTime;
    }
    public void setHandleTime(Date handleTime)
    {
        this.handleTime = handleTime;
    }
}
