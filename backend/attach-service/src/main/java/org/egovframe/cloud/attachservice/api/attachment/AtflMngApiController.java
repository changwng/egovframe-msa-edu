package org.egovframe.cloud.attachservice.api.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.cloud.attachservice.api.attachment.dto.*;
import org.egovframe.cloud.common.dto.RequestDto;
import org.egovframe.cloud.common.exception.BusinessMessageException;
import org.egovframe.cloud.attachservice.api.attachment.dto.*;
import org.egovframe.cloud.attachservice.service.attachment.AtflMngService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * org.egovframe.cloud.attachservice.api.attachment.AtflMngApiController
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
@RequestMapping("/api/v1/atfl")
public class AtflMngApiController {

    private final AtflMngService atflMngService;

    /**
     * 첨부파일 업로드 - 단건
     * 물리적 파일에 대해 업로드만 진행 (.temp)
     * 추후 저장 필요
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public AtflMngFileResponseDto upload(@RequestParam("file") MultipartFile file) {
        return atflMngService.uploadFile(file);
    }

    /**
     * 첨부파일 업로드 - 여러 건
     * 물리적 파일에 대해 업로드만 진행 (.temp)
     * 추후 저장 필요
     *
     * @param files
     * @return
     */
    @PostMapping(value = "/upload/multi")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AtflMngFileResponseDto> uploadMulti(@RequestParam("files") List<MultipartFile> files) {
        return atflMngService.uploadFiles(files);
    }

    /**
     * 에디터에서 파일 업로드
     * 현재 이미지만 적용
     *
     * @param editorRequestDto
     * @return
     */
    @PostMapping(value = "/upload/editor")
    @ResponseStatus(HttpStatus.CREATED)
    public AtflMngEditorResponseDto uploadEditor(@RequestBody AtflMngBase64RequestDto editorRequestDto) {
        return atflMngService.uploadEditor(editorRequestDto);
    }

