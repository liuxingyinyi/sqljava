package com.hccx.sqljava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SqlJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlJavaApplication.class, args);
        log.info("启动完成");
    }

}
