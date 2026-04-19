package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.community.domain.CommunityPost;
import com.ruoyi.community.service.ICommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区动态管理控制器
 */
@RestController
@RequestMapping("/community/post")
public class CommunityPostController extends BaseController {

    @Autowired
    private ICommunityPostService postService;

    /**
     * 1. 获取动态列表（支持按标签筛选）
     * GET /community/post/list?tag=xxx
     */
    @GetMapping("/list")
    public AjaxResult list(CommunityPost post) {
        startPage(); // 若依分页，如需分页可开启
        return success(postService.selectPostList(post));
    }

    /**
     * 2. 获取单条动态详情
     * GET /community/post/{postId}
     */
    @GetMapping("/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId) {
        return success(postService.selectPostById(postId));
    }

    /**
     * 3. 发表动态
     * POST /community/post
     * body: {"content":"动态内容","tag":"#好人好事"}
     */
    @PostMapping
    public AjaxResult add(@RequestBody CommunityPost post) {
        try {
            // 尝试获取登录用户
            SysUser user = SecurityUtils.getLoginUser().getUser();
            post.setUserId(user.getUserId());
            post.setUserName(user.getNickName());
        } catch (Exception e) {
            // 未登录用户使用默认信息
            post.setUserId(1L);
            post.setUserName("匿名用户");
        }
        post.setLikes(0);
        post.setDislikes(0);
        post.setPoints(10); // 发表分享奖励10积分
        return toAjax(postService.insertPost(post));
    }

    /**
     * 4. 点赞/点踩
     * PUT /community/post/like
     * body: {"postId":1, "type":"like"}  // type: like 或 dislike
     * body: {"postId":1, "type":"unlike"}  // 取消点赞
     * body: {"postId":1, "type":"undislike"}  // 取消点踩
     */
    @PutMapping("/like")
    public AjaxResult updateLike(@RequestBody Map<String, Object> param) {
        Long postId = Long.valueOf(param.get("postId").toString());
        String type = param.get("type").toString();
        CommunityPost post = postService.selectPostById(postId);
        if (post == null) {
            return error("动态不存在");
        }
        if ("like".equals(type)) {
            post.setLikes(post.getLikes() + 1);
        } else if ("dislike".equals(type)) {
            post.setDislikes(post.getDislikes() + 1);
        } else if ("unlike".equals(type)) {
            if (post.getLikes() > 0) {
                post.setLikes(post.getLikes() - 1);
            }
        } else if ("undislike".equals(type)) {
            if (post.getDislikes() > 0) {
                post.setDislikes(post.getDislikes() - 1);
            }
        } else {
            return error("type参数错误");
        }
        return toAjax(postService.updatePost(post));
    }

    /**
     * 5. 发表评论
     * POST /community/post/comment
     * body: {"postId":1, "content":"评论内容"}
     */
    @PostMapping("/comment")
    public AjaxResult addComment(@RequestBody Map<String, Object> param) {
        Long postId = Long.valueOf(param.get("postId").toString());
        String content = param.get("content").toString();
        // 由于是匿名访问，使用默认用户信息
        Long userId = 1L; // 默认用户ID
        String userName = "匿名用户"; // 默认用户名
        int rows = postService.addComment(postId, userId, userName, content);
        return toAjax(rows);
    }

}