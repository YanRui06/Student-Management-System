#!/bin/bash

# 项目部署前安全检查脚本

echo "========================================="
echo "    项目部署前安全检查"
echo "========================================="

# 检查是否存在敏感文件
SENSITIVE_FILES=(
    "src/main/resources/database.properties"
    ".env"
    "config.properties"
)

echo "检查敏感文件..."
for file in "${SENSITIVE_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "⚠️  发现敏感文件: $file"
        echo "   请确保此文件在 .gitignore 中"
    fi
done

# 检查.gitignore文件
if [ -f ".gitignore" ]; then
    echo "✓ .gitignore 文件存在"
    
    # 检查是否包含敏感文件规则
    if grep -q "database.properties" .gitignore; then
        echo "✓ database.properties 已在 .gitignore 中"
    else
        echo "⚠️  database.properties 未在 .gitignore 中"
    fi
    
    if grep -q "*.env" .gitignore; then
        echo "✓ .env 文件已在 .gitignore 中"
    else
        echo "⚠️  .env 文件未在 .gitignore 中"
    fi
else
    echo "❌ .gitignore 文件不存在"
fi

# 检查是否存在示例配置文件
if [ -f "src/main/resources/database.properties.example" ]; then
    echo "✓ 配置文件示例存在"
else
    echo "⚠️  配置文件示例不存在"
fi

# 检查README文件
if [ -f "README.md" ]; then
    echo "✓ README.md 文件存在"
    if grep -q "配置" README.md; then
        echo "✓ README.md 包含配置说明"
    else
        echo "⚠️  README.md 缺少配置说明"
    fi
else
    echo "⚠️  README.md 文件不存在"
fi

# 检查源代码中是否有硬编码的敏感信息
echo "检查源代码中的敏感信息..."
if grep -r "password.*=" src/main/java/ 2>/dev/null | grep -v "ConfigManager" | grep -v "getProperty"; then
    echo "⚠️  源代码中可能包含硬编码密码"
else
    echo "✓ 源代码中未发现硬编码密码"
fi

# 检查是否有IP地址硬编码
if grep -r "jdbc:mysql://[0-9]" src/main/java/ 2>/dev/null; then
    echo "⚠️  源代码中可能包含硬编码IP地址"
else
    echo "✓ 源代码中未发现硬编码IP地址"
fi

echo ""
echo "========================================="
echo "    检查完成"
echo "========================================="

echo ""
echo "部署前清单："
echo "□ 确保所有敏感文件都在 .gitignore 中"
echo "□ 确保配置文件示例已创建"
echo "□ 确保README文件包含完整的配置说明"
echo "□ 确保源代码中没有硬编码的敏感信息"
echo "□ 测试应用程序能否正常运行"
echo ""
echo "如果以上检查都通过，你的项目现在可以安全地上传到GitHub了！"
