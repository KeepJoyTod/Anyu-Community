package com.ruoyi.community.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.community.domain.CommunityComment;
import com.ruoyi.community.domain.CommunityPost;
import com.ruoyi.community.mapper.CommunityCommentMapper;
import com.ruoyi.community.mapper.CommunityPostMapper;
import com.ruoyi.community.service.ICommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommunityPostServiceImpl implements ICommunityPostService {

    @Autowired
    private CommunityPostMapper postMapper;
    @Autowired
    private CommunityCommentMapper commentMapper;

    @Override
    public List<CommunityPost> selectPostList(CommunityPost post) {
        return postMapper.selectPostList(post);
    }

    @Override
    public CommunityPost selectPostById(Long postId) {
        return postMapper.selectPostById(postId);
    }

    @Override
    public int insertPost(CommunityPost post) {
        return postMapper.insertPost(post);
    }

    @Override
    public int updatePost(CommunityPost post) {
        return postMapper.updatePost(post);
    }

    @Override
    public int deletePostById(Long postId) {
        return postMapper.deletePostById(postId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public int addComment(Long postId, Long userId, String userName, String content) {
        if (postId == null || postId <= 0) {
            throw new ServiceException("帖子 ID 不能为空");
        }
        if (userId == null || userId <= 0) {
            throw new ServiceException("用户 ID 不能为空");
        }
        if (StringUtils.isBlank(userName)) {
            throw new ServiceException("用户名不能为空");
        }
        if (StringUtils.isBlank(content)) {
            throw new ServiceException("评论内容不能为空");
        }
        if (content.length() > 1000) {
            throw new ServiceException("评论内容不能超过 1000 字");
        }
        
        CommunityPost post = postMapper.selectPostById(postId);
        if (post == null) {
            throw new ServiceException("帖子不存在");
        }
        if ("1".equals(post.getStatus())) {
            throw new ServiceException("帖子已被删除，无法评论");
        }
        
        CommunityComment comment = new CommunityComment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setUserName(userName);
        comment.setContent(content.trim());
        comment.setCreateTime(new Date());
        
        int result = commentMapper.insertComment(comment);
        if (result <= 0) {
            throw new ServiceException("添加评论失败");
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public int likePost(Long postId) {
        if (postId == null || postId <= 0) {
            throw new ServiceException("帖子 ID 不能为空");
        }
        return postMapper.updateLike(postId);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public int dislikePost(Long postId) {
        if (postId == null || postId <= 0) {
            throw new ServiceException("帖子 ID 不能为空");
        }
        return postMapper.updateDislike(postId);
    }
}