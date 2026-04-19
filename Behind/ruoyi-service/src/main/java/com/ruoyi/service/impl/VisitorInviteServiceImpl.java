package com.ruoyi.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.domain.VisitorInvite;
import com.ruoyi.mapper.VisitorInviteMapper;
import com.ruoyi.service.IVisitorInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 访客邀请Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class VisitorInviteServiceImpl implements IVisitorInviteService {

    @Autowired
    private VisitorInviteMapper visitorInviteMapper;

    @Override
    public VisitorInvite selectVisitorInviteById(Long id) {
        return visitorInviteMapper.selectById(id);
    }

    @Override
    public List<VisitorInvite> selectRecentVisitorInvite(Integer limit) {
        return visitorInviteMapper.selectRecentVisitorInvite(limit);
    }

    @Override
    public int insertVisitorInvite(VisitorInvite visitorInvite) {
        // 新增默认设置待来访状态（0），对应前端待签入
        if (visitorInvite.getStatus() == null) {
            visitorInvite.setStatus("0");
        }
        // 【修正】适配RuoYi BaseEntity的Date类型，设置创建时间和创建人
        visitorInvite.setCreateTime(new Date());
        visitorInvite.setCreateBy(SecurityUtils.getUsername());
        return visitorInviteMapper.insert(visitorInvite);
    }

    @Override
    public int signInVisitor(Long id) {
        return visitorInviteMapper.updateVisitorSignInStatus(id);
    }
}