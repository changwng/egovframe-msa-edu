package org.egovframe.cloud.cmsservice.api.attachment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngUpdateRequestDto
 * <p>
 * 첨부파일 수정 저장 시 요청 dto class
 *
 * @author 표준프레임워크센터 changwng
 * @version 1.0
 * @since 2024/01/20
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2024/01/20    changwng  최초 생성
 * </pre>
 */
@Getter
@NoArgsConstructor
@ToString
public class AtflMngUpdateRequestDto {
    private String atflId;
    private String delYn;

    @Builder
    public AtflMngUpdateRequestDto(String atflId, String delYn) {
        this.atflId = atflId;
        this.delYn = delYn;
    }
}
