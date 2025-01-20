package org.egovframe.cloud.cmsservice.config;

import java.util.function.Consumer;

import org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngEntityMessage;
import org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngTempSaveRequestDto;
import org.egovframe.cloud.cmsservice.service.attachment.AtflMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * org.egovframe.cloud.cmsservice.config.EventStreamConfig
 * <p>
 * Event Stream 설정 클래스
 *
 * @author 표준프레임워크센터 changwng
 * @version 1.0
 * @since 2024/01/20
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2024/01/20    changwng     최초 생성
 * </pre>
 */
@Slf4j
@Configuration
public class EventStreamConfig {

    @Autowired
    private AtflMngService atflMngService;

    /**
     * 첨부 파일 정보 업데이트 : rabbitmq event stream
     * @return Consumer<AtflMngEntityMessage>
     */
    @Bean
    public Consumer<AtflMngEntityMessage> atflMngEntity() {
        return atflMngEntityMessage -> {
            log.debug("Received AtflMngEntityMessage: {}", atflMngEntityMessage);
            AtflMngTempSaveRequestDto requestDto = AtflMngTempSaveRequestDto.builder()
                    .lnkgDmnId(atflMngEntityMessage.getEntityId())
                    .lnkgDmnNm(atflMngEntityMessage.getEntityName())
                    .build();
            atflMngService.updateEntity(atflMngEntityMessage.getAtflId(), requestDto);
        };
    }
}
