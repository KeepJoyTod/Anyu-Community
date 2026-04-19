package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class AiCommunityEvent {
    private Long id;
    private String eventTitle;
    private String eventContent;
    private String eventType;
    private String eventLevel;
    private String aiSuggest;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}