package org.egovframe.cloud.attachservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * org.egovframe.cloud.attachservice.api.attachment.dto.AtflMngBase64RequestDto
 * <p>
 * 첨부파일 Base64 요청 DTO Class
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
@Schema(description = "첨부파일 Base64 요청 DTO")
public class AtflMngBase64RequestDto {
    @Schema(description = "필드명")
    private String fieldName;
    
    @Schema(description = "파일 타입")
    private String fileType;
    
    @Schema(description = "Base64로 인코딩된 파일 데이터")
    private String fileBase64;
    
    @Schema(description = "원본 파일명")
    private String originalName;
    
    @Schema(description = "파일 크기")
    private Long size;

    @Builder
    public AtflMngBase64RequestDto(String fieldName, String fileType, String fileBase64, String originalName, Long size) {
        this.fieldName = fieldName;
        this.fileType = fileType;
        this.fileBase64 = fileBase64;
        this.originalName = originalName;
        this.size = size;
    }
}
