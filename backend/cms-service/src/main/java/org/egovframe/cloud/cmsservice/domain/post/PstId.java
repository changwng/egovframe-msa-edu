package org.egovframe.cloud.cmsservice.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class PstId implements Serializable {

    @Column(name = "bbs_id", length = 10)
    private Integer bbsId;

    @Column(name = "pst_no" )
    private Long pstNo;

    /**
     * 빌드 패턴 클래스 생성자
     *
     * @param bbsId 게시판 번호
     * @param pstNo 게시물 번호
     */
    @Builder
    public PstId(Integer bbsId, Long pstNo) {
        this.bbsId = bbsId;
        this.pstNo = pstNo;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by java.util.HashMap.
     *
     * @return int a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(bbsId, pstNo);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param   object   the reference object with which to compare.
     * @return  {@code true} if this object is the same as the obj
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PstId)) return false;
        PstId that = (PstId) object;
        return Objects.equals(bbsId, that.getBbsId()) &&
                Objects.equals(pstNo, that.getPstNo());
    }

    @Override
    public String toString() {
        return this.bbsId+"_"+this.pstNo;
    }
}
