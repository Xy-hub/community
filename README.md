## 社区
## 资料
[Spring Boot 文档](https://spring.io/projects/spring-boot)
[Spring 文档](https://spring.io/guides)
[Github Oauth](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)
## 工具
[Git](https://git-scm.com/)
[lombook](https://projectlombok.org/)
[thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
[markdown editor](http://editor.md.ipandao.com/)

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

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL COMMENT '父类id\n',
  `type` int(11) NOT NULL COMMENT '父类类型\n',
  `commentator` int(11) NOT NULL COMMENT '评论人',
  `gnt_create` int(11) NOT NULL COMMENT '创建时间',
  `gmt_modified` int(11) NOT NULL COMMENT '更新时间',
  `like_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

create table if not exists notification
(
	id int auto_increment
		primary key,
	notifier int not null comment '发出通知的人
',
	receiver int not null comment '收到通知的人',
	outer_id int not null comment '外键',
	gmt_create bigint not null comment '通知的时间
',
	status int default 0 not null comment '通知的状态，已读或未读',
	type int not null comment '通知的类型',
	notifier_name varchar(100) null,
	outer_title varchar(255) null
);


```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

