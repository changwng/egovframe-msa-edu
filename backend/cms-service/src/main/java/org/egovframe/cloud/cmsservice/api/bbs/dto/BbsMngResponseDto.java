package org.egovframe.cloud.cmsservice.api.bbs.dto;

import lombok.Getter;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;

@Getter
public class BbsMngResponseDto {
    private String bbsId;
    private String bbsTypeCd;
    private String bbsNm;
    private String bbsExpln;
    private String refUseYn;
    private String atflUseYn;
    private Integer atflMaxCnt;
    private Long atflMaxFlsz;
    private String ansUseYn;
    private String cmntUseYn;
    private String dgstfnEvlUseYn;
    private String smsUseYn;
    private String upndFixUseYn;
    private String pstgPrdUseYn;
    private String popupUseYn;

    public BbsMngResponseDto(BbsMng entity) {
        this.bbsId = entity.getBbsId();
        this.bbsTypeCd = entity.getBbsTypeCd();
        this.bbsNm = entity.getBbsNm();
        this.bbsExpln = entity.getBbsExpln();
        this.refUseYn = entity.getRefUseYn();
        this.atflUseYn = entity.getAtflUseYn();
        this.atflMaxCnt = entity.getAtflMaxCnt();
        this.atflMaxFlsz = entity.getAtflMaxFlsz();
        this.ansUseYn = entity.getAnsUseYn();
        this.cmntUseYn = entity.getCmntUseYn();
        this.dgstfnEvlUseYn = entity.getDgstfnEvlUseYn();
        this.smsUseYn = entity.getSmsUseYn();
        this.upndFixUseYn = entity.getUpndFixUseYn();
        this.pstgPrdUseYn = entity.getPstgPrdUseYn();
        this.popupUseYn = entity.getPopupUseYn();
    }
}
