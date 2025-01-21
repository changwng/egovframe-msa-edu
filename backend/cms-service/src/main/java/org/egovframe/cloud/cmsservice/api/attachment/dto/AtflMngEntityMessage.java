package org.egovframe.cloud.cmsservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngEntityMessage
 * <p>
 * 첨부파일 엔티티 메시지 DTO 클래스
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
 *  2024/01/20    changwng     최초 생성
 * </pre>
 */
@Getter
@NoArgsConstructor
@ToString
@Schema(description = "첨부파일 엔티티 메시지 DTO")
public class AtflMngEntityMessage {

    @Schema(description = "첨부파일 ID")
    private String atflId;
    
    @Schema(description = "엔티티 이름")
    private String entityName;
    
    @Schema(description = "엔티티 ID")
    private String entityId;

    @Builder
    public AtflMngEntityMessage(String atflId, String entityName, String entityId) {
        this.atflId = atflId;
        this.entityName = entityName;
        this.entityId = entityId;
    }
}
