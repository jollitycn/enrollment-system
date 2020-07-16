package com.educiot.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@MapperScan({"com.educiot.server.data.mapper"})
@ServletComponentScan(basePackages = {"com.educiot.server"})
@SpringBootApplication(scanBasePackages = { "com.educiot.server"})
public class EduciotServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduciotServerApplication.class, args);
    }
}
