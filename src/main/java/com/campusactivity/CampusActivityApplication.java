package com.campusactivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园活动平台主应用类
 *
 * 这是Spring Boot应用的入口点，包含main方法用于启动整个应用
 *
 * @SpringBootApplication 注解包含了以下三个注解的功能：
 * - @Configuration: 标识这是一个配置类
 * - @EnableAutoConfiguration: 启用Spring Boot的自动配置
 * - @ComponentScan: 自动扫描当前包及子包下的组件
 *
 * 启动后可通过以下方式访问：
 * - 主页: http://localhost:8080
 * - H2控制台: http://localhost:8080/h2-console
 * - API文档: http://localhost:8080/swagger-ui.html
 *
 * 默认测试账号：
 * - 管理员: admin / admin123
 * - 普通用户: user / user123
 */
@SpringBootApplication
public class CampusActivityApplication {

    /**
     * 应用程序入口点
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(CampusActivityApplication.class, args);
    }
}