# Manage

#### 项目介绍
Manage管理系统，基于核心框架Spring 4.3.18.RELEASE，集成SpringMVC、Mybatis、Shiro、RabbitMQ、Solr、CAS、Activiti、Swagger2、CXF、Redis、Log4j、ServerMonitor、JWT、Freemarker、监控组件Monitor等常用框架及组件，实现了权限管理，Activiti工作流程引擎、全文检索、CAS单点登陆、消息推送以及提供了对外的WebService接口等功能

#### 项目功能
1，采用主流的Activiti流程引擎，实现审批业务的自由流转以及自定义表单等功能

2，基于Apache CXF实现的WebService对外接口，提供soap和restful两种访问方式

3，整合CAS + Shiro + Redis单点登录，实现多个系统统一登录登出

4，使用Redis + Shiro自定义SessionDAO实现分布式Session共享

5，使用Solr全文搜索引擎，实现基本的增、删、改、查、关键字分页查询、带高亮的关键字查询等功能

6，APP基于JSON Web Token (JWT)认证，使用Swagger2生成一个具有互动性的API文档平台

7，采用Shiro实现权限管理

8，集成Druid Monitor、Server Monitor实现对数据/服务器的监控

9，实现系统操作日志记录以及在线用户、新增用户、访客等统计功能

10，实现账号在线数控制以及登录错误次数控制

#### 项目架构
项目采用模型-视图-控制器模式 (MVC)

#### 项目结构
#### —manage
##### ——manage-activiti    工作流
##### ——manage-app    APP接口
##### ——manage-cas-server    单点登录服务
##### ——manage-common    公共模块
##### ——manage-dao    持久层
##### ——manage-model    业务实体
##### ——manage-openapi    对外接口
##### ——manage-rabbitmq    消息中间件
##### ——manage-redis    缓存
##### ——manage-service    业务逻辑层
##### ——manage-shiro    权限管理
##### ——manage-solr    搜索引擎
##### ——manage-web    后台管理

##### Project Modules
#manage-web
![manage-web](https://images.gitee.com/uploads/images/2018/1026/152450_460b3e41_1486552.png "Module 'manage-web'.png")

#manage-app
![manage-app](https://images.gitee.com/uploads/images/2018/1026/153210_e0eb38c8_1486552.png "Module 'manage-app'.png")

#manage-openapi
![manage-openapi](https://images.gitee.com/uploads/images/2018/1026/153946_d15e3b78_1486552.png "Module 'manage-openapi'.png")

##### Spring Web Flow
#manage-cas-server
![manage-cas-server](https://images.gitee.com/uploads/images/2018/1026/152521_8730dc4e_1486552.png "Module manage-cas-server.png")

#### 项目环境
Intelli JIDEA

Maven

Jdk1.8

Tomcat8

MariaDB

Redis

Solr

#### 技术选型

核心框架：Spring 4.3.18.RELEASE 

视图框架：Spring MVC

模板引擎：Freemarker

连接池：Druid

持久层框架：Mybatis

权限框架：Shiro

消息队列：RabbitMQ

搜索引擎：Solr 7.5.0

单点登陆:：CAS 4.2.7

工作流引擎：Activiti 5.22.0

Api文档框架：Swagger2

Web Service框架：CXF

数据库：MariaDB

缓存：Redis

认证机制：JSON Web Token (JWT)

日志组件：Log4j

监控组件：Druid Monitor、Server Monitor

测试框架：JUnit4

#### 在线演示
地址：http://119.29.190.136:8080/login.html

账号：admin

密码：123456

#### 本地安装
1. 项目涉及的软件可参照官方提供的方式或者以下链接进行安装

    Mysql/MariaDB：https://gitee.com/laiyw/codes/4aob15k3xz28g0hpfcelt25
    
    Solr：https://gitee.com/laiyw/codes/6nt7expfcgdhqu0m98ow179
    
    RabbitMQ：https://gitee.com/laiyw/codes/swj8qkvmadenr1gc3ul0i41
    
    Redis：https://gitee.com/laiyw/codes/286cnvbrwtqop49su7kg598
    
2. 创建数据库manage，数据库编码为UTF-8，导入manage.sql脚本

3. 修改manage-common/src/main/resources/origin.properties文件，更改相应的数据源、Redis、RabbitMQ、Solr配置信息；如果使用SSO，则需要修改manage-cas-server/web/WEB-INF/cas.properties配置文件中的数据源

4. 修改manage-dao/src/main/resources/spring-dataSource.xml中Properties文件载入地址

5. 项目访问路径

    后台管理：[http://localhost:port/login.html](http://)
    
    单点登陆：[http://localhost:port/login](http://)

#### 效果图
#管理后台登录
![管理后台登录](https://images.gitee.com/uploads/images/2018/1101/141049_60c7bc9b_1486552.png "管理后台登录.png")

#首页
![首页](https://images.gitee.com/uploads/images/2018/1105/123540_dd8f2beb_1486552.png "首页.png")

#个人中心
![个人中心](https://images.gitee.com/uploads/images/2018/1105/123653_1a1fd2ff_1486552.png "个人中心.png")

#系统基础设置
![系统基础设置](https://images.gitee.com/uploads/images/2018/1107/133749_c24d6144_1486552.png "系统基础设置.png")

#访问日志
![访问日志](https://images.gitee.com/uploads/images/2018/1101/144304_f9d2c81b_1486552.png "访问日志.png")

#访客统计
![访客统计](https://images.gitee.com/uploads/images/2018/1101/144037_43812a1b_1486552.png "访客统计.png")

#服务器监控
![服务器监控](https://images.gitee.com/uploads/images/2018/1101/144432_1406888d_1486552.png "服务器监控.png")

#### 技术交流
QQ：1319404727  **（添加时请备注，谢谢）** 



#### 项目功能还在开发与完善中，请各位大佬多多指教 :tw-1f37b:

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request