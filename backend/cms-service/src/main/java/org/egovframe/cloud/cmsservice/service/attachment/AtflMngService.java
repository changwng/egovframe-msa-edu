package org.egovframe.cloud.cmsservice.service.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.cloud.cmsservice.utils.PortalUtils;
import org.egovframe.cloud.common.dto.RequestDto;
import org.egovframe.cloud.common.exception.BusinessMessageException;
import org.egovframe.cloud.common.service.AbstractService;
import org.egovframe.cloud.common.exception.EntityNotFoundException;
import org.egovframe.cloud.cmsservice.api.attachment.dto.*;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMng;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMngId;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMngRepository;
import org.egovframe.cloud.cmsservice.utils.StorageUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * org.egovframe.cloud.cmsservice.service.attachment.AtflMngService
 * <p>
 * 첨부파일 서비스 클래스
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
@Service
public class AtflMngService extends AbstractService {
    private static final String SUCCESS_MESSAGE = "Success";
    private static final String FILE_SEPARATOR = File.separator;
    private static final String EDITOR_FILE_SEPARATOR = "-";
    private static final String BASE_PATH = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
    private static final String EDITOR_PATH = "editor/"+BASE_PATH;

    private final AtflMngRepository atflMngRepository;
    private final StorageUtils storageUtils;

    /**
     * 첨부파일 업로드
     *
     * @param file 업로드할 파일
     * @return AtflMngFileResponseDto
     */
    public AtflMngFileResponseDto uploadFile(MultipartFile file) {
        return upload(file, BASE_PATH, true);
    }

    /**
     * 여러 첨부파일 업로드
     *
     * @param files 업로드할 파일 목록
     * @return List<AtflMngFileResponseDto>
     */
    public List<AtflMngFileResponseDto> uploadFiles(List<MultipartFile> files) {
        return files.stream()
                .map(file -> upload(file, BASE_PATH, true))
                .collect(Collectors.toList());
    }

    /**
     * 첨부파일 업로드
     * .temp 파일 생성
     * 추후 저장로직에서 rename
     *
     * @param file
     * @param basePath
     * @return
     */
    private AtflMngFileResponseDto upload(MultipartFile file, String basePath, boolean isTemp) {
        String storeFile = storageUtils.storeFile(file, basePath, isTemp);
        return AtflMngFileResponseDto.builder()
                .orgnlFileNm(file.getOriginalFilename())
                .physFileNm(StringUtils.cleanPath(basePath + FILE_SEPARATOR + storeFile))
                .message(SUCCESS_MESSAGE)
                .atflFlsz(file.getSize())
                .atflType(file.getContentType())
                .build();
    }

    /**
     * 에디터용 파일 업로드
     *
     * @param editorRequestDto Base64 인코딩된 파일 데이터
     * @return AtflMngEditorResponseDto
     */
    public AtflMngEditorResponseDto uploadEditor(AtflMngBase64RequestDto editorRequestDto) throws BusinessMessageException {
        String fileBase64 = editorRequestDto.getFileBase64();

        if (Objects.isNull(fileBase64) || "".equals(fileBase64)) {
            // 업로드할 파일이 없습니다.
            throw new BusinessMessageException(getMessage("valid.file.not_exists"));
        }

        if (fileBase64.length() > 400000) {
            //파일 용량이 너무 큽니다.
            throw new BusinessMessageException(getMessage("valid.file.too_big"));
        }
        String storeFile =  storageUtils.storeBase64File(editorRequestDto, EDITOR_PATH);

        return AtflMngEditorResponseDto.builder()
                .uploaded(1)
                .url(EDITOR_PATH.replaceAll(FILE_SEPARATOR, EDITOR_FILE_SEPARATOR) + EDITOR_FILE_SEPARATOR + storeFile)
                .originalFileName(editorRequestDto.getOriginalName())
                .size(editorRequestDto.getSize())
                .fileType(editorRequestDto.getFileType())
                .message(SUCCESS_MESSAGE)
                .build();
        /*try {
            String filename = editorRequestDto.getFilename();
            if (!StringUtils.hasText(filename)) {
                filename = RandomStringUtils.randomAlphanumeric(8) + ".png";
            }
            
            AtflMngFileResponseDto fileResponseDto = storageUtils.storeBase64(editorRequestDto.getUploadImage(),
                    filename, EDITOR_PATH, false);
            
            return AtflMngEditorResponseDto.success(fileResponseDto.getOrgnlFileNm(), 
                    "/api/v1/atfl/images/editor/" + fileResponseDto.getPhysFileNm());
        } catch (Exception e) {
            log.error("Failed to upload editor file", e);
            return AtflMngEditorResponseDto.fail(editorRequestDto.getFilename(), e.getMessage());
        }*/
    }

