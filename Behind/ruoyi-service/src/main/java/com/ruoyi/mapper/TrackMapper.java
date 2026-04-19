package com.ruoyi.mapper;

import com.ruoyi.domain.TrackEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrackMapper {
    /**
     * 根据快递ID获取轨迹列表
     */
    List<TrackEntity> selectTracksByPackageId(Long packageId);
    
    /**
     * 插入轨迹
     */
    int insert(TrackEntity trackEntity);
}