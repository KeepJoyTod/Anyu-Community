package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.domain.CommunityWaterFee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommunityWaterFeeMapper extends BaseMapper<CommunityWaterFee> {
    CommunityWaterFee selectCommunityWaterFeeById(Long waterId);
    
    List<CommunityWaterFee> selectCommunityWaterFeeByUserId(Long userId);
    
    List<CommunityWaterFee> selectCommunityWaterFeeList(CommunityWaterFee communityWaterFee);
    
    int updateCommunityWaterFee(CommunityWaterFee communityWaterFee);
    
    int insertCommunityWaterFee(CommunityWaterFee communityWaterFee);

    int deleteCommunityWaterFeeByIds(Long[] waterIds);
}
