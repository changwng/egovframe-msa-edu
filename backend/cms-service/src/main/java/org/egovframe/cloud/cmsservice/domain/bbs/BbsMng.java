package org.egovframe.cloud.cmsservice.domain.bbs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.BaseEntity;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "bbs_mng")
public class BbsMng extends BaseEntity {
    @Id
    @Column(name = "bbs_id", columnDefinition = "integer" ,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bbsId;                 // 게시판 ID

    @Column(name = "bbs_type_cd", length = 7, nullable = false)
    private String bbsTypeCd;            // 게시판 유형 코드

    @Column(name = "bbs_nm", length = 100, nullable = false)
    private String bbsNm;                // 게시판 명

    @Column(name = "bbs_expln", length = 1000, nullable = false)
    private String bbsExpln;             // 게시판 설명

    @Column(name = "ref_use_yn", length = 1, nullable = false)
    private String refUseYn;             // 참조 사용 여부

    @Column(name = "atfl_use_yn", length = 1, nullable = false)
    private String atflUseYn;            // 첨부파일 사용 여부

    @Column(name = "atfl_max_cnt")
    private Integer atflMaxCnt;          // 첨부파일 최대 개수

    @Column(name = "atfl_max_flsz")
    private Long atflMaxFlsz;            // 첨부파일 최대 파일 크기

    @Column(name = "ans_use_yn", length = 1, nullable = false)
    private String ansUseYn;             // 답변 사용 여부

    @Column(name = "cmnt_use_yn", length = 1, nullable = false)
    private String cmntUseYn;            // 댓글 사용 여부

    @Column(name = "dgstfn_evl_use_yn", length = 1, nullable = false)
    private String dgstfnEvlUseYn;       // 만족도 평가 사용 여부

    @Column(name = "sms_use_yn", length = 1, nullable = false)
    private String smsUseYn;             // SMS 사용 여부

    @Column(name = "upnd_fix_use_yn", length = 1, nullable = false)
    private String upndFixUseYn;         // 상단 고정 사용 여부

    @Column(name = "pstg_prd_use_yn", length = 1, nullable = false)
    private String pstgPrdUseYn;         // 게시 기간 사용 여부

    @Column(name = "popup_use_yn", length = 1, nullable = false)
    private String popupUseYn;           // 팝업 사용 여부

    /**
     * 게시물 엔티티
     */
    @OneToMany(mappedBy = "bbsMng", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Pst> psts = new ArrayList<>();

    @Builder
    public BbsMng(Integer bbsId, String bbsTypeCd, String bbsNm, String bbsExpln,
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

    public void update(String bbsTypeCd, String bbsNm, String bbsExpln,
                      String refUseYn, String atflUseYn, Integer atflMaxCnt, Long atflMaxFlsz,
                      String ansUseYn, String cmntUseYn, String dgstfnEvlUseYn, String smsUseYn,
                      String upndFixUseYn, String pstgPrdUseYn, String popupUseYn) {
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

    public boolean isAtflAllowed() {
        return "Y".equals(this.atflUseYn);
    }

    public boolean isCmntAllowed() {
        return "Y".equals(this.cmntUseYn);
    }

    public boolean isAnsAllowed() {
        return "Y".equals(this.ansUseYn);
    }
}
