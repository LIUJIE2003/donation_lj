-- ========================================
-- 公益项目管理网站 - 数据库初始化脚本
-- 数据库：MySQL 8.0
-- 字符集：utf8mb4
-- ========================================

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS donation_db 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_general_ci;

-- 2. 使用数据库
USE donation_db;

-- 3. 创建系统用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键自增',
    username VARCHAR(50) NOT NULL COMMENT '用户名，登录账号',
    password VARCHAR(200) NOT NULL COMMENT '密码，BCrypt加密存储',
    role VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色：admin-管理员，user-普通用户',
    status INT DEFAULT 1 COMMENT '用户状态：0-禁用，1-正常',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 4. 插入默认管理员账户（密码：admin）
INSERT INTO sys_user (username, password, role, status)
VALUES ('admin', '$2a$10$cDydJePgGYw7tr/XsuNreOPHfaAMM/knaFIi8Xkp5bd2My/JmmmpG', 'admin', 1);

-- 5. 插入普通用户账户（密码：123456）
INSERT INTO sys_user (username, password, role, status)
VALUES ('user', '$2a$10$7/V4f7JCoUeeopx.BnBPBuWEgyEHTLrWJZ08FwgMjEdiACjlF756m', 'user', 1);

-- 5. 验证数据
SELECT * FROM sys_user;

-- ========================================
-- 说明：
-- 1. 此脚本会创建数据库donation_db
-- 2. 创建sys_user表，包含id、username、password、status等字段
-- 3. 插入默认管理员账户（admin/admin）
-- 4. 密码使用MD5加密存储，admin的MD5值为：21232f297a57a5a743894a0e4a801fc3
-- 
-- 使用方法：
-- 1. 在MySQL中执行此脚本
-- 2. 或使用Navicat等工具导入执行
-- ========================================
