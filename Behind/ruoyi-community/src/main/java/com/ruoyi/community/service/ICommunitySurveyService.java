package com.ruoyi.community.service;

import com.ruoyi.community.domain.CommunitySurvey;

import java.util.List;

/**
 * 满意度调查Service接口
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
public interface ICommunitySurveyService
{
    /**
     * 查询满意度调查
     * 
     * @param id 满意度调查主键
     * @return 满意度调查
     */
    public CommunitySurvey selectCommunitySurveyById(Long id);

    /**
     * 查询满意度调查列表
     * 
     * @param communitySurvey 满意度调查
     * @return 满意度调查集合
     */
    public List<CommunitySurvey> selectCommunitySurveyList(CommunitySurvey communitySurvey);

    /**
     * 新增满意度调查
     * 
     * @param communitySurvey 满意度调查
     * @return 结果
     */
    public int insertCommunitySurvey(CommunitySurvey communitySurvey);

    /**
     * 修改满意度调查
     * 
     * @param communitySurvey 满意度调查
     * @return 结果
     */
    public int updateCommunitySurvey(CommunitySurvey communitySurvey);

    /**
     * 批量删除满意度调查
     * 
     * @param ids 需要删除的满意度调查主键集合
     * @return 结果
     */
    public int deleteCommunitySurveyByIds(Long[] ids);

    /**
     * 删除满意度调查信息
     * 
     * @param id 满意度调查主键
     * @return 结果
     */
    public int deleteCommunitySurveyById(Long id);
}
