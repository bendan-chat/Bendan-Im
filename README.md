# Bendan-Im

<p align="center">
 <img src="https://img.shields.io/badge/Spring%20Cloud-2021-blue.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-2.7-blue.svg" alt="Downloads">
</p>



## 系统说明

- 基于 Spring Cloud 2021 、Spring Boot 2.7、 OAuth2 的 RBAC **基于AI的Web聊天系统**
- 提供对常见容器化支持 Docker 支持
- 提供 lambda 、stream api 、webflux 的生产实践


```
Bendan-Im
├── bendan-common -- 系统公共模块
├    ├── bendan-common-core -- 公共工具类核心包
├    ├── bendan-common-business-- 系统业务实体相关
├    ├── bendan-common-mail -- 邮箱相关相关
├    ├── bendan-common-mybatis-- mybatis相关
├    ├── bendan-common-oss -- 通用文件系统和第三方服务
├    ├── bendan-common-security -- 安全工具类
├    └── bendan-common-swagger -- Swagger Api文档生成
├── bendan-dependencies -- 公共依赖版本
├── bendan-admin-- 系统基本API模块[9000]
├── bendan-gateway -- Spring Cloud Gateway网关[9999]
├── bendan-chat-- 聊天服务器[9004、9991]
├── bendan-auth-- 授权服务提供[9001]
```

### 核心依赖

| 依赖                        | 版本       |
| --------------------------- | ---------- |
| Spring Boot                 | 2.7.5      |
| Spring Cloud                | 2021.0.5   |
| Spring Cloud Alibaba        | 2021.0.4.0 |
| Spring Authorization Server | 0.4.0      |
| Mybatis Plus                | 3.5.3.1    |
| hutool                      | 5.8.12     |

### 模块说明

```lua
Bendan-Im
├── bendan-common -- 系统公共模块
├    ├── bendan-common-core -- 公共工具类核心包
├    ├── bendan-common-business-- 系统业务实体相关
├    ├── bendan-common-mail -- 邮箱相关相关
├    ├── bendan-common-mybatis-- mybatis相关
├    ├── bendan-common-oss -- 通用文件系统和第三方服务
├    ├── bendan-common-security -- 安全工具类
├    └── bendan-common-swagger -- Swagger Api文档生成
├── bendan-dependencies -- 公共依赖版本
├── bendan-admin-- 系统基本API模块[9000]
├── bendan-gateway -- Spring Cloud Gateway网关[9999]
├── bendan-chat-- 聊天服务器[9004、9991]
├── bendan-auth-- 授权服务提供[9001]
```

## 系统技术栈

![image-20230227105752454](https://cdn.jsdelivr.net/gh/obeast-dragon/cloud-bed/pictures/image-20230227105752454.png)

## 系统架构图

### 系统架构总图

![image-20230227105512927](https://cdn.jsdelivr.net/gh/obeast-dragon/cloud-bed/pictures/image-20230227105512927.png)



### 聊天架构图

![image-20230227105626212](https://cdn.jsdelivr.net/gh/obeast-dragon/cloud-bed/pictures/image-20230227105626212.png)

