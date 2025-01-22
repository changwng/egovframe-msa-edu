package org.egovframe.cloud.cmsservice.api.bbs;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngListResponseDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngResponseDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngSaveRequestDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngUpdateRequestDto;
import org.egovframe.cloud.cmsservice.service.bbs.BbsMngService;
import org.egovframe.cloud.common.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bbsmng")
public class BbsMngApiController {

    private final BbsMngService bbsMngService;




    /**
     * 게시판 페이지 목록 조회
     *
     * @param requestDto 요청 DTO
     * @param pageable   페이지 정보
     * @return Page<BbsMngListResponseDto> 페이지 게시판 목록 응답 DTO
     */
    @GetMapping("/boards")
    public Page<BbsMngListResponseDto> findPage(RequestDto requestDto,
                                                @PageableDefault(sort = "bbs_id", direction = Sort.Direction.DESC) Pageable pageable) {
        return bbsMngService.findPage(requestDto, pageable);
    }

    /**
     * 게시판 단건 조회
     *
     * @param bbsId 게시판 번호
     * @return BbsMngResponseDto 게시판 상세 응답 DTO
     */
    @GetMapping("/boards/{bbsId}")
    public BbsMngResponseDto findById(@PathVariable Integer bbsId) {
        return bbsMngService.findById(bbsId);
    }

    /**
     * 게시판 등록
     *
     * @param requestDto 게시판 등록 요청 DTO
     * @return BbsMngResponseDto 게시판 상세 응답 DTO
     */
    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public BbsMngResponseDto save(@RequestBody @Valid BbsMngSaveRequestDto requestDto) {
        return bbsMngService.save(requestDto);
    }

    /**
     * 게시판 수정
     *
     * @param bbsId    게시판 번호
     * @param requestDto 게시판 수정 요청 DTO
     * @return BbsMngResponseDto 게시판 상세 응답 DTO
     */
    @PutMapping("/boards/{bbsId}")
    public BbsMngResponseDto update(@PathVariable Integer bbsId, @RequestBody @Valid BbsMngUpdateRequestDto requestDto) {
        return bbsMngService.update(bbsId, requestDto);
    }

    /**
     * 게시판 삭제
     *
     * @param bbsId 게시판 번호
     */
    @DeleteMapping("/boards/{bbsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer bbsId) {
        bbsMngService.delete(bbsId);
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

}


