package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.domain.CommunityPoints;

import java.util.List;

/**
 * <p>
 * 积分表 Mapper 接口
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface CommunityPointsMapper extends BaseMapper<CommunityPoints> {


    Integer getMyPointsByName(String username);
}
