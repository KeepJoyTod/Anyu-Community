package com.ruoyi.mapper;

import com.ruoyi.domain.Venue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 场地Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface VenueMapper {
    List<Venue> selectList();
}