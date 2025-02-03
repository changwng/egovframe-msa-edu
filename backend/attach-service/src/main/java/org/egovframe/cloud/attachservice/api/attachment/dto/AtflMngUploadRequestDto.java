package org.egovframe.cloud.attachservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * org.egovframe.cloud.attachservice.api.attachment.dto.AtflMngUploadRequestDto
 * <p>
 * 첨부파일 업로드 저장 시 요청  DTO Class
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
@Schema(description = "첨부파일 업로드 요청 DTO")
public class AtflMngUploadRequestDto {
    @Schema(description = "연결 도메인 명")
    private String lnkgDmnNm;
    
    @Schema(description = "연결 도메인 ID")
    private String lnkgDmnId;

    @Builder
    public AtflMngUploadRequestDto(String lnkgDmnNm, String lnkgDmnId) {
        this.lnkgDmnNm = lnkgDmnNm;
        this.lnkgDmnId = lnkgDmnId;
    }
}
