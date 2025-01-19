package org.egovframe.cloud.cmsservice.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.BaseEntity;
import org.egovframe.cloud.cmsservice.domain.post.Pst;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "pst_cmnt")
public class PstCmnt extends BaseEntity {

    @EmbeddedId
    private PstCmntId pstCmntId;         // 복합키 (bbsId + pstNo + cmntNo)

    @Column(name = "cmnt_cn", length = 1000)
    private String cmntCn;               // 댓글 내용

    @Column(name = "wrtr_nm", length = 20)
    private String wrtrNm;               // 작성자명

    @Column(name = "cmnt_pswd", length = 64, nullable = false)
    private String cmntPswd;             // 댓글 비밀번호

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn;                // 삭제 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "bbs_id", referencedColumnName = "bbs_id", insertable = false, updatable = false),
        @JoinColumn(name = "pst_no", referencedColumnName = "pst_no", insertable = false, updatable = false)
    })
    private Pst pst;                     // 게시물 참조

    @Builder
    public PstCmnt(PstCmntId pstCmntId, String cmntCn, String wrtrNm,
                   String cmntPswd, String delYn, Pst pst) {
        this.pstCmntId = pstCmntId;
        this.cmntCn = cmntCn;
        this.wrtrNm = wrtrNm;
        this.cmntPswd = cmntPswd;
        this.delYn = delYn;
        this.pst = pst;
    }

    public void update(String cmntCn, String wrtrNm) {
        this.cmntCn = cmntCn;
        this.wrtrNm = wrtrNm;
    }

    public void delete() {
        this.delYn = "Y";
    }

    public boolean isDeleted() {
        return "Y".equals(this.delYn);
    }

    public boolean matchPassword(String password) {
        return this.cmntPswd.equals(password);
    }
}
