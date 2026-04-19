package com.ruoyi.service;

import com.ruoyi.domain.Suggestion;

import java.util.List;

public interface ISuggestionService
{
    boolean save(Suggestion suggestion);

    List<Suggestion> selectList(Suggestion suggestion);

    boolean removeById(Long id);
}
