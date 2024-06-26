# Notification Filter

# Notification Filter
# 2024春季学期Android应用开发技术课程项目

# 🚀 通知过滤器App项目介绍
## 应用概览
* 应用名称：通知过滤器
* TargetSDK：Android 14 (API 级别 34)
* 版本号：1.0.0
* 应用大小：9.74MB

##  小组成员：
- **课程名称**：Android 应用开发技术
- **开发团队**：
    - 黎江楠 (`2022013519`)
    - 王佳琦 (`2022013524`)
    - 彭惊茗 (`2022013523`)
    - 张文欣 (`2022013526`)
- **专业**：计算机科学与技术
- **年级**：2022级
- **学院**：信息科学与技术学院

## 开发技术 🛠️
- **编程语言**：Java
- **开发平台**：IntelliJ IDEA，Android Studio


## 项目目的 🎯
通知过滤器App致力于帮助用户👥管理和过滤手机通知。用户可以设置关键词过滤和自定义规则，让通知更加个性化，避免接收不相关或不重要的通知，从而提升专注度和效率。

## 项目优点 🌈
1. **个性化过滤**：用户自定义关键词，打造专属通知体验。
2. **效率提升**：减少干扰，快速聚焦重要信息。
3. **专注度增强**：过滤无关通知，保持专注，提高生产力。

## 功能介绍 🛠️
### 关键词过滤 🔍
- 用户设置关键词，应用自动过滤含关键词的通知。

### 通知历史记录 📝
- 记录过滤历史，用户可回顾和分析通知过滤情况。

### 配置页面 📱
- 用户设置过滤规则，包括关键词和自定义规则。

### 架构图展示 🏗️
- UML图和项目导图清晰展示应用架构和组件关系。

### 核心代码讲解 💻
- 详细讲解了MainActivity初始化、权限请求、通知监听器状态检查等。

### 单例模式 🔐
- 类如`LogWriter`、`GlobalProfileReader`采用单例模式，提高资源效率。

### 过滤服务 🛡️
- `NotificationListener`服务监听通知并根据规则拦截。

### 日志管理 📖
- `LogWriter`类负责将过滤日志写入文件系统。

### 配置读取与写入 📑
- `GlobalProfileReader`和`RuleProfileReader`类管理过滤规则的读取与保存。

## 开发技术 🛠️
- **编程语言**：Java
- **开发平台**：Android Studio
- **设计模式**：单例模式、观察者模式等

## 用户界面 🎨
- 提供多样化用户界面，包括开屏页、首页、通知历史页面等，通过Fragment进行页面管理。

## 项目结构 🏰
- 模块化设计，包括前台界面、后台服务、数据管理、日志管理等。

## 软件架构图 🏗️
![Architecture_Diagram_of_NotificationFilter](\assets_Markdown\Architecture_Diagram_of_NotificationFilter.jpg)

