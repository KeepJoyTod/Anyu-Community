package com.ruoyi.web.controller.service;

import com.ruoyi.domain.PackageEntity;
import com.ruoyi.domain.TrackEntity;
import com.ruoyi.service.IPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/station")
@CrossOrigin
public class PackageController {

    @Autowired
    private IPackageService packageService;

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @GetMapping("/packages")
    public Map<String, Object> getPackages(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        List<PackageEntity> packages = packageService.getPackageList(page, size);
        List<Map<String, Object>> list = packages.stream().map(this::toMap).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        return result;
    }

    @GetMapping("/packages/{id}")
    public Map<String, Object> getPackageDetail(@PathVariable("id") Long id) {
        PackageEntity pkg = packageService.getPackageById(id);
        Map<String, Object> result = new HashMap<>();
        if (pkg != null) {
            Map<String, Object> data = toMap(pkg);
            List<TrackEntity> tracks = packageService.getPackageTracks(id);
            List<Map<String, Object>> trackList = tracks.stream().map(t -> {
                Map<String, Object> tm = new HashMap<>();
                tm.put("time", t.getTrackTime() != null ? t.getTrackTime().format(fmt) : null);
                tm.put("desc", t.getDescription());
                return tm;
            }).collect(Collectors.toList());
            data.put("tracks", trackList);

            result.put("code", 200);
            result.put("msg", "success");
            result.put("data", data);
        } else {
            result.put("code", 404);
            result.put("msg", "Package not found");
        }
        return result;
    }

    @GetMapping("/packages/{id}/tracks")
    public Map<String, Object> getPackageTracks(@PathVariable("id") Long id) {
        List<TrackEntity> tracks = packageService.getPackageTracks(id);
        List<Map<String, Object>> trackList = tracks.stream().map(t -> {
            Map<String, Object> tm = new HashMap<>();
            tm.put("time", t.getTrackTime() != null ? t.getTrackTime().format(fmt) : null);
            tm.put("desc", t.getDescription());
            return tm;
        }).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", trackList);
        return result;
    }

    @PutMapping("/packages/{id}/pickup")
    public Map<String, Object> pickupPackage(@PathVariable("id") Long id) {
        boolean result = packageService.pickupPackage(id);
        Map<String, Object> response = new HashMap<>();
        if (result) {
            response.put("code", 200);
            response.put("msg", "success");
        } else {
            response.put("code", 500);
            response.put("msg", "Failed to update status");
        }
        return response;
    }

    @GetMapping("/packages/pending/count")
    public Map<String, Object> getPendingCount() {
        int count = packageService.getPendingCount();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", count);
        return result;
    }

    @GetMapping("/packages/pending")
    public Map<String, Object> getPendingPackages() {
        List<PackageEntity> packages = packageService.getPendingPackages();
        List<Map<String, Object>> list = packages.stream().map(this::toMap).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", list);
        return result;
    }

    private Map<String, Object> toMap(PackageEntity e) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", e.getId());
        m.put("company", e.getCompany());
        m.put("expressIcon", e.getExpressIcon());
        m.put("pickupCode", e.getPickupCode());
        m.put("trackingNo", e.getTrackingNo());
        m.put("arrivalTime", e.getArrivalTime() != null ? e.getArrivalTime().format(fmt) : null);
        m.put("status", e.getStatus());
        return m;
    }
}