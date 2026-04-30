package com.ruoyi.web.controller.service;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.domain.RepairOrder;
import com.ruoyi.service.IRepairOrderService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/repairs")
public class RepairController extends BaseController
{
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    @Autowired
    private IRepairOrderService repairOrderService;

    @PostMapping
    public AjaxResult create(@RequestBody RepairCreateRequest request)
    {
        if (request == null)
        {
            return AjaxResult.error("请求体不能为空");
        }
        if (!StringUtils.hasText(request.getRepairType()))
        {
            return AjaxResult.error("报修类型不能为空");
        }
        if (!StringUtils.hasText(request.getRepairAddress()))
        {
            return AjaxResult.error("报修地址不能为空");
        }
        if (!StringUtils.hasText(request.getRepairDesc()))
        {
            return AjaxResult.error("问题描述不能为空");
        }
        if (StringUtils.hasText(request.getUserPhone()) && !PHONE_PATTERN.matcher(request.getUserPhone().trim()).matches())
        {
            return AjaxResult.error("联系电话格式不正确");
        }

        SysUser currentUser = getLoginUser().getUser();
        LocalDateTime now = LocalDateTime.now();

        RepairOrder order = new RepairOrder();
        order.setRepairId(request.getRepairId() == null ? 0L : request.getRepairId());
        order.setUserId(currentUser.getUserId());
        order.setUserName(StringUtils.defaultIfBlank(currentUser.getNickName(), currentUser.getUserName()));
        order.setUserRoom(StringUtils.defaultIfBlank(request.getUserRoom(), String.valueOf(currentUser.getUserId())));
        order.setUserPhone(StringUtils.defaultIfBlank(StringUtils.trim(request.getUserPhone()), currentUser.getPhonenumber()));
        order.setRepairType(StringUtils.trim(request.getRepairType()));
        order.setRepairAddress(StringUtils.trim(request.getRepairAddress()));
        order.setRepairDesc(StringUtils.trim(request.getRepairDesc()));
        order.setRepairImages(StringUtils.trim(request.getRepairImages()));
        order.setOrderStatus("pending");
        order.setRemark(StringUtils.trim(request.getRemark()));
        order.setCreateTime(now);
        order.setUpdateTime(now);

        repairOrderService.insertRepairOrder(order);
        return AjaxResult.success(toView(order));
    }

    @GetMapping
    public AjaxResult list()
    {
        return AjaxResult.success(Collections.emptyList());
    }

    @GetMapping("/pending")
    public AjaxResult pending()
    {
        List<RepairOrder> orders = repairOrderService.selectPendingRepairOrders();
        List<Map<String, Object>> result = new ArrayList<>();
        Long currentUserId = getUserId();
        boolean admin = SecurityUtils.isAdmin(currentUserId);
        for (RepairOrder order : orders)
        {
            if (admin || Objects.equals(order.getUserId(), currentUserId))
            {
                result.add(toView(order));
            }
        }
        return AjaxResult.success(result);
    }

    @GetMapping("/{orderId}")
    public AjaxResult detail(@PathVariable Long orderId)
    {
        RepairOrder order = repairOrderService.selectRepairOrderById(orderId);
        if (order == null)
        {
            return AjaxResult.error(404, "工单不存在");
        }
        Long currentUserId = getUserId();
        if (!SecurityUtils.isAdmin(currentUserId) && !Objects.equals(order.getUserId(), currentUserId))
        {
            return AjaxResult.error(403, "无权访问该工单");
        }
        return AjaxResult.success(toView(order));
    }

