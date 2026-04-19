package com.ruoyi.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
public class Suggestion extends BaseEntity
{
    private static long serialVersionUID = 1L;
    private Long suggestionId;
    private Long userId;
    private String userName;
    private String userPhone;
    private String title;
    private String content;
    private String images;
    private String status;
    private Long handleUserId;
    private String handleUserName;
    private String handleResult;
    private Date handleTime;
    public Long getSuggestionId()
    {
        return suggestionId;
    }
    public void setSuggestionId(Long suggestionId)
    {
        this.suggestionId = suggestionId;
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
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public String getImages()
    {
        return images;
    }
    public void setImages(String images)
    {
        this.images = images;
    }
    // 辅助方法：将字符串转换为List
    public List<String> getImagesList()
    {
        if (this.images == null || this.images.isEmpty())
        {
            return null;
        }
        return Arrays.asList(this.images.split(","));
    }
    // 辅助方法：将List转换为字符串
    public void setImagesList(List<String> imagesList)
    {
        if (imagesList == null || imagesList.isEmpty())
        {
            this.images = null;
        }
        else
        {
            this.images = String.join(",", imagesList);
        }
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
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
