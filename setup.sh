#!/bin/bash

# 学生管理系统配置脚本

echo "========================================="
echo "    学生管理系统配置向导"
echo "========================================="

CONFIG_FILE="src/main/resources/database.properties"
EXAMPLE_FILE="src/main/resources/database.properties.example"

# 检查示例文件是否存在
if [ ! -f "$EXAMPLE_FILE" ]; then
    echo "错误: 找不到配置文件模板 $EXAMPLE_FILE"
    exit 1
fi

# 如果配置文件已存在，询问是否覆盖
if [ -f "$CONFIG_FILE" ]; then
    echo "配置文件已存在: $CONFIG_FILE"
    read -p "是否要重新配置? (y/n): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "配置取消"
        exit 0
    fi
fi

echo "开始配置数据库连接..."

# 获取数据库配置信息
read -p "请输入数据库主机地址 (默认: localhost): " DB_HOST
DB_HOST=${DB_HOST:-localhost}

read -p "请输入数据库端口 (默认: 3306): " DB_PORT
DB_PORT=${DB_PORT:-3306}

read -p "请输入数据库名称: " DB_NAME
while [ -z "$DB_NAME" ]; do
    echo "数据库名称不能为空"
    read -p "请输入数据库名称: " DB_NAME
done

read -p "请输入数据库用户名: " DB_USER
while [ -z "$DB_USER" ]; do
    echo "数据库用户名不能为空"
    read -p "请输入数据库用户名: " DB_USER
done

read -s -p "请输入数据库密码: " DB_PASSWORD
echo
while [ -z "$DB_PASSWORD" ]; do
    echo "数据库密码不能为空"
    read -s -p "请输入数据库密码: " DB_PASSWORD
    echo
done

# 创建配置文件
cat > "$CONFIG_FILE" << EOF
# 数据库配置文件
# 自动生成于 $(date)
database.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8&useUnicode=true
database.username=${DB_USER}
database.password=${DB_PASSWORD}
database.driver=com.mysql.cj.jdbc.Driver
EOF

echo "✓ 配置文件创建成功: $CONFIG_FILE"

# 测试数据库连接
echo "正在测试数据库连接..."
mvn compile exec:java -Dexec.mainClass="DatabaseUtil" -Dexec.args="test" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✓ 数据库连接测试成功!"
    echo "你现在可以运行 ./start.sh 来启动应用程序"
else
    echo "✗ 数据库连接测试失败"
    echo "请检查你的数据库配置信息"
fi

echo "配置完成!"
