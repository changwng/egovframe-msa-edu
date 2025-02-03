package org.egovframe.cloud.attachservice.config;

import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

/**
 * 오픈소스 업로드 솔루션으로 대용량 파일 분할 업로드 방식으로 동작함
 * 201.CREATE, 204.PATCH, 업로드후에
 * https://tus.io/demo
 * https://github.com/tus
 */
@Configuration
public class TusConfig {

    //@Value("${tus.data.path}") // temp upload path
    @Value("${file.directory:${user.home}/egovframe-msa-edu}")
    private String rootPath;
    @Value("${tus.data.path:/files/tus/temp}")
    private String tusDataPath;

    // 업로드 만료 기한
    @Value("${tus.data.expiration:60000}")
    Long tusDataExpiration;

    @Bean
    public TusFileUploadService tus() {
        return new TusFileUploadService()
                .withStoragePath(tusDataPath)
                .withDownloadFeature()
                .withUploadExpirationPeriod(tusDataExpiration)
                .withThreadLocalCache(true)
                .withMaxUploadSize(1024L * 1024L * 1024L * 3)
                .withUploadURI("/tus/upload");
    }
}

