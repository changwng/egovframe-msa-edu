package org.egovframe.cloud.cmsservice.api.post.dto;

import lombok.Getter;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;

import javax.validation.constraints.NotBlank;

/**
 * PstDeleteRequestDto
 * <p>
 * 게시물 삭제 요청 DTO 클래스
 *
 * @author USER
 * @version 1.0
 * @since 2025/01/22
 */
@Getter
public class PstDeleteRequestDto {
    @NotBlank(message = "{board.board_no} {err.required}")
    private Integer bbsId;

    @NotBlank(message = "{posts.posts_no} {err.required}")
    private Long pstNo;

    public PstDeleteRequestDto(Integer bbsId, Long pstNo) {
        this.bbsId = bbsId;
        this.pstNo = pstNo;
    }

    /**
     * 게시물 삭제 요청 DTO 속성 값으로 게시물 엔티티 빌더를 사용하여 객체 생성
     *
     * @return Pst 게시물 엔티티
     */
    public Pst toEntity() {
        return Pst.builder()
                .pstId(PstId.builder()
                        .bbsId(bbsId)
                        .pstNo(pstNo)
                        .build())
                .bbsMng(BbsMng.builder()
                        .bbsId(bbsId)
                        .build())
                .build();
    }

}
