package com.ruoyi.service.impl;

import com.ruoyi.domain.Notice;
import com.ruoyi.mapper.NoticeMapper;
import com.ruoyi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通知服务实现
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Map<String, Object> listNotices(int page, int size) {
        int offset = (page - 1) * size;
        List<Notice> notices = noticeMapper.selectPage(offset, size);
        List<Map<String, Object>> items = new ArrayList<>();
        for (Notice notice : notices) {
            items.add(toMap(notice));
        }
        long unreadCount = getUnreadCount();
        long totalCount = getTotalCount();
        Map<String, Object> result = new HashMap<>();
        result.put("list", items);
        result.put("total", totalCount);
        result.put("unreadCount", unreadCount);
        return result;
    }

    @Override
    public Map<String, Object> getNoticeDetail(Long id) {
        Notice notice = noticeMapper.selectById(id);
        if (notice == null) {
            return null;
        }
        // 增加阅读次数
        notice.setReadCount(notice.getReadCount() != null ? notice.getReadCount() + 1 : 1);
        noticeMapper.updateById(notice);
        return toMap(notice);
    }

    @Override
    public boolean markNoticeRead(Long id) {
        Notice notice = noticeMapper.selectById(id);
        if (notice == null) {
            return false;
        }
        // 增加阅读次数
        notice.setReadCount(notice.getReadCount() != null ? notice.getReadCount() + 1 : 1);
        return noticeMapper.updateById(notice) > 0;
    }

    @Override
    public int markAllNoticesRead() {
        List<Notice> unreadNotices = noticeMapper.selectUnread();
        if (CollectionUtils.isEmpty(unreadNotices)) {
            return 0;
        }
        int updated = 0;
        for (Notice notice : unreadNotices) {
            // 增加阅读次数
            notice.setReadCount(notice.getReadCount() != null ? notice.getReadCount() + 1 : 1);
            updated += noticeMapper.updateById(notice);
        }
        return updated;
    }

    @Override
    public long getUnreadCount() {
        return noticeMapper.selectCountUnread();
    }

    @Override
    public long getTotalCount() {
        return noticeMapper.selectCountAll();
    }

    /**
     * 将Notice对象转换为Map
     */
    private Map<String, Object> toMap(Notice notice) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", notice.getId());
        map.put("icon", getIconByType(notice.getNoticeType()));
        map.put("title", notice.getNoticeTitle());
        map.put("content", notice.getNoticeContent());
        // 使用moreInfo作为more_info字段，如果没有则使用publishUserName
        map.put("more_info", notice.getMoreInfo() != null ? notice.getMoreInfo() : notice.getPublishUserName());
        map.put("time", formatTime(notice.getPublishTime() != null ? notice.getPublishTime() : notice.getCreateTime()));
        map.put("read", notice.getReadCount() != null && notice.getReadCount() > 0);
        return map;
    }
    
    /**
     * 格式化时间
     */
    private String formatTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 根据通知类型获取图标
     */
    private String getIconByType(String noticeType) {
        if (noticeType == null) return "📄";
        switch (noticeType) {
            case "announcement":
                return "📢";
            case "notice":
                return "📝";
            case "warning":
                return "⚠️";
            default:
                return "📄";
        }
    }
}
