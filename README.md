# BootBlog

[![Build Status](https://travis-ci.org/xiahongjian/boot-blog.svg?branch=master)](https://travis-ci.org/xiahongjian/boot-blog) 
![GitHub](https://img.shields.io/github/license/xiahongjian/boot-blog.svg)


一个使用Spring Boot + MyBatis + FreeMarker实现的博客系统。  
演示网址: [https://blog.hongjian.tech](https://blog.hongjian.tech/)


## 概述 Overview

首先此博客系统是根据[Tale](https://github.com/otale/tale)修改而来，主要是将后台实现替换成了Spring Boot+MyBatis+FreeMarker，同时根据自己的需求做了一些功能上的增删，
在此也要感谢[Tale](https://github.com/otale/tale)的作者[@biezhi](https://github.com/biezhi)写出了这么棒的项目。

项目主要使用到的框架和技术有：

* Spring Boot
* MyBatis
* MyBatis通用mapper
* FreeMarker
* Mybatis-PageHelper
* Druid


## 部署 Deploy

1. clone此项目

```
$ git clone https://github.com/xiahongjian/boot-blog.git
```

2. 创建数据库及表

执行`boot-blog/src/resources/schema-mysql.sql`中的SQL创建数据库及表。
执行`boot-blog/src/resources/data.sql`中的SQL插入基本数据。

3. 编译打包项目

```
$ cd /your/path/boot-blog
$ mvn package
```
执行完后，在`target`目录下会有`boot-blog.jar`，此jar包为打包好的项目。

4. 编辑配置文件

在`boot-blog.jar`的同级目录下创建`application-prod.yml`

```yaml
server:
    port: 8080
spring:
    datasource:
        driver-class-name: com.mysql.jdbc.Driver
        # Your database link
        url: jdbc:mysql://127.0.0.1:3306/Blog?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false
        username: yourname
        password: yourpassword
    druid:
        stat-view-servlet:
        login-username: yourname
        login-password: yourpassword
logging:
    level:
        hongjian.tech.blog: info
# mapper
mapper:
    indentity: MYSQL

# pagehelper
pagehelper:
    helperDailect: mysql
```

5. 启动

```
$ java -jar boot-blog.jar --spring.profiles.active=prod
```
访问[http://localhost:8080](http://localhost:8080)完成博客安装相关配置。

## 感谢 Thanks

* [otale/tale](https://github.com/otale/tale)
* [abel533/Mapper](https://github.com/abel533/Mapper)
* [vdurmont/emoji-java](https://github.com/vdurmont/emoji-java)
* [alibaba/druid](https://github.com/alibaba/druid)
* [pagehelper/Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)