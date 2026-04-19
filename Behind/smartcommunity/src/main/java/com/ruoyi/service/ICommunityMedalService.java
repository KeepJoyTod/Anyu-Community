package com.ruoyi.service;

import com.ruoyi.domain.CommunityMedal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 勋章表 服务类
 * </p>
 *
 * @author ruirui
 * @since 2026-03-25
 */
@Service
public interface ICommunityMedalService extends IService<CommunityMedal> {

    Integer getMyHallByName(String username);
}
