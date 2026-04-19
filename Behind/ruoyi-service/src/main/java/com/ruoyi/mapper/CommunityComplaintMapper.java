package com.ruoyi.mapper;

import com.ruoyi.domain.CommunityComplaint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityComplaintMapper
{
    CommunityComplaint selectById(Long id);

    List<CommunityComplaint> selectList(CommunityComplaint complaint);

    int insert(CommunityComplaint complaint);

    int updateById(CommunityComplaint complaint);

    int deleteById(Long id);
}
