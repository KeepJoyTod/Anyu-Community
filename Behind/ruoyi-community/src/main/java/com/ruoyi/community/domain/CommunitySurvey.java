package com.ruoyi.community.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 满意度调查对象 community_survey
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
public class CommunitySurvey extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 整体满意度（1-5星） */
    @Excel(name = "整体满意度", readConverterExp = "1=非常不满意,2=不满意,3=一般,4=满意,5=非常满意")
    private Integer rating;

    /** 具体服务评价（JSON格式） */
    @Excel(name = "具体服务评价")
    private String services;

    /** 建议和意见 */
    @Excel(name = "建议和意见")
    private String suggestion;

    // 服务评价列表（用于前端展示和后端处理）
    private List<ServiceRating> serviceList;

    // 服务评价内部类
    public static class ServiceRating {
        private String name;
        private Integer rating;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public List<ServiceRating> getServiceList() {
        if (serviceList == null && StringUtils.isNotBlank(services)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(services);
                serviceList = new ArrayList<>();
                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        ServiceRating serviceRating = new ServiceRating();
                        serviceRating.setName(node.get("name").asText());
                        serviceRating.setRating(node.get("rating").asInt());
                        serviceList.add(serviceRating);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return serviceList;
    }

    public void setServiceList(List<ServiceRating> serviceList) {
        this.serviceList = serviceList;
    }
}
