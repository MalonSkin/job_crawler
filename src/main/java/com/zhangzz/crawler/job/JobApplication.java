package com.zhangzz.crawler.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author zhangzz
 * @Create 2019/5/12 16:29
 */
@SpringBootApplication
@EnableScheduling
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
