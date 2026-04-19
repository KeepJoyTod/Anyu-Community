package com.ruoyi.web.controller.community;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.community.service.ICommunityExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 小程序端积分商城控制器
 */
@RestController
@RequestMapping("/home/mall")
public class CommunityMallController extends BaseController {

    @Autowired
    private ICommunityExchangeService exchangeService;

    /**
     * 获取用户可兑换的账单列表
     * @param userId 用户ID
     * @return 账单列表
     */
    @GetMapping("/getBills/{userId}")
    public AjaxResult getBills(@PathVariable("userId") Long userId) {
        List<Map<String, Object>> bills = exchangeService.getUnpaidBillsByUserId(userId);
        return success(bills);
    }

    /**
     * 积分兑换
     * @param userId 用户ID
     * @param param 兑换信息
     * @return 结果
     */
    @PostMapping("/exchange/{userId}")
    public AjaxResult exchange(@PathVariable("userId") Long userId, @RequestBody Map<String, Object> param) {
        String billType = (String) param.get("billType");
        Integer pointsUsed = Integer.valueOf(param.get("pointsUsed").toString());
        Double amount = Double.valueOf(param.get("amount").toString());

        boolean success = exchangeService.exchangePoints(userId, billType, pointsUsed, amount);
        if (success) {
            return success("兑换成功");
        } else {
            return error("兑换失败，可能积分不足");
        }
    }

    /**
     * 获取用户的积分兑换记录
     * @param userId 用户ID
     * @return 兑换记录列表
     */
    @GetMapping("/exchangeRecords/{userId}")
    public AjaxResult getExchangeRecords(@PathVariable("userId") Long userId) {
        List<Map<String, Object>> records = exchangeService.getExchangeRecordsByUserId(userId);
        return success(records);
    }
}
