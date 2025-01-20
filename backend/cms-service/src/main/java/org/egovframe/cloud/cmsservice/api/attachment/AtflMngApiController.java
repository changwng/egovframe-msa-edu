package org.egovframe.cloud.cmsservice.api.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.cloud.common.dto.RequestDto;
import org.egovframe.cloud.common.exception.BusinessMessageException;
import org.egovframe.cloud.cmsservice.api.attachment.dto.*;
import org.egovframe.cloud.cmsservice.service.attachment.AtflMngService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
/**
 * org.egovframe.cloud.cmsservice.api.attachment.AtflMngApiController
 * <p>
 * 첨부파일 API 컨트롤러 클래스
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
@Slf4j
@RequiredArgsConstructor
@RestController
public class AtflMngApiController {

    private final AtflMngService atflMngService;

    /**
     * 첨부파일 업로드 - 단건
     * 물리적 파일에 대해 업로드만 진행 (.temp)
     * 추후 저장 필요
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/api/v1/atfl/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public AtflMngFileResponseDto upload(@RequestParam("file") MultipartFile file) {
        return atflMngService.uploadFile(file);
    }

   /* *//**
     * 첨부파일 업로드 - 여러 건
     * 물리적 파일에 대해 업로드만 진행 (.temp)
     * 추후 저장 필요
     *
     * @param files
     * @return
     *//*
    @PostMapping(value = "/api/v1/atfl/upload/multi")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AtflMngFileResponseDto> uploadMulti(@RequestParam("files") List<MultipartFile> files) {
        return atflMngService.uploadFiles(files);
    }

    *//**
     * 에디터에서 파일 업로드
     * 현재 이미지만 적용
     *
     * @param editorRequestDto
     * @return
     *//*
    @PostMapping(value = "/api/v1/atfl/upload/editor")
    @ResponseStatus(HttpStatus.CREATED)
    public AtflMngEditorResponseDto uploadEditor(@RequestBody AtflMngBase64RequestDto editorRequestDto) {
        return atflMngService.uploadEditor(editorRequestDto);
    }

    *//**
     * 에디터에서 파일 경로(명) 이미지 load
     *
     * @param imagename
     * @return
     * @throws IOException
     *//*
    @GetMapping(value = "/api/v1/atfl/images/editor/{imagename}")
    public ResponseEntity<byte[]> loadImages(@PathVariable("imagename") String imagename) {
        AtflMngImageResponseDto image = atflMngService.loadImage(imagename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getImageContent());
    }

    *//**
     * 첨부파일 임시 저장 처리
     * 첨부파일 저장 - 물리적 파일은 .temp로 저장 된 후 호출되어야 함
     * 새롭게 attachment code를 생성해야 하는 경우
     * @param saveRequestDtoList
     * @return
     *//*
    @PostMapping("/api/v1/atfl/file")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody List<AtflMngTempSaveRequestDto> saveRequestDtoList ) throws BusinessMessageException, IOException { //AtflMngTempSaveRequestDto saveRequestDto) {
        return atflMngService.saveTemp(saveRequestDtoList);
    }

    *//**
     * 첨부파일 다운로드
     *
     * @param atflId 첨부파일 ID
     * @return ResponseEntity<Resource>
     *//*
    @GetMapping("/api/v1/atfl/download/{atflId}")
    public ResponseEntity<Resource> download(@PathVariable String atflId) {
        AtflMngDownloadResponseDto responseDto = atflMngService.download(atflId);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(responseDto.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.builder("attachment")
                                .filename(responseDto.getOrgnlFileNm(), StandardCharsets.UTF_8)
                                .build().toString())
                .body(responseDto.getFile());
    }

    *//**
     * 첨부파일 페이징 목록 조회
     *
     * @param requestDto
     * @param pageable
     * @return
     *//*
    @GetMapping("/api/v1/atfl/attachments")
    public Page<AtflMngResponseDto> search(RequestDto requestDto, Pageable pageable) {
        return atflMngService.search(requestDto, pageable);
    }

    *//**
     * 첨부파일 삭제
     *
     * @param atflId
     *//*
    @DeleteMapping("/api/v1/atfl/attachments/{atflId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String atflId) {
        atflMngService.delete(atflId);
    }*/
}
