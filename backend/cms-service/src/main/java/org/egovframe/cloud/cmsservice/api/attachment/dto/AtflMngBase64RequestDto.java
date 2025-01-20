package org.egovframe.cloud.cmsservice.api.attachment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngBase64RequestDto
 * <p>
 * 첨부파일 Base64 요청 DTO Class
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
public class AtflMngBase64RequestDto {
    private String fieldName;
    private String fileType;
    private String fileBase64;
    private String originalName;
    private Long size;

    @Builder
    public AtflMngBase64RequestDto(String fieldName, String fileType, String fileBase64, String originalName, Long size) {
        this.fieldName = fieldName;
        this.fileType = fileType;
        this.fileBase64 = fileBase64;
        this.originalName = originalName;
        this.size = size;
    }
}
