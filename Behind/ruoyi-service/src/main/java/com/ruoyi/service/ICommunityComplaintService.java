package com.ruoyi.service;

import com.ruoyi.domain.CommunityComplaint;

import java.util.List;

public interface ICommunityComplaintService
{
    boolean save(CommunityComplaint complaint);

    List<CommunityComplaint> selectList(CommunityComplaint complaint);

    boolean removeById(Long id);
}
