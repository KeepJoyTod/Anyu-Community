package com.ruoyi.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.domain.CommunityPoints;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface ICommunityPointsService extends IService<CommunityPoints> {


    Integer getMyPointsByName(String username);
}
