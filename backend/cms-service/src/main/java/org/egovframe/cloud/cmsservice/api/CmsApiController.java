package org.egovframe.cloud.cmsservice.api;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngResponseDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngSaveRequestDto;
import org.egovframe.cloud.cmsservice.service.bbs.BbsMngService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CmsApiController {

    private final Environment env;
    /**
     * 서비스 상태 확인
     *
     * @return
     */
    @GetMapping("/actuator/health-info")
    public String status() {
        return String.format("GET CMS Service on" +
                "\n local.server.port :" + env.getProperty("local.server.port")
                + "\n egov.message :" + env.getProperty("egov.message")
        );
    }
    /**
     * 서비스 상태 확인
     *
     * @return
     */
    @GetMapping("/actuator/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("CMS Service is UP");
    }


}
