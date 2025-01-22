package org.egovframe.cloud.cmsservice.api.comment.dto;

import lombok.Getter;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmnt;

@Getter
public class PstCmntResponseDto {
    private Integer bbsId;
    private Long pstNo;
    private Long cmntNo;
    private String cmntCn;
    private String wrtrNm;
    private String delYn;

    public PstCmntResponseDto(PstCmnt entity) {
        this.bbsId = entity.getPstCmntId().getPstId().getBbsId();
        this.pstNo = entity.getPstCmntId().getPstId().getPstNo();
        this.cmntNo = entity.getPstCmntId().getCmntNo();
        this.cmntCn = entity.getCmntCn();
        this.wrtrNm = entity.getWrtrNm();
        this.delYn = entity.getDelYn();
    }
}
