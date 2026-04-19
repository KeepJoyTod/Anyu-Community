package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小区通知管理对象 community_notice
 * 
 * @author ruoyi
 * @date 2026-03-19
 */
public class CommunityNotice1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 通知标题 */
    @Excel(name = "通知标题")
    private String noticeTitle;

    /** 通知内容 */
    @Excel(name = "通知内容")
    private String noticeContent;

    /** 通知类型（announcement:公告, notice:通知, warning:警示） */
    @Excel(name = "通知类型", readConverterExp = "a=nnouncement:公告,,n=otice:通知,,w=arning:警示")
    private String noticeType;

    /** 发布人ID（关联sys_user表id） */
    @Excel(name = "发布人ID", readConverterExp = "关=联sys_user表id")
    private Long publishUserId;

    /** 发布人姓名（冗余） */
    @Excel(name = "发布人姓名", readConverterExp = "冗=余")
    private String publishUserName;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 状态（published:已发布, draft:草稿, revoked:已撤销） */
    @Excel(name = "状态", readConverterExp = "p=ublished:已发布,,d=raft:草稿,,r=evoked:已撤销")
    private String status;

    /** 是否置顶（0:否, 1:是） */
    @Excel(name = "是否置顶", readConverterExp = "0=:否,,1=:是")
    private String topFlag;

    /** 置顶结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "置顶结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date topEndTime;

    /** 阅读次数（冗余：统计用） */
    @Excel(name = "阅读次数", readConverterExp = "冗=余：统计用")
    private Long readCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setNoticeTitle(String noticeTitle) 
    {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeTitle() 
    {
        return noticeTitle;
    }

    public void setNoticeContent(String noticeContent) 
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() 
    {
        return noticeContent;
    }

    public void setNoticeType(String noticeType) 
    {
        this.noticeType = noticeType;
    }

    public String getNoticeType() 
    {
        return noticeType;
    }

    public void setPublishUserId(Long publishUserId) 
    {
        this.publishUserId = publishUserId;
    }

    public Long getPublishUserId() 
    {
        return publishUserId;
    }

    public void setPublishUserName(String publishUserName) 
    {
        this.publishUserName = publishUserName;
    }

    public String getPublishUserName() 
    {
        return publishUserName;
    }

    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setTopFlag(String topFlag) 
    {
        this.topFlag = topFlag;
    }

    public String getTopFlag() 
    {
        return topFlag;
    }

    public void setTopEndTime(Date topEndTime) 
    {
        this.topEndTime = topEndTime;
    }

    public Date getTopEndTime() 
    {
        return topEndTime;
    }

    public void setReadCount(Long readCount) 
    {
        this.readCount = readCount;
    }

    public Long getReadCount() 
    {
        return readCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("noticeTitle", getNoticeTitle())
            .append("noticeContent", getNoticeContent())
            .append("noticeType", getNoticeType())
            .append("publishUserId", getPublishUserId())
            .append("publishUserName", getPublishUserName())
            .append("publishTime", getPublishTime())
            .append("status", getStatus())
            .append("topFlag", getTopFlag())
            .append("topEndTime", getTopEndTime())
            .append("readCount", getReadCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
