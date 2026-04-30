package com.ruoyi.web.controller.station;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.PackageEntity;
import com.ruoyi.service.IPackageService;
import com.ruoyi.system.domain.CommunityStation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/station/station")
public class CommunityStationController extends BaseController {
    @Autowired
    private IPackageService packageService;

    @GetMapping("/list")
    public TableDataInfo list(CommunityStation communityStation) {
        startPage();
        List<CommunityStation> list = packageService.listPackages(toPackageQuery(communityStation))
                .stream()
                .map(this::toStation)
                .collect(Collectors.toList());
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, CommunityStation communityStation) {
        List<CommunityStation> list = packageService.listPackages(toPackageQuery(communityStation))
                .stream()
                .map(this::toStation)
                .collect(Collectors.toList());
        ExcelUtil<CommunityStation> util = new ExcelUtil<>(CommunityStation.class);
        util.exportExcel(response, list, "package_station");
    }

    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        PackageEntity packageEntity = packageService.getPackageById(id);
        return success(packageEntity == null ? null : toStation(packageEntity));
    }

    @Log(title = "package_station", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommunityStation communityStation) {
        return toAjax(packageService.savePackage(toPackage(communityStation, true)));
    }

    @Log(title = "package_station", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommunityStation communityStation) {
        return toAjax(packageService.updatePackage(toPackage(communityStation, false)));
    }

    @Log(title = "package_station", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(packageService.deletePackagesByIds(ids));
    }

    private PackageEntity toPackageQuery(CommunityStation station) {
        PackageEntity packageEntity = new PackageEntity();
        if (station == null) {
            return packageEntity;
        }
        packageEntity.setId(station.getId());
        packageEntity.setTrackingNo(station.getExpressNo());
        packageEntity.setCompany(station.getExpressCompany());
        packageEntity.setPickupCode(station.getUserRoom());
        packageEntity.setStatus(toStatusCode(station.getStationStatus()));
        packageEntity.setArrivalTime(toLocalDateTime(station.getStorageTime()));
        return packageEntity;
    }

    private PackageEntity toPackage(CommunityStation station, boolean withDefaults) {
        PackageEntity packageEntity = toPackageQuery(station);
        if (withDefaults && packageEntity.getStatus() == null) {
            packageEntity.setStatus(2);
        }
        if (withDefaults && packageEntity.getArrivalTime() == null) {
            packageEntity.setArrivalTime(LocalDateTime.now());
        }
        return packageEntity;
    }

    private CommunityStation toStation(PackageEntity packageEntity) {
        CommunityStation station = new CommunityStation();
        station.setId(packageEntity.getId());
        station.setExpressNo(packageEntity.getTrackingNo());
        station.setExpressCompany(packageEntity.getCompany());
        station.setUserRoom(packageEntity.getPickupCode());
        station.setStationStatus(toStatusLabel(packageEntity.getStatus()));
        station.setStorageTime(toDate(packageEntity.getArrivalTime()));
        if (Integer.valueOf(3).equals(packageEntity.getStatus())) {
            station.setPickTime(toDate(packageEntity.getArrivalTime()));
        }
        station.setOperator("\u7cfb\u7edf");
        return station;
    }

    private Integer toStatusCode(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(status);
        } catch (NumberFormatException ignored) {
            String value = status.toLowerCase();
            if (value.contains("picked") || status.contains("\u5df2\u53d6")) {
                return 3;
            }
            if (value.contains("pending") || value.contains("in_storage")
                    || status.contains("\u5f85\u53d6") || status.contains("\u5df2\u5165\u5e93")) {
                return 2;
            }
            if (value.contains("exception") || value.contains("overdue") || status.contains("\u5f02\u5e38")) {
                return 4;
            }
            return 1;
        }
    }

    private String toStatusLabel(Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case 2:
                return "\u5f85\u53d6\u4ef6";
            case 3:
                return "\u5df2\u53d6\u4ef6";
            case 4:
                return "\u5f02\u5e38";
            case 0:
            case 1:
            default:
                return "\u8fd0\u8f93\u4e2d";
        }
    }

    private LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
