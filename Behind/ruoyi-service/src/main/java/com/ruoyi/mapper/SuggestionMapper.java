package com.ruoyi.mapper;

import com.ruoyi.domain.Suggestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuggestionMapper
{
    Suggestion selectById(Long id);

    List<Suggestion> selectList(Suggestion suggestion);

    int insert(Suggestion suggestion);

    int updateById(Suggestion suggestion);

    int deleteById(Long id);
}