    /**
     * 이미지 로드
     *
     * @param imagename 이미지 파일명
     * @return AtflMngImageResponseDto
     */
    @Transactional(readOnly = true)
    public AtflMngImageResponseDto loadImage(String imagename) {
        if(FILE_SEPARATOR.equals("\\")) {//윈도우기반 자바시스템일 때 하이픈 character to be escaped is missing 에러방지
            imagename = imagename.replaceAll(EDITOR_FILE_SEPARATOR, "\\\\"); //getFileSystem().getPath에서 디스크의 경로를 사용할 때
        } else { //리눅스 또는 맥 기반 자바시스템 경로일 때(아래)
            imagename = imagename.replaceAll(EDITOR_FILE_SEPARATOR, FILE_SEPARATOR);
        }
        return storageUtils.loadImage(imagename);
    }
    /**
     * img 태그에서 호출 시 byte[] 형태의 값으로 incoding
     *
     * @param atflId
     * @return
     */
    @Transactional(readOnly = true)
    public AtflMngImageResponseDto loadImageByUniqueId(String atflId) throws EntityNotFoundException {
        AtflMng attachment = findAttachmentByUniqueId(atflId);

        return storageUtils.loadImage(attachment.getPhysFileNm());
    }
    /**
     * unique id 로 첨부파일 조회 : AtflId
     *
     * @param atflId
     * @return
     */
    private AtflMng findAttachmentByUniqueId(String atflId) {
        return atflMngRepository.findByAtflId(atflId)
                // 파일을 찾을 수 없습니다.
                .orElseThrow(() -> new EntityNotFoundException(getMessage("valid.file.not_found") + " ID= " + atflId));
    }

    /**
     * 첨부파일 임시 저장
     *
     * @param saveRequestDtoList 저장 요청 DTO
     * @return List<AtflMngResponseDto>
     */
    @Transactional
    public String saveTemp(List<AtflMngTempSaveRequestDto> saveRequestDtoList) {

        String attachmentCode = PortalUtils.randomAlphanumeric(20);
        for (int i = 0; i < saveRequestDtoList.size(); i++) {
            AtflMngTempSaveRequestDto requestDto = saveRequestDtoList.get(i);
            AtflMngId attachmentId = AtflMngId.builder()
                    .atflCd(attachmentCode)
                    .atflSn( i+1 )
                    .build();

            // 첨부파일 .temp 제거
            String renameTemp = ""; // storageUtils.moveFromTemp(requestDto.getPhysFileNm());
            atflMngRepository.save(requestDto.toEntity(attachmentId, renameTemp));
        }
        return attachmentCode;
        //

    }

