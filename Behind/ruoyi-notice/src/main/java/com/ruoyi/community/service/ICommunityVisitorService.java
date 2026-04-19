package com.ruoyi.community.service;

import java.util.List;
import com.ruoyi.community.domain.CommunityVisitor;

public interface ICommunityVisitorService 
{
    public CommunityVisitor selectCommunityVisitorById(Long id);

    public List<CommunityVisitor> selectCommunityVisitorList(CommunityVisitor communityVisitor);

    public int insertCommunityVisitor(CommunityVisitor communityVisitor);

    public int updateCommunityVisitor(CommunityVisitor communityVisitor);

    public int deleteCommunityVisitorByIds(Long[] ids);

    public int deleteCommunityVisitorById(Long id);
}
