package org.egovframe.cloud.attachservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import org.egovframe.cloud.attachservice.domain.attachment.AtflMng;

/**
 * org.egovframe.cloud.attachservice.api.attachment.dto.AtflMngResponseDto
 * <p>
 * 첨부파일 응답 dto class
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
@Setter
@NoArgsConstructor
@ToString
@Schema(description = "첨부파일 응답 DTO")
public class AtflMngResponseDto {
    @Schema(description = "첨부파일 코드")
    private String atflCd;
    
    @Schema(description = "첨부파일 일련번호")
    private Integer atflSn;
    
    @Schema(description = "첨부파일 ID (URI용)")
    private String atflId;
    
    @Schema(description = "물리 파일명")
    private String physFileNm;
    
    @Schema(description = "원본 파일명")
    private String orgnlFileNm;
    
    @Schema(description = "파일 크기")
    private Long atflFlsz;
    
    @Schema(description = "파일 유형")
    private String atflType;
    
    @Schema(description = "다운로드 건수")
    private Long dwnldNocs;
    
    @Schema(description = "삭제 여부")
    private String delYn;
    
    @Schema(description = "연결 도메인 ID")
    private String lnkgDmnId;
    
    @Schema(description = "연결 도메인 명")
    private String lnkgDmnNm;
    
    @Schema(description = "생성일시")
    private LocalDateTime regDt;

    @Builder
    public AtflMngResponseDto(AtflMng atflMng) {
        this.atflCd = atflMng.getAtflMngId().getAtflCd();
        this.atflSn = atflMng.getAtflMngId().getAtflSn();
        this.atflId = atflMng.getAtflId();
        this.physFileNm = atflMng.getPhysFileNm();
        this.orgnlFileNm = atflMng.getOrgnlFileNm();
        this.atflFlsz = atflMng.getAtflFlsz();
        this.atflType = atflMng.getAtflType();
        this.dwnldNocs = atflMng.getDwnldNocs();
        this.delYn = atflMng.getDelYn();
        this.lnkgDmnId = atflMng.getLnkgDmnId();
        this.lnkgDmnNm = atflMng.getLnkgDmnNm();
        this.regDt = atflMng.getRegDt();
    }
}
