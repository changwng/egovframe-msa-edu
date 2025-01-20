package org.egovframe.cloud.cmsservice.api.attachment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngImageResponseDto
 * <p>
 * 첨부파일 이미지 응답 DTO Class
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
public class AtflMngImageResponseDto {
    private String mimeType;
    private byte[] data;

    @Builder
    public AtflMngImageResponseDto(String mimeType, byte[] data) {
        this.mimeType = mimeType;
        this.data = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
    }
}
