package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityWaterFee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CommunityWaterFeeMapper {
    CommunityWaterFee selectWaterFeeById(Long waterId);
    List<CommunityWaterFee> selectUnpaidByUserId(Long userId);
    int updateStatusToPaid(@Param("waterId") Long waterId, @Param("paymentNo") String paymentNo);
    int insertWaterFee(CommunityWaterFee waterFee);
    int updateBalanceByUserId(@Param("userId") Long userId, @Param("amount") java.math.BigDecimal amount);
    CommunityWaterFee selectLatestByUserId(Long userId);
}