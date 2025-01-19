package org.egovframe.cloud.cmsservice.domain.attachment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "atfl_mng")
public class AtflMng extends BaseEntity {

    @Id
    @Column(name = "atfl_cd", length = 20)
    private String atflCd;               // 첨부파일 코드

    @Column(name = "atfl_sn", nullable = false)
    private Integer atflSn;              // 첨부파일 일련번호

    @Column(name = "dwnld_nocs", nullable = false)
    private Long dwnldNocs;              // 다운로드 건수

    @Column(name = "lnkg_dmn_id", length = 100)
    private String lnkgDmnId;            // 연결 도메인 ID

    @Column(name = "lnkg_dmn_nm", length = 100)
    private String lnkgDmnNm;            // 연결 도메인 명

    @Column(name = "atfl_type", length = 40, nullable = false)
    private String atflType;             // 첨부파일 유형

    @Column(name = "orgnl_file_nm", length = 100, nullable = false)
    private String orgnlFileNm;          // 원본 파일명

    @Column(name = "phys_file_nm", length = 100, nullable = false)
    private String physFileNm;           // 물리 파일명

    @Column(name = "atfl_flsz", nullable = false)
    private Long atflFlsz;               // 첨부파일 파일 크기

    @Column(name = "atfl_id", length = 80, nullable = false)
    private String atflId;               // 첨부파일 ID

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn;                // 삭제 여부

    @Builder
    public AtflMng(String atflCd, Integer atflSn, Long dwnldNocs, String lnkgDmnId,
                   String lnkgDmnNm, String atflType, String orgnlFileNm,
                   String physFileNm, Long atflFlsz, String atflId, String delYn) {
        this.atflCd = atflCd;
        this.atflSn = atflSn;
        this.dwnldNocs = dwnldNocs;
        this.lnkgDmnId = lnkgDmnId;
        this.lnkgDmnNm = lnkgDmnNm;
        this.atflType = atflType;
        this.orgnlFileNm = orgnlFileNm;
        this.physFileNm = physFileNm;
        this.atflFlsz = atflFlsz;
        this.atflId = atflId;
        this.delYn = delYn;
    }

    public void increaseDwnldNocs() {
        this.dwnldNocs = this.dwnldNocs + 1;
    }
}
