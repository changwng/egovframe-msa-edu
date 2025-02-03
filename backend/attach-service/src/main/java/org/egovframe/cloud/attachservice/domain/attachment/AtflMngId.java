package org.egovframe.cloud.attachservice.domain.attachment;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * org.egovframe.cloud.attachservice.domain.attachment.AtflMngId
 * <p>
 * 첨부파일 엔티티 복합키 class
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
 *  2024/01/20    changwng     최초 생성
 * </pre>
 */
@Data
@Embeddable
public class AtflMngId implements Serializable {
    @Column(name = "atfl_cd", length = 20)
    private String atflCd;    // 첨부파일 코드 (UUID)

    @Column(name = "atfl_sn")
    private Integer atflSn;   // 첨부파일 일련번호

    @Builder
    public AtflMngId(String atflCd, Integer atflSn) {
        this.atflCd = atflCd;
        this.atflSn = atflSn;
    }

    public AtflMngId() {
        this.atflCd = UUID.randomUUID().toString();
        this.atflSn = 1;
    }
}