    /**
     * 에디터에서 파일 경로(명) 이미지 load
     *
     * @param imagename
     * @return
     */
    @GetMapping(value = "/images/editor/{imagename}")
    public ResponseEntity<byte[]> loadImages(@PathVariable("imagename") String imagename) {
        AtflMngImageResponseDto image = atflMngService.loadImage(imagename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getMimeType()))
                .body(image.getData());
    }

    /**
     * unique id로 이미지 태그에서 이미지 load
     *
     * @param atflId
     * @return
     */
    @GetMapping(value = "/images/{atflId}")
    public ResponseEntity<byte[]> loadImagesByUniqueId(@PathVariable String atflId) {
        AtflMngImageResponseDto image = atflMngService.loadImageByUniqueId(atflId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getMimeType()))
                .body(image.getData());
    }

    /**
     * 첨부파일 다운로드
     *
     * @param atflId
     * @return
     */
    @GetMapping(value = "/download/{atflId}")
    public ResponseEntity<?> downloadFile(@PathVariable String atflId) {
        AtflMngDownloadResponseDto downloadFile = atflMngService.downloadFile(atflId);

        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(downloadFile.getOriginalFileName(), StandardCharsets.UTF_8)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(downloadFile.getFile());
        /*
          AttachmentDownloadResponseDto downloadFile = attachmentService.downloadFile(uniqueId);

        String mimeType = null;
        try {
            // get mime type
            URLConnection connection = new URL(downloadFile.getFile().getURL().toString()).openConnection();
            mimeType = connection.getContentType();
        } catch (IOException ex) {
            log.error("download fail", ex);
            throw new BusinessMessageException("Sorry. download fail... \uD83D\uDE3F");
        }

        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(downloadFile.getOriginalFileName(), StandardCharsets.UTF_8)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(downloadFile.getFile());
         */
    }

    /**
     * 첨부파일 코드로 첨부파일 목록 조회
     *
     * @param atflCd
     * @return
     */
    @GetMapping(value = "/attachments/{atflCd}")
    public List<AtflMngResponseDto> findByCode(@PathVariable String atflCd) {
        return atflMngService.findByCode(atflCd);
    }

    /**
     * 첨부파일 다운로드
     *
     * @param atflId
     * @return
     */
    @GetMapping(value = "/attachments/download/{atflId}")
    public ResponseEntity<?> downloadAttachment(@PathVariable String atflId) {
        AtflMngDownloadResponseDto downloadFile = atflMngService.downloadAttachment(atflId);

        String mimeType = null;
        try {
            // get mime type
            URLConnection connection = new URL(downloadFile.getFile().getURL().toString()).openConnection();
            mimeType = connection.getContentType();
        } catch (IOException ex) {
            log.error("download fail", ex);
            throw new BusinessMessageException("Sorry. download fail... \uD83D\uDE3F");
        }

        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(downloadFile.getOriginalFileName(), StandardCharsets.UTF_8)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);
        headers.setContentDisposition(contentDisposition);

        return ResponseEntity.ok()
                .headers(headers)
                .body(downloadFile.getFile());
    }

    /**
     * 첨부파일 저장 - 물리적 파일은 .temp로 저장 된 후 호출되어야 함
     * 새롭게 attachment code를 생성해야 하는 경우
     *
     * @param saveRequestDtoList
     * @return 새로 생성한 첨부파일 code
     */
    @PostMapping(value = "/attachments/file")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody List<AtflMngTempSaveRequestDto> saveRequestDtoList) {
        return atflMngService.save(saveRequestDtoList);
    }

    /**
     * 첨부파일 저장 - 물리적 파일은 .temp로 저장 된 후 호출되어야 함
     * 이미 attachment code 가 있는 경우 seq만 새로 생성해서 저장
     * or
     * isDelete = true 인 경우 삭제 여부 Y
     *
     * @param saveRequestDtoList
     * @return
     */
    @PutMapping(value = "/attachments/file/{atflCd}")
    public String saveByCode(@PathVariable String atflCd, @RequestBody List<AtflMngTempSaveRequestDto> saveRequestDtoList) {
        return atflMngService.saveByCode(atflCd, saveRequestDtoList);
    }

    /**
     * 관리자 - 전체 첨부파일 목록 조회
     *
     * @param searchRequestDto
     * @param pageable
     * @return
     */
    @GetMapping(value = "/attachments")
    public Page<AtflMngResponseDto> search(RequestDto searchRequestDto, Pageable pageable) {
        return atflMngService.search(searchRequestDto, pageable);
    }

    /**
     * 관리자 - 삭제여부 토글
     *
     * @param atflId
     * @param delYn
     * @return
     */
    @PutMapping(value = "/attachments/{atflId}/{delYn}")
    public String toggleDelete(@PathVariable String atflId, @PathVariable String delYn) {
        return atflMngService.toggleDelete(atflId, delYn);
    }

    /**
     * 관리자 - 하나의 파일 삭제
     * 물리적 파일 삭제
     *
     * @param atflId
     */
    @DeleteMapping(value = "/attachments/{atflId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String atflId) {
        atflMngService.delete(atflId);
    }

    /**
     * 첨부파일 저장
     * 새롭게 attachment code를 생성해야 하는 경우
     *
     * @param files
     * @param uploadRequestDto
     * @return
     */
    @PostMapping(value = "/attachments/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAndSave(@RequestPart(value = "files", required = true) List<MultipartFile> files,
                              @RequestPart(value = "info", required = false) AtflMngUploadRequestDto uploadRequestDto) {
        return atflMngService.uploadAndSave(files, uploadRequestDto);
    }

    /**
     * 첨부파일 저장
     * 이미 attachment code 가 있는 경우 이므로 seq만 새로 생성해서 저장
     * or
     * isDelete = true 인 경우 삭제 여부 Y
     *
     * @param files
     * @param atflCd
     * @param uploadRequestDto
     * @param updateRequestDtoList
     * @return
     */
    @PutMapping(value = "/attachments/upload/{atflCd}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String uploadAndUpdate(@PathVariable String atflCd,
                                @RequestPart(value = "files", required = true) List<MultipartFile> files,
                                @RequestPart(value = "info", required = true) AtflMngUploadRequestDto uploadRequestDto,
                                @RequestPart(value = "list", required = false) List<AtflMngUpdateRequestDto> updateRequestDtoList) {
        return atflMngService.uploadAndUpdate(files, atflCd, uploadRequestDto, updateRequestDtoList);
    }

    /**
     * 첨부파일 저장 - 업로드 없이 기존 파일만 삭제
     * isDelete = true 인 경우 삭제 여부 Y
     *
     * @param atflCd
     * @param uploadRequestDto
     * @param updateRequestDtoList
     * @return
     */
    @PutMapping(value = "/attachments/{atflCd}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String update(@PathVariable String atflCd,
                        @RequestPart(value = "info") AtflMngUploadRequestDto uploadRequestDto,
                        @RequestPart(value = "list") List<AtflMngUpdateRequestDto> updateRequestDtoList) {
        return atflMngService.uploadAndUpdate(null, atflCd,
                uploadRequestDto, updateRequestDtoList);
    }

    /**
     * attachmentCode로 해당하는 모든 첨부파일의 entity 정보 업데이트
     * 신규 entity의 경우 entity가 저장 된 후 entity id가 생성되므로
     * entity 저장 후 해당 api 호출하여 entity 정보를 업데이트 해준다.
     *
     * @param atflCd
     * @param uploadRequestDto
     * @return
     */
    @PutMapping("/attachments/{atflCd}/info")
    public String updateEntity(@PathVariable String atflCd,
                             @RequestBody AtflMngUploadRequestDto uploadRequestDto) {
        return atflMngService.updateEntity(atflCd, uploadRequestDto);
    }

    /**
     * 첨부파일 저장 후 기능 저장 시 오류 날 경우
     * 해당 첨부파일 코드에 조회되는 첨부파일 목록 전부 삭제
     * rollback
     *
     * @param atflCd
     */
    @DeleteMapping("/attachments/{atflCd}/children")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllEmptyEntity(@PathVariable String atflCd) {
        atflMngService.deleteAllEmptyEntity(atflCd);
    }
}
