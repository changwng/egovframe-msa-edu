package org.egovframe.cloud.cmsservice.api.attachment.dto;

import lombok.Getter;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMng;

@Getter
public class AtflMngResponseDto {
    private String atflCd;
    private Integer atflSn;
    private Long dwnldNocs;
    private String lnkgDmnId;
    private String lnkgDmnNm;
    private String atflType;
    private String orgnlFileNm;
    private String physFileNm;
    private Long atflFlsz;
    private String atflId;
    private String delYn;

    public AtflMngResponseDto(AtflMng entity) {
        this.atflCd = entity.getAtflCd();
        this.atflSn = entity.getAtflSn();
        this.dwnldNocs = entity.getDwnldNocs();
        this.lnkgDmnId = entity.getLnkgDmnId();
        this.lnkgDmnNm = entity.getLnkgDmnNm();
        this.atflType = entity.getAtflType();
        this.orgnlFileNm = entity.getOrgnlFileNm();
        this.physFileNm = entity.getPhysFileNm();
        this.atflFlsz = entity.getAtflFlsz();
        this.atflId = entity.getAtflId();
        this.delYn = entity.getDelYn();
    }
}
