# bubbling-frame2.0-workflow

##  介绍  ##

bubbling@frame2.0,开箱即用的后台管理系统。

##  软件架构  ##

语言：java

前端：Antdvue+Bpmn.js

后端：SpringBoot+MybatiesPlus+Activiti7

##  演示地址

开放时间9:00-23:00

http://yangwen.i234.me:8084

账号admin

密码111111

##  效果图

1. 流程定义

   

2. 登录

![](https://gitee.com/yanwen1020053105/md-image/raw/master/bubbling-frame2.0/%E7%99%BB%E5%BD%95.png)

2. 用户管理

![](https://gitee.com/yanwen1020053105/md-image/raw/master/bubbling-frame2.0/%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%86.png)

3. 部门管理

![](https://gitee.com/yanwen1020053105/md-image/raw/master/bubbling-frame2.0/%E9%83%A8%E9%97%A8%E7%AE%A1%E7%90%86.png)

4. 角色管理

![](https://gitee.com/yanwen1020053105/md-image/raw/master/bubbling-frame2.0/%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86.png)

5. 菜单管理

![](https://gitee.com/yanwen1020053105/md-image/raw/master/bubbling-frame2.0/%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%86.png)

##  安装教程

###  后端运行（bubbling-service）

1.  使用git将项目克隆到本地
2.  创建数据库并执行数据库脚本bubbling-frame2.0\db\bubbling_frame2.0.sql
3.  修改bubbling-frame2.0\bubbling-service\src\main\resources\application.properties为spring.profiles.active=prod
4.  修改bubbling-frame2.0\bubbling-service\src\main\resources\application-prod.properties的数据库连接及用户名密码
5.  运行bubbling-frame2.0\bubbling-service\src\main\java\com\bubbling\BubblingMain.java

###  前端运行（bubbling-front）

1. 安装webstorm或HBuilder
2. 安装node.js、cnpm、yarn
3. 打开cmd命令窗口，进入bubbling-front目录
4. 执行yarn install安装依赖
5. 导入项目到ide
6. 在ide中运行或者执行yarn run serve命令

##  使用说明

1.  访问http://127.0.0.1:8083/login.html 用户名admin密码111111
2.  使用了antdvueAdmin登录界面的矢量背景图，https://gitee.com/iczer/vue-antd-admin#https://iczer.gitee.io/vue-antd-admin
