package org.egovframe.cloud.cmsservice.api.bbs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;

@Getter
@NoArgsConstructor
public class BbsMngSaveRequestDto {
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

    @Builder
    public BbsMngSaveRequestDto(String bbsTypeCd, String bbsNm, String bbsExpln,
                               String refUseYn, String atflUseYn, Integer atflMaxCnt,
                               Long atflMaxFlsz, String ansUseYn, String cmntUseYn,
                               String dgstfnEvlUseYn, String smsUseYn, String upndFixUseYn,
                               String pstgPrdUseYn, String popupUseYn) {
        this.bbsTypeCd = bbsTypeCd;
        this.bbsNm = bbsNm;
        this.bbsExpln = bbsExpln;
        this.refUseYn = refUseYn;
        this.atflUseYn = atflUseYn;
        this.atflMaxCnt = atflMaxCnt;
        this.atflMaxFlsz = atflMaxFlsz;
        this.ansUseYn = ansUseYn;
        this.cmntUseYn = cmntUseYn;
        this.dgstfnEvlUseYn = dgstfnEvlUseYn;
        this.smsUseYn = smsUseYn;
        this.upndFixUseYn = upndFixUseYn;
        this.pstgPrdUseYn = pstgPrdUseYn;
        this.popupUseYn = popupUseYn;
    }

    public BbsMng toEntity() {
        return BbsMng.builder()
                .bbsTypeCd(bbsTypeCd)
                .bbsNm(bbsNm)
                .bbsExpln(bbsExpln)
                .refUseYn(refUseYn)
                .atflUseYn(atflUseYn)
                .atflMaxCnt(atflMaxCnt)
                .atflMaxFlsz(atflMaxFlsz)
                .ansUseYn(ansUseYn)
                .cmntUseYn(cmntUseYn)
                .dgstfnEvlUseYn(dgstfnEvlUseYn)
                .smsUseYn(smsUseYn)
                .upndFixUseYn(upndFixUseYn)
                .pstgPrdUseYn(pstgPrdUseYn)
                .popupUseYn(popupUseYn)
                .build();
    }
}
