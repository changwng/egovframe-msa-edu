package org.egovframe.cloud.cmsservice.api.attachment;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.attachment.dto.AtflMngResponseDto;
import org.egovframe.cloud.cmsservice.service.attachment.AtflMngService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/attachments")
public class AtflMngApiController {

    private final AtflMngService atflMngService;
    private final String uploadDir = "uploads"; // 실제 환경에 맞게 설정

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "lnkgDmnId", required = false) String lnkgDmnId,
            @RequestParam(value = "lnkgDmnNm", required = false) String lnkgDmnNm) throws IOException {
        String atflCd = atflMngService.upload(file, lnkgDmnId, lnkgDmnNm);
        Map<String, String> response = new HashMap<>();
        response.put("atflCd", atflCd);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{atflCd}")
    public ResponseEntity<AtflMngResponseDto> findById(@PathVariable String atflCd) {
        return ResponseEntity.ok(atflMngService.findById(atflCd));
    }

    @GetMapping("/{atflCd}/download")
    public ResponseEntity<Resource> download(@PathVariable String atflCd) throws MalformedURLException {
        AtflMngResponseDto file = atflMngService.findById(atflCd);
        Path filePath = Paths.get(uploadDir).resolve(file.getPhysFileNm());
        Resource resource = new UrlResource(filePath.toUri());

        atflMngService.increaseDwnldNocs(atflCd);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getAtflType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOrgnlFileNm() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{atflCd}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String atflCd) {
        atflMngService.delete(atflCd);
        return ResponseEntity.noContent().build();
    }
}
