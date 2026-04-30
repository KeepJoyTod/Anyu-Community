package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.domain.CommunityElectricityFee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 电费表 Mapper 接口
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Mapper
public interface CommunityElectricityFeeMapper extends BaseMapper<CommunityElectricityFee> {
    List<CommunityElectricityFee> selectCommunityElectricityFeeList(CommunityElectricityFee communityElectricityFee);


    List<CommunityElectricityFee> selectByUserId(Long userId);

    List<CommunityElectricityFee> selectByFeeStatus(String feeStatus);

    List<CommunityElectricityFee> selectByReadMonth(String readMonth);

    int updateById(CommunityElectricityFee communityElectricityFee);

    int insert(CommunityElectricityFee communityElectricityFee);

    CommunityElectricityFee selectById(Long electricityId);

    int deleteCommunityElectricityFeeByIds(Long[] electricityIds);

}
