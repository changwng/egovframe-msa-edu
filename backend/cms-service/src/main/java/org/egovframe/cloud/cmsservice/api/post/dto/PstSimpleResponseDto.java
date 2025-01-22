package org.egovframe.cloud.cmsservice.api.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngResponseDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
/**
 * PstSimpleResponseDto
 * <p>
 * 게시물 응답 DTO 클래스
 *
 * @author USER
 * @version 1.0
 * @since 2025/01/22
 */
@Getter
@NoArgsConstructor
public class PstSimpleResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 게시판 ID
     */
    @Schema(description = "게시판 ID")
    private Integer bbsId;

    /**
     * 게시물 번호
     */
    @Schema(description = "게시물 번호")
    private Long pstNo;
    /**
     * 게시물 제목
     */
    @Schema(description = "게시물 제목")
    private String pstTtl;

    /**
     * 게시물 내용
     */
    @Schema(description = "게시물 내용")
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
    /*
     * 신규 여부
     */
    private Boolean isNew;

    @QueryProjection
    public PstSimpleResponseDto(Integer bbsId, Long pstNo, String pstTtl, String pstCn, LocalDateTime regDt) {
        this.bbsId = bbsId;
        this.pstNo = pstNo;
        this.pstTtl = pstTtl;
        this.pstCn = pstCn;
        this.regDt = regDt;
    }

    /**
     * 신규 여부 계산
     *
     * @param boardResponseDto 게시판 상세 응답 DTO
     * @return PostsSimpleResponseDto 게시물 응답 DTO
     */
    public PstSimpleResponseDto setIsNew(BbsMngResponseDto boardResponseDto) {
       // if (boardResponseDto.getNewDisplayDayCount() != null) {
         /*   int compareTo = regDt.toLocalDate().compareTo(LocalDate.now());
            this.isNew = 0 <= compareTo && compareTo <= boardResponseDto.getNewDisplayDayCount();*/

       // }
        /*    this.isNew = false;
        }*/
        this.isNew = regDt.plusDays(2).compareTo(LocalDateTime.now()) >= 0;
        return this;
    }
}
