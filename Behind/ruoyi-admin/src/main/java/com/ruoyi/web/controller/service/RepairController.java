package com.ruoyi.web.controller.service;

import com.ruoyi.domain.RepairOrder;
import com.ruoyi.service.IRepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/repairs")
@CrossOrigin
public class RepairController {
    @Autowired
    private IRepairOrderService repairOrderService;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @PostMapping
    public Map<String, Object> create(@RequestBody Map<String, Object> body) {
        RepairOrder o = new RepairOrder();
        // 设置默认值，确保不会为null
        o.setRepairId(0L);
        o.setUserId(0L);
        o.setUserRoom("");
        o.setRepairType(asText(body.get("repairType")));
        o.setRepairDesc(asText(body.get("repairDesc")));
        o.setUserName(asText(body.get("userName")));
        o.setUserPhone(asText(body.get("userPhone")));
        o.setRepairAddress(asText(body.get("repairAddress")));
        o.setRepairImages(asText(body.get("repairImages")));
        o.setOrderStatus(asText(body.get("orderStatus")));
        o.setCreateTime(LocalDateTime.now());
        o.setUpdateTime(LocalDateTime.now());
        repairOrderService.insertRepairOrder(o);
        return ok(toMap(o));
    }

    @GetMapping
    public Map<String, Object> list() {
        return ok(Collections.emptyList());
    }

    @GetMapping("/pending")
    public Map<String, Object> pending() {
        List<RepairOrder> orders = repairOrderService.selectPendingRepairOrders();
        List<Map<String, Object>> result = new ArrayList<>();
        for (RepairOrder order : orders) {
            result.add(toMap(order));
        }
        return ok(result);
    }

    @GetMapping("/{orderId}")
    public Map<String, Object> detail(@PathVariable Long orderId) {
        RepairOrder o = repairOrderService.selectRepairOrderById(orderId);
        if (o == null) return fail(404, "工单不存在");
        return ok(toMap(o));
    }

    @PutMapping("/{orderId}/cancel")
    public Map<String, Object> cancel(@PathVariable Long orderId) {
        int result = repairOrderService.cancelRepairOrder(orderId);
        if (result == 0) {
            RepairOrder o = repairOrderService.selectRepairOrderById(orderId);
            if (o == null) return fail(404, "工单不存在");
        }
        RepairOrder o = repairOrderService.selectRepairOrderById(orderId);
        return ok(Collections.singletonMap("status", o.getOrderStatus()));
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) return fail(400, "文件为空");
        Path root = Paths.get("uploads", "repair");
        Files.createDirectories(root);
        String ext = getExt(Objects.requireNonNull(file.getOriginalFilename()));
        String name = System.currentTimeMillis() + "-" + UUID.randomUUID() + (ext.isEmpty()? "" : ("."+ext));
        Path dest = root.resolve(name);
        file.transferTo(dest.toFile());
        String url = "/uploads/repair/" + name;
        Map<String, Object> data = new HashMap<>();
        data.put("url", url);
        return ok(data);
    }

    private String getExt(String filename) {
        int i = filename.lastIndexOf('.');
        return i >= 0 ? filename.substring(i+1) : "";
    }

    private String toJsonArrayString(Collection<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean first = true;
        for (Object o : list) {
            if (!first) sb.append(',');
            first = false;
            sb.append('"').append(String.valueOf(o).replace("\"","\\\"")).append('"');
        }
        sb.append(']');
        return sb.toString();
    }

    private String asText(Object v) { return v == null ? null : String.valueOf(v); }

    private Map<String, Object> toMap(RepairOrder o) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("orderId", o.getOrderId());
        m.put("type", o.getRepairType());
        m.put("desc", o.getRepairDesc());
        m.put("contactName", o.getUserName());
        m.put("phone", o.getUserPhone());
        m.put("address", o.getRepairAddress());
        m.put("images", parseImages(o.getRepairImages()));
        m.put("status", o.getOrderStatus());
        m.put("createdAt", o.getCreateTime() == null ? null : o.getCreateTime().format(fmt));
        return m;
    }

    private List<String> parseImages(String json) {
        if (!StringUtils.hasText(json)) return Collections.emptyList();
        String s = json.trim();
        if (s.startsWith("[") && s.endsWith("]")) {
            s = s.substring(1, s.length()-1).trim();
            if (s.isEmpty()) return Collections.emptyList();
            List<String> list = new ArrayList<>();
            for (String part : s.split(",")) {
                String p = part.trim();
                if (p.startsWith("\"") && p.endsWith("\"")) p = p.substring(1, p.length()-1);
                list.add(p.replace("\\\"", "\""));
            }
            return list;
        }
        return Arrays.asList(s.split(","));
    }

    private Map<String, Object> ok(Object data) {
        Map<String, Object> r = new HashMap<>();
        r.put("code", 200);
        r.put("msg", "OK");
        r.put("data", data);
        return r;
    }

    private Map<String, Object> fail(int code, String msg) {
        Map<String, Object> r = new HashMap<>();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
}

