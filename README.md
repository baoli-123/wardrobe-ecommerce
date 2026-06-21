# Wardrobe E-Commerce / 网上衣橱电商系统

> 一个基于 Vue 3 的前后端分离电商平台，覆盖商品展示、购物车、下单及管理端数据看板等电商核心流程。

→ [在线预览](https://wardrobe-ecommerce-rho.vercel.app/)

---

## 技术栈

| 分层 | 技术 |
|------|------|
| 前端（用户端） | Vue 3 + Vue Router + Vuex 4 + Element Plus + Axios + Less + Webpack |
| 前端（管理端） | Vue 3 + Vue Router + Vuex 4 + Element Plus + Axios + Less + Webpack |
| 后端 | Java Servlet + MySQL + Druid + Tomcat |
| 工具 | Git / ESLint / Maven |

## 项目结构

`
wardrobe-ecommerce/
├── frontend/
│   ├── user/          # 用户端前端（商品浏览、购物车、下单）
│   └── admin/         # 管理端前端（商品管理、订单管理、用户管理）
└── backend/           # Java Servlet 后端 API
    └── src/
        ├── main/java/com/itheima/
        │   ├── controller/   # Servlet 控制器
        │   ├── service/      # 业务逻辑层
        │   ├── dao/          # 数据访问层
        │   ├── model/        # 数据对象
        │   └── utils/        # 工具类
        └── resources/
            └── druid.properties  # 数据库连接配置
`

## 功能模块

### 用户端（frontend/user）
| 路由 | 功能 | 需要登录 |
|------|------|------|
| / | 商品首页 | ✖ |
| /clothDetails | 商品详情 | ✖ |
| /login | 登录 | ✖ |
| /register | 注册 | ✖ |
| /cart | 购物车 | ✔ |
| /order | 订单 | ✔ |
| /personal | 个人中心 | ✔ |

### 管理端（frontend/admin）
| 功能模块 | 说明 |
|------|------|
| 商品管理 | 添加/编辑/删除商品 |
| 订单管理 | 查看/处理订单 |
| 用户管理 | 管理用户账户 |

## 本地运行

### 前端

`ash
# 用户端
# 在项目根目录
npm install
npm run serve    # 访问 http://localhost:7070

# 管理端（另开一个终端）
cd admin
npm install
npm run serve    # 访问 http://localhost:7071
`

### 后端

1. 导入 wardrobe.sql 到 MySQL
2. 修改 ackend/src/main/resources/druid.properties 中的数据库连接信息
3. 使用 IDEA 打开 ackend/ 目录，配置 Tomcat 后启动（端口 8080）

> 后端启动后，前端通过 devServer proxy 自动转发 API 请求到 http://localhost:8080

## 项目亮点

- **组件化架构**：基于 Vue 3 封装 10+ 业务组件，形成内部组件库
- **性能优化**：路由懒加载 + computed 缓存，减少首屏体积
- **规范化开发**：Less 全局样式变量 + ESLint 统一代码风格
- **AI 工具辅助**：引入 Codex、Claude 等进行代码生成与调试

## 证书与荣誉

- 英语四级（CET-4）
- HarmonyOS 应用开发者高级证书
- 信息工程学院一等奖学金
- 校级优秀学生干部
