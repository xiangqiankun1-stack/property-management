# 智慧物业管理系统

> 基于 Spring Boot 3 + Vue 3 的前后端分离物业管理平台，实现物业管理业务数字化、流程化和智能化。

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](<https://img.shields.io/badge/Spring%20Boot-3.0.2-brightgreen.svg>)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.5.38-4FC08D.svg)](https://vuejs.org/)
[![Vite](https://img.shields.io/badge/Vite-8.0.16-646CFF.svg)](https://vitejs.dev/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![MyBatis Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.12-1E90FF.svg)](https://baomidou.com/)
[![Element Plus](<https://img.shields.io/badge/Element%20Plus-2.14.2-409EFF.svg>)](https://element-plus.org/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](./LICENSE)

---

## 📖 项目简介

随着城市住宅小区数量不断增加，传统物业管理方式逐渐暴露出信息分散、业务处理效率低、沟通成本高、数据统计困难等问题。智慧物业管理系统旨在为物业公司提供一站式数字化管理解决方案，整合用户权限管理、基础数据维护、物业服务办理、财务收费管理和数据统计分析等功能，提高物业工作人员办公效率，提升业主服务体验。

本项目为 **Java 实训课程团队项目**，采用前后端分离架构，涵盖从需求分析、数据库设计、接口开发到前端实现的完整软件开发生命周期。

---

## ✨ 功能模块

### 🔐 系统管理

- **用户管理** — 用户注册/登录（JWT Token 认证）、增删改查、分页搜索
- **角色管理** — 角色 CRUD、用户角色分配（多对多关联）
- **权限管理** — RBAC 权限模型，菜单/按钮级权限控制
- **登录拦截** — 全局拦截器，ThreadLocal 线程隔离用户上下文

### 🏘️ 基础数据管理

- **小区管理** — 小区信息维护（名称、地址、面积、开发商、物业公司等）
- **楼栋管理** — 关联小区，含总层数、每层户数
- **房屋管理** — 关联楼栋，含房号、楼层、面积、户型、状态
- **业主管理** — 房屋绑定业主，含姓名、电话、身份证号

### 🔧 物业服务

- **报修管理** — 报修申请 → 派单 → 完工确认 → 服务评价（完整流程闭环）
- **投诉管理** — 投诉提交 → 处理回复 → 状态跟踪
- **巡检管理** — 巡检任务创建与巡检记录整改跟踪

### 💰 财务管理

- **费用项目管理** — 物业费、水费、电费、停车费等收费项目维护
- **账单管理** — 自动生成账单编号，支持缴费状态筛选
- **缴费管理** — 缴费登记，自动更新账单状态，重复缴费校验

### 📊 数据统计

- **综合数据看板** — 报修/投诉/业主/缴费统计卡片
- **报修趋势图** — ECharts 折线图，按月展示报修趋势
- **投诉趋势图** — ECharts 柱状图，按月展示投诉趋势
- **缴费分布图** — ECharts 饼图，按费用类型展示缴费分布

---

## 🛠 技术栈

| 层级                  | 技术                                             |
| --------------------- | ------------------------------------------------ |
| **后端框架**    | Spring Boot 3.0.2                                |
| **ORM**         | MyBatis-Plus 3.5.12                              |
| **数据库**      | MySQL 8.0                                        |
| **认证**        | JWT (auth0 java-jwt 4.4.0)                       |
| **前端框架**    | Vue 3.5.38 (Composition API +`<script setup>`) |
| **构建工具**    | Vite 8.0.16（前端）/ Maven（后端）               |
| **UI 组件库**   | Element Plus 2.14.2（中文语言包）                |
| **状态管理**    | Pinia 3.0.4                                      |
| **路由**        | Vue Router 5.1.0                                 |
| **HTTP 客户端** | Axios 1.18.1                                     |
| **图表**        | ECharts 6.1.0                                    |
| **CSS 预处理**  | Sass 1.101.0                                     |
| **工具库**      | Lombok 1.18.38                                   |

---

## 🏗 项目结构

```
property-management/
├── backend/                          # 后端 Spring Boot 项目
│   ├── pom.xml                       # Maven 依赖配置
│   └── src/main/
│       ├── java/com/property_management/
│       │   ├── PropertyManagementApplication.java  # 启动类
│       │   ├── common/               # 统一响应 (R.java)、分页封装
│       │   ├── config/               # CORS、WebMVC、MyBatis-Plus 配置
│       │   ├── controller/           # 控制器层 (19个)
│       │   ├── service/              # 服务接口 + 实现 (18对)
│       │   ├── dao/                  # 实体类 + DTO (22个)
│       │   ├── mapper/               # MyBatis-Plus Mapper 接口 (18个)
│       │   ├── enums/                # 枚举类
│       │   ├── interceptors/         # JWT 登录拦截器
│       │   └── utils/                # JWT 工具、MD5 工具、ThreadLocal 工具
│       └── resources/
│           └── application.yaml      # 应用配置（端口 8080，MySQL 连接）
├── frontend/                         # 前端 Vue 3 项目
│   ├── vite.config.js                # Vite 构建配置
│   ├── index.html                    # 入口 HTML
│   └── src/
│       ├── main.js                   # 应用入口（Pinia + Router + Element Plus）
│       ├── App.vue                   # 根组件
│       ├── api/                      # API 请求模块 (14个)
│       ├── router/                   # 路由配置（含导航守卫）
│       ├── stores/                   # Pinia 状态管理 (user, menu)
│       ├── utils/                    # Axios 封装、认证工具
│       ├── layout/                   # 布局组件 (Layout, Aside, Header, Main)
│       ├── views/                    # 页面视图 (14个 SFC)
│       └── components/               # 公共组件 (CommonDialog, CommonTable, Pagination 等)
├── database/                         # 数据库脚本
│   ├── schema.sql                    # 18张表的 DDL（含索引、外键、逻辑删除）
│   └── init_data.sql                 # 种子数据
├── docs/                             # 项目文档
│   ├── 01_需求分析.md
│   ├── 02_数据库设计.md
│   ├── 03_API文档.md
│   ├── 04_测试报告.md
│   ├── 05_用户手册.md
│   ├── 06_项目总结.md
│   └── image/                        # 文档图片资源
└── README.md                         # 本文件
```

---

## 🚀 快速开始

### 环境要求

- **JDK** 17+
- **Node.js** 18+
- **MySQL** 8.0+
- **Maven** 3.8+

### 1. 克隆项目

```bash
git clone https://gitee.com/link50k/property-management.git
cd property-management
```

### 2. 初始化数据库

```bash
# 登录 MySQL 后执行
mysql -u root -p
```

```sql
CREATE DATABASE IF NOT EXISTS property_management DEFAULT CHARACTER SET utf8mb4;
USE property_management;
SOURCE database/schema.sql;
SOURCE database/init_data.sql;
```

### 3. 配置并启动后端

编辑 `backend/src/main/resources/application.yaml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/property_management?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
```

```bash
cd backend
mvn spring-boot:run
```

后端服务运行在 `http://localhost:8080`。

### 4. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端开发服务器运行在 `http://localhost:5173`。

### 5. 登录系统

| 角色     | 用户名        | 密码       |
| -------- | ------------- | ---------- |
| 管理员   | `admin`     | `123456` |
| 维修工   | `worker01`  | `123456` |
| 客服人员 | `service01` | `123456` |

---

## 📡 API 概览

- **基础路径**: `http://localhost:8080/api/v1`
- **认证方式**: JWT Bearer Token（登录获取，144 小时有效）
- **响应格式**: 统一的 `R<T>` 封装 `{ code, message, data }`

| 模块     | 接口数量 | 主要功能                           |
| -------- | -------- | ---------------------------------- |
| 登录认证 | 2        | 用户登录、用户注册                 |
| 用户管理 | 5        | 用户 CRUD、分页查询                |
| 角色管理 | 5        | 角色 CRUD、权限分配                |
| 小区管理 | 4        | 小区增删改查                       |
| 楼栋管理 | 4        | 楼栋增删改查（关联小区）           |
| 房屋管理 | 4        | 房屋增删改查（关联楼栋）           |
| 业主管理 | 5        | 业主增删改查（绑定房屋）           |
| 报修管理 | 5        | 报修申请、派单、完工、评价         |
| 投诉管理 | 3        | 投诉提交、处理、查询               |
| 费用项目 | 4        | 收费项目 CRUD                      |
| 账单管理 | 3        | 账单生成、查询、缴费               |
| 缴费管理 | 2        | 缴费登记、查询                     |
| 数据统计 | 3        | 统计卡片数据、趋势图数据、缴费分布 |

> 完整 API 文档见 [docs/03_API文档.md](./docs/03_API文档.md)

---

## 📊 数据库设计

数据库包含 **4 大模块、18 张表**：

| 模块               | 表名                    | 说明          |
| ------------------ | ----------------------- | ------------- |
| **系统权限** | `sys_user_info`       | 用户信息      |
|                    | `sys_role_info`       | 角色定义      |
|                    | `sys_permission_info` | 权限信息      |
|                    | `sys_user_role`       | 用户-角色关联 |
|                    | `sys_role_permission` | 角色-权限关联 |
| **基础数据** | `community`           | 小区信息      |
|                    | `building`            | 楼栋信息      |
|                    | `house`               | 房屋信息      |
|                    | `owner`               | 业主信息      |
| **物业服务** | `repair`              | 报修记录      |
|                    | `repair_order`        | 维修派单      |
|                    | `repair_evaluate`     | 服务评价      |
|                    | `complaint`           | 投诉建议      |
|                    | `inspection`          | 巡检任务      |
|                    | `inspection_record`   | 巡检记录      |
| **财务管理** | `fee_item`            | 费用项目      |
|                    | `bill`                | 账单          |
|                    | `payment_record`      | 缴费记录      |

> 完整数据库设计见 [docs/02_数据库设计.md](./docs/02_数据库设计.md)

---

## 📚 项目文档

| 文档                                       | 说明                           |
| ------------------------------------------ | ------------------------------ |
| [01_需求分析.md](./docs/01_需求分析.md)     | 项目背景、功能需求、业务流程图 |
| [02_数据库设计.md](./docs/02_数据库设计.md) | ER 图、表结构、索引设计        |
| [03_API文档.md](./docs/03_API文档.md)       | 全部 49 个 API 接口文档        |
| [04_测试报告.md](./docs/04_测试报告.md)     | 测试用例、结果与 Bug 列表      |
| [05_用户手册.md](./docs/05_用户手册.md)     | 安装部署说明、功能使用指南     |
| [06_项目总结.md](./docs/06_项目总结.md)     | 完成情况、技术难点、团队分工   |

---

## 👥 团队成员

| 成员   | 角色            | 职责                             |
| ------ | --------------- | -------------------------------- |
| 胡林康 | 组长 / 文档编写 | 项目架构设计、系统测试、文档编写 |
| 李军霖 | 后端开发 / 测试 | 后端业务开发、系统测试           |
| 高攀   | 后端开发 / 测试 | 后端业务开发、系统测试           |
| 向乾坤 | 前端开发        | 前端页面编写与设计               |
| 吴兴强 | 前端开发        | 前端页面编写与设计               |

---



## 📝 License

本项目仅用于学习与实训目的。

---

**项目周期**: 2026 年 7 月 1 日 — 2026 年 7 月 8 日（8 个半天实训课）
