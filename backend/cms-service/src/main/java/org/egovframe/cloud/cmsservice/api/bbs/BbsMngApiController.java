package org.egovframe.cloud.cmsservice.api.bbs;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngResponseDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngSaveRequestDto;
import org.egovframe.cloud.cmsservice.service.bbs.BbsMngService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bbs")
public class BbsMngApiController {

    private final BbsMngService bbsMngService;

    /**
     * 서비스 상태 확인
     *
     * @return
     */
    @GetMapping("/actuator/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("CMS Service is UP");
    }

    /**
     * 게시판 목록 조회
     *
     * @param searchText
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<BbsMngResponseDto>> findAll(@RequestParam(required = false) String searchText, Pageable pageable) {
        return ResponseEntity.ok(bbsMngService.findAll(searchText, pageable));
    }

    /**
     * 게시판 단건 조회
     *
     * @param bbsId
     * @return
     */
    @GetMapping("/{bbsId}")
    public ResponseEntity<BbsMngResponseDto> findById(@PathVariable String bbsId) {
        return ResponseEntity.ok(bbsMngService.findById(bbsId));
    }

    /**
     * 게시판 등록
     *
     * @param requestDto
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> save(@RequestBody BbsMngSaveRequestDto requestDto) {
        return ResponseEntity.ok(bbsMngService.save(requestDto));
    }

    /**
     * 게시판 수정
     *
     * @param bbsId
     * @param requestDto
     * @return
     */
    @PutMapping("/{bbsId}")
    public ResponseEntity<String> update(@PathVariable String bbsId, @RequestBody BbsMngSaveRequestDto requestDto) {
        return ResponseEntity.ok(bbsMngService.update(bbsId, requestDto));
    }

    /**
     * 게시판 삭제
     *
     * @param bbsId
     * @return
     */
    @DeleteMapping("/{bbsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String bbsId) {
        bbsMngService.delete(bbsId);
        return ResponseEntity.noContent().build();
    }
}
