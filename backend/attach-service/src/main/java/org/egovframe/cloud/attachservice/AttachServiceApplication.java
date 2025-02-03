package org.egovframe.cloud.attachservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"org.egovframe.cloud.common", "org.egovframe.cloud.servlet", "org.egovframe.cloud.attachservice"}) // org.egovframe.cloud.common package 포함하기 위해
@EntityScan({"org.egovframe.cloud.servlet.domain", "org.egovframe.cloud.attachservice.domain"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
//@EnableJpaAuditing
public class AttachServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachServiceApplication.class, args);
    }
}
