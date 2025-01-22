package org.egovframe.cloud.cmsservice.api.comment;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.comment.dto.PstCmntResponseDto;
import org.egovframe.cloud.cmsservice.api.comment.dto.PstCmntSaveRequestDto;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmntId;
import org.egovframe.cloud.cmsservice.service.comment.PstCmntService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bbs/{bbsId}/posts/{pstNo}/comments")
public class PstCmntApiController {

    private final PstCmntService pstCmntService;

    @GetMapping
    public ResponseEntity<Page<PstCmntResponseDto>> findAll(@PathVariable Integer bbsId,
                                                          @PathVariable Long pstNo,
                                                          Pageable pageable) {
        return ResponseEntity.ok(pstCmntService.findAll(bbsId, pstNo, pageable));
    }

    @GetMapping("/{cmntNo}")
    public ResponseEntity<PstCmntResponseDto> findById(@PathVariable Integer bbsId,
                                                     @PathVariable Long pstNo,
                                                     @PathVariable Long cmntNo) {
        return ResponseEntity.ok(pstCmntService.findById(bbsId, pstNo, cmntNo));
    }

    @PostMapping
    public ResponseEntity<PstCmntId> save(@PathVariable Integer bbsId,
                                        @PathVariable Long pstNo,
                                        @RequestBody PstCmntSaveRequestDto requestDto) {
        return ResponseEntity.ok(pstCmntService.save(bbsId, pstNo, requestDto));
    }

    @PutMapping("/{cmntNo}")
    public ResponseEntity<PstCmntId> update(@PathVariable Integer bbsId,
                                          @PathVariable Long pstNo,
                                          @PathVariable Long cmntNo,
                                          @RequestBody PstCmntSaveRequestDto requestDto) {
        return ResponseEntity.ok(pstCmntService.update(bbsId, pstNo, cmntNo, requestDto));
    }

    @DeleteMapping("/{cmntNo}")
    public ResponseEntity<Void> delete(@PathVariable Integer bbsId,
                                     @PathVariable Long pstNo,
                                     @PathVariable Long cmntNo) {
        pstCmntService.delete(bbsId, pstNo, cmntNo);
        return ResponseEntity.ok().build();
    }
}
