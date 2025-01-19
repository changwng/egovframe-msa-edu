package org.egovframe.cloud.cmsservice.api.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;

@Getter
@NoArgsConstructor
public class PstSaveRequestDto {
    private String pstTtl;
    private String pstCn;
    private String refUrl;
    private String popupYn;
    private String upndFixYn;
    private String prdPstgYn;
    private Long upPstNo;
    private Integer ansLoc;
    private String pstPswd;
    private String wrtrNm;
    private String wrtrCplc;
    private String atflCd;

    @Builder
    public PstSaveRequestDto(String pstTtl, String pstCn, String refUrl,
                            String popupYn, String upndFixYn, String prdPstgYn,
                            Long upPstNo, Integer ansLoc, String pstPswd,
                            String wrtrNm, String wrtrCplc, String atflCd) {
        this.pstTtl = pstTtl;
        this.pstCn = pstCn;
        this.refUrl = refUrl;
        this.popupYn = popupYn;
        this.upndFixYn = upndFixYn;
        this.prdPstgYn = prdPstgYn;
        this.upPstNo = upPstNo;
        this.ansLoc = ansLoc;
        this.pstPswd = pstPswd;
        this.wrtrNm = wrtrNm;
        this.wrtrCplc = wrtrCplc;
        this.atflCd = atflCd;
    }

    public Pst toEntity(BbsMng bbsMng, PstId pstId, Long pstSeqNo, Long pstSortSeq) {
        return Pst.builder()
                .pstId(pstId)
                .bbsMng(bbsMng)
                .pstTtl(pstTtl)
                .pstCn(pstCn)
                .inqNocs(0L)
                .refUrl(refUrl)
                .popupYn(popupYn)
                .upndFixYn(upndFixYn)
                .prdPstgYn(prdPstgYn)
                .pstSeqNo(pstSeqNo)
                .upPstNo(upPstNo)
                .ansLoc(ansLoc)
                .pstSortSeq(pstSortSeq)
                .pstPswd(pstPswd)
                .delYn("N")
                .wrtrNm(wrtrNm)
                .wrtrCplc(wrtrCplc)
                .atflCd(atflCd)
                .build();
    }
}
