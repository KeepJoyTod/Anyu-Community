package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityPayment;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CommunityPaymentMapper {
    CommunityPayment selectPaymentById(Long paymentId);
    List<CommunityPayment> selectUnpaidByUserId(Long userId);
    int updateStatusToPaid(@Param("paymentId") Long paymentId, @Param("paymentNo") String paymentNo);
}