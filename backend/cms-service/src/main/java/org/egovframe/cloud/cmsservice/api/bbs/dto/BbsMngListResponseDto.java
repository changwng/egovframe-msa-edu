package org.egovframe.cloud.cmsservice.api.bbs.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * org.egovframe.cloud.boardservice.api.board.dto.BbsMngListResponseDto
 * <p>
 * 게시판 목록 응답 DTO 클래스
 *
 * @author 표준프레임워크센터 jooho
 * @version 1.0
 * @since 2021/07/26
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2021/07/26    jooho       최초 생성
 * </pre>
 */
@Getter
@NoArgsConstructor
@Schema(description = "게시판 목록 응답 DTO 클래스")
public class BbsMngListResponseDto implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 115181553377528728L;
 

        /**
         *  게시판 ID
         */
        @Schema(description = "게시판 ID")
        private Integer bbsId;
        /**
         *  게시판 유형 코드
         */
        @Schema(description = "게시판 유형 코드")
        private String bbsTypeCd;
        /**
         *  게시판 명
         */
        @Schema(description = "게시판 명")
        private String bbsNm;
        /**
         *  게시판 설명
         */
        @Schema(description = "게시판 설명")
        private String bbsExpln;
        /**
         * 첨부파일 최대 개수
         */
        @Schema(description = "첨부파일 최대 개수")
        private Integer atflMaxCnt;
        /**
         * 첨부파일 최대 파일 크기
         */
        @Schema(description = "첨부파일 최대 파일 크기")
        private Long atflMaxFlsz;

        /**
         * 생성 일시
         */
        @Schema(description = "생성 일시")
        private LocalDateTime regDt;
        /**
         * 생성자
         */
        @Schema(description = "생성자")
        private String rgtr;


    /**
     * 게시판 목록 응답 DTO 생성자
     *
     * @param bbsId          게시판 번호
     * @param bbsTypeCd        게시판 명
     * @param bbsNm     스킨 유형 코드
     */
    @QueryProjection
    public BbsMngListResponseDto(Integer bbsId, String bbsTypeCd, String bbsNm, String bbsExpln) {
        this.bbsId = bbsId;
        this.bbsTypeCd = bbsTypeCd;
        this.bbsNm = bbsNm;
        this.bbsExpln = bbsExpln;
    }

    /**
     * 게시판 목록 응답 DTO 생성자
     *
     * @param bbsId          게시판 번호
     * @param bbsNm        게시판 명
     * @param bbsExpln      게시판 설명
     * @param atflMaxCnt 스킨 유형 코드 명
     * @param atflMaxFlsz      생성 일시
     * @param regDt      생성 일시
     * @param rgtr      생성자
     */
    @QueryProjection
    public BbsMngListResponseDto(Integer bbsId, String bbsTypeCd, String bbsNm, String bbsExpln, Integer atflMaxCnt
                                  , Long atflMaxFlsz , LocalDateTime regDt, String rgtr) {
        this.bbsId = bbsId;
        this.bbsTypeCd = bbsTypeCd;
        this.bbsNm = bbsNm;
        this.bbsExpln = bbsExpln;
        this.atflMaxCnt = atflMaxCnt;
        this.atflMaxFlsz = atflMaxFlsz;
        this.regDt = regDt;
        this.rgtr = rgtr;
    }

}
