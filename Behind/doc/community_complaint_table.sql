-- 小区投诉表
DROP TABLE IF EXISTS community_complaint;
CREATE TABLE community_complaint (
    id                  BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id             BIGINT          DEFAULT NULL COMMENT '投诉人ID（关联sys_user表id）',
    user_name           VARCHAR(50)     DEFAULT NULL COMMENT '投诉人姓名（冗余）',
    user_phone          VARCHAR(20)     DEFAULT NULL COMMENT '投诉人手机号（冗余）',
    complaint_title     VARCHAR(200)    DEFAULT NULL COMMENT '投诉标题',
    complaint_content   TEXT            DEFAULT NULL COMMENT '投诉内容',
    complaint_type      VARCHAR(20)     DEFAULT NULL COMMENT '投诉类型（environment:环境问题, security:安全问题, service:服务问题, noise:噪音问题）',
    complaint_status    VARCHAR(20)     DEFAULT 'pending' COMMENT '投诉状态（pending:待处理, processing:处理中, resolved:已解决, rejected:已驳回）',
    handle_user_id      BIGINT          DEFAULT NULL COMMENT '处理人ID（关联sys_user表id）',
    handle_user_name    VARCHAR(50)     DEFAULT NULL COMMENT '处理人姓名（冗余）',
    handle_result       TEXT            DEFAULT NULL COMMENT '处理结果',
    handle_time         DATETIME        DEFAULT NULL COMMENT '处理时间',
    create_by           VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time         DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by           VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time         DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark              VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_complaint_status (complaint_status),
    KEY idx_complaint_type (complaint_type)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='小区投诉表';
