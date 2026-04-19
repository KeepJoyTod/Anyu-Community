package com.ruoyi.web.controller.community;

import java.util.List;

import com.ruoyi.community.service.ICommunityNoticeService1;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.ruoyi.community.domain.CommunityNotice;
//import com.ruoyi.community.service.ICommunityNoticeService;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController("homeNoticeController")
@RequestMapping("/home/notice")
public class CommunityNoticeController extends BaseController
{
    @Autowired
    @Qualifier("homeCommunityNoticeService")
    private ICommunityNoticeService1 communityNoticeService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityNotice communityNotice)
    {
        startPage();
        communityNotice.setStatus("0");
        List<CommunityNotice> list = communityNoticeService.selectCommunityNoticeList(communityNotice);
        return getDataTable(list);
    }

    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId)
    {
        return success(communityNoticeService.selectCommunityNoticeById(noticeId));
    }

    @GetMapping("/top")
    public AjaxResult getTopNotices()
    {
        CommunityNotice query = new CommunityNotice();
        query.setStatus("0");
        query.setIsTop("1");
        startPage();
        List<CommunityNotice> list = communityNoticeService.selectCommunityNoticeList(query);
        return success(getDataTable(list).getRows());
    }
}
