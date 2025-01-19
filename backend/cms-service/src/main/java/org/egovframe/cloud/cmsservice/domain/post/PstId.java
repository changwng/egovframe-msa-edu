package org.egovframe.cloud.cmsservice.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PstId implements Serializable {

    @Column(name = "bbs_id", length = 10)
    private String bbsId;

    @Column(name = "pst_no")
    private Long pstNo;
}
