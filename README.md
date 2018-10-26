# Manage

#### 项目介绍
管理系统快速开发框架，集成工作流Activiti、单点登录CAS、权限框架shiro、缓存Redis等常用组件

#### 项目功能
1，采用主流的Activiti流程引擎，实现审批业务的自由流转以及自定义表单等功能

2，基于Apache CXF实现的WebService对外接口，提供soap和restful两种访问方式

3，整合CAS + Shiro + Redis单点登陆,实现多个系统统一登陆登出

4，使用Redis + Shiro自定义SessionDAO实现Session共享

5，使用Solr全文搜索引擎，实现基本的增、删、改、查、关键字分页查询、带高亮的关键字查询等功能

6，APP基于JSON Web Token (JWT)认证，使用Swagger2生成一个具有互动性的API文档控制台

7，采用Shiro实现功能权限

#### 项目架构
项目采用模型-视图-控制器模式 (MVC)

#### 项目结构
##### Project Modules
#manage-web
![manage-web](https://images.gitee.com/uploads/images/2018/1026/152450_460b3e41_1486552.png "Module 'manage-web'.png")

#manage-app
![manage-app](https://images.gitee.com/uploads/images/2018/1026/153210_e0eb38c8_1486552.png "Module 'manage-app'.png")

#manage-openapi
![manage-openapi](https://images.gitee.com/uploads/images/2018/1026/153110_db36104a_1486552.png "Module 'manage-openapi'.png")

##### Spring Web Flow
#manage-cas-server
![manage-cas-server](https://images.gitee.com/uploads/images/2018/1026/152521_8730dc4e_1486552.png "Module manage-cas-server.png")

#### 项目环境
Intelli JIDEA

Maven

Jdk1.7

Tomcat7

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

测试框架：JUnit4

#### 安装教程
1. 项目涉及的软件可参照官方提供的方式或者参照本人记录的方式进行安装

    Mysql/MariaDB：https://gitee.com/laiyw/codes/4aob15k3xz28g0hpfcelt25
    
    Solr：https://gitee.com/laiyw/codes/6nt7expfcgdhqu0m98ow179
    
    RabbitMQ：https://gitee.com/laiyw/codes/swj8qkvmadenr1gc3ul0i41
    
    Redis：https://gitee.com/laiyw/codes/286cnvbrwtqop49su7kg598
    
2. 创建数据库manage，数据库编码为UTF-8，导入manage.sql脚本

3. 修改manage-common/src/main/resources/origin.properties文件，更改相应的数据源、Redis、RabbitMQ、Solr配置信息；如果使用SSO，则需要修改manage-cas-server/web/WEB-INF/cas.properties配置文件中的数据源

4. 项目访问路径

    后台管理：[http://localhost:port/login.html](http://)
    
    单点登陆：[http://localhost:port/login](http://)

#### 效果图
#单点登录
![单点登录](https://images.gitee.com/uploads/images/2018/1026/152229_4f1e723b_1486552.png "单点登录.png")

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request