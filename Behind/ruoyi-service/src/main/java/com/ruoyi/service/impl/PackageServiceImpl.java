package com.ruoyi.service.impl;

import com.ruoyi.domain.PackageEntity;
import com.ruoyi.domain.TrackEntity;
import com.ruoyi.mapper.PackageMapper;
import com.ruoyi.service.IPackageService;
import com.ruoyi.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PackageServiceImpl implements IPackageService {

    @Autowired
    private PackageMapper packageMapper;

    @Autowired
    private ITrackService trackService;

    @Override
    public List<PackageEntity> listPackages(PackageEntity packageEntity) {
        return packageMapper.selectList(packageEntity);
    }

    @Override
    public List<PackageEntity> getPackageList(int page, int size) {
        int offset = (page - 1) * size;
        return packageMapper.selectPackageList(offset, size);
    }

    @Override
    public PackageEntity getPackageById(Long id) {
        return packageMapper.selectById(id);
    }

    @Override
    public List<TrackEntity> getPackageTracks(Long packageId) {
        return trackService.getTracksByPackageId(packageId);
    }

    @Override
    public boolean pickupPackage(Long id) {
        PackageEntity packageEntity = packageMapper.selectById(id);
        if (packageEntity != null) {
            packageEntity.setStatus(3);
            boolean result = packageMapper.updateById(packageEntity) > 0;
            
            // 添加取件轨迹
            TrackEntity trackEntity = new TrackEntity();
            trackEntity.setPackageId(id);
            trackEntity.setTrackTime(LocalDateTime.now());
            trackEntity.setDescription("快递已被取件");
            trackService.save(trackEntity);
            
            return result;
        }
        return false;
    }

    @Override
    public int getPendingCount() {
        return packageMapper.selectPendingCount();
    }

    @Override
    public List<PackageEntity> getPendingPackages() {
        return packageMapper.selectPendingPackages();
    }

    @Override
    public int savePackage(PackageEntity packageEntity) {
        if (packageEntity.getArrivalTime() == null) {
            packageEntity.setArrivalTime(LocalDateTime.now());
        }
        if (packageEntity.getStatus() == null) {
            packageEntity.setStatus(2);
        }
        return packageMapper.insert(packageEntity);
    }

    @Override
    public int updatePackage(PackageEntity packageEntity) {
        return packageMapper.updateById(packageEntity);
    }

    @Override
    public int deletePackagesByIds(Long[] ids) {
        return packageMapper.deleteByIds(ids);
    }
}
