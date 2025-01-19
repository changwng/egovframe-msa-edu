package org.egovframe.cloud.cmsservice.api.post.dto;

import lombok.Getter;
import org.egovframe.cloud.cmsservice.domain.post.Pst;

@Getter
public class PstResponseDto {
    private String bbsId;
    private Long pstNo;
    private String pstTtl;
    private String pstCn;
    private Long inqNocs;
    private String refUrl;
    private String popupYn;
    private String upndFixYn;
    private String prdPstgYn;
    private Long pstSeqNo;
    private Long upPstNo;
    private Integer ansLoc;
    private Long pstSortSeq;
    private String delYn;
    private String delRsn;
    private String wrtrNm;
    private String wrtrCplc;
    private String atflCd;

    public PstResponseDto(Pst entity) {
        this.bbsId = entity.getPstId().getBbsId();
        this.pstNo = entity.getPstId().getPstNo();
        this.pstTtl = entity.getPstTtl();
        this.pstCn = entity.getPstCn();
        this.inqNocs = entity.getInqNocs();
        this.refUrl = entity.getRefUrl();
        this.popupYn = entity.getPopupYn();
        this.upndFixYn = entity.getUpndFixYn();
        this.prdPstgYn = entity.getPrdPstgYn();
        this.pstSeqNo = entity.getPstSeqNo();
        this.upPstNo = entity.getUpPstNo();
        this.ansLoc = entity.getAnsLoc();
        this.pstSortSeq = entity.getPstSortSeq();
        this.delYn = entity.getDelYn();
        this.delRsn = entity.getDelRsn();
        this.wrtrNm = entity.getWrtrNm();
        this.wrtrCplc = entity.getWrtrCplc();
        this.atflCd = entity.getAtflCd();
    }
}
