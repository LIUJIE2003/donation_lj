-- ========================================
-- 公益项目管理网站 - 数据库表结构
-- 数据库：MySQL 8.0
-- 字符集：utf8mb4
-- ========================================

USE donation_db;

-- ========================================
-- 1. 公益项目表
-- ========================================
DROP TABLE IF EXISTS project;
CREATE TABLE project (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    name VARCHAR(100) NOT NULL COMMENT '项目名称',
    description TEXT COMMENT '项目描述',
    image_url VARCHAR(255) COMMENT '项目图片URL',
    target_amount DECIMAL(15, 2) NOT NULL DEFAULT 0 COMMENT '目标金额',
    current_amount DECIMAL(15, 2) NOT NULL DEFAULT 0 COMMENT '当前已筹金额',
    beneficiary_count INT DEFAULT 0 COMMENT '受益人数',
    status INT DEFAULT 1 COMMENT '状态：0-未开始，1-进行中，2-已结束',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公益项目表';

-- ========================================
-- 2. 捐赠人表
-- ========================================
DROP TABLE IF EXISTS donor;
CREATE TABLE donor (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '捐赠人ID',
    name VARCHAR(50) NOT NULL COMMENT '捐赠人姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    address VARCHAR(255) COMMENT '联系地址',
    type INT DEFAULT 1 COMMENT '类型：1-个人，2-企业',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) COMMENT '创建人（登记人）',
    PRIMARY KEY (id),
    KEY idx_name (name),
    KEY idx_type (type),
    KEY idx_create_by (create_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='捐赠人表';

-- ========================================
-- 3. 捐赠记录表
-- ========================================
DROP TABLE IF EXISTS donation;
CREATE TABLE donation (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '捐赠记录ID',
    donor_id BIGINT NOT NULL COMMENT '捐赠人ID',
    donor_name VARCHAR(50) COMMENT '捐赠人姓名（冗余）',
    project_id BIGINT NOT NULL COMMENT '项目ID',
    project_name VARCHAR(100) COMMENT '项目名称（冗余）',
    amount DECIMAL(15, 2) NOT NULL COMMENT '捐赠金额',
    pay_type INT DEFAULT 1 COMMENT '支付方式：1-现金，2-银行转账，3-支付宝，4-微信',
    message VARCHAR(500) COMMENT '捐赠留言',
    donate_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '捐赠时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_donor_id (donor_id),
    KEY idx_project_id (project_id),
    KEY idx_donate_time (donate_time),
    CONSTRAINT fk_donation_donor FOREIGN KEY (donor_id) REFERENCES donor(id),
    CONSTRAINT fk_donation_project FOREIGN KEY (project_id) REFERENCES project(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='捐赠记录表';

-- ========================================
-- 插入测试数据
-- ========================================

-- 插入测试项目
INSERT INTO project (name, description, target_amount, current_amount, beneficiary_count, status, start_time, end_time) VALUES
('山区儿童助学计划', '为偏远山区儿童提供学习用品和教育资助', 50000.00, 32500.00, 120, 1, '2026-01-01', '2026-12-31'),
('孤寡老人关爱行动', '为社区孤寡老人提供生活物资和陪伴服务', 30000.00, 15000.00, 45, 1, '2026-02-01', '2026-11-30'),
('流浪动物救助站', '为流浪动物提供食物、医疗和领养服务', 20000.00, 8000.00, 0, 1, '2026-03-01', '2026-10-31');

-- 插入测试捐赠人
INSERT INTO donor (name, phone, email, type, remark) VALUES
('张三', '13800138001', 'zhangsan@email.com', 1, '热心公益人士'),
('李四', '13900139002', 'lisi@email.com', 1, '长期支持者'),
('爱心企业有限公司', '010-12345678', 'charity@company.com', 2, '企业捐赠'),
('王五', '13700137003', 'wangwu@email.com', 1, '首次捐赠');

-- 插入测试捐赠记录
INSERT INTO donation (donor_id, donor_name, project_id, project_name, amount, pay_type, message, donate_time) VALUES
(1, '张三', 1, '山区儿童助学计划', 5000.00, 3, '希望孩子们好好学习', '2026-03-10 10:30:00'),
(2, '李四', 1, '山区儿童助学计划', 3000.00, 4, '支持教育事业', '2026-03-12 14:20:00'),
(3, '爱心企业有限公司', 2, '孤寡老人关爱行动', 10000.00, 2, '企业社会责任', '2026-03-15 09:00:00'),
(1, '张三', 2, '孤寡老人关爱行动', 2000.00, 3, '关爱老人', '2026-03-18 16:45:00'),
(4, '王五', 3, '流浪动物救助站', 500.00, 4, '小动物加油', '2026-03-20 11:10:00');

-- 验证数据
SELECT '项目数量' as info, COUNT(*) as count FROM project
UNION ALL
SELECT '捐赠人数量', COUNT(*) FROM donor
UNION ALL
SELECT '捐赠记录数量', COUNT(*) FROM donation;
