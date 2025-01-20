package org.egovframe.cloud.cmsservice.domain.attachment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.BaseEntity;

import javax.persistence.*;
import org.springframework.util.StringUtils;
import java.util.Objects;

/**
 * org.egovframe.cloud.cmsservice.domain.attachment.AtflMng
 * <p>
 * 첨부파일 엔티티 class
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
@NoArgsConstructor
@Entity
@Table(name = "atfl_mng")
public class AtflMng extends BaseEntity {

    @EmbeddedId
    private AtflMngId atflMngId;  // 복합키

    /**
     * 복합키를 HTTP URI에 표현하기 힘드므로 대체키를 추가한다.
     */
    @Column(name = "atfl_id", length = 80, nullable = false)
    private String atflId;               // 첨부파일 ID (URI용 대체키)

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

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn;                // 삭제 여부
    /**
     * entity 정보 update
     *
     * @param lnkgDmnNm
     * @param lnkgDmnId
     * @return
     */
    public AtflMng updateEntity(String lnkgDmnNm, String lnkgDmnId) {
        this.lnkgDmnNm = lnkgDmnNm;
        this.lnkgDmnId = lnkgDmnId;
        return this;
    }
    @Builder
    public AtflMng(AtflMngId atflMngId, String atflId, Long dwnldNocs, String lnkgDmnId,
                   String lnkgDmnNm, String atflType, String orgnlFileNm,
                   String physFileNm, Long atflFlsz, String delYn) {
        this.atflMngId = atflMngId;
        this.atflId = atflId;
        this.dwnldNocs = dwnldNocs;
        this.lnkgDmnId = lnkgDmnId;
        this.lnkgDmnNm = lnkgDmnNm;
        this.atflType = atflType;
        this.orgnlFileNm = orgnlFileNm;
        this.physFileNm = physFileNm;
        this.atflFlsz = atflFlsz;
        this.delYn = delYn;
    }

    public void increaseDwnldNocs() {
        this.dwnldNocs = this.dwnldNocs + 1;
    }


    /**
     * 삭제 여부 토글
     * 이전함수가 updateIsDelete
     * @param delYn
     * @return
     */
    public AtflMng updateIsDelYn(String delYn) {
        this.delYn = delYn;
        return this;
    }
    public boolean haslnkgDmnId()  {
        return (Objects.nonNull(lnkgDmnId) || StringUtils.hasText(lnkgDmnId)) && !"-1".equals(lnkgDmnId);
    }

    public boolean isDeleted() {
        return (delYn != null && "Y".equals(delYn));
    }
    /**
     * 첨부파일 다운로드 할 때 마다 Download 횟수 + 1
     *
     * @return
     */
    public AtflMng updateDownloadCnt() {
        this.dwnldNocs = getDwnldNocs() == null ? 1 : getDwnldNocs() + 1;
        return this;
    }
}
