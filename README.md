## 社区
## 资料
[Spring Boot 文档](https://spring.io/projects/spring-boot)
[Spring 文档](https://spring.io/guides)
[Github Oauth](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)
## 工具
[Git](https://git-scm.com/)
##脚本
```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `account_id` varchar(100) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8

```