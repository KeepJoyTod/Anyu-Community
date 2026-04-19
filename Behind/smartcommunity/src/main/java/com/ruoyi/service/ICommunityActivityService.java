package com.ruoyi.service;

import com.ruoyi.domain.CommunityActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 社区活动表 服务类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface ICommunityActivityService extends IService<CommunityActivity> {


    List<String> getRepairList();
}
