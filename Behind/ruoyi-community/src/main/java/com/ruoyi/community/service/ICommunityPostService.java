package com.ruoyi.community.service;

import com.ruoyi.community.domain.CommunityPost;
import java.util.List;

public interface ICommunityPostService {
    List<CommunityPost> selectPostList(CommunityPost post);
    CommunityPost selectPostById(Long postId);
    int insertPost(CommunityPost post);
    int updatePost(CommunityPost post);
    int deletePostById(Long postId);
    int addComment(Long postId, Long userId, String userName, String content);
}