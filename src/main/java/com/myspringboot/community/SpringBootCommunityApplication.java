package com.myspringboot.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.myspringboot.community.mapper")
public class SpringBootCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCommunityApplication.class, args);
    }

}
