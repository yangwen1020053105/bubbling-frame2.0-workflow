package com.bubbling;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bubbling.**.mapper")
public class BubblingMain {
    public static void main(String[] args) {
        SpringApplication.run(BubblingMain.class,args);
    }
}
