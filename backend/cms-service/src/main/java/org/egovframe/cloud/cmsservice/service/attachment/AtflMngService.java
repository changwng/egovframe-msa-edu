package org.egovframe.cloud.cmsservice.service.attachment;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngResponseDto;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMng;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMngRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AtflMngService {

    private final AtflMngRepository atflMngRepository;
    private final String uploadDir = "uploads"; // 실제 환경에 맞게 설정

    @Transactional
    public String upload(MultipartFile file, String lnkgDmnId, String lnkgDmnNm) throws IOException {
        // 업로드 디렉토리 생성
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 정보 생성
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String physicalFilename = UUID.randomUUID().toString() + fileExtension;
        String atflCd = "FILE_" + UUID.randomUUID().toString().substring(0, 8);

        // 파일 저장
        Path filePath = uploadPath.resolve(physicalFilename);
        Files.copy(file.getInputStream(), filePath);

        // DB에 파일 정보 저장
        AtflMng atflMng = AtflMng.builder()
                .atflCd(atflCd)
                .atflSn(1)
                .dwnldNocs(0L)
                .lnkgDmnId(lnkgDmnId)
                .lnkgDmnNm(lnkgDmnNm)
                .atflType(file.getContentType())
                .orgnlFileNm(originalFilename)
                .physFileNm(physicalFilename)
                .atflFlsz(file.getSize())
                .atflId(UUID.randomUUID().toString())
                .delYn("N")
                .build();

        atflMngRepository.save(atflMng);
        return atflCd;
    }

    @Transactional(readOnly = true)
    public AtflMngResponseDto findById(String atflCd) {
        AtflMng entity = atflMngRepository.findById(atflCd)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다. atflCd=" + atflCd));
        return new AtflMngResponseDto(entity);
    }

    @Transactional
    public void delete(String atflCd) {
        AtflMng entity = atflMngRepository.findById(atflCd)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다. atflCd=" + atflCd));

        // 물리적 파일 삭제
        Path filePath = Paths.get(uploadDir, entity.getPhysFileNm());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 중 오류가 발생했습니다.", e);
        }

        // DB에서 삭제
        atflMngRepository.delete(entity);
    }

    @Transactional
    public void increaseDwnldNocs(String atflCd) {
        AtflMng entity = atflMngRepository.findById(atflCd)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다. atflCd=" + atflCd));
        entity.increaseDwnldNocs();
    }

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int lastDotIndex = filename.lastIndexOf(".");
        return lastDotIndex == -1 ? "" : filename.substring(lastDotIndex);
    }
}
