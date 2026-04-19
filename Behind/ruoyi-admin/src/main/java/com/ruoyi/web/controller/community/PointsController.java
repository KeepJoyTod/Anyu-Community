package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.community.domain.CommunityPoints;
import com.ruoyi.community.mapper.CommunityElectricityFeeMapper;
import com.ruoyi.community.mapper.CommunityPaymentMapper;
import com.ruoyi.community.mapper.CommunityWaterFeeMapper;
import com.ruoyi.community.service.ICommunityExchangeService;
import com.ruoyi.community.service.ICommunityPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/points")
public class PointsController extends BaseController {

    @Autowired
    private ICommunityPointsService pointsService;
    @Autowired
    private ICommunityExchangeService exchangeService;
    @Autowired
    private CommunityWaterFeeMapper waterFeeMapper;
    @Autowired
    private CommunityElectricityFeeMapper electricityFeeMapper;
    @Autowired
    private CommunityPaymentMapper paymentMapper;

    @GetMapping("/getPoints")
    public AjaxResult getPoints() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        CommunityPoints points = pointsService.getPointsByUserId(user.getUserId());
        Map<String, Object> data = new HashMap<>();
        if (points != null) {
            data.put("totalPoints", points.getTotalPoints());
            data.put("availablePoints", points.getAvailablePoints());
        } else {
            data.put("totalPoints", 0);
            data.put("availablePoints", 0);
        }
        return success(data);
    }

    @GetMapping("/getBills")
    public AjaxResult getBills() {
        Long userId = SecurityUtils.getUserId();
        List<Map<String, Object>> bills = new ArrayList<>();

        waterFeeMapper.selectUnpaidByUserId(userId).forEach(water -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", water.getWaterId());
            item.put("type", "water");
            item.put("typeName", "水费");
            item.put("amount", water.getTotalFee());
            item.put("deadline", water.getDueDate());
            item.put("pointsNeeded", water.getTotalFee().multiply(new java.math.BigDecimal(100)).intValue());
            item.put("name", water.getUserRoom() + " - " + water.getReadMonth() + "水费");
            bills.add(item);
        });

        electricityFeeMapper.selectUnpaidByUserId(userId).forEach(elec -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", elec.getElectricityId());
            item.put("type", "electricity");
            item.put("typeName", "电费");
            item.put("amount", elec.getTotalFee());
            item.put("deadline", elec.getDueDate());
            item.put("pointsNeeded", elec.getTotalFee().multiply(new java.math.BigDecimal(100)).intValue());
            item.put("name", elec.getUserRoom() + " - " + elec.getReadMonth() + "电费");
            bills.add(item);
        });

        paymentMapper.selectUnpaidByUserId(userId).forEach(payment -> {
            Map<String, Object> item = new HashMap<>();
            item.put("id", payment.getId());
            item.put("type", "property");
            item.put("typeName", "物业费");
            item.put("amount", payment.getPaymentAmount());
            item.put("deadline", payment.getDueTime());
            item.put("pointsNeeded", payment.getPaymentAmount().multiply(new java.math.BigDecimal(100)).intValue());
            item.put("name", payment.getUserRoom() + " - 物业费账单");
            bills.add(item);
        });

        return success(bills);
    }

    @PostMapping("/exchange")
    public AjaxResult exchange(@RequestBody Map<String, Object> param) {
        Long userId = SecurityUtils.getUserId();
        String billType = (String) param.get("billType");
        Integer pointsUsed = Integer.valueOf(param.get("pointsUsed").toString());
        // 计算兑换金额：100积分 = 10元
        Double amount = pointsUsed / 10.0;

        boolean success = exchangeService.exchangePoints(userId, billType, pointsUsed, amount);
        if (success) {
            return success("兑换成功");
        } else {
            return error("兑换失败，可能积分不足或账单状态异常");
        }
    }

    @GetMapping("/exchangeRecords")
    public AjaxResult getExchangeRecords() {
        Long userId = SecurityUtils.getUserId();
        List<Map<String, Object>> records = exchangeService.getExchangeRecordsByUserId(userId);
        return success(records);
    }
}