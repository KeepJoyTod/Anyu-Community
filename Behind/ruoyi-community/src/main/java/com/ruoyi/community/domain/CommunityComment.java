package com.ruoyi.community.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long commentId;
    private Long postId;
    private Long userId;
    private String userName;
    private String content;
    private String status;

    // getter / setter
    public Long getCommentId() { return commentId; }
    public void setCommentId(Long commentId) { this.commentId = commentId; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}