package com.ruoyi.web.controller.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.domain.CommunityComplaint;
import com.ruoyi.domain.Suggestion;
import com.ruoyi.service.ICommunityComplaintService;
import com.ruoyi.service.ISuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController
{
    @Autowired
    private ICommunityComplaintService complaintService;
    @Autowired
    private ISuggestionService suggestionService;
    @PostMapping("/complaint")
    public AjaxResult submitComplaint(@RequestBody Map<String, Object> params)
    {
        try {
            SysUser currentUser = SecurityUtils.getLoginUser().getUser();
            CommunityComplaint complaint = new CommunityComplaint();
            // 前端参数转换
            complaint.setComplaintTitle((String) params.get("title"));
            complaint.setComplaintContent((String) params.get("content"));
            complaint.setUserPhone((String) params.get("contact"));
            // 当前用户信息
            complaint.setUserId(currentUser.getUserId());
            complaint.setUserName(currentUser.getNickName());
            // 默认状态和类型
            complaint.setComplaintStatus("pending");
            complaint.setComplaintType("service");
            complaintService.save(complaint);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("提交投诉失败：" + e.getMessage());
        }
    }
    @PostMapping("/suggestion")
    public AjaxResult submitSuggestion(@RequestBody Map<String, Object> params)
    {
        try {
            SysUser currentUser = SecurityUtils.getLoginUser().getUser();
            Suggestion suggestion = new Suggestion();
            // 前端参数转换
            suggestion.setTitle((String) params.get("title"));
            suggestion.setContent((String) params.get("content"));
            suggestion.setUserPhone((String) params.get("contact"));
            // 处理images字段（数组转字符串）
            Object imagesObj = params.get("images");
            if (imagesObj != null) {
                if (imagesObj instanceof List) {
                    List<String> imagesList = (List<String>) imagesObj;
                    suggestion.setImages(String.join(",", imagesList));
                } else if (imagesObj instanceof String) {
                    suggestion.setImages((String) imagesObj);
                }
            }
            // 当前用户信息
            suggestion.setUserId(currentUser.getUserId());
            suggestion.setUserName(currentUser.getNickName());
            suggestion.setStatus("0");
            suggestionService.save(suggestion);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("提交建议失败：" + e.getMessage());
        }
    }
    @GetMapping("/list")
    public AjaxResult getFeedbackList()
    {
        try {
            Long userId = SecurityUtils.getUserId();
            CommunityComplaint complaintQuery = new CommunityComplaint();
            complaintQuery.setUserId(userId);
            List<CommunityComplaint> complaints = complaintService.selectList(complaintQuery);

            Suggestion suggestionQuery = new Suggestion();
            suggestionQuery.setUserId(userId);
            List<Suggestion> suggestions = suggestionService.selectList(suggestionQuery);

            List<Map<String, Object>> result = new ArrayList<>();
            for (CommunityComplaint complaint : complaints)
            {
                Map<String, Object> item = new HashMap<>();
                item.put("id", "c_" + complaint.getId());
                item.put("title", complaint.getComplaintTitle());
                item.put("content", complaint.getComplaintContent());
                item.put("contact", complaint.getUserPhone());
                item.put("createTime", complaint.getCreateTime());
                item.put("type", "complaint");
                result.add(item);
            }
            for (Suggestion suggestion : suggestions)
            {
                Map<String, Object> item = new HashMap<>();
                item.put("id", "s_" + suggestion.getSuggestionId());
                item.put("title", suggestion.getTitle());
                item.put("content", suggestion.getContent());
                item.put("contact", suggestion.getUserPhone());
                item.put("images", suggestion.getImages());
                item.put("createTime", suggestion.getCreateTime());
                item.put("type", "suggestion");
                result.add(item);
            }
            result.sort((o1, o2) -> {
                Date t1 = (Date) o1.get("createTime");
                Date t2 = (Date) o2.get("createTime");
                if (t1 == null && t2 == null) return 0;
                if (t1 == null) return 1;
                if (t2 == null) return -1;
                return t2.compareTo(t1);
            });
            return AjaxResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("获取反馈列表失败：" + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public AjaxResult deleteFeedback(@PathVariable String id)
    {
        try {
            if (id.startsWith("c_"))
            {
                Long cId = Long.parseLong(id.substring(2));
                complaintService.removeById(cId);
            }
            else if (id.startsWith("s_"))
            {
                Long sId = Long.parseLong(id.substring(2));
                suggestionService.removeById(sId);
            }
            else
            {
                return AjaxResult.error("无效的记录标识");
            }
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("删除反馈失败：" + e.getMessage());
        }
    }
}
