package com.ruoyi.service.impl;

import com.ruoyi.domain.TrackEntity;
import com.ruoyi.mapper.TrackMapper;
import com.ruoyi.service.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements ITrackService {

    @Autowired
    private TrackMapper trackMapper;

    @Override
    public List<TrackEntity> getTracksByPackageId(Long packageId) {
        return trackMapper.selectTracksByPackageId(packageId);
    }
    
    /**
     * 保存轨迹
     */
    public boolean save(TrackEntity trackEntity) {
        return trackMapper.insert(trackEntity) > 0;
    }
}