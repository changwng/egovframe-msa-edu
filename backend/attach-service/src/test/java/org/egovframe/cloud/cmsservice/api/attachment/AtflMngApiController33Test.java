/*
package org.egovframe.cloud.attachservice.api.attachment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egovframe.cloud.attachservice.api.attachment.dto.*;
import org.egovframe.cloud.attachservice.service.attachment.AtflMngService;
import org.egovframe.cloud.common.dto.RequestDto;
import org.egovframe.cloud.common.exception.BusinessMessageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

*/
/**
 * org.egovframe.cloud.attachservice.api.attachment.AtflMngApiControllerTest
 * <p>
 * 첨부파일 API 컨트롤러 테스트 클래스
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
 *//*

@ExtendWith(MockitoExtension.class)
class AtflMngApiControllerTest {

    @InjectMocks
    private AtflMngApiController atflMngApiController;

    @Mock
    private AtflMngService atflMngService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(atflMngApiController)
                .build();
        objectMapper = new ObjectMapper();
    }

    private MockMultipartFile getTestFile() throws IOException {
        Path path = Paths.get("src/test/resources/testfiles/test.txt");
        String name = "file";
        String originalFileName = "test.txt";
        String contentType = MediaType.TEXT_PLAIN_VALUE;
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    private MockMultipartFile getTestImage() throws IOException {
        Path path = Paths.get("src/test/resources/testfiles/test.jpg");
        String name = "file";
        String originalFileName = "test.jpg";
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    private MockMultipartFile getTestPdf() throws IOException {
        Path path = Paths.get("src/test/resources/testfiles/test.pdf");
        String name = "file";
        String originalFileName = "test.pdf";
        String contentType = MediaType.APPLICATION_PDF_VALUE;
        byte[] content = Files.readAllBytes(path);
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    @Test
    @DisplayName("단일 파일 업로드 테스트")
    void uploadFile() throws Exception {
        // given
        MockMultipartFile file = getTestFile();

        AtflMngFileResponseDto responseDto = AtflMngFileResponseDto.builder()
                .orgnlFileNm("test.txt")
                .physFileNm("202501/test.txt")
                .atflFlsz(13L)
                .atflType(MediaType.TEXT_PLAIN_VALUE)
                .build();

        given(atflMngService.uploadFile(any(MultipartFile.class)))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(multipart("/api/v1/atfl/upload")
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orgnlFileNm").value("test.txt"))
                .andExpect(jsonPath("$.physFileNm").value("202501/test.txt"));
    }

    @Test
    @DisplayName("다중 파일 업로드 테스트")
    void uploadFiles() throws Exception {
        // given
        MockMultipartFile file1 = getTestFile();
        MockMultipartFile file2 = getTestFile();

        List<AtflMngFileResponseDto> responseDtos = Arrays.asList(
                AtflMngFileResponseDto.builder()
                        .orgnlFileNm("test1.txt")
                        .physFileNm("202501/test1.txt")
                        .build(),
                AtflMngFileResponseDto.builder()
                        .orgnlFileNm("test2.txt")
                        .physFileNm("202501/test2.txt")
                        .build()
        );

        given(atflMngService.uploadFiles(any()))
                .willReturn(responseDtos);

        // when & then
        mockMvc.perform(multipart("/api/v1/atfl/upload/multi")
                        .file(file1)
                        .file(file2))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].orgnlFileNm").value("test1.txt"))
                .andExpect(jsonPath("$[1].orgnlFileNm").value("test2.txt"));
    }

    @Test
    @DisplayName("에디터 파일 업로드 테스트")
    void uploadEditor() throws Exception {

        Resource testFile = new FileSystemResource(Paths.get("src/test/resources/testfiles/test.txt").toFile());
        // given
        String base64Image = Base64.getEncoder().encodeToString("test image".getBytes());
      */
/*  AtflMngBase64RequestDto requestDto = AtflMngBase64RequestDto.builder()
                .fileBase64(base64Image)
                .fileName("test.jpg")
                .contentType("image/jpeg")
                .build();
*//*

        AtflMngBase64RequestDto requestDto = AtflMngBase64RequestDto.builder()
                .fieldName("upload")
                .fileType("text")
                .fileBase64(base64Image)
                .originalName(testFile.getFilename())
                .size(testFile.getFile().length())
                .build();


        AtflMngEditorResponseDto responseDto = AtflMngEditorResponseDto.builder()
                .uploaded(1)
                .originalFileName("test.jpg")
                .url("/api/v1/atfl/images/editor/test.jpg")
                .build();

        given(atflMngService.uploadEditor(any(AtflMngBase64RequestDto.class)))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/v1/atfl/upload/editor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uploaded").value(1))
                .andExpect(jsonPath("$.fileName").value("test.jpg"));
    }

    @Test
    @DisplayName("에디터 이미지 로드 테스트")
    void loadImages() throws Exception {
        // given
        String imageName = "test.jpg";
        AtflMngImageResponseDto responseDto = AtflMngImageResponseDto.builder()
                .imageContent("test image".getBytes())
                .contentType(MediaType.IMAGE_JPEG_VALUE)
                .build();

        given(atflMngService.loadImage(imageName))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/atfl/images/editor/{imagename}", imageName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE));
    }



    @Test
    @DisplayName("파일 다운로드 테스트")
    void download() throws Exception {
        // given
        String atflId = "test-file-id";
        byte[] fileContent = "Test file content".getBytes();
        Resource resource = new ByteArrayResource(fileContent);

        AtflMngDownloadResponseDto responseDto = AtflMngDownloadResponseDto.builder()
                .file(resource)
                .orgnlFileNm("test.txt")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .build();

        given(atflMngService.download(atflId))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/atfl/download/{atflId}", atflId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", 
                        "attachment; filename=\"test.txt\""))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE));
    }

    @Test
    @DisplayName("첨부파일 페이징 목록 조회 테스트")
    void search() throws Exception {
        // given
        RequestDto requestDto = new RequestDto();
       */
/* requestDto.setKeywordType("orgnlFileNm");
        requestDto.setKeyword("test");*//*


        Pageable pageable = PageRequest.of(0, 10);
        List<AtflMngResponseDto> content = Arrays.asList(
                AtflMngResponseDto.builder()
                        .atflId("file1")
                        .orgnlFileNm("test1.txt")
                        .build(),
                AtflMngResponseDto.builder()
                        .atflId("file2")
                        .orgnlFileNm("test2.txt")
                        .build()
        );
        Page<AtflMngResponseDto> responsePage = new PageImpl<>(content, pageable, 2);

        given(atflMngService.search(any(RequestDto.class), any(Pageable.class)))
                .willReturn(responsePage);

        // when & then
        mockMvc.perform(get("/api/v1/atfl/attachments")
                        .param("keywordType", "orgnlFileNm")
                        .param("keyword", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].atflId").value("file1"))
                .andExpect(jsonPath("$.content[1].atflId").value("file2"))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @DisplayName("파일 다운로드 실패 테스트 - 파일 없음")
    void downloadNotFound() throws Exception {
        // given
        String atflId = "non-existing-file";
        given(atflMngService.download(atflId))
                .willThrow(new BusinessMessageException("valid.file.not_found"));

        // when & then
        mockMvc.perform(get("/api/v1/atfl/download/{atflId}", atflId))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("파일 삭제 테스트")
    void delete() throws Exception {
        // given
        String atflId = "test-file-id";

        // when & then
       */
/* mockMvc.perform(delete("/api/v1/atfl/attachments/{atflId}", atflId))
                .andExpect(status().isNoContent());*//*


        verify(atflMngService).delete(atflId);
    }
}
*/
