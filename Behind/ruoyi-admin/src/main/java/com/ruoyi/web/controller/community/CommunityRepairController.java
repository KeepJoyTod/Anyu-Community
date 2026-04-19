package com.ruoyi.web.controller.community;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.ruoyi.community.domain.CommunityRepair;
import com.ruoyi.community.domain.CommunityRepairOrder;
import com.ruoyi.community.service.ICommunityRepairService;
import com.ruoyi.community.service.ICommunityRepairOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;

@RestController
@RequestMapping("/home/repair")
public class CommunityRepairController extends BaseController
{
    @Autowired
    private ICommunityRepairService communityRepairService;

    @Autowired
    private ICommunityRepairOrderService communityRepairOrderService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityRepairOrder communityRepairOrder)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        communityRepairOrder.setUserId(userId);
        List<CommunityRepairOrder> list = communityRepairOrderService.selectCommunityRepairOrderList(communityRepairOrder);
        return getDataTable(list);
    }

    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(communityRepairOrderService.selectCommunityRepairOrderById(orderId));
    }

    //@Log(title = "报修单据", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    public AjaxResult add(@RequestBody CommunityRepairOrder communityRepairOrder)
    {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        communityRepairOrder.setUserId(userId);
        communityRepairOrder.setUserName(username);
        communityRepairOrder.setOrderStatus("pending");
        communityRepairOrder.setCreateBy(username);
        return toAjax(communityRepairOrderService.insertCommunityRepairOrder(communityRepairOrder));
    }

    //@Log(title = "取消报修", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{orderId}")
    public AjaxResult cancel(@PathVariable("orderId") Long orderId)
    {
        CommunityRepairOrder order = communityRepairOrderService.selectCommunityRepairOrderById(orderId);
        if (order == null)
        {
            return error("报修单据不存在");
        }
        
        Long userId = SecurityUtils.getUserId();
        if (!order.getUserId().equals(userId))
        {
            return error("无权操作该报修单据");
        }
        
        if (!"pending".equals(order.getOrderStatus()))
        {
            return error("当前状态无法取消");
        }
        
        order.setOrderStatus("cancelled");
        order.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(communityRepairOrderService.updateCommunityRepairOrder(order));
    }
}
