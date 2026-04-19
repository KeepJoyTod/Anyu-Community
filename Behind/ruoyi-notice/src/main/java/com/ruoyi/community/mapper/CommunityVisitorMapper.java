package com.ruoyi.community.mapper;

import java.util.List;
import com.ruoyi.community.domain.CommunityVisitor;

public interface CommunityVisitorMapper 
{
    public CommunityVisitor selectCommunityVisitorById(Long id);

    public List<CommunityVisitor> selectCommunityVisitorList(CommunityVisitor communityVisitor);

    public int insertCommunityVisitor(CommunityVisitor communityVisitor);

    public int updateCommunityVisitor(CommunityVisitor communityVisitor);

    public int deleteCommunityVisitorById(Long id);

    public int deleteCommunityVisitorByIds(Long[] ids);
}
