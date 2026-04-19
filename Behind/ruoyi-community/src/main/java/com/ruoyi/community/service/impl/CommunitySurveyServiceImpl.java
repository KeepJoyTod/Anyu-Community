package com.ruoyi.community.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.community.domain.CommunitySurvey;
import com.ruoyi.community.mapper.CommunitySurveyMapper;
import com.ruoyi.community.service.ICommunitySurveyService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 满意度调查Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
@Service
public class CommunitySurveyServiceImpl implements ICommunitySurveyService
{
    @Autowired
    private CommunitySurveyMapper communitySurveyMapper;

    /**
     * 查询满意度调查
     * 
     * @param id 满意度调查主键
     * @return 满意度调查
     */
    @Override
    public CommunitySurvey selectCommunitySurveyById(Long id)
    {
        return communitySurveyMapper.selectCommunitySurveyById(id);
    }

    /**
     * 查询满意度调查列表
     * 
     * @param communitySurvey 满意度调查
     * @return 满意度调查集合
     */
    @Override
    public List<CommunitySurvey> selectCommunitySurveyList(CommunitySurvey communitySurvey)
    {
        return communitySurveyMapper.selectCommunitySurveyList(communitySurvey);
    }

    /**
     * 新增满意度调查
     * 
     * @param communitySurvey 满意度调查
     * @return 结果
     */
    @Override
    public int insertCommunitySurvey(CommunitySurvey communitySurvey)
    {
        // 将服务评价列表转换为JSON字符串
        if (communitySurvey.getServiceList() != null && !communitySurvey.getServiceList().isEmpty()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String servicesJson = mapper.writeValueAsString(communitySurvey.getServiceList());
                communitySurvey.setServices(servicesJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        communitySurvey.setCreateTime(DateUtils.getNowDate());
        communitySurvey.setUpdateTime(DateUtils.getNowDate());
        return communitySurveyMapper.insertCommunitySurvey(communitySurvey);
    }

    /**
     * 修改满意度调查
     * 
     * @param communitySurvey 满意度调查
     * @return 结果
     */
    @Override
    public int updateCommunitySurvey(CommunitySurvey communitySurvey)
    {
        // 将服务评价列表转换为JSON字符串
        if (communitySurvey.getServiceList() != null && !communitySurvey.getServiceList().isEmpty()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String servicesJson = mapper.writeValueAsString(communitySurvey.getServiceList());
                communitySurvey.setServices(servicesJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        communitySurvey.setUpdateTime(DateUtils.getNowDate());
        return communitySurveyMapper.updateCommunitySurvey(communitySurvey);
    }

    /**
     * 批量删除满意度调查
     * 
     * @param ids 需要删除的满意度调查主键集合
     * @return 结果
     */
    @Override
    public int deleteCommunitySurveyByIds(Long[] ids)
    {
        return communitySurveyMapper.deleteCommunitySurveyByIds(ids);
    }

    /**
     * 删除满意度调查信息
     * 
     * @param id 满意度调查主键
     * @return 结果
     */
    @Override
    public int deleteCommunitySurveyById(Long id)
    {
        return communitySurveyMapper.deleteCommunitySurveyById(id);
    }
}
