package org.egovframe.cloud.cmsservice.api.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import java.util.ArrayList;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngResponseDto;

/**
 * PstResponseDto
 * <p>
 * 게시물 상세 응답 DTO 클래스
 *
 * @author USER
 * @version 1.0
 * @since 2025/01/22
 */
@Getter
@NoArgsConstructor
public class PstResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 게시판 ID
     */
    @Schema(description = "게시판 ID")
    private Integer bbsId;

    /**
     * 게시물 번호
     */
    @Schema(description = "게시물 번호")
    private Long pstNo;

    /**
     * 게시물 제목
     */
    @Schema(description = "게시물 제목")
    private String pstTtl;

    /**
     * 게시물 내용
     */
    @Schema(description = "게시물 내용")
    private String pstCn;

    /**
     * 조회수
     */
    @Schema(description = "조회수")
    private Long inqNocs;

    /**
     * 참조 URL
     */
    @Schema(description = "참조 URL")
    private String refUrl;

    /**
     * 팝업사용여부
     */
    @Schema(description = "팝업사용여부")
    private String popupYn;

    /**
     * 상단고정여부
     */
    @Schema(description = "상단고정여부")
    private String upndFixYn;

    @Schema(description = "기간 게시 여부")
    private String prdPstgYn;
    /**
     * 게시물 일련번호
     */
    @Schema(description = "게시물 일련번호")
    private Long pstSeqNo;

    /**
     *  상위 게시물 번호
     */
    @Schema(description = "상위 게시물 번호")
    private Long upPstNo;

    /**
     * 답변위치
     */
    @Schema(description = "답변위치")
    private Integer ansLoc;

    /**
     * 게시물 정렬 순서
     */
    @Schema(description = "게시물 정렬 순서")
    private Long pstSortSeq;

    /**
     * 게시물 비밀번호
     */
    @Schema(description = "게시물 비밀번호")
    private String pstPswd;

    /**
     * 삭제여부
     */
    @Schema(description = "삭제여부")
    private String delYn;

    /**
     * 삭제사유
     */
    @Schema(description = "삭제사유")
    private String delRsn;

    /**
     * 작성자명
     */
    @Schema(description = "작성자명")
    private String wrtrNm;

    /**
     * 작성자연락처
     */
    @Schema(description = "작성자연락처")
    private String wrtrCplc;

    /**
     * 첨부파일코드
     */
    @Schema(description = "첨부파일 코드 (UUID)")
    private String atflCd;

    /**
     * 등록일시
     */
    @Schema(description = "등록일자")
    private LocalDateTime regDt;

    /**
     * 신규 여부
     */
    @Schema(description = "신규여부")
    private Boolean isNew;

    /**
     * 게시판 응답 DTO
     */
    private BbsMngResponseDto bmsMng;
    /**
     * 이전 게시물 응답 DTO List
     */
    private List<PstSimpleResponseDto> prevPosts;

    /**
     * 다음 게시물 응답 DTO List
     */
    private List<PstSimpleResponseDto> nextPosts;
    @QueryProjection
    public PstResponseDto(Integer bbsId, Long pstNo, String pstTtl, String pstCn, Long inqNocs, String refUrl, String popupYn
            , String upndFixYn, String prdPstgYn, Long pstSeqNo, Long upPstNo, Integer ansLoc, Long pstSortSeq, String pstPswd, String delYn
            , String delRsn, String wrtrNm, String wrtrCplc, String atflCd, LocalDateTime regDt
            , BbsMngResponseDto bmsMng ) {
        this.bbsId = bbsId;
        this.pstNo = pstNo;
        this.pstTtl = pstTtl;
        this.pstCn = pstCn;
        this.inqNocs = inqNocs;
        this.refUrl = refUrl;
        this.popupYn = popupYn;
        this.upndFixYn = upndFixYn;
        this.prdPstgYn = prdPstgYn;
        this.pstSeqNo = pstSeqNo;
        this.upPstNo = upPstNo;
        this.ansLoc = ansLoc;
        this.pstSortSeq = pstSortSeq;
        this.pstPswd = pstPswd;
        this.delYn = delYn;
        this.delRsn = delRsn;
        this.wrtrNm = wrtrNm;
        this.wrtrCplc = wrtrCplc;
        this.atflCd = atflCd;
        this.regDt = regDt;
        this.bmsMng = bmsMng;

       // this.isNew = regDt.plusDays(this.board.getNewDisplayDayCount()).compareTo(LocalDateTime.now()) <= 0;
        this.isNew = regDt.plusDays(2).compareTo(LocalDateTime.now()) >= 0;
    }

    public PstResponseDto(Pst entity) {
       this.bbsId = entity.getBbsMng().getBbsId();
       this.pstNo = entity.getPstId().getPstNo();
        this.pstTtl = entity.getPstTtl();
        this.pstCn = entity.getPstCn();
        this.inqNocs = entity.getInqNocs();
        this.refUrl = entity.getRefUrl();
        this.popupYn = entity.getPopupYn();
        this.upndFixYn = entity.getUpndFixYn();
        this.pstSeqNo = entity.getPstSeqNo();
        this.upPstNo = entity.getUpPstNo();
        this.ansLoc = entity.getAnsLoc();
        this.pstSortSeq = entity.getPstSortSeq();
        this.pstPswd = entity.getPstPswd();
        this.delYn = entity.getDelYn();
        this.delRsn = entity.getDelRsn();
        this.wrtrNm = entity.getWrtrNm();
        this.wrtrCplc = entity.getWrtrCplc();
        this.atflCd = entity.getAtflCd();
        this.regDt = entity.getRegDt();
        this.prdPstgYn = entity.getPrdPstgYn();
        this.isNew = regDt.plusDays(2).compareTo(LocalDateTime.now()) >= 0;
    }

    /**
     * 조회 수 증가
     */
    public void increaseInqNocs() {
        this.inqNocs = this.inqNocs + 1;
    }

    /**
     * 이전 게시물
     */
    public void setPrevPosts(List<PstSimpleResponseDto> prevPosts) {
        this.prevPosts = prevPosts == null ? null : new ArrayList<>(prevPosts);
    }

    /**
     * 다음 게시물
     */
    public void setNextPosts(List<PstSimpleResponseDto> nextPosts) {
        this.nextPosts = nextPosts == null ? null : new ArrayList<>(nextPosts);
    }
}
