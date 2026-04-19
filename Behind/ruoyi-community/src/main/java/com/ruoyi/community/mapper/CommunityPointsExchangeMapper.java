package com.ruoyi.community.mapper;

import com.ruoyi.community.domain.CommunityPointsExchange;
import java.util.List;

public interface CommunityPointsExchangeMapper {
    int insertPointsExchange(CommunityPointsExchange exchange);
    List<CommunityPointsExchange> selectByUserId(Long userId);
}