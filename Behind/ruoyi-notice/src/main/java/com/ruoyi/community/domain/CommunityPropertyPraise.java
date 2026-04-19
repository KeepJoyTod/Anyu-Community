package com.ruoyi.community.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityPropertyPraise extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long ownerId;

    private String ownerName;

    private String praiseType;

    private String praiseContent;

    private String praiseTarget;

    private String status;

    private String replyContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;

    private Long communityId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }

    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }

    public void setPraiseType(String praiseType) 
    {
        this.praiseType = praiseType;
    }

    public String getPraiseType() 
    {
        return praiseType;
    }

    public void setPraiseContent(String praiseContent) 
    {
        this.praiseContent = praiseContent;
    }

    public String getPraiseContent() 
    {
        return praiseContent;
    }

    public void setPraiseTarget(String praiseTarget) 
    {
        this.praiseTarget = praiseTarget;
    }

    public String getPraiseTarget() 
    {
        return praiseTarget;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setReplyContent(String replyContent) 
    {
        this.replyContent = replyContent;
    }

    public String getReplyContent() 
    {
        return replyContent;
    }

    public void setReplyTime(Date replyTime) 
    {
        this.replyTime = replyTime;
    }

    public Date getReplyTime() 
    {
        return replyTime;
    }

    public void setCommunityId(Long communityId) 
    {
        this.communityId = communityId;
    }

    public Long getCommunityId() 
    {
        return communityId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ownerId", getOwnerId())
            .append("ownerName", getOwnerName())
            .append("praiseType", getPraiseType())
            .append("praiseContent", getPraiseContent())
            .append("praiseTarget", getPraiseTarget())
            .append("status", getStatus())
            .append("replyContent", getReplyContent())
            .append("replyTime", getReplyTime())
            .append("communityId", getCommunityId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
