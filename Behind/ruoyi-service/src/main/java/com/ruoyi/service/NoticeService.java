package com.ruoyi.service;

import java.util.Map;

/**
 * 通知服务接口
 */
public interface NoticeService {

    /**
     * 获取通知列表
     * @param page 页码
     * @param size 每页大小
     * @return 包含通知列表、总数和未读数量的Map
     */
    Map<String, Object> listNotices(int page, int size);

    /**
     * 获取通知详情
     * @param id 通知ID
     * @return 通知详情Map
     */
    Map<String, Object> getNoticeDetail(Long id);

    /**
     * 标记通知为已读
     * @param id 通知ID
     * @return 更新结果
     */
    boolean markNoticeRead(Long id);

    /**
     * 标记所有通知为已读
     * @return 更新的数量
     */
    int markAllNoticesRead();

    /**
     * 获取未读通知数量
     * @return 未读通知数量
     */
    long getUnreadCount();

    /**
     * 获取所有通知数量
     * @return 所有通知数量
     */
    long getTotalCount();
}
