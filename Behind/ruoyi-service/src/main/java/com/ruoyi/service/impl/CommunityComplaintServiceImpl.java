package com.ruoyi.service.impl;

import com.ruoyi.domain.CommunityComplaint;
import com.ruoyi.mapper.CommunityComplaintMapper;
import com.ruoyi.service.ICommunityComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("mainCommunityComplaintService")
public class CommunityComplaintServiceImpl implements ICommunityComplaintService
{
    @Autowired
    private CommunityComplaintMapper complaintMapper;

    @Override
    public boolean save(CommunityComplaint complaint)
    {
        try {
            return complaintMapper.insert(complaint) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CommunityComplaint> selectList(CommunityComplaint complaint)
    {
        try {
            return complaintMapper.selectList(complaint);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean removeById(Long id)
    {
        try {
            return complaintMapper.deleteById(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
