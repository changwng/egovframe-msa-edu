package org.egovframe.cloud.cmsservice.config;

import org.egovframe.cloud.cmsservice.utils.FileStorageUtils;
import org.egovframe.cloud.cmsservice.utils.FtpStorageUtils;
import org.egovframe.cloud.cmsservice.utils.StorageUtils;
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
