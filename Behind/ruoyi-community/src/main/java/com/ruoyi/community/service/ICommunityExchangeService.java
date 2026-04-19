package com.ruoyi.community.service;

import java.util.List;
import java.util.Map;

public interface ICommunityExchangeService {
    boolean exchangePoints(Long userId, String billType, Integer pointsUsed, Double amount);
    boolean exchangePoints(Long userId, String billType, Long billId, Integer pointsUsed);
    List<Map<String, Object>> getUnpaidBillsByUserId(Long userId);
    List<Map<String, Object>> getExchangeRecordsByUserId(Long userId);
}