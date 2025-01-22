package org.egovframe.cloud.cmsservice.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.BaseEntity;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmnt;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "pst")
public class Pst extends BaseEntity {

    @EmbeddedId
    private PstId pstId;                 // 복합키 (bbsId + pstNo)



    @Column(name = "pst_ttl", length = 200, nullable = false)
    private String pstTtl;               // 게시물 제목

    @Column(name = "pst_cn", columnDefinition = "text")
    private String pstCn;                // 게시물 내용

    @Column(name = "inq_nocs", nullable = false)
    private Long inqNocs;                // 조회 건수

    @Column(name = "ref_url", length = 1000)
    private String refUrl;               // 참조 URL

    @Column(name = "popup_yn", length = 1, nullable = false)
    private String popupYn;              // 팝업 여부

    @Column(name = "upnd_fix_yn", length = 1, nullable = false)
    private String upndFixYn;            // 상단 고정 여부

    @Column(name = "prd_pstg_yn", length = 1, nullable = false)
    private String prdPstgYn;            // 기간 게시 여부

    @Column(name = "pst_seq_no", nullable = false)
    private Long pstSeqNo;               // 게시물 일련번호

    @Column(name = "up_pst_no")
    private Long upPstNo;                // 상위 게시물 번호

    @Column(name = "ans_loc", nullable = false)
    private Integer ansLoc;              // 답변 위치

    @Column(name = "pst_sort_seq", nullable = false)
    private Long pstSortSeq;             // 게시물 정렬 순서

    @Column(name = "pst_pswd", length = 64, nullable = false)
    private String pstPswd;              // 게시물 비밀번호

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn;                // 삭제 여부

    @Column(name = "del_rsn", length = 1000)
    private String delRsn;               // 삭제 사유

    @Column(name = "wrtr_nm", length = 20)
    private String wrtrNm;               // 작성자명

    @Column(name = "wrtr_cplc", length = 11)
    private String wrtrCplc;             // 작성자 연락처

    @Column(name = "atfl_cd", length = 20)
    private String atflCd;               // 첨부파일 코드 (UUID)

    @MapsId("bbsId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bbs_id")
    private BbsMng bbsMng;               // 게시판 엔티티

    /**
     * 댓글 엔티티
     */
    @OneToMany(mappedBy = "pst", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PstCmnt> pstCmnts;

    @Builder
    public Pst(PstId pstId, BbsMng bbsMng, String pstTtl, String pstCn, Long inqNocs,
               String refUrl, String popupYn, String upndFixYn, String prdPstgYn,
               Long pstSeqNo, Long upPstNo, Integer ansLoc, Long pstSortSeq,
               String pstPswd, String delYn, String delRsn, String wrtrNm,
               String wrtrCplc, String atflCd) {
        this.pstId = pstId;
        this.bbsMng = bbsMng;
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
    }

    public void update(String pstTtl, String pstCn, String refUrl,
                       String popupYn, String upndFixYn, String prdPstgYn,
                       String wrtrNm, String wrtrCplc, String atflCd) {
        this.pstTtl = pstTtl;
        this.pstCn = pstCn;
        this.refUrl = refUrl;
        this.popupYn = popupYn;
        this.upndFixYn = upndFixYn;
        this.prdPstgYn = prdPstgYn;
        this.wrtrNm = wrtrNm;
        this.wrtrCplc = wrtrCplc;
        this.atflCd = atflCd;
    }

    public void increaseInqNocs() {
        this.inqNocs = this.inqNocs + 1;
    }

    public void delete(String delRsn) {
        this.delYn = "Y";
        this.delRsn = delRsn;
    }

    public boolean isDeleted() {
        return "Y".equals(this.delYn);
    }

    public boolean isPopup() {
        return "Y".equals(this.popupYn);
    }

    public boolean isUpndFixed() {
        return "Y".equals(this.upndFixYn);
    }

    public boolean matchPassword(String password) {
        return this.pstPswd.equals(password);
    }

    public void setUpPstNo(Long upPstNo) {
        this.upPstNo = upPstNo;
    }

    public void setAnsLoc(Integer ansLoc) {
        this.ansLoc = ansLoc;
    }
}
