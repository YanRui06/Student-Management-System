-- 学生管理系统数据库初始化脚本
-- 执行此脚本来创建必要的数据库表

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS student_management 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE student_management;

-- 创建学生表
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '学生ID',
    student_id VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    age INT NOT NULL COMMENT '年龄',
    gender ENUM('男', '女') NOT NULL COMMENT '性别',
    major VARCHAR(100) COMMENT '专业',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    address TEXT COMMENT '地址',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生信息表';

-- 创建索引
CREATE INDEX idx_student_id ON students(student_id);
CREATE INDEX idx_name ON students(name);
CREATE INDEX idx_major ON students(major);

-- 插入示例数据
INSERT INTO students (student_id, name, age, gender, major, email, phone, address) VALUES
('2024001', '张三', 20, '男', '计算机科学与技术', 'zhangsan@example.com', '13800138001', '北京市海淀区'),
('2024002', '李四', 19, '女', '软件工程', 'lisi@example.com', '13800138002', '上海市浦东新区'),
('2024003', '王五', 21, '男', '信息安全', 'wangwu@example.com', '13800138003', '广州市天河区'),
('2024004', '赵六', 20, '女', '数据科学与大数据技术', 'zhaoliu@example.com', '13800138004', '深圳市南山区');

-- 显示创建结果
SELECT '数据库初始化完成！' as result;
SELECT COUNT(*) as '学生总数' FROM students;
SELECT * FROM students ORDER BY id;
