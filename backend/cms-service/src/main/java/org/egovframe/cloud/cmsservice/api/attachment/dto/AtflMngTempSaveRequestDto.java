package org.egovframe.cloud.cmsservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMng;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMngId;
import org.springframework.util.StringUtils;
import java.util.Objects;
import java.util.UUID;

/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngTempSaveRequestDto
 * <p>
 * 첨부파일 임시 저장 요청 DTO Class
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
@Schema(description = "첨부파일 임시 저장 요청 DTO")
public class AtflMngTempSaveRequestDto {
    @Schema(description = "첨부파일 ID (URI용 대체키)")
    private String atflId;
    
    @Schema(description = "원본 파일명")
    private String orgnlFileNm;
    
    @Schema(description = "물리 파일명")
    private String physFileNm;
    
    @Schema(description = "파일 크기")
    private Long atflFlsz;
    
    @Schema(description = "파일 유형")
    private String atflType;
    
    @Schema(description = "연결 도메인명")
    private String lnkgDmnNm;
    
    @Schema(description = "연결 도메인 ID")
    private String lnkgDmnId;
    
    @Schema(description = "삭제여부")
    private String delYn;

    public boolean hasAtflId() {
        return Objects.nonNull(atflId) || StringUtils.hasText(atflId);
    }

    @Builder
    public AtflMngTempSaveRequestDto(String atflId, String orgnlFileNm, String physFileNm, Long atflFlsz, String atflType,
                                     String lnkgDmnNm, String lnkgDmnId, String delYn) {
        this.atflId = atflId;
        this.orgnlFileNm = orgnlFileNm;
        this.physFileNm = physFileNm;
        this.atflFlsz = atflFlsz;
        this.atflType = atflType;
        this.lnkgDmnNm = lnkgDmnNm;
        this.lnkgDmnId = lnkgDmnId;
        this.delYn = delYn;
    }

    public boolean hasUniqueId() {
        return Objects.nonNull(atflId) || StringUtils.hasText(atflId);
    }
    public AtflMng toEntity(AtflMngId atflMngId, String physicalFileName) {
        return AtflMng.builder()
                .atflMngId(atflMngId)
                .atflId(atflId == null ? UUID.randomUUID().toString() : atflId)
                .physFileNm(physicalFileName)
                .orgnlFileNm(this.orgnlFileNm)
                .atflFlsz(this.atflFlsz)
                .atflType(this.atflType)
                .lnkgDmnNm(this.lnkgDmnNm)
                .lnkgDmnId(this.lnkgDmnId)
                .delYn(this.delYn)
                .build();
    }
}
