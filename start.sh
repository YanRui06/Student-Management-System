#!/bin/bash

# 学生管理系统启动脚本

echo "========================================="
echo "    学生管理系统启动脚本"
echo "========================================="

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "错误: Java 未安装或未在PATH中"
    echo "请安装Java 21或更高版本"
    exit 1
fi

# 检查Maven环境
if ! command -v mvn &> /dev/null; then
    echo "错误: Maven 未安装或未在PATH中"
    echo "请安装Maven 3.6+版本"
    exit 1
fi

# 检查配置文件
if [ ! -f "src/main/resources/database.properties" ]; then
    echo "错误: 数据库配置文件不存在"
    echo "请运行以下命令创建配置文件:"
    echo "cp src/main/resources/database.properties.example src/main/resources/database.properties"
    echo "然后编辑该文件填入你的数据库配置"
    exit 1
fi

echo "✓ 环境检查通过"

# 编译项目
echo "正在编译项目..."
mvn compile

if [ $? -ne 0 ]; then
    echo "✗ 编译失败"
    exit 1
fi

echo "✓ 编译成功"

# 运行项目
echo "正在启动学生管理系统..."
mvn exec:java

echo "程序已退出"
