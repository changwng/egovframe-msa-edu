package org.egovframe.cloud.attachservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableDiscoveryClient
@Profile({"default", "local", "dev", "prod"})
public class DiscoveryConfig {
}
