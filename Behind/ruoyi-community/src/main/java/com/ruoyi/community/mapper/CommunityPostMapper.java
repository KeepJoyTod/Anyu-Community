package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityPost;
import java.util.List;

/*
public interface CommunityPostMapper {
    List<CommunityPost> selectPostList(CommunityPost post);
    CommunityPost selectPostById(Long postId);
    int insertPost(CommunityPost post);
    int updatePost(CommunityPost post);
    int deletePostById(Long postId);
}*/
// ... existing code ...
public interface CommunityPostMapper {
    List<CommunityPost> selectPostList(CommunityPost post);
    CommunityPost selectPostById(Long postId);
    int insertPost(CommunityPost post);
    int updatePost(CommunityPost post);
    int deletePostById(Long postId);
    int updateLike(Long postId);
    int updateDislike(Long postId);
}
