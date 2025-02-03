package org.egovframe.cloud.attachservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * org.egovframe.cloud.attachservice.api.attachment.dto.AtflMngUploadResponseDto
 * <p>
 * 첨부파일 에디터 응답 DTO Class
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
@ToString
@Schema(description = "첨부파일 업로드 응답 DTO")
public class AtflMngUploadResponseDto {
    @Schema(description = "원본 파일명")
    protected String originalFileName;
    
    @Schema(description = "처리 메시지")
    protected String message;
    
    @Schema(description = "파일 타입")
    protected String fileType;
    
    @Schema(description = "파일 크기")
    protected long size;
}
