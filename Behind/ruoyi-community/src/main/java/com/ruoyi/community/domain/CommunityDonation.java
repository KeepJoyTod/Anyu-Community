package com.ruoyi.community.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

public class CommunityDonation extends BaseEntity {

    private Long id;
    private String title;
    private String content;
    private BigDecimal targetMoney;
    private BigDecimal nowMoney;
    private Integer donorCount;
    private String image;
    private Integer status;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public BigDecimal getTargetMoney() {
        return targetMoney;
    }
    public void setTargetMoney(BigDecimal targetMoney) {
        this.targetMoney = targetMoney;
    }
    public BigDecimal getNowMoney() {
        return nowMoney;
    }
    public void setNowMoney(BigDecimal nowMoney) {
        this.nowMoney = nowMoney;
    }
    public Integer getDonorCount() {
        return donorCount;
    }
    public void setDonorCount(Integer donorCount) {
        this.donorCount = donorCount;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

}