    /**
     * 첨부파일 다운로드 - 삭제 파일 불가
     *
     * @param atflId
     * @return
     */
    @Transactional
    public AtflMngDownloadResponseDto downloadFile(String atflId) throws EntityNotFoundException, BusinessMessageException {
        AtflMng attachment = findAttachmentByUniqueId(atflId);

        if (attachment.isDeleted()) {
            throw new BusinessMessageException(getMessage("err.entity.not.found"));
        }

        Resource resource = storageUtils.downloadFile(attachment.getPhysFileNm());

        // 첨부파일 다운로드 할 때 마다 Download 횟수 + 1
        attachment.updateDownloadCnt();

        return AtflMngDownloadResponseDto.builder()
                .file(resource)
                .originalFileName(attachment.getOrgnlFileNm())
                .build();
    }
    /**
     * 첨부파일 다운로드
     *
     * @param atflId 첨부파일 ID
     * @return AtflMngDownloadResponseDto
     */
   /* @Transactional
    public AtflMngDownloadResponseDto download(String atflId) {
        AtflMng atflMng = atflMngRepository.findByAtflId(atflId)
                .orElseThrow(() -> new BusinessMessageException(getMessage("valid.file.not_found")));

        // 삭제된 파일 체크
        if ("Y".equals(atflMng.getDelYn())) {
            throw new BusinessMessageException(getMessage("valid.file.deleted"));
        }

        // 다운로드 횟수 증가
        atflMng.increaseDwnldNocs();

        // Resource로 파일 로드
        return null;
        *//*Resource resource = storageUtils.getDownloadFileAsResource(atflMng.getPhysFileNm());

        return AtflMngDownloadResponseDto.builder()
                .file(resource)
                .orgnlFileNm(atflMng.getOrgnlFileNm())
                .contentType(atflMng.getAtflType())
                .build();*//*
    }*/

    /**
     * 다운로드용 파일 조회
     *
     * @param physFileNm 물리 파일명
     * @return byte[]
     */
    public byte[] getDownloadFile(String physFileNm) {
        return null ; // storageUtils.getDownloadFile(physFileNm);
    }
    /**
     * 첨부파일 코드로 첨부파일 목록 조회
     *
     * @param atflCd
     * @return
     */
    @Transactional(readOnly = true)
    public List<AtflMngResponseDto> findByCode(String atflCd) {
        List<AtflMng> attachmentList = atflMngRepository.findByCode(atflCd);
        return attachmentList.stream()
                .map(atflMng -> AtflMngResponseDto.builder().atflMng(atflMng).build())
                .collect(Collectors.toList());
    }

    /**
     * 첨부파일 다운로드 - 삭제 파일 가능
     *
     * @param atflId
     * @return
     */
    @Transactional
    public AtflMngDownloadResponseDto downloadAttachment(String atflId) throws EntityNotFoundException {
        AtflMng atflMng = findAttachmentByUniqueId(atflId);

        Resource resource = storageUtils.downloadFile(atflMng.getPhysFileNm());

        // 첨부파일 다운로드 할 때 마다 Download 횟수 + 1
        atflMng.updateDownloadCnt();

        return AtflMngDownloadResponseDto.builder()
                .file(resource)
                .originalFileName(atflMng.getOrgnlFileNm())
                .build();
    }

    /**
     * 첨부파일 저장
     *
     * @param saveRequestDtoList
     * @return 생성된 첨부파일 코드
     */
    @Transactional
    public String save(List<AtflMngTempSaveRequestDto> saveRequestDtoList) {
        String attachmentCode = PortalUtils.randomAlphanumeric(20);
        for (int i = 0; i < saveRequestDtoList.size(); i++) {
            AtflMngTempSaveRequestDto requestDto = saveRequestDtoList.get(i);
            AtflMngId attachmentId = AtflMngId.builder()
                    .atflCd(attachmentCode)
                    .atflSn(i + 1)
                    .build();

            // 첨부파일 .temp 제거
            String renameTemp = storageUtils.renameTemp(requestDto.getPhysFileNm());

            atflMngRepository.save(requestDto.toEntity(attachmentId, renameTemp));
        }
        return attachmentCode;
    }

    /**
     * 첨부파일 저장
     * 이미 attachment code 가 있는 경우 seq만 새로 생성해서 저장
     * or
     * isUserDelete = true 인 경우 삭제 여부 Y
     *
     * @param attachmentCode
     * @param saveRequestDtoList
     * @return
     */
    @Transactional
    public String saveByCode(String attachmentCode, List<AtflMngTempSaveRequestDto> saveRequestDtoList) throws EntityNotFoundException {
        for (AtflMngTempSaveRequestDto saveRequestDto : saveRequestDtoList) {
            // 사용자 삭제인 경우 삭제여부 Y
            if ("Y".equals(saveRequestDto.getDelYn())) {
                AtflMng attachment = findAttachmentByUniqueId(saveRequestDto.getAtflId());
                attachment.updateIsDelYn(saveRequestDto.getDelYn());
                continue;
            }

            if (!saveRequestDto.hasAtflId()) {
                // 해당 attachment에 seq 조회해서 attachmentid 생성
                AtflMngId atflMngId = atflMngRepository.getId(attachmentCode);
                //새로운 첨부파일 저장 (물리적 파일 .temp 제거)
                String renameTemp = storageUtils.renameTemp(saveRequestDto.getPhysFileNm());

                atflMngRepository.save(saveRequestDto.toEntity(atflMngId, renameTemp));
            }
        }

        return attachmentCode;
    }



    /**
     * 관리자 - 전체 첨부파일 목록 조회
     *
     * @param requestDto 검색 조건
     * @param pageable 페이징 정보
     * @return Page<AtflMngResponseDto>
     */
    @Transactional(readOnly = true)
    public Page<AtflMngResponseDto> search(RequestDto requestDto, Pageable pageable) {
        return atflMngRepository.search(requestDto, pageable)
                .map(atflMng -> AtflMngResponseDto.builder().atflMng(atflMng).build());
               /* .map(entity -> AtflMngResponseDto.builder()
                        .atflCd(entity.getAtflMngId().getAtflCd())
                        .atflSn(entity.getAtflMngId().getAtflSn())
                        .atflId(entity.getAtflId())
                        .physFileNm(entity.getPhysFileNm())
                        .orgnlFileNm(entity.getOrgnlFileNm())
                        .atflFlsz(entity.getAtflFlsz())
                        .atflType(entity.getAtflType())
                        .dwnldNocs(entity.getDwnldNocs())
                        .delYn(entity.getDelYn())
                        .lnkgDmnId(entity.getLnkgDmnId())
                        .lnkgDmnNm(entity.getLnkgDmnNm())
                        .reg_dt(entity.getReg_dt())
                        .build());*/
    }

    /**
     * 관리자 - 삭제여부 토글
     *
     * @param aftflId
     * @param delYn
     * @return
     */
    @Transactional
    public String toggleDelete(String aftflId, String delYn) throws EntityNotFoundException {
        AtflMng atflMng = findAttachmentByUniqueId(aftflId);
        atflMng.updateIsDelYn(delYn);
        return aftflId;
    }
    /**
     * 첨부파일 삭제
     *
     * @param atflId 첨부파일 ID
     */
    @Transactional
    public void delete(String atflId) {
        AtflMng atflMng = atflMngRepository.findByAtflId(atflId)
                .orElseThrow(() -> new BusinessMessageException(getMessage("valid.file.not_found")));
        // 물리적 파일 삭제
        boolean deleted = storageUtils.deleteFile(atflMng.getPhysFileNm());
        if (!deleted) {
            throw new BusinessMessageException(getMessage("valid.file.not_deleted"));
        }
        atflMngRepository.delete(atflMng);
    }

    /**
     * 첨부파일 엔티티 정보 업데이트
     *
     * @param atflCd 첨부파일 코드
     * @param uploadRequestDto 업데이트 요청 DTO
     */
    @Transactional
    public String updateEntity(String atflCd, AtflMngUploadRequestDto  uploadRequestDto) {
     /*   AtflMng atflMng = atflMngRepository.findByAtflId(atflId)
                .orElseThrow(() -> new BusinessMessageException(getMessage("valid.file.not_found")));

        atflMng.updateEntity(requestDto.getLnkgDmnNm(), requestDto.getLnkgDmnId());*/
        List<AtflMng> attachments = atflMngRepository.findByCode(atflCd);
        for (AtflMng attachment : attachments) {
            attachment.updateEntity(uploadRequestDto.getLnkgDmnNm(), uploadRequestDto.getLnkgDmnId());
        }
        return atflCd;
    }

    /**
     * 첨부파일 업로드 및 저장
     *
     * @param files
     * @param uploadRequestDto
     */
    public String uploadAndSave(List<MultipartFile> files, AtflMngUploadRequestDto uploadRequestDto) {
        String atflCd = PortalUtils.randomAlphanumeric(20);

        for (int i = 0; i < files.size(); i++) {
            AtflMngId attachmentId = AtflMngId.builder()
                    .atflCd(atflCd)
                    .atflSn(i + 1)
                    .build();

            // 물리적 파일 생성
            AtflMngFileResponseDto fileResponseDto = upload(files.get(i), BASE_PATH, false);

            atflMngRepository.save(fileResponseDto.toEntity(attachmentId, uploadRequestDto));
        }

        return atflCd;
    }

    /**
     * 첨부파일 저장
     * 이미 attachment code 가 있는 경우 이므로 seq만 새로 생성해서 저장
     * or
     * isUserDelete = true 인 경우 삭제 여부 Y
     *
     * @param files
     * @param attachmentCode
     * @param uploadRequestDto
     * @param updateRequestDtoList
     * @return
     */
    @Transactional
    public String uploadAndUpdate(List<MultipartFile> files,
                                  String attachmentCode,
                                  AtflMngUploadRequestDto uploadRequestDto,
                                  List<AtflMngUpdateRequestDto> updateRequestDtoList) throws EntityNotFoundException {

        // 기존 파일 삭제 처리
        deleteExistingFile(updateRequestDtoList);

        if (Objects.nonNull(files)) {
            //새로운 파일 저장 처리
            for (int i = 0; i < files.size(); i++) {
                // 해당 attachment에 seq 조회해서 attachmentid 생성
                AtflMngId attachmentId = atflMngRepository.getId(attachmentCode);

                // 물리적 파일 생성
                AtflMngFileResponseDto fileResponseDto = upload(files.get(i), BASE_PATH, false);

                atflMngRepository.save(fileResponseDto.toEntity(attachmentId, uploadRequestDto));
            }
        }


        return attachmentCode;
    }

    /**
     * 기존 첨부파일 삭제 처리
     *
     * @param updateRequestDtoList
     */
    private void deleteExistingFile(List<AtflMngUpdateRequestDto> updateRequestDtoList) {
        if (Objects.isNull(updateRequestDtoList)) {
            return;
        }
        for (AtflMngUpdateRequestDto saveRequestDto : updateRequestDtoList) {
            if ( "Y".equals(saveRequestDto.getDelYn())) {
                AtflMng atflMng = findAttachmentByAtflId(saveRequestDto.getAtflId());
                atflMng.updateIsDelYn(saveRequestDto.getDelYn()); // 책임 위임의 원칙
            }
        }
    }

    /**
     * unique id(atflId) 로 첨부파일 조회
     *
     * @param atflId
     * @return
     */
    private AtflMng findAttachmentByAtflId(String atflId) {
        return atflMngRepository.findByAtflId(atflId)
                // 파일을 찾을 수 없습니다.
                .orElseThrow(() -> new EntityNotFoundException(getMessage("valid.file.not_found") + " ID= " + atflId));
    }

    /**
     * 첨부파일 저장 후 기능 저장 시 오류 날 경우
     * 조회되는 첨부파일 목록 전부 삭제
     * rollback
     *
     * @param attachmentCode
     */
    public void deleteAllEmptyEntity(String atflCd) throws EntityNotFoundException, BusinessMessageException {
        List<AtflMng> attachmentList = atflMngRepository.findByCode(atflCd);

        if (Objects.isNull(attachmentList) || attachmentList.size() <= 0) {
            throw new EntityNotFoundException(getMessage("valid.file.not_found") + " ID= " + atflCd);
        }

        for (AtflMng attachment: attachmentList) {
            // 첨부파일 저장 후 기능 저장 시 오류 날 경우에만 첨부파일 전체 삭제를 하므로
            // entity 정보가 있는 경우에는 삭제하지 못하도록 한다.
            if (attachment.haslnkgDmnId()) { // hasEntityId의 대체품
                throw new BusinessMessageException(getMessage("valid.file.not_deleted"));
            }
            deleteFile(attachment);
        }
    }

    /**
     * 첨부파일 삭제
     *
     * @param atflMng
     */
    private void deleteFile(AtflMng atflMng) {
        // 물리적 파일 삭제
        boolean deleted = storageUtils.deleteFile(atflMng.getPhysFileNm());
        if (!deleted) {
            throw new BusinessMessageException(getMessage("valid.file.not_deleted"));
        }
        atflMngRepository.delete(atflMng);
    }



}
