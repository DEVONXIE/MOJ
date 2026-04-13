package com.devon.moj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.devon.moj.mapper")
public class MojApplication {

    public static void main(String[] args) {
        SpringApplication.run(MojApplication.class, args);
    }

}
