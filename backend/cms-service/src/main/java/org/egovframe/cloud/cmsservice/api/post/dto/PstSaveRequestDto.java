package org.egovframe.cloud.cmsservice.api.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * PstSaveRequestDto
 * <p>
 * 게시물 등록 요청 DTO 클래스
 *
 * @author USER
 * @version 1.0
 * @since 2025/01/22
 */
@Getter
@NoArgsConstructor
public class PstSaveRequestDto {
    @NotBlank(message = "{posts.posts_title} {err.required}")
    @Schema(description = "게시물 제목")
    private String pstTtl;


    /**
     * 게시물 내용
     */
    @Schema(description = "게시물 내용")
    @NotBlank(message = "{posts.posts_content} {err.required}")
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


    @Builder
    public PstSaveRequestDto(String pstTtl, String pstCn, String atflCd, String upndFixYn, String prdPstgYn, Long pstSeqNo
            , Long upPstNo, Integer ansLoc, Long pstSortSeq, String pstPswd
            , String delYn, String delRsn, String wrtrNm, String wrtrCplc, Long inqNocs, String refUrl, String popupYn) {
        this.pstTtl = pstTtl;
        this.pstCn = pstCn;
        this.atflCd = atflCd;
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
        this.inqNocs = inqNocs;
        this.refUrl = refUrl;
        this.popupYn = popupYn;
    }


    /**
     * 게시물 등록 요청 DTO 속성 값으로 게시물 엔티티 빌더를 사용하여 객체 생성
     *
     * @param bbsId 게시판 번호
     * @param pstNo 게시물 번호
     * @return Posts 게시물 엔티티
     */
    public Pst toEntity(Integer bbsId, Long pstNo) {
        return Pst.builder()
                .bbsMng(BbsMng.builder()
                        .bbsId(bbsId)
                        .build())
                .pstId(PstId.builder()
                        .bbsId(bbsId)
                        .pstNo(pstNo)
                        .build())
                .pstTtl(pstTtl)
                .pstCn(pstCn)
                .inqNocs(inqNocs)
                .refUrl(refUrl)
                .popupYn(popupYn)
                .upndFixYn(upndFixYn)
                .prdPstgYn(prdPstgYn)
                .pstSeqNo(pstSeqNo)
                .upPstNo(upPstNo)
                .ansLoc(ansLoc) // ansLoc
                .pstSortSeq(pstSortSeq)
                .pstPswd(pstPswd)
                .delYn(delYn)
                .delRsn(delRsn)
                .wrtrNm(wrtrNm)
                .wrtrCplc(wrtrCplc) // wrtrCplc
                .atflCd(atflCd)
                .build();
    }


    /**
     * 신규 저장시 사용 하는 구문
     * @param bbsMng
     * @param pstId
     * @param pstSeqNo
     * @param pstSortSeq
     * @return
     */
    public Pst toEntity(BbsMng bbsMng, PstId pstId, Long pstSeqNo, Long pstSortSeq) {
        return Pst.builder()
                .pstId(pstId)
                .bbsMng(bbsMng)
                .pstTtl(pstTtl)
                .pstCn(pstCn)
                .inqNocs(0L)
                .refUrl(refUrl)
                .popupYn(popupYn)
                .upndFixYn(upndFixYn)
                .prdPstgYn(prdPstgYn)
                .pstSeqNo(pstSeqNo)
                .upPstNo(upPstNo)
                .ansLoc(ansLoc)
                .pstSortSeq(pstSortSeq)
                .pstPswd(pstPswd)
                .delYn("N")
                .wrtrNm(wrtrNm)
                .wrtrCplc(wrtrCplc)
                .atflCd(atflCd)
                .build();
    }
}
