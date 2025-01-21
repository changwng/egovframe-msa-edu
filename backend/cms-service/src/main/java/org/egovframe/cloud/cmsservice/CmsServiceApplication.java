package org.egovframe.cloud.cmsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ComponentScan(basePackages={"org.egovframe.cloud.common", "org.egovframe.cloud.servlet", "org.egovframe.cloud.cmsservice"}) // org.egovframe.cloud.common package 포함하기 위해
@EntityScan({"org.egovframe.cloud.servlet.domain", "org.egovframe.cloud.cmsservice.domain"})
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
//@EnableJpaAuditing
public class CmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsServiceApplication.class, args);
    }
}
