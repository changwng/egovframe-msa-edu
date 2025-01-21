package org.egovframe.cloud.cmsservice.api.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngEditorResponseDto
 * <p>
 * 첨부파일 에디터 응답 DTO Class
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
@Schema(description = "첨부파일 에디터 응답 DTO")
public class AtflMngEditorResponseDto extends AtflMngUploadResponseDto{
    @Schema(description = "업로드 상태 (1: 성공, 0: 실패)")
    private Integer uploaded;      // 업로드 상태 (1: 성공, 0: 실패)
    @Schema(description = "파일 URL")
    private String url;           // 파일 URL

    @Builder
    public AtflMngEditorResponseDto(String originalFileName, String message, String fileType, long size, int uploaded, String url) {
        this.originalFileName = originalFileName;
        this.message = message;
        this.fileType = fileType;
        this.size = size;
        this.uploaded = uploaded;
        this.url = url;
    }

  /*  public static AtflMngEditorResponseDto success(String fileName, String url) {
        return AtflMngEditorResponseDto.builder()
                .uploaded(1)
                .originalFileName(fileName)
                .url(url)
                .build();
    }*/

 /*   public static AtflMngEditorResponseDto fail(String fileName, String error) {
        return AtflMngEditorResponseDto.builder()
                .uploaded(0)
                .originalFileName(fileName)
                .error(error)
                .build();
    }*/
}
