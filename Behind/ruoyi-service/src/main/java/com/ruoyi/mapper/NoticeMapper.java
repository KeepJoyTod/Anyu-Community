package com.ruoyi.mapper;

import com.ruoyi.domain.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知Mapper接口
 */
@Mapper
public interface NoticeMapper {
    /**
     * 分页查询通知
     */
    List<Notice> selectPage(@Param("offset") int offset, @Param("size") int size);
    
    /**
     * 统计所有通知数量
     */
    long selectCountAll();
    
    /**
     * 统计未读通知数量
     */
    long selectCountUnread();
    
    /**
     * 查询未读通知
     */
    List<Notice> selectUnread();
    
    /**
     * 根据ID查询通知
     */
    Notice selectById(Long id);
    
    /**
     * 根据ID更新通知
     */
    int updateById(Notice notice);
}

