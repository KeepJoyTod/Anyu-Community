package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityElectricityFee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CommunityElectricityFeeMapper {
    CommunityElectricityFee selectElectricityFeeById(Long electricityId);
    List<CommunityElectricityFee> selectUnpaidByUserId(Long userId);
    int updateStatusToPaid(@Param("electricityId") Long electricityId, @Param("paymentNo") String paymentNo);
    int insertElectricityFee(CommunityElectricityFee electricityFee);
    int updateBalanceByUserId(@Param("userId") Long userId, @Param("amount") java.math.BigDecimal amount);
    CommunityElectricityFee selectLatestByUserId(Long userId);
}