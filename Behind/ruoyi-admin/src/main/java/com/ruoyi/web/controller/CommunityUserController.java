package com.ruoyi.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.system.service.ICommunityNoticeService;
import com.ruoyi.system.service.ICommunityPayymentService;
import com.ruoyi.system.service.ICommunityReservationnService;
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
import com.ruoyi.system.domain.CommunityUser1;
import com.ruoyi.system.service.ICommunityUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 社区住户信息Controller
 *
 * @author ruoyi
 * @date 2026-03-19
 */
@RestController
@RequestMapping("/api/home")
public class CommunityUserController extends BaseController
{
    @Autowired
    private ICommunityUserService communityUserService;
    @Autowired
    private ICommunityNoticeService communityNoticeService;
    @Autowired
    private ICommunityPayymentService communityPaymentService;
    @Autowired
    private ICommunityReservationnService communityReservationService;

    /**
     * 查询用户等数据
     */
    @PreAuthorize("@ss.hasPermi('api:home:getPanelData')")
    @GetMapping("/getPanelData")
    public Map<String,Object> getPanelData()
    {
        Map<String,Object> map = new HashMap<String,Object>();
        Integer userCount=communityUserService.getUserNum();
        Integer messageCount=communityNoticeService.getNoticeNum();
        Double purchaseCount=communityPaymentService.getMoney();
        Integer shoppingCount=communityReservationService.getReservationNum();
        map.put("userCount",userCount);
        map.put("messageCount",messageCount);
        map.put("purchaseCount",purchaseCount);
        map.put("shoppingCount",shoppingCount);
        map.put("code",200);
        return map;
    }
    @PreAuthorize("@ss.hasPermi('api:home:getLinelData')")
    @GetMapping("/getLinelData")
    public Map<String,Object> getLinelData()
    {
        Map<String,Object> map = new HashMap<String,Object>();
        Integer userCount1=communityUserService.getUserCount1();
        Integer userCount2=communityUserService.getUserCount2();
        Integer userCount3=communityUserService.getUserCount3();
        Integer userCount4=communityUserService.getUserCount4();
        Integer userCount5=communityUserService.getUserCount5();
        List<Integer> countData= new ArrayList<>();
        countData.add(userCount1);
        countData.add(userCount2);
        countData.add(userCount3);
        countData.add(userCount4);
        countData.add(userCount5);
        Map<String,List<Integer>> data = new HashMap<>();
        data.put("countData",countData);
        map.put("code",200);
        map.put("data",data);
        return map;
    }
    /**
     * 导出社区住户信息列表
     */
    @PreAuthorize("@ss.hasPermi('home:user:export')")
    @Log(title = "社区住户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityUser1 communityUser)
    {
        List<CommunityUser1> list = communityUserService.selectCommunityUserList(communityUser);
        ExcelUtil<CommunityUser1> util = new ExcelUtil<CommunityUser1>(CommunityUser1.class);
        util.exportExcel(response, list, "社区住户信息数据");
    }

    /**
     * 获取社区住户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('home:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(communityUserService.selectCommunityUserById(id));
    }

    /**
     * 新增社区住户信息
     */
    @PreAuthorize("@ss.hasPermi('home:user:add')")
    @Log(title = "社区住户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunityUser1 communityUser)
    {
        return toAjax(communityUserService.insertCommunityUser(communityUser));
    }

    /**
     * 修改社区住户信息
     */
    @PreAuthorize("@ss.hasPermi('home:user:edit')")
    @Log(title = "社区住户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityUser1 communityUser)
    {
        return toAjax(communityUserService.updateCommunityUser(communityUser));
    }

    /**
     * 删除社区住户信息
     */
    @PreAuthorize("@ss.hasPermi('home:user:remove')")
    @Log(title = "社区住户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(communityUserService.deleteCommunityUserByIds(ids));
    }
}