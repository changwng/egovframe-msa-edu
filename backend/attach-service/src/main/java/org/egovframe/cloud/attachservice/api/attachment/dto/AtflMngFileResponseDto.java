package org.egovframe.cloud.attachservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.attachservice.domain.attachment.AtflMng;
import org.egovframe.cloud.attachservice.domain.attachment.AtflMngId;

import java.util.UUID;

/**
 * org.egovframe.cloud.attachservice.api.attachment.dto.AtflMngFileResponseDto
 * <p>
 * 첨부파일 업로드 응답 DTO Class
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
@Schema(description = "첨부파일 업로드 응답 DTO")
public class AtflMngFileResponseDto extends AtflMngUploadResponseDto{
    @Schema(description = "원본 파일명")
    private String orgnlFileNm;    // 원본 파일명
    @Schema(description = "물리 파일명")
    private String physFileNm;     // 물리 파일명
    @Schema(description = "처리 메시지")
    private String message;        // 처리 메시지
    @Schema(description = "파일 크기")
    private Long atflFlsz;         // 파일 크기
    @Schema(description = "파일 유형")
    private String atflType;       // 파일 유형

    @Builder
    public AtflMngFileResponseDto(String orgnlFileNm, String physFileNm,
                                String message, Long atflFlsz, String atflType) {
        this.orgnlFileNm = orgnlFileNm;
        this.physFileNm = physFileNm;
        this.message = message;
        this.atflFlsz = atflFlsz;
        this.atflType = atflType;
    }
    public AtflMng toEntity(AtflMngId atflMngId, AtflMngUploadRequestDto  uploadRequestDto) {
        return AtflMng.builder()
                .atflMngId(atflMngId)
                .atflId(UUID.randomUUID().toString())
                .physFileNm(this.physFileNm)
                .orgnlFileNm(this.orgnlFileNm)
                .atflFlsz(this.atflFlsz)
                .atflType(this.atflType)
                .lnkgDmnNm(uploadRequestDto.getLnkgDmnNm())
                .lnkgDmnId(uploadRequestDto.getLnkgDmnId())
                .delYn("N") // defalut N으로 수정 해야함
                .dwnldNocs(0L)
                .build();
    }
}
