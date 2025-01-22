package org.egovframe.cloud.cmsservice.api.bbs.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
//import org.egovframe.cloud.cmsservice.api.post.dto.PstSimpleResponseDto;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BbsMngResponseDto {
    /**
     * 게시판 ID
     */
    @Schema(description = "게시판 ID")
    private Integer bbsId;
    /**
     * 게시판 구분 코드
     */
    @Schema(description = "게시판 구분 코드")
    private String bbsTypeCd;
    /**
     * 게시판 이름
     */
    @Schema(description = "게시판 이름")
    private String bbsNm;
    /**
     * 게시판 설명
     */
    @Schema(description = "게시판 설명")
    private String bbsExpln;
    /**
     *  사용 여부
     */
    @Schema(description = "  사용 여부")
    private String refUseYn;
    /**
     * 첨부파일 사용 여부
     */
    @Schema(description = "첨부파일 사용 여부")
    private String atflUseYn;
    /**
     * 첨부파일 최대 개수(NULL 허용)
     */
    @Schema(description = "첨부파일 최대 개수(NULL 허용)")
    private Integer atflMaxCnt;
    /**
     * 첨부파일 최대 크기(NULL 허용)
     */
    @Schema(description = "첨부파일 최대 크기(NULL 허용)")
    private Long atflMaxFlsz;
    /**
     * 답변 사용 여부
     */
    @Schema(description = "답변 사용 여부")
    private String ansUseYn;
    /**
     * 댓글 사용 여부
     */
    @Schema(description = "댓글 사용 여부")
    private String cmntUseYn;
    /**
     * 만족도 평가 사용 여부
     */
    @Schema(description = "만족도 평가 사용 여부")
    private String dgstfnEvlUseYn;
    /**
     * SMS 발송 사용 여부
     */
    @Schema(description = "SMS 발송 사용 여부")
    private String smsUseYn;
    /**
     * 상단 고정 표시 사용 여부
     */
    @Schema(description = "상단 고정 표시 사용 여부")
    private String upndFixUseYn;
    /**
     * 게시물 기간 사용 여부
     */
    @Schema(description = "게시물 기간 사용 여부")
    private String pstgPrdUseYn;
    /**
     * 팝업 사용 여부
     */
    @Schema(description = "팝업 사용 여부")
    private String popupUseYn;

    /**
     * 게시물 목록
     */
//    private List<PstSimpleResponseDto> psts;

@QueryProjection
public BbsMngResponseDto(Integer bbsId, String bbsTypeCd, String bbsNm, String bbsExpln,
                         String refUseYn, String atflUseYn, Integer atflMaxCnt, Long atflMaxFlsz,
                         String ansUseYn, String cmntUseYn, String dgstfnEvlUseYn, String smsUseYn,
                         String upndFixUseYn, String pstgPrdUseYn, String popupUseYn) {
    this.bbsId = bbsId;
    this.bbsTypeCd = bbsTypeCd;
    this.bbsNm = bbsNm;
    this.bbsExpln = bbsExpln;
    this.refUseYn = refUseYn;
    this.atflUseYn = atflUseYn;
    this.atflMaxCnt = atflMaxCnt;
    this.atflMaxFlsz = atflMaxFlsz;
    this.ansUseYn = ansUseYn;
    this.cmntUseYn = cmntUseYn;
    this.dgstfnEvlUseYn = dgstfnEvlUseYn;
    this.smsUseYn = smsUseYn;
    this.upndFixUseYn = upndFixUseYn;
    this.pstgPrdUseYn = pstgPrdUseYn;
    this.popupUseYn = popupUseYn;
}
    public BbsMngResponseDto(BbsMng entity) {
        this.bbsId = entity.getBbsId();
        this.bbsTypeCd = entity.getBbsTypeCd();
        this.bbsNm = entity.getBbsNm();
        this.bbsExpln = entity.getBbsExpln();
        this.refUseYn = entity.getRefUseYn();
        this.atflUseYn = entity.getAtflUseYn();
        this.atflMaxCnt = entity.getAtflMaxCnt();
        this.atflMaxFlsz = entity.getAtflMaxFlsz();
        this.ansUseYn = entity.getAnsUseYn();
        this.cmntUseYn = entity.getCmntUseYn();
        this.dgstfnEvlUseYn = entity.getDgstfnEvlUseYn();
        this.smsUseYn = entity.getSmsUseYn();
        this.upndFixUseYn = entity.getUpndFixUseYn();
        this.pstgPrdUseYn = entity.getPstgPrdUseYn();
        this.popupUseYn = entity.getPopupUseYn();
    }
    /**
     * DTO를 BbsMng 엔티티로 변환합니다.
     * 이 메서드는 현재 DTO의 모든 필드 값을 사용하여 새로운 BbsMng 엔티티를 생성합니다.
     *
     * @return 현재 DTO 정보로 생성된 BbsMng 엔티티
     */
    public BbsMng toEntity() {
        return BbsMng.builder()
                .bbsId(this.bbsId)
                .bbsTypeCd(this.bbsTypeCd)
                .bbsNm(this.bbsNm)
                .bbsExpln(this.bbsExpln)
                .refUseYn(this.refUseYn)
                .atflUseYn(this.atflUseYn)
                .atflMaxCnt(this.atflMaxCnt)
                .atflMaxFlsz(this.atflMaxFlsz)
                .ansUseYn(this.ansUseYn)
                .cmntUseYn(this.cmntUseYn)
                .dgstfnEvlUseYn(this.dgstfnEvlUseYn)
                .smsUseYn(this.smsUseYn)
                .upndFixUseYn(this.upndFixUseYn)
                .pstgPrdUseYn(this.pstgPrdUseYn)
                .popupUseYn(this.popupUseYn)
                .build();
    }

    /**
     * 최근 게시물 목록 세팅
     *
     * @param psts 게시물 목록
     */
//     public void setNewestPosts(List<PstSimpleResponseDto> psts) {
//        this.psts = psts == null ? null : new ArrayList<>(psts);
//    }

}
