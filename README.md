# agilebpm-base-spring-boot

## 项目介绍

本项目旨在让 spring boot 项目 以比较简单的形式整合 AgileBPM 的流程服务

AgileBPM 主工程 ：https://gitee.com/agile-bpm/agile-bpm-basic

该项目含一下 starter
-  agilebpm-base-starter 基础模块
-  agilebpm-sys-starter 系统模块
-  agilebpm-security-starter 鉴权模块
-  agilebpm-wf-starter 流程模块
-  agilebpm-ui 前端项目
-  agilebpm-spring-boot-samples  spring boot 案例项目

另外 bus，form, org 模块直接依赖 rest 模块即可引入服务，没有提供 starter 
服务整合,模块详细介绍请参考 https://agile-bpm.gitee.io/docs/bootstrap/integration.html#spring-boot-1-X-整合


#### 软件架构

目前基于spring boot 1.5.15 ,2x 的会稍后一些

#### 使用说明
- Clone Spring Boot 版本项目
```
https://gitee.com/agile-bpm/agilebpm-base-spring-boot.git
```
- 对根目录执行 maven 命令 `install` 安装相关依赖jar

- 引入 Maven Spring Boot 案例项目  `agilebpm-spring-boot-samples`
 
- 启动项目, 在 SamplesApplication.java 中执行 main 方法

- 访问 http://localhost:8080 即可体验 AgileBPM ！


#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
