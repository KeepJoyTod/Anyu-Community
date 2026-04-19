package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 社区住户信息对象 community_user
 *
 * @author ruoyi
 * @date 2026-03-20
 */
public class CommunityUser1 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID（住户唯一标识） */
    private String id;

    /** 住户姓名 */
    @Excel(name = "住户姓名")
    private String name;

    /** 手机号（11位，唯一） */
    @Excel(name = "手机号", readConverterExp = "1=1位，唯一")
    private String phone;

    /** 身份证号（可选，脱敏存储） */
    @Excel(name = "身份证号", readConverterExp = "可=选，脱敏存储")
    private String idCard;

    /** 性别：0-未知，1-男，2-女 */
    @Excel(name = "性别：0-未知，1-男，2-女")
    private Long gender;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 入住日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入住日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date liveData;

    /** 具体住址（如：3号楼2单元08室/10号楼1单元102室） */
    @Excel(name = "具体住址", readConverterExp = "如=：3号楼2单元08室/10号楼1单元102室")
    private String specificAddress;

    /** 紧急联系人姓名 */
    @Excel(name = "紧急联系人姓名")
    private String emergencyContact;

    /** 紧急联系人手机号 */
    @Excel(name = "紧急联系人手机号")
    private String emergencyPhone;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getIdCard()
    {
        return idCard;
    }

    public void setGender(Long gender)
    {
        this.gender = gender;
    }

    public Long getGender()
    {
        return gender;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setLiveData(Date liveData)
    {
        this.liveData = liveData;
    }

    public Date getLiveData()
    {
        return liveData;
    }

    public void setSpecificAddress(String specificAddress)
    {
        this.specificAddress = specificAddress;
    }

    public String getSpecificAddress()
    {
        return specificAddress;
    }

    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyPhone(String emergencyPhone)
    {
        this.emergencyPhone = emergencyPhone;
    }

    public String getEmergencyPhone()
    {
        return emergencyPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("phone", getPhone())
                .append("idCard", getIdCard())
                .append("gender", getGender())
                .append("birthday", getBirthday())
                .append("liveData", getLiveData())
                .append("specificAddress", getSpecificAddress())
                .append("emergencyContact", getEmergencyContact())
                .append("emergencyPhone", getEmergencyPhone())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("remark", getRemark())
                .toString();
    }
}
