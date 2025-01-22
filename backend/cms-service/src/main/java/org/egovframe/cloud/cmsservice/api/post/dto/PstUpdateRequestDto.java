package org.egovframe.cloud.cmsservice.api.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * PstUpdateRequestDto
 * <p>
 * 게시물 수정 요청 DTO 클래스
 *
 * @author USER
 * @version 1.0
 * @since 2025/01/22
 */
@Getter
@NoArgsConstructor
public class PstUpdateRequestDto {
    @NotBlank(message = "{posts.pstTtl} {err.required}")
    private String pstTtl;

    @NotBlank(message = "{posts.pstCn} {err.required}")
    private String pstCn;

    private String atflCd;

    @NotNull(message = "{posts.noticeAt} {err.required}")
    @Schema(description = "상단고정여부")
    private String upndFixYn;

    @Builder
    public PstUpdateRequestDto(String pstTtl, String pstCn, String atflCd, String upndFixYn) {
        this.pstTtl = pstTtl;
        this.pstCn = pstCn;
        this.atflCd = atflCd;
        this.upndFixYn = upndFixYn;
    }
    /**
     * 게시물 등록 요청 DTO 속성 값으로 게시물 엔티티 빌더를 사용하여 객체 생성
     *
     * @return Posts 게시물 엔티티
     */
    public Pst toEntity(Integer bbsId, Long pstNo) {
        return Pst.builder()
                .bbsMng(BbsMng.builder()
                        .bbsId(bbsId)
                        .build())
                .pstId(PstId.builder()
                        .bbsId(bbsId)
                        .pstNo(pstNo)
                        .build())
                .pstTtl(pstTtl)
                .pstCn(pstCn)
                .upndFixYn(upndFixYn)
                .atflCd(atflCd)
                .build();
    }


}
