package org.egovframe.cloud.cmsservice.api.attachment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMng;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMngId;
import org.springframework.util.StringUtils;
import java.util.Objects;
import lombok.ToString;

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
public class AtflMngTempSaveRequestDto {
    private String atflId;               // 첨부파일 ID (URI용 대체키)
    private String orgnlFileNm;    // 원본 파일명
    private String physFileNm;     // 물리 파일명
    private Long atflFlsz;         // 파일 크기
    private String atflType;       // 파일 유형

    private String lnkgDmnNm;     // 연결 도메인명

    private String lnkgDmnId;     // 연결 도메인 ID
    private String delYn;          // 삭제여부
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
                .atflId(UUID.randomUUID().toString())
                .physFileNm(physicalFileName)
                .orgnlFileNm(this.orgnlFileNm)
                .atflFlsz(this.atflFlsz)
                .atflType(this.atflType)
                .lnkgDmnNm(this.lnkgDmnNm)
                .lnkgDmnId(this.lnkgDmnId)
                .build();
    }
}
