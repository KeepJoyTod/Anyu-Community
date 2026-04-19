package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.domain.CommunityRepairr;

import java.util.List;

/**
 * <p>
 * 维修表 Mapper 接口
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
public interface CommunityRepairrMapper extends BaseMapper<CommunityRepairr> {

    List<String> selectGetRepairList();
}
