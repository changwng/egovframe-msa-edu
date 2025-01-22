package org.egovframe.cloud.cmsservice.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.post.PstId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
    public class PstCmntId implements Serializable {

    /**
     * 게시물 복합키
     */
    private PstId pstId;

    @Column(name = "cmnt_no")
    private Long cmntNo;

    @Builder
    public PstCmntId(PstId pstId, Long cmntNo) {
        this.pstId  = pstId;
        this.cmntNo = cmntNo;
    }

}
