# lc-co-admin-vue管理系统

## 系统简介

**admin是完全开源的通用的前后端分离的项目快速开发平台，前端基于若依框架进行二次开发，后端采用SpringBoot系列框架独立开发，支持高度自定义化满足绝大部分的功能，代码侵入较少，易扩展第三方组件库。**


- 前端技术栈

| Vue | 2.x |
| --- | --- |
| ElementUI | 2.x |
- 后端技术栈

| SpringBoot | 2.3.7.RELEASE |
| --- | --- |
| SpringSecurity | 5.3.6.RELEASE |
| Mybatis | 2.2.2 |
| Redis | 5.0.14.1 |
| Druid | 1.2.11 |
| log4j2 | 2.13.3 |
| JWT | ~ |
- 系统认证采用JWT形式，支持多端认证，简化认证解析过程，支持自定义化开发，JWT防止篡改，安全性高
- 采用RBAC（基于角色的访问控制）模型的权限控制，实现了动态路由菜单控制，轻松管控系统权限，简化了用户与权限的关系，易维护
- 整合Quartz定时任务框架，实现高性能的定时任务管理系统，任务控制性强
- 整合Velocity模板引擎，实现前后端代码生成器，一键式生成CRUD代码，解放双手，提高开发（摸鱼不是 👀）效率

## 内置功能

1. 用户管理：用户是系统功能操作人员，该功能主要完成系统用户配置。
2. 部门管理：配置系统组织机构（公司、部门、小组），树型数据结构呈现数据权限。
3. 岗位管理：配置系统用户所属担任职务。
4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5. 角色管理：角色菜单权限分配、设置角色按部门进行数据范围权限划分。
6. 字典管理：对系统中经常使用的一些较为固定的数据进行字典数据维护。
7. 参数管理：对系统动态设置常用参数。
8. 通知公告：对系统通知公告信息发布维护。
9. 操作日志：对系统操作日志进行记录和查询。
10. 访问日志：对系统访问日志进行记录与查询
11. 用户会话：对当前系统中活跃用户状态进行监控，支持强制下线用户。
12. 定时任务：在线可视化操作（添加、修改、删除)任务调度以及查询执行结果日志。
13. 代码生成：支持前后端代码的生成（java、html、xml、sql）CRUD生成以及Zip压缩下载 。
14. 系统接口：使用Knife4j + Swagger 根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 缓存监控：对系统的缓存信息查询，命令统计，以及缓存列表数据查询等。
17. 在线构建器：拖动表单元素生成相应的HTML代码。
18. 连接池监视：采用Druid数据源监控，监视当前系统数据库连接池状态，支持Sql监控，Spring监控，Session监控，以及Wall防火墙监控

## 在线体验

- 超级管理员    admin/123456
- test测试用户   test/123456

演示地址：http://175.178.219.58:1024

文档地址：

## 演示图

![login-demo](https://lc-co-bucket.oss-cn-shenzhen.aliyuncs.com/items-images/lc-co-admin-vue-images/readme-images/login-demo.png)

![system-demo](https://lc-co-bucket.oss-cn-shenzhen.aliyuncs.com/items-images/lc-co-admin-vue-images/readme-images/system-demo.png)

## 鸣谢
感谢若依框架[若依](http://120.79.202.7/)提供前端模板
