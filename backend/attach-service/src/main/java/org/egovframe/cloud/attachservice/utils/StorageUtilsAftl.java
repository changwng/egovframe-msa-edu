package org.egovframe.cloud.attachservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.egovframe.cloud.attachservice.api.attachment.dto.AtflMngFileResponseDto;
import org.egovframe.cloud.attachservice.api.attachment.dto.AtflMngImageResponseDto;
import org.egovframe.cloud.common.exception.BusinessMessageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import static org.egovframe.cloud.attachservice.utils.PortalUtils.getPhysicalFileName;

/**
 * org.egovframe.cloud.attachservice.utils.StorageUtilsAftl
 * <p>
 * 파일 저장소 유틸리티 클래스
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
@Component
public class StorageUtilsAftl {

    private static final String FILE_SEPARATOR = File.separator;
    private static final String TEMP_DIR = "temp";
    private static final String SUCCESS_MESSAGE = "Success";

    @Value("${app.file.storage.path}")
    private String rootPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(rootPath));
            Files.createDirectories(Paths.get(rootPath, TEMP_DIR));
        } catch (IOException e) {
            throw new BusinessMessageException("Could not initialize storage location");
        }
    }

    /**
     * 파일 저장
     *
     * @param file 업로드된 파일
     * @param basePath 기본 경로
     * @param isTemp 임시 파일 여부
     * @return 저장된 파일명
     */
    public String storeFile(MultipartFile file, String basePath, boolean isTemp) {
        try {
            if (file.isEmpty()) {
                throw new BusinessMessageException("Failed to store empty file");
            }

            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            if (filename.contains("..")) {
                throw new BusinessMessageException("Cannot store file with relative path outside current directory " + filename);
            }

            String storedFilename = generateStoredFilename(filename);
            Path targetLocation = getTargetLocation(basePath, storedFilename, isTemp);
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation);

            return storedFilename;
        } catch (IOException e) {
            throw new BusinessMessageException("Failed to store file");
        }
    }

    /**
     * Base64 인코딩된 파일 저장
     *
     * @param base64Data Base64 인코딩된 데이터
     * @param filename 파일명
     * @param basePath 기본 경로
     * @param isTemp 임시 파일 여부
     * @return AtflMngFileResponseDto
     */
    public AtflMngFileResponseDto storeBase64(String base64Data, String filename, String basePath, boolean isTemp) {
        try {
            String[] parts = base64Data.split(",");
            String imageData = parts.length > 1 ? parts[1] : parts[0];
            byte[] decodedBytes = Base64.getDecoder().decode(imageData);

            String storedFilename = generateStoredFilename(filename);
            Path targetLocation = getTargetLocation(basePath, storedFilename, isTemp);
            Files.createDirectories(targetLocation.getParent());
            Files.write(targetLocation, decodedBytes);

            return AtflMngFileResponseDto.builder()
                    .orgnlFileNm(filename)
                    .physFileNm(basePath + FILE_SEPARATOR + storedFilename)
                    .message(SUCCESS_MESSAGE)
                    .atflFlsz((long) decodedBytes.length)
                    .atflType(getContentType(filename))
                    .build();
        } catch (IOException e) {
            throw new BusinessMessageException("Failed to store base64 file");
        }
    }

    /**
     * 이미지 로드
     *
     * @param imagename 이미지 파일명
     * @return AtflMngImageResponseDto
     */
    public AtflMngImageResponseDto loadImage(String imagename) {
       // try {
            Path filePath = Paths.get(rootPath, imagename);
            if (!Files.exists(filePath)) {
                throw new BusinessMessageException("Image not found: " + imagename);
            }

            return AtflMngImageResponseDto.builder()
                  //  .imageContent(Files.readAllBytes(filePath))
                   // .contentType(getContentType(imagename))
                    .build();
        /*} catch (IOException e) {
            throw new BusinessMessageException("Failed to load image");
        }*/
    }

    /**
     * 다운로드용 파일 조회
     *
     * @param physFileNm 물리 파일명
     * @return byte[]
     */
    public byte[] getDownloadFile(String physFileNm) {
        try {
            Path filePath = Paths.get(rootPath, physFileNm);
            if (!Files.exists(filePath)) {
                throw new BusinessMessageException("File not found: " + physFileNm);
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new BusinessMessageException("Failed to load file");
        }
    }

    /**
     * 다운로드용 파일 조회 (Resource 타입)
     *
     * @param physFileNm 물리 파일명
     * @return Resource
     */
    public Resource getDownloadFileAsResource(String physFileNm) {
        try {
            Path filePath = Paths.get(rootPath, physFileNm);
            if (!Files.exists(filePath)) {
                throw new BusinessMessageException("File not found: " + physFileNm);
            }
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new BusinessMessageException("Failed to load file as resource");
        }
    }



    /**
     * 임시 파일을 실제 위치로 이동
     *
     * @param physFileNm 물리 파일명
     */
    public String moveFromTemp(String physFileNm) {
        try {
            Path source = Paths.get(rootPath, TEMP_DIR, physFileNm);
            Path target = Paths.get(rootPath, physFileNm);
            Files.createDirectories(target.getParent());
            Files.move(source, target);
            return target.getFileName().toString();
        } catch (IOException e) {
            throw new BusinessMessageException("Failed to move file from temp");
        }
    }

    /**
     * 파일 삭제
     *
     * @param physFileNm 물리 파일명
     */
    public void deleteFile(String physFileNm) {
        try {
            Path filePath = Paths.get(rootPath, physFileNm);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new BusinessMessageException("Failed to delete file");
        }
    }

    /**
     * 저장될 파일명 생성
     *
     * @param originalFilename 원본 파일명
     * @return 생성된 파일명
     */
    private String generateStoredFilename(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        return UUID.randomUUID().toString() + (StringUtils.hasText(extension) ? "." + extension : "");
    }

    /**
     * 대상 위치 경로 조회
     *
     * @param basePath 기본 경로
     * @param filename 파일명
     * @param isTemp 임시 파일 여부
     * @return Path
     */
    private Path getTargetLocation(String basePath, String filename, boolean isTemp) {
        if (isTemp) {
            return Paths.get(rootPath, TEMP_DIR, basePath, filename);
        }
        return Paths.get(rootPath, basePath, filename);
    }

    /**
     * Content Type 조회
     *
     * @param filename 파일명
     * @return Content Type
     */
    private String getContentType(String filename) {
        try {
            return Files.probeContentType(Paths.get(filename));
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

}
