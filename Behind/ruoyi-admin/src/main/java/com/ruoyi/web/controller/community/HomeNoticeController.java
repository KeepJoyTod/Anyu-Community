package com.ruoyi.web.controller.community;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.community.domain.CommunityNotice;
import com.ruoyi.community.service.ICommunityNoticeService1;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController("homeNoticeControllerV2")
@RequestMapping("/home/notice/v2")
public class HomeNoticeController extends BaseController
{
    @Autowired
    private ICommunityNoticeService1 communityNoticeService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityNotice communityNotice)
    {
        startPage();
        // 暂时不加状态过滤，查看所有数据
        // communityNotice.setStatus("0");
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
