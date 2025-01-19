package org.egovframe.cloud.cmsservice.api.post;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.post.dto.PstResponseDto;
import org.egovframe.cloud.cmsservice.api.post.dto.PstSaveRequestDto;
import org.egovframe.cloud.cmsservice.domain.post.PstId;
import org.egovframe.cloud.cmsservice.service.post.PstService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bbs/{bbsId}/posts")
public class PstApiController {

    private final PstService pstService;

    @GetMapping
    public ResponseEntity<Page<PstResponseDto>> findAll(@PathVariable String bbsId,
                                                      @RequestParam(required = false) String searchType,
                                                      @RequestParam(required = false) String searchKeyword,
                                                      Pageable pageable) {
        return ResponseEntity.ok(pstService.findAll(bbsId, searchType, searchKeyword, pageable));
    }

    @GetMapping("/{pstNo}")
    public ResponseEntity<PstResponseDto> findById(@PathVariable String bbsId,
                                                 @PathVariable Long pstNo) {
        return ResponseEntity.ok(pstService.findById(bbsId, pstNo));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PstId> save(@PathVariable String bbsId,
                                    @RequestBody PstSaveRequestDto requestDto) {
        return ResponseEntity.ok(pstService.save(bbsId, requestDto));
    }

    @PostMapping("/{upPstNo}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PstId> reply(@PathVariable String bbsId,
                                     @PathVariable Long upPstNo,
                                     @RequestBody PstSaveRequestDto requestDto) {
        return ResponseEntity.ok(pstService.reply(bbsId, upPstNo, requestDto));
    }

    @PutMapping("/{pstNo}")
    public ResponseEntity<PstId> update(@PathVariable String bbsId,
                                      @PathVariable Long pstNo,
                                      @RequestBody PstSaveRequestDto requestDto) {
        return ResponseEntity.ok(pstService.update(bbsId, pstNo, requestDto));
    }

    @DeleteMapping("/{pstNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String bbsId,
                                     @PathVariable Long pstNo,
                                     @RequestParam String delRsn) {
        pstService.delete(bbsId, pstNo, delRsn);
        return ResponseEntity.noContent().build();
    }
}
