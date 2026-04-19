package com.ruoyi.web.controller.community;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.community.domain.CommunityVisitor;
import com.ruoyi.community.service.ICommunityVisitorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;

// @RestController  // disabled to avoid duplicate bean conflict with visiter/CommunityVisitorController
@RequestMapping("/home/visitor")
public class CommunityVisitorController extends BaseController
{
    @Autowired
    private ICommunityVisitorService communityVisitorService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityVisitor communityVisitor)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        communityVisitor.setOwnerId(userId);
        List<CommunityVisitor> list = communityVisitorService.selectCommunityVisitorList(communityVisitor);
        return getDataTable(list);
    }

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityVisitorService.selectCommunityVisitorById(id));
    }

    //@Log(title = "访客邀请", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    public AjaxResult add(@RequestBody CommunityVisitor communityVisitor)
    {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        communityVisitor.setOwnerId(userId);
        communityVisitor.setOwnerName(username);
        communityVisitor.setCreateBy(username);
        return toAjax(communityVisitorService.insertCommunityVisitor(communityVisitor));
    }

    //@Log(title = "访客邀请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(communityVisitorService.deleteCommunityVisitorById(id));
    }
}
