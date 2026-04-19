package com.ruoyi.community.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import java.util.List;

public class CommunityPost extends BaseEntity {
    private long serialVersionUID = 1L;

    private Long postId;
    private Long userId;
    private String userName;
    private String content;
    private String tag;
    private Integer likes;
    private Integer dislikes;
    private Integer points;
    private String status;
    private List<CommunityComment> comments; // 关联评论列表

    // getter / setter
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }

    public Integer getDislikes() { return dislikes; }
    public void setDislikes(Integer dislikes) { this.dislikes = dislikes; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<CommunityComment> getComments() { return comments; }
    public void setComments(List<CommunityComment> comments) { this.comments = comments; }

}