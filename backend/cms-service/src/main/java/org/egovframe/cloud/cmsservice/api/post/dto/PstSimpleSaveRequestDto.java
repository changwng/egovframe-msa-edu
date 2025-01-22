package org.egovframe.cloud.cmsservice.api.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * PstSimpleSaveRequestDto
 * <p>
 * 게시물 등록 요청 DTO 클래스
 *
 * @author USER
 * @version 1.0
 * @since 2025/01/22
 */
@Getter
@NoArgsConstructor
public class PstSimpleSaveRequestDto {


    /**
     * 게시물 제목
     */
    @Schema(description = "게시물 제목")
    @NotBlank(message = "{posts.pstTtl} {err.required}")
    private String pstTtl;

    /**
     * 게시물 내용
     */
    @Schema(description = "게시물 내용")
    @NotBlank(message = "{posts.pstCn} {err.required}")
    private String pstCn;

    /**
     * 첨부파일코드
     */
    @Schema(description = "첨부파일 코드 (UUID)")
    private String atflCd;
    /**
     * 등록일시
     */
    @Schema(description = "등록일자")
    private LocalDateTime regDt;

    /**
     * 신규 여부
     */
    @Schema(description = "신규여부")
    private Boolean isNew;


    @Builder
    public PstSimpleSaveRequestDto(String pstTtl, String pstCn, String atflCd , LocalDateTime regDt) {
        this.pstTtl = pstTtl;
        this.pstCn = pstCn;
        this.atflCd = atflCd;
    }
    /**
     * 게시물 등록 요청 DTO 속성 값으로 게시물 엔티티 빌더를 사용하여 객체 생성
     *
     * @param bbsId 게시판 번호
     * @param pstNo 게시물 번호
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
                .atflCd(atflCd)
                .build();
    }
}
