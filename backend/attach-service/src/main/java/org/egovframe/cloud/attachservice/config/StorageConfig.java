package org.egovframe.cloud.attachservice.config;

import org.egovframe.cloud.attachservice.utils.FileStorageUtils;
import org.egovframe.cloud.attachservice.utils.FtpStorageUtils;
import org.egovframe.cloud.attachservice.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class StorageConfig {

    @Value("${ftp.enabled:false}")
    private boolean ftpEnabled;

    @Bean
    @Primary
    public StorageUtils storageUtils(FileStorageUtils fileStorageUtils, FtpStorageUtils ftpStorageUtils) {
        return ftpEnabled ? ftpStorageUtils : fileStorageUtils;
    }
}
