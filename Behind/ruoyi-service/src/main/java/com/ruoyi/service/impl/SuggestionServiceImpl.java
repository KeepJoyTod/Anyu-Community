package com.ruoyi.service.impl;

import com.ruoyi.domain.Suggestion;
import com.ruoyi.mapper.SuggestionMapper;
import com.ruoyi.service.ISuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionServiceImpl implements ISuggestionService
{
    @Autowired
    private SuggestionMapper suggestionMapper;

    @Override
    public boolean save(Suggestion suggestion)
    {
        try {
            return suggestionMapper.insert(suggestion) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Suggestion> selectList(Suggestion suggestion)
    {
        try {
            return suggestionMapper.selectList(suggestion);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean removeById(Long id)
    {
        try {
            return suggestionMapper.deleteById(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
