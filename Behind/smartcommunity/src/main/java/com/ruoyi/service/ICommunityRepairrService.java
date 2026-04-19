package com.ruoyi.service;

import com.ruoyi.domain.CommunityRepairr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 维修表 服务类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface ICommunityRepairrService extends IService<CommunityRepairr> {

    List<String> getList();

}
