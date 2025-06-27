# Student-Management-System

学生管理系统 随着学习进度更新

## 更新日志

**第一次更新-2025.05.09**

将静态数组管理学生改为动态(ArrayList)

**第二次修改-2025.06.25**

将整体逻辑改为mysql管理学生信息

**第三次修改-2025.06.27**

优化数据库连接安全性，使用配置文件管理数据库连接信息

## 环境要求

- Java 21
- Maven 3.6+
- MySQL 8.0+

## 数据库初始化

在配置应用程序之前，你需要初始化数据库：

```bash
# 登录MySQL
mysql -u root -p

# 执行初始化脚本
source database/init.sql

# 或者直接执行
mysql -u root -p < database/init.sql
```

初始化脚本将会：

- 创建 `student_management` 数据库
- 创建 `students` 表及相关索引
- 插入一些示例数据

## 安装和配置

### 方法一：使用配置脚本（推荐）

```bash
# 克隆项目
git clone https://github.com/your-username/Student-Management-System.git
cd Student-Management-System

# 运行配置脚本
./setup.sh

# 启动应用
./start.sh
```

### 方法二：手动配置

1. 克隆项目：

   ```bash
   git clone https://github.com/your-username/Student-Management-System.git
   cd Student-Management-System
   ```
2. 配置数据库：

   ```bash
   cp src/main/resources/database.properties.example src/main/resources/database.properties
   ```
3. 编辑 `src/main/resources/database.properties` 文件：

   ```properties
   database.url=jdbc:mysql://your-host:3306/your-database-name?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=utf8&useUnicode=true
   database.username=your-username
   database.password=your-password
   database.driver=com.mysql.cj.jdbc.Driver
   ```
4. 编译和运行：

   ```bash
   mvn clean compile
   mvn exec:java
   ```

## 安全说明

### 配置文件安全

- `database.properties` 文件包含敏感信息，已被添加到 `.gitignore` 中，不会上传到版本控制
- 请使用 `database.properties.example` 作为配置模板
- 在生产环境中，请确保数据库密码足够复杂且定期更换

### 最佳实践

- **永远不要**将包含真实密码的配置文件提交到版本控制
- 在服务器部署时，使用环境变量或安全的密钥管理服务
- 定期轮换数据库密码
- 使用最小权限原则，数据库用户只需要必要的权限

### 文件结构说明

```
Student-Management-System/
├── src/main/java/              # Java源代码目录
│   ├── Center.java             # 主程序入口和用户界面
│   ├── Student.java            # 学生实体类
│   ├── StudentDAO.java         # 数据访问对象，处理数据库操作
│   ├── DatabaseUtil.java      # 数据库连接工具类
│   ├── ConfigManager.java     # 配置管理工具类
│   └── ValidationUtil.java    # 数据验证工具类
├── src/main/resources/         # 资源文件目录
│   ├── database.properties     # 数据库配置文件（敏感信息，不上传）
│   └── database.properties.example # 配置文件模板
├── database/                   # 数据库相关文件
│   └── init.sql               # 数据库初始化脚本
├── setup.sh                   # 配置向导脚本
├── start.sh                   # 启动脚本
├── deploy-check.sh            # 部署前安全检查脚本
├── .gitignore                # Git忽略文件配置
├── pom.xml                   # Maven项目配置文件
├── README.md                 # 项目简介
└── LICENSE                   # 项目许可证
```

## 功能特性

- 学生信息管理（增删改查）
- 安全的数据库连接配置
- 输入数据验证

## 许可证

MIT License
