package com.ruoyi.mapper;

import com.ruoyi.domain.PackageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PackageMapper {
    /**
     * 获取快递列表（分页）
     */
    List<PackageEntity> selectPackageList(int offset, int size);

    /**
     * 获取待取件数量
     */
    int selectPendingCount();

    /**
     * 获取待取件列表
     */
    List<PackageEntity> selectPendingPackages();
    
    /**
     * 根据ID查询快递
     */
    PackageEntity selectById(Long id);
    
    /**
     * 根据ID更新快递
     */
    int updateById(PackageEntity packageEntity);
}