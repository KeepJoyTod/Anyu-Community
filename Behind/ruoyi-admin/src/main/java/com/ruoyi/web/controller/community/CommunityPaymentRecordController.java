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
import com.ruoyi.community.domain.CommunityPaymentRecord;
import com.ruoyi.community.service.ICommunityPaymentRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;

@RestController
@RequestMapping("/home/payment")
public class CommunityPaymentRecordController extends BaseController
{
    @Autowired
    private ICommunityPaymentRecordService communityPaymentRecordService;

    @GetMapping("/records")
    public TableDataInfo list(CommunityPaymentRecord communityPaymentRecord)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        communityPaymentRecord.setOwnerId(userId);
        List<CommunityPaymentRecord> list = communityPaymentRecordService.selectCommunityPaymentRecordList(communityPaymentRecord);
        return getDataTable(list);
    }
}
