package org.egovframe.cloud.cmsservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngDownloadResponseDto
 * <p>
 * 첨부파일 다운로드 응답 DTO 클래스
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
 *  2024/01/20    changwng  최초 생성
 * </pre>
 */
@Getter
@NoArgsConstructor
@Schema(description = "첨부파일 다운로드 응답 DTO")
public class AtflMngDownloadResponseDto {

    @Schema(description = "원본 파일명")
    private String originalFileName;
    
    @Schema(description = "다운로드 파일 리소스")
    private Resource file;

    @Builder
    public AtflMngDownloadResponseDto(String originalFileName, Resource file) {
        this.originalFileName = originalFileName;
        this.file = file;
    }
}
