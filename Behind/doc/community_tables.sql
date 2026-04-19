-- 访客邀请表
DROP TABLE IF EXISTS community_visitor;
CREATE TABLE community_visitor (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    owner_id        BIGINT          DEFAULT NULL COMMENT '业主ID',
    owner_name      VARCHAR(50)     DEFAULT NULL COMMENT '业主姓名',
    visitor_name    VARCHAR(50)     NOT NULL COMMENT '访客姓名',
    visitor_phone   VARCHAR(20)     DEFAULT NULL COMMENT '访客电话',
    visitor_id_card VARCHAR(18)     DEFAULT NULL COMMENT '访客身份证号',
    visit_reason    VARCHAR(500)    DEFAULT NULL COMMENT '来访事由',
    expected_visit_time DATETIME    DEFAULT NULL COMMENT '预计来访时间',
    actual_visit_time DATETIME      DEFAULT NULL COMMENT '实际来访时间',
    leave_time      DATETIME        DEFAULT NULL COMMENT '离开时间',
    status          CHAR(1)         DEFAULT '0' COMMENT '状态（0:待来访,1:已到访,2:已离开,3:已取消）',
    invite_code     VARCHAR(20)     DEFAULT NULL COMMENT '邀请码',
    community_id    BIGINT          DEFAULT NULL COMMENT '小区ID',
    building        VARCHAR(50)     DEFAULT NULL COMMENT '楼栋',
    unit            VARCHAR(50)     DEFAULT NULL COMMENT '单元',
    room            VARCHAR(50)     DEFAULT NULL COMMENT '房间号',
    create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_owner_id (owner_id),
    KEY idx_invite_code (invite_code),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='访客邀请表';

-- 生活缴费账单表
DROP TABLE IF EXISTS community_bill;
CREATE TABLE community_bill (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    owner_id        BIGINT          DEFAULT NULL COMMENT '业主ID',
    owner_name      VARCHAR(50)     DEFAULT NULL COMMENT '业主姓名',
    bill_type       CHAR(1)         DEFAULT NULL COMMENT '账单类型（1:物业费,2:水费,3:电费,4:燃气费,5:停车费,6:其他）',
    bill_no         VARCHAR(50)     DEFAULT NULL COMMENT '账单编号',
    bill_amount     DECIMAL(10,2)   DEFAULT NULL COMMENT '账单金额',
    paid_amount     DECIMAL(10,2)   DEFAULT 0.00 COMMENT '已付金额',
    arrears_amount  DECIMAL(10,2)   DEFAULT NULL COMMENT '欠费金额',
    bill_date       DATE            DEFAULT NULL COMMENT '账单日期',
    due_date        DATE            DEFAULT NULL COMMENT '缴费截止日期',
    status          CHAR(1)         DEFAULT '0' COMMENT '状态（0:未支付,1:部分支付,2:已支付）',
    community_id    BIGINT          DEFAULT NULL COMMENT '小区ID',
    building        VARCHAR(50)     DEFAULT NULL COMMENT '楼栋',
    unit            VARCHAR(50)     DEFAULT NULL COMMENT '单元',
    room            VARCHAR(50)     DEFAULT NULL COMMENT '房间号',
    usage_amount    DECIMAL(10,2)   DEFAULT NULL COMMENT '用量（水电气等）',
    unit_price      DECIMAL(10,4)   DEFAULT NULL COMMENT '单价',
    last_reading    DECIMAL(10,2)   DEFAULT NULL COMMENT '上期读数',
    current_reading DECIMAL(10,2)   DEFAULT NULL COMMENT '本期读数',
    create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_owner_id (owner_id),
    KEY idx_bill_no (bill_no),
    KEY idx_status (status),
    KEY idx_bill_type (bill_type)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='生活缴费账单表';

-- 支付记录表
DROP TABLE IF EXISTS community_payment_record;
CREATE TABLE community_payment_record (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    bill_id         BIGINT          NOT NULL COMMENT '账单ID',
    owner_id        BIGINT          DEFAULT NULL COMMENT '业主ID',
    owner_name      VARCHAR(50)     DEFAULT NULL COMMENT '业主姓名',
    pay_amount      DECIMAL(10,2)   NOT NULL COMMENT '支付金额',
    pay_method      CHAR(1)         DEFAULT NULL COMMENT '支付方式（1:微信,2:支付宝,3:银行卡,4:现金）',
    transaction_no  VARCHAR(100)    DEFAULT NULL COMMENT '支付流水号',
    pay_time        DATETIME        DEFAULT NULL COMMENT '支付时间',
    status          CHAR(1)         DEFAULT '0' COMMENT '状态（0:待支付,1:支付成功,2:支付失败,3:已退款）',
    community_id    BIGINT          DEFAULT NULL COMMENT '小区ID',
    create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_bill_id (bill_id),
    KEY idx_owner_id (owner_id),
    KEY idx_transaction_no (transaction_no),
    KEY idx_pay_time (pay_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='支付记录表';

-- 社区通知表
DROP TABLE IF EXISTS community_notice;
CREATE TABLE community_notice (
    notice_id       BIGINT          NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    notice_title    VARCHAR(200)    NOT NULL COMMENT '通知标题',
    notice_content  TEXT            DEFAULT NULL COMMENT '通知内容',
    notice_type     CHAR(1)         DEFAULT '1' COMMENT '通知类型（1:公告,2:通知,3:紧急）',
    status          CHAR(1)         DEFAULT '0' COMMENT '状态（0:正常,1:关闭）',
    is_top          CHAR(1)         DEFAULT '0' COMMENT '是否置顶（0:否,1:是）',
    publish_time    DATETIME        DEFAULT NULL COMMENT '发布时间',
    publisher       VARCHAR(50)     DEFAULT NULL COMMENT '发布人',
    community_id    BIGINT          DEFAULT NULL COMMENT '小区ID',
    create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (notice_id),
    KEY idx_notice_type (notice_type),
    KEY idx_status (status),
    KEY idx_is_top (is_top),
    KEY idx_publish_time (publish_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='社区通知表';

-- 插入测试数据
INSERT INTO community_notice (notice_title, notice_content, notice_type, status, is_top, publish_time, publisher, create_time, update_time) VALUES
('关于小区电梯维护的通知', '尊敬的业主：\n\n为保障电梯安全运行，我司计划于2026年3月25日对小区电梯进行年度维护保养，届时将暂停使用。\n\n维护时间：2026年3月25日 9:00-17:00\n\n请各位业主提前做好出行安排，给您带来的不便敬请谅解。\n\n物业服务中心\n2026年3月24日', '2', '0', '1', NOW(), '物业服务中心', NOW(), NOW()),
('紧急停水通知', '尊敬的业主：\n\n因市政供水管道抢修，小区将于2026年3月24日18:00-22:00停水。\n\n请各位业主提前储水，给您带来的不便敬请谅解。\n\n物业服务中心', '3', '0', '1', NOW(), '物业服务中心', NOW(), NOW()),
('春季绿化养护通知', '尊敬的业主：\n\n为提升小区环境品质，我司将于近期开展春季绿化养护工作，包括修剪草坪、施肥、病虫害防治等。\n\n养护时间：2026年3月26日-3月28日\n\n请各位业主注意避让作业区域，感谢您的配合！\n\n物业服务中心', '1', '0', '0', NOW(), '物业服务中心', NOW(), NOW());

-- 物业信息表
DROP TABLE IF EXISTS community_property;
CREATE TABLE community_property (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    property_name   VARCHAR(100)    NOT NULL COMMENT '物业名称',
    contact_person  VARCHAR(50)     DEFAULT NULL COMMENT '联系人',
    contact_phone   VARCHAR(20)     DEFAULT NULL COMMENT '联系电话',
    address         VARCHAR(200)    DEFAULT NULL COMMENT '物业地址',
    work_time       VARCHAR(100)    DEFAULT NULL COMMENT '工作时间',
    service_content TEXT            DEFAULT NULL COMMENT '服务内容',
    community_id    BIGINT          DEFAULT NULL COMMENT '小区ID',
    create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='物业信息表';

-- 物业表扬表
DROP TABLE IF EXISTS community_property_praise;
CREATE TABLE community_property_praise (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    owner_id        BIGINT          DEFAULT NULL COMMENT '业主ID',
    owner_name      VARCHAR(50)     DEFAULT NULL COMMENT '业主姓名',
    praise_type     VARCHAR(50)     DEFAULT NULL COMMENT '表扬类型',
    praise_content  TEXT            NOT NULL COMMENT '表扬内容',
    praise_target   VARCHAR(100)    DEFAULT NULL COMMENT '表扬对象',
    status          CHAR(1)         DEFAULT '0' COMMENT '状态（0:待处理,1:已处理）',
    reply_content   TEXT            DEFAULT NULL COMMENT '回复内容',
    reply_time      DATETIME        DEFAULT NULL COMMENT '回复时间',
    community_id    BIGINT          DEFAULT NULL COMMENT '小区ID',
    create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_owner_id (owner_id),
    KEY idx_status (status)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='物业表扬表';

-- 插入物业信息测试数据
INSERT INTO community_property (property_name, contact_person, contact_phone, address, work_time, service_content, create_time) VALUES
('阳光家园物业服务中心', '张经理', '010-88888888', '北京市东城区阳光家园社区物业楼1层', '周一至周五 8:00-18:00，周六日 9:00-17:00', '1. 日常维修服务\n2. 环境卫生维护\n3. 安全保卫服务\n4. 停车场管理\n5. 绿化养护\n6. 代收快递\n7. 社区活动组织', NOW());

-- 社区用户表
DROP TABLE IF EXISTS community_user;
CREATE TABLE community_user (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    phone           VARCHAR(20)     NOT NULL COMMENT '手机号码',
    password        VARCHAR(100)    NOT NULL COMMENT '密码',
    real_name       VARCHAR(50)     DEFAULT NULL COMMENT '真实姓名',
    avatar          VARCHAR(200)    DEFAULT NULL COMMENT '头像',
    gender          CHAR(1)         DEFAULT '0' COMMENT '性别（0:未知,1:男,2:女）',
    community_id    BIGINT          DEFAULT NULL COMMENT '小区ID',
    building        VARCHAR(50)     DEFAULT NULL COMMENT '楼栋',
    unit            VARCHAR(50)     DEFAULT NULL COMMENT '单元',
    room            VARCHAR(50)     DEFAULT NULL COMMENT '房间号',
    status          CHAR(1)         DEFAULT '0' COMMENT '状态（0:正常,1:停用）',
    last_login_time DATETIME        DEFAULT NULL COMMENT '最后登录时间',
    create_by       VARCHAR(64)     DEFAULT '' COMMENT '创建者',
    create_time     DATETIME        DEFAULT NULL COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT '' COMMENT '更新者',
    update_time     DATETIME        DEFAULT NULL COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_phone (phone)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='社区用户表';

-- 插入测试用户数据（密码为123456）
INSERT INTO community_user (phone, password, real_name, community_id, building, unit, room, create_time) VALUES
('13800138000', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '张三', 1, '1号楼', '1单元', '101', NOW()),
('13800138001', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '李四', 1, '2号楼', '2单元', '202', NOW());
