#!/bin/bash

# 检查项目Fork信息脚本
# 用于查看谁复刻了本项目

echo "============================================"
echo "      学生管理系统 - Fork 信息查询"
echo "    Student Management System - Fork Info"
echo "============================================"
echo ""

# 设置仓库信息
REPO_OWNER="YanRui06"
REPO_NAME="Student-Management-System"

echo "正在获取 $REPO_OWNER/$REPO_NAME 的 Fork 信息..."
echo "Fetching fork information for $REPO_OWNER/$REPO_NAME..."
echo ""

# 使用GitHub API获取Fork信息
# 不需要认证token即可获取公开仓库的fork信息
FORKS_URL="https://api.github.com/repos/$REPO_OWNER/$REPO_NAME/forks?per_page=100&sort=newest"

# 检查是否安装了curl或wget
if command -v curl &> /dev/null; then
    RESPONSE=$(curl -s -H "Accept: application/vnd.github.v3+json" "$FORKS_URL" 2>&1)
elif command -v wget &> /dev/null; then
    RESPONSE=$(wget -q -O - --header="Accept: application/vnd.github.v3+json" "$FORKS_URL" 2>&1)
else
    echo "错误: 未找到 curl 或 wget 命令"
    echo "Error: Neither curl nor wget is installed"
    echo "请安装 curl 或 wget 后重试"
    exit 1
fi

# 检查API响应是否有效
if [ -z "$RESPONSE" ] || [[ "$RESPONSE" == *"Blocked"* ]] || [[ "$RESPONSE" == *"error"* ]]; then
    echo "⚠ 无法访问 GitHub API（可能被网络限制）"
    echo "⚠ Cannot access GitHub API (may be blocked by network)"
    echo ""
    echo "请使用以下方式查看Fork信息:"
    echo "Please use the following methods to view fork information:"
    echo ""
    echo "1. 访问GitHub网页 (Visit GitHub web page):"
    echo "   https://github.com/$REPO_OWNER/$REPO_NAME/network/members"
    echo ""
    echo "2. 在项目主页查看Fork数量 (Check fork count on project page):"
    echo "   https://github.com/$REPO_OWNER/$REPO_NAME"
    echo ""
    exit 0
fi

# 检查是否安装了jq用于JSON解析
if command -v jq &> /dev/null; then
    # 使用jq解析JSON
    FORK_COUNT=$(echo "$RESPONSE" | jq '. | length')
    
    if [ "$FORK_COUNT" -eq 0 ]; then
        echo "暂无Fork记录"
        echo "No forks found yet"
    else
        echo "找到 $FORK_COUNT 个Fork"
        echo "Found $FORK_COUNT fork(s)"
        echo ""
        echo "Fork 列表 (Fork List):"
        echo "--------------------------------------------"
        
        # 显示每个fork的详细信息
        echo "$RESPONSE" | jq -r '.[] | 
            "--------------------------------------------\n" +
            "用户 (Owner): \(.owner.login)\n" +
            "仓库 (Repository): \(.full_name)\n" +
            "Fork时间 (Forked at): \(.created_at)\n" +
            "仓库地址 (URL): \(.html_url)\n" +
            "描述 (Description): \(.description // "无描述")\n" +
            "Stars: \(.stargazers_count) | Forks: \(.forks_count)"'
        
        echo "--------------------------------------------"
    fi
else
    # 如果没有jq，使用简单的grep解析
    echo "注意: 未安装jq，显示原始数据"
    echo "Note: jq not installed, showing raw data"
    echo ""
    
    # 提取基本信息
    FORK_COUNT=$(echo "$RESPONSE" | grep -o '"login"' | wc -l)
    
    if [ "$FORK_COUNT" -eq 0 ]; then
        echo "暂无Fork记录"
        echo "No forks found yet"
    else
        echo "找到约 $FORK_COUNT 个Fork"
        echo "Found approximately $FORK_COUNT fork(s)"
        echo ""
        echo "建议安装 jq 以获得更好的显示效果:"
        echo "  Ubuntu/Debian: sudo apt-get install jq"
        echo "  macOS: brew install jq"
        echo ""
        echo "原始数据 (Raw data):"
        echo "$RESPONSE" | grep -E '"login"|"full_name"|"html_url"|"created_at"' | head -20
    fi
fi

echo ""
echo "============================================"
echo "更多信息请访问 (For more information, visit):"
echo "https://github.com/$REPO_OWNER/$REPO_NAME/network/members"
echo "============================================"
