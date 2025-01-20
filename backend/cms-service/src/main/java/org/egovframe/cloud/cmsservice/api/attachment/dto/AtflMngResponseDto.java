package org.egovframe.cloud.cmsservice.api.attachment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMng;
/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngResponseDto
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
public class AtflMngResponseDto {
    private String atflCd;            // 첨부파일 코드
    private Integer atflSn;           // 첨부파일 일련번호
    private String atflId;            // 첨부파일 ID (URI용)
    private String physFileNm;        // 물리 파일명
    private String orgnlFileNm;       // 원본 파일명
    private Long atflFlsz;            // 파일 크기
    private String atflType;          // 파일 유형
    private Long dwnldNocs;           // 다운로드 건수
    private String delYn;             // 삭제 여부
    private String lnkgDmnId;         // 연결 도메인 ID
    private String lnkgDmnNm;         // 연결 도메인 명
    private LocalDateTime reg_dt; // 생성일시

   /* @Builder
    public AtflMngResponseDto(String atflCd, Integer atflSn, String atflId, 
            String physFileNm, String orgnlFileNm, Long atflFlsz, 
            String atflType, Long dwnldNocs, String delYn, 
            String lnkgDmnId, String lnkgDmnNm, LocalDateTime reg_dt) {
        this.atflCd = atflCd;
        this.atflSn = atflSn;
        this.atflId = atflId;
        this.physFileNm = physFileNm;
        this.orgnlFileNm = orgnlFileNm;
        this.atflFlsz = atflFlsz;
        this.atflType = atflType;
        this.dwnldNocs = dwnldNocs;
        this.delYn = delYn;
        this.lnkgDmnId = lnkgDmnId;
        this.lnkgDmnNm = lnkgDmnNm;
        this.reg_dt = reg_dt;
    }*/

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
        this.reg_dt = atflMng.getReg_dt();
    }
}