    @PutMapping("/{orderId}/cancel")
    public AjaxResult cancel(@PathVariable Long orderId)
    {
        RepairOrder order = repairOrderService.selectRepairOrderById(orderId);
        if (order == null)
        {
            return AjaxResult.error(404, "工单不存在");
        }
        Long currentUserId = getUserId();
        if (!SecurityUtils.isAdmin(currentUserId) && !Objects.equals(order.getUserId(), currentUserId))
        {
            return AjaxResult.error(403, "无权取消该工单");
        }
        if (!"pending".equals(order.getOrderStatus()))
        {
            return AjaxResult.error("当前状态不可取消");
        }

        int result = repairOrderService.cancelRepairOrder(orderId);
        if (result <= 0)
        {
            return AjaxResult.error("取消报修失败");
        }

        RepairOrder updated = repairOrderService.selectRepairOrderById(orderId);
        return AjaxResult.success(Collections.singletonMap("status", updated == null ? "canceled" : updated.getOrderStatus()));
    }

    @PostMapping("/upload")
    public AjaxResult upload(@RequestPart("file") MultipartFile file)
    {
        if (file == null || file.isEmpty())
        {
            return AjaxResult.error("上传文件不能为空");
        }
        try
        {
            String fileUrl = FileUploadUtils.upload(RuoYiConfig.getUploadPath() + "/repair", file, MimeTypeUtils.IMAGE_EXTENSION, true);
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("url", fileUrl);
            return AjaxResult.success(data);
        }
        catch (Exception e)
        {
            return AjaxResult.error("文件上传失败: " + e.getMessage());
        }
    }

    private Map<String, Object> toView(RepairOrder order)
    {
        Map<String, Object> view = new LinkedHashMap<>();
        view.put("orderId", order.getOrderId());
        view.put("type", order.getRepairType());
        view.put("desc", order.getRepairDesc());
        view.put("contactName", order.getUserName());
        view.put("phone", order.getUserPhone());
        view.put("address", order.getRepairAddress());
        view.put("images", parseImages(order.getRepairImages()));
        view.put("status", order.getOrderStatus());
        view.put("createdAt", order.getCreateTime() == null ? null : order.getCreateTime().format(FORMATTER));
        return view;
    }

    private List<String> parseImages(String rawImages)
    {
        if (!StringUtils.hasText(rawImages))
        {
            return Collections.emptyList();
        }
        String value = rawImages.trim();
        if (value.startsWith("[") && value.endsWith("]"))
        {
            value = value.substring(1, value.length() - 1).trim();
            if (!StringUtils.hasText(value))
            {
                return Collections.emptyList();
            }
            List<String> images = new ArrayList<>();
            for (String part : value.split(","))
            {
                String image = part.trim();
                if (image.startsWith("\"") && image.endsWith("\"") && image.length() >= 2)
                {
                    image = image.substring(1, image.length() - 1);
                }
                images.add(image.replace("\\\"", "\""));
            }
            return images;
        }
        return StringUtils.str2List(value, ",", true, true);
    }

    public static class RepairCreateRequest
    {
        private Long repairId;
        private String userPhone;
        private String userRoom;
        private String repairType;
        private String repairAddress;
        private String repairDesc;
        private String repairImages;
        private String remark;

        public Long getRepairId()
        {
            return repairId;
        }

        public void setRepairId(Long repairId)
        {
            this.repairId = repairId;
        }

        public String getUserPhone()
        {
            return userPhone;
        }

        public void setUserPhone(String userPhone)
        {
            this.userPhone = userPhone;
        }

        public String getUserRoom()
        {
            return userRoom;
        }

        public void setUserRoom(String userRoom)
        {
            this.userRoom = userRoom;
        }

        public String getRepairType()
        {
            return repairType;
        }

        public void setRepairType(String repairType)
        {
            this.repairType = repairType;
        }

        public String getRepairAddress()
        {
            return repairAddress;
        }

        public void setRepairAddress(String repairAddress)
        {
            this.repairAddress = repairAddress;
        }

        public String getRepairDesc()
        {
            return repairDesc;
        }

        public void setRepairDesc(String repairDesc)
        {
            this.repairDesc = repairDesc;
        }

        public String getRepairImages()
        {
            return repairImages;
        }

        public void setRepairImages(String repairImages)
        {
            this.repairImages = repairImages;
        }

        public String getRemark()
        {
            return remark;
        }

        public void setRemark(String remark)
        {
            this.remark = remark;
        }
    }
}
