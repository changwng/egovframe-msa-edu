package org.egovframe.cloud.cmsservice.api.bbs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * org.egovframe.cloud.boardservice.api.board.dto.BbsMngUpdateRequestDto
 * <p>
 * 게시판 수정 요청 DTO 클래스
 *
 * @author 표준프레임워크센터 jooho
 * @version 1.0
 * @since 2021/07/08
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2021/07/08    jooho       최초 생성
 * </pre>
 */
@Getter
@NoArgsConstructor
public class BbsMngUpdateRequestDto {

    /**
     * 게시판 구분 코드
     */
    @NotNull(message = "{board.bbs_type_cd} {err.required}")
    private String bbsTypeCd;
    /**
     * 게시판 이름
     */
    @NotBlank(message = "{board.bbs_nm} {err.required}")
    private String bbsNm;
    /**
     * 게시판 설명
     */
    @NotBlank(message = "{board.bbs_expln} {err.required}")
    private String bbsExpln;
    /**
     *  사용 여부
     */
    @NotNull(message = "{board.ref_use_yn} {err.required}")
    private String refUseYn;
    /**
     * 첨부파일 사용 여부
     */
    @NotNull(message = "{board.atfl_use_yn} {err.required}")
    private String atflUseYn;
    /**
     * 첨부파일 최대 개수(NULL 허용)
     */
    private Integer atflMaxCnt;
    /**
     * 첨부파일 최대 크기(NULL 허용)
     */
    private Long atflMaxFlsz;
    /**
     * 답변 사용 여부
     */
    @NotNull(message = "{board.ans_use_yn} {err.required}")
    private String ansUseYn;
    /**
     * 댓글 사용 여부
     */
    @NotNull(message = "{board.cmnt_use_yn} {err.required}")
    private String cmntUseYn;
    /**
     * 만족도 평가 사용 여부
     */
    @NotNull(message = "{board.dgstfn_evl_use_yn} {err.required}")
    private String dgstfnEvlUseYn;
    /**
     * SMS 발송 사용 여부
     */
    @NotNull(message = "{board.sms_use_yn} {err.required}")
    private String smsUseYn;
    /**
     * 상단 고정 표시 사용 여부
     */
    @NotNull(message = "{board.upnd_fix_use_yn} {err.required}")
    private String upndFixUseYn;
    /**
     * 게시물 기간 사용 여부
     */
    @NotNull(message = "{board.pstg_prd_use_yn} {err.required}")
    private String pstgPrdUseYn;
    /**
     * 팝업 사용 여부
     */
    @NotNull(message = "{board.popup_use_yn} {err.required}")
    private String popupUseYn;
    /**
     * 게시판 수정 요청 DTO 클래스 생성자
     * 빌더 패턴으로 객체 생성
     *
     * @param bbsTypeCd    게시판 구분 코드
     * @param bbsNm        게시판 이름
     * @param bbsExpln     게시판 설명
     * @param refUseYn     사용 여부
     * @param atflUseYn    첨부파일 사용 여부
     * @param atflMaxCnt   첨부파일 최대 개수(NULL 허용)
     * @param atflMaxFlsz  첨부파일 최대 크기(NULL 허용)
     * @param ansUseYn     답변 사용 여부
     * @param cmntUseYn    댓글 사용 여부
     * @param dgstfnEvlUseYn 만족도 평가 사용 여부
     * @param smsUseYn     SMS 발송 사용 여부
     * @param upndFixUseYn 상단 고정 표시 사용 여부
     * @param pstgPrdUseYn 게시물 기간 사용 여부
     * @param popupUseYn   팝업 사용 여부
     */
    @Builder
    public BbsMngUpdateRequestDto(String bbsTypeCd, String bbsNm, String bbsExpln,
                                  String refUseYn, String atflUseYn, Integer atflMaxCnt,
                                  Long atflMaxFlsz, String ansUseYn, String cmntUseYn,
                                  String dgstfnEvlUseYn, String smsUseYn, String upndFixUseYn,
                                  String pstgPrdUseYn, String popupUseYn) {
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

    /**
     * 게시판 등록 요청 DTO 속성 값으로 게시판 엔티티 빌더를 사용하여 객체 생성
     *
     * @return BbsMng 게시판 엔티티
     */
    public BbsMng toEntity() {
        return BbsMng.builder()
                .bbsTypeCd(bbsTypeCd)
                .bbsNm(bbsNm)
                .bbsExpln(bbsExpln)
                .refUseYn(refUseYn)
                .atflUseYn(atflUseYn)
                .atflMaxCnt(atflMaxCnt)
                .atflMaxFlsz(atflMaxFlsz)
                .ansUseYn(ansUseYn)
                .cmntUseYn(cmntUseYn)
                .dgstfnEvlUseYn(dgstfnEvlUseYn)
                .smsUseYn(smsUseYn)
                .upndFixUseYn(upndFixUseYn)
                .pstgPrdUseYn(pstgPrdUseYn)
                .popupUseYn(popupUseYn)
                .build();
    }

}
