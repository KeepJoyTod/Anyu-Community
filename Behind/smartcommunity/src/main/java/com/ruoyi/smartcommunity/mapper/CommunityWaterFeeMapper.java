package com.ruoyi.smartcommunity.mapper;

import com.ruoyi.domain.CommunityWaterFee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 水费表 Mapper 接口
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Mapper
public interface CommunityWaterFeeMapper {
    List<CommunityWaterFee> selectCommunityWaterFeeByUserId(Long userId);
    List<CommunityWaterFee> selectCommunityWaterFeeList();
    CommunityWaterFee selectCommunityWaterFeeById(Long waterId);
    int updateCommunityWaterFee(CommunityWaterFee communityWaterFee);
    int insertCommunityWaterFee(CommunityWaterFee communityWaterFee);
}
