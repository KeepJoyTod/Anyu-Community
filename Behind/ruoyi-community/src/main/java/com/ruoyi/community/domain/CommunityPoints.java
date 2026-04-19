package com.ruoyi.community.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class CommunityPoints extends BaseEntity {
    private long serialVersionUID = 1L;

    @Excel(name = "积分ID")
    private Long pointsId;

    @Excel(name = "用户ID")
    private Long userId;

    @Excel(name = "总积分")
    private Integer totalPoints;

    @Excel(name = "可用积分")
    private Integer availablePoints;

    @Excel(name = "冻结积分")
    private Integer frozenPoints;

    @Excel(name = "勋章ID")
    private Long medalId;

    public Long getPointsId() { return pointsId; }
    public void setPointsId(Long pointsId) { this.pointsId = pointsId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
    public Integer getAvailablePoints() { return availablePoints; }
    public void setAvailablePoints(Integer availablePoints) { this.availablePoints = availablePoints; }
    public Integer getFrozenPoints() { return frozenPoints; }
    public void setFrozenPoints(Integer frozenPoints) { this.frozenPoints = frozenPoints; }
    public Long getMedalId() { return medalId; }
    public void setMedalId(Long medalId) { this.medalId = medalId; }
}