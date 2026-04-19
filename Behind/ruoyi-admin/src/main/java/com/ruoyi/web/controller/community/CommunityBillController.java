package com.ruoyi.web.controller.community;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.community.domain.CommunityBill;
import com.ruoyi.community.domain.CommunityPaymentRecord;
import com.ruoyi.community.service.ICommunityBillService;
import com.ruoyi.community.service.ICommunityPaymentRecordService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

@RestController
@RequestMapping("/home/bill")
public class CommunityBillController extends BaseController
{
    @Autowired
    private ICommunityBillService communityBillService;

    @Autowired
    private ICommunityPaymentRecordService communityPaymentRecordService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityBill communityBill)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        communityBill.setOwnerId(userId);
        List<CommunityBill> list = communityBillService.selectCommunityBillList(communityBill);
        return getDataTable(list);
    }

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(communityBillService.selectCommunityBillById(id));
    }

    //@Log(title = "生活缴费", businessType = BusinessType.UPDATE)
    //@PostMapping("/pay")
    @Transactional
    public AjaxResult pay(@RequestBody CommunityPaymentRecord paymentRecord)
    {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();

        CommunityBill bill = communityBillService.selectCommunityBillById(paymentRecord.getBillId());
        if (bill == null)
        {
            return error("账单不存在");
        }
        if (!bill.getOwnerId().equals(userId))
        {
            return error("无权支付该账单");
        }
        if ("2".equals(bill.getStatus()))
        {
            return error("该账单已支付完成");
        }

        BigDecimal payAmount = paymentRecord.getPayAmount();
        BigDecimal paidAmount = bill.getPaidAmount() != null ? bill.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal billAmount = bill.getBillAmount();
        BigDecimal newPaidAmount = paidAmount.add(payAmount);

        if (newPaidAmount.compareTo(billAmount) > 0)
        {
            return error("支付金额不能超过欠费金额");
        }

        paymentRecord.setOwnerId(userId);
        paymentRecord.setOwnerName(username);
        paymentRecord.setTransactionNo(UUID.randomUUID().toString().replace("-", ""));
        paymentRecord.setPayTime(DateUtils.getNowDate());
        paymentRecord.setStatus("1");
        paymentRecord.setCreateBy(username);
        communityPaymentRecordService.insertCommunityPaymentRecord(paymentRecord);

        bill.setPaidAmount(newPaidAmount);
        bill.setArrearsAmount(billAmount.subtract(newPaidAmount));
        if (newPaidAmount.compareTo(billAmount) == 0)
        {
            bill.setStatus("2");
        }
        else if (newPaidAmount.compareTo(BigDecimal.ZERO) > 0)
        {
            bill.setStatus("1");
        }
        bill.setUpdateBy(username);
        communityBillService.updateCommunityBill(bill);

        return success("支付成功");
    }

    @GetMapping("/payment/records")
    public TableDataInfo paymentRecords(CommunityPaymentRecord paymentRecord)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        paymentRecord.setOwnerId(userId);
        List<CommunityPaymentRecord> list = communityPaymentRecordService.selectCommunityPaymentRecordList(paymentRecord);
        return getDataTable(list);
    }
}
