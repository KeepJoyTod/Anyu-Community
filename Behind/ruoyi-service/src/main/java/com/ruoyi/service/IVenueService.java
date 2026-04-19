package com.ruoyi.service;

import com.ruoyi.domain.Venue;

import java.util.List;

/**
 * 场地服务接口
 * 
 * @author ruoyi
 */
public interface IVenueService
{
    /**
     * 获取场地列表
     * 
     * @return 场地列表
     */
    List<Venue> listVenues();
}
