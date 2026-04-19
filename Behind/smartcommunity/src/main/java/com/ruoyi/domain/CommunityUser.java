package com.ruoyi.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("community_user")
public class CommunityUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String phone;

    private String idCard;

    private Integer gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date liveData;

    private String specificAddress;

    private String emergencyContact;

    private String emergencyPhone;

    private String password;

}
