package com.ruoyi.web.controller.community;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.community.domain.CommunityProperty;
import com.ruoyi.community.domain.CommunityPropertyPraise;
import com.ruoyi.community.service.ICommunityPropertyService;
import com.ruoyi.community.service.ICommunityPropertyPraiseService;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController("propertyController")
@RequestMapping("/home/property")
public class PropertyController extends BaseController
{
    @Autowired
    @Qualifier("homeCommunityPropertyService")
    private ICommunityPropertyService communityPropertyService;

    @Autowired
    @Qualifier("homeCommunityPropertyPraiseService")
    private ICommunityPropertyPraiseService communityPropertyPraiseService;

    @GetMapping("/info")
    public AjaxResult getPropertyInfo()
    {
        CommunityProperty property = communityPropertyService.selectCommunityProperty();
        return success(property);
    }

    @GetMapping("/praise/list")
    public TableDataInfo getPraiseList(CommunityPropertyPraise communityPropertyPraise)
    {
        startPage();
        List<CommunityPropertyPraise> list = communityPropertyPraiseService.selectCommunityPropertyPraiseList(communityPropertyPraise);
        return getDataTable(list);
    }

    @PostMapping("/praise/add")
    public AjaxResult addPraise(@RequestBody CommunityPropertyPraise communityPropertyPraise)
    {
        return toAjax(communityPropertyPraiseService.insertCommunityPropertyPraise(communityPropertyPraise));
    }
}
