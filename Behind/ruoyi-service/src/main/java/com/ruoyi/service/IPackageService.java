package com.ruoyi.service;

import com.ruoyi.domain.PackageEntity;
import com.ruoyi.domain.TrackEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPackageService {
    /**
     * 获取快递列表（分页）
     */
    List<PackageEntity> getPackageList(int page, int size);

    /**
     * 获取快递详情
     */
    PackageEntity getPackageById(Long id);

    /**
     * 获取快递轨迹
     */
    List<TrackEntity> getPackageTracks(Long packageId);

    /**
     * 取件
     */
    boolean pickupPackage(Long id);

    /**
     * 获取待取件数量
     */
    int getPendingCount();

    /**
     * 获取待取件列表
     */
    List<PackageEntity> getPendingPackages();
}