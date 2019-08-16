## 社区
## 资料
[Spring Boot 文档](https://spring.io/projects/spring-boot)
[Spring 文档](https://spring.io/guides)
[Github Oauth](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)
## 工具
[Git](https://git-scm.com/)
[lombook](https://projectlombok.org/)
[thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

## 脚本
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

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '问题标题',
  `description` text COMMENT '描述\n',
  `gmt_create` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `creator` int(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `view_count` int(11) DEFAULT '0' COMMENT '浏览数',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

