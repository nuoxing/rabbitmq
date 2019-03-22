package com.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 项目启动类
 * @author ljj
 * @SpringBootApplication springboot应用注解
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
    
    
  
}

