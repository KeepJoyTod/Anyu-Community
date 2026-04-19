package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityComment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CommunityCommentMapper {
    List<CommunityComment> selectCommentsByPostId(Long postId);
    int insertComment(CommunityComment comment);
    int deleteCommentById(Long commentId);
}