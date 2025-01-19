package org.egovframe.cloud.cmsservice.api.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmnt;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmntId;
import org.egovframe.cloud.cmsservice.domain.post.Pst;

@Getter
@NoArgsConstructor
public class PstCmntSaveRequestDto {
    private String cmntCn;
    private String wrtrNm;
    private String cmntPswd;

    @Builder
    public PstCmntSaveRequestDto(String cmntCn, String wrtrNm, String cmntPswd) {
        this.cmntCn = cmntCn;
        this.wrtrNm = wrtrNm;
        this.cmntPswd = cmntPswd;
    }

    public PstCmnt toEntity(PstCmntId pstCmntId, Pst pst) {
        return PstCmnt.builder()
                .pstCmntId(pstCmntId)
                .cmntCn(cmntCn)
                .wrtrNm(wrtrNm)
                .cmntPswd(cmntPswd)
                .delYn("N")
                .pst(pst)
                .build();
    }
}
