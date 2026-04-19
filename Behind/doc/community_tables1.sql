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
