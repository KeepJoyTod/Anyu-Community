package com.ruoyi.service;

import com.ruoyi.domain.TrackEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITrackService {
    /**
     * 根据快递ID获取轨迹列表
     */
    List<TrackEntity> getTracksByPackageId(Long packageId);
    
    /**
     * 保存轨迹
     */
    boolean save(TrackEntity trackEntity);
}