package org.egovframe.cloud.cmsservice.service.post;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.post.dto.PstResponseDto;
import org.egovframe.cloud.cmsservice.api.post.dto.PstSaveRequestDto;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMngRepository;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;
import org.egovframe.cloud.cmsservice.domain.post.PstRepository;
import org.egovframe.cloud.common.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cloud.stream.function.StreamBridge;
import org.egovframe.cloud.common.dto.AttachmentEntityMessage;
import org.egovframe.cloud.common.dto.RequestDto;
import org.egovframe.cloud.common.exception.BusinessMessageException;
import org.egovframe.cloud.common.exception.EntityNotFoundException;
import org.egovframe.cloud.common.exception.InvalidValueException;
 import org.egovframe.cloud.cmsservice.service.bbs.BbsMngService;
import org.egovframe.cloud.cmsservice.api.post.dto.*;
import org.egovframe.cloud.cmsservice.api.bbs.dto.*;
import org.egovframe.cloud.cmsservice.service.bbs.*;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PstService extends AbstractService {
    /**
     * 게시물 레파지토리 인터페이스
     */
    private final PstRepository pstRepository;
    /**
     * 게시판 서비스 클래스
     */
    private final BbsMngRepository bbsMngRepository;

    /**
     * 이벤트 메시지 발행하기 위한 spring cloud stream 유틸리티 클래스
     */
    private final StreamBridge streamBridge;

    /**
     * 게시판 서비스 클래스
     */
    private final BbsMngService bbsMngService;

    /**
     * 조회 조건에 일치하는 게시물 페이지 목록 조회
     *
     * @param boardNo    게시판 번호
     * @param delYn   삭제 여부
     * @param requestDto 요청 DTO
     * @param pageable   페이지 정보
     * @return Page<PstListResponseDto> 페이지 게시물 목록 응답 DTO
     */
    @Transactional(readOnly = true)
    public Page<PstListResponseDto> findPage(Integer boardNo, String delYn, RequestDto requestDto, Pageable pageable) {
        if (boardNo == null || boardNo <= 0) throw new InvalidValueException(getMessage("err.invalid.input.value"));
        return pstRepository.findPage(boardNo, delYn, requestDto, pageable);
    }
    /**
     * 최근 게시물이 포함된 게시판 목록 조회
     *
     * @param boardNos   게시판 번호 목록
     * @param postsCount 게시물 수
     * @return Map<Integer, BoardResponseDto> 최근 게시물이 포함된 게시판 상세 응답 DTO Map
     */
    @Transactional(readOnly = true)
    public Map<Integer, BbsMngResponseDto> findNewest(List<Integer> boardNos, Integer postsCount) throws InvalidValueException {
        if (boardNos == null || boardNos.isEmpty())
            throw new InvalidValueException(getMessage("err.invalid.input.value"));

        List<BbsMngResponseDto> boards = bbsMngService.findAllByBoardNos(boardNos);

        List<PstSimpleResponseDto> allPosts = pstRepository.findAllByBoardNosLimitCount(boardNos, postsCount);
        Map<Integer, List<PstSimpleResponseDto>> postsGroup = allPosts.stream().collect(Collectors.groupingBy(PstSimpleResponseDto::getBbsId, Collectors.toList()));

        Map<Integer, BbsMngResponseDto> data = new HashMap<>(); // 요청한 게시판 순서로 리턴하기 위해서 map 리턴
        for (BbsMngResponseDto board : boards) {
            List<PstSimpleResponseDto> posts = postsGroup.get(board.getBbsId());
            if (posts != null) {
                board.setNewestPosts(posts.stream().map(post -> post.setIsNew(board))
                        .collect(Collectors.toList()));
            }
            data.put(board.getBbsId(), board);
        }

        return data;
    }

//===================.  이전 체크 로직을 위해 생성한 부분 25.01.23 ============
    @Transactional(readOnly = true)
    public Page<PstResponseDto> findAll(Integer bbsId, Pageable pageable) {
        // TODO: Implement findAll with bbsId filter
        return pstRepository.findAll(pageable)
                .map(PstResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<PstResponseDto> findAll(Integer bbsId, String searchType, String searchKeyword, Pageable pageable) {
        return pstRepository.findAllBySearchCondition(bbsId, searchType, searchKeyword, pageable)
                .map(PstResponseDto::new);
    }

    @Transactional(readOnly = true)
    public PstResponseDto findById(Integer bbsId, Long pstNo) {
        Pst entity = findPstById(bbsId, pstNo);
        entity.increaseInqNocs(); // 조회수 증가
        return new PstResponseDto(entity);
    }

    @Transactional
    public PstId save(Integer bbsId, PstSaveRequestDto requestDto) {
        BbsMng bbsMng = findBbsMngById(bbsId);

        // 첨부파일 사용 여부 확인
        if (requestDto.getAtflCd() != null && !bbsMng.isAtflAllowed()) {
            throw new IllegalStateException("첨부파일을 사용할 수 없는 게시판입니다.");
        }

        // 게시물 번호 생성
        Long pstNo = generatePstNo(bbsId);
        PstId pstId = new PstId(bbsId, pstNo);

        // 게시물 순서 계산
        Long pstSeqNo = generatePstSeqNo(bbsId);
        Long pstSortSeq = generatePstSortSeq(bbsId);

        Pst pst = requestDto.toEntity(bbsMng, pstId, pstSeqNo, pstSortSeq);
        pst = pstRepository.save(pst);
        return pst.getPstId();
    }

    @Transactional
    public PstId reply(Integer bbsId, Long upPstNo, PstSaveRequestDto requestDto) {
        // 상위 게시물 조회
        Pst parentPst = findPstById(bbsId, upPstNo);
        BbsMng bbsMng = parentPst.getBbsMng();

        // 답변 사용 여부 확인
        if (!bbsMng.isAnsAllowed()) {
            throw new IllegalStateException("답변을 사용할 수 없는 게시판입니다.");
        }

        // 첨부파일 사용 여부 확인
        if (requestDto.getAtflCd() != null && !bbsMng.isAtflAllowed()) {
            throw new IllegalStateException("첨부파일을 사용할 수 없는 게시판입니다.");
        }

        // 게시물 번호 생성
        Long pstNo = generatePstNo(bbsId);
        PstId pstId = new PstId(bbsId, pstNo);

        // 게시물 순서 계산
        Long pstSeqNo = generatePstSeqNo(bbsId);
        Long pstSortSeq = generatePstSortSeq(bbsId, upPstNo);

        // 답변 위치 계산 (부모 답변 위치 + 1)
        Integer ansLoc = parentPst.getAnsLoc() + 1;

        Pst pst = requestDto.toEntity(bbsMng, pstId, pstSeqNo, pstSortSeq);
        pst.setUpPstNo(upPstNo);
        pst.setAnsLoc(ansLoc);
        
        pst = pstRepository.save(pst);
        return pst.getPstId();
    }

    @Transactional
    public PstId update(Integer bbsId, Long pstNo, PstSaveRequestDto requestDto) {
        Pst entity = findPstById(bbsId, pstNo);
        BbsMng bbsMng = entity.getBbsMng();

        // 첨부파일 사용 여부 확인
        if (requestDto.getAtflCd() != null && !bbsMng.isAtflAllowed()) {
            throw new IllegalStateException("첨부파일을 사용할 수 없는 게시판입니다.");
        }

        entity.update(
            requestDto.getPstTtl(),
            requestDto.getPstCn(),
            requestDto.getRefUrl(),
            requestDto.getPopupYn(),
            requestDto.getUpndFixYn(),
            requestDto.getPrdPstgYn(),
            requestDto.getWrtrNm(),
            requestDto.getWrtrCplc(),
            requestDto.getAtflCd()
        );

        return entity.getPstId();
    }

    @Transactional
    public void delete(Integer bbsId, Long pstNo, String delRsn) {
        Pst entity = findPstById(bbsId, pstNo);
        entity.delete(delRsn);
    }

    private Pst findPstById(Integer bbsId, Long pstNo) {
        return pstRepository.findById(new PstId(bbsId, pstNo))
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. bbsId=" + bbsId + ", pstNo=" + pstNo));
    }

    private BbsMng findBbsMngById(Integer bbsId) {
        return bbsMngRepository.findById(bbsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. bbsId=" + bbsId));
    }

    private Long generatePstNo(Integer bbsId) {
        return pstRepository.getNextPstNo(bbsId);
    }

    private Long generatePstSeqNo(Integer bbsId) {
        return pstRepository.getNextPstSeqNo(bbsId);
    }

    private Long generatePstSortSeq(Integer bbsId) {
        return pstRepository.getNextPstSortSeq(bbsId, null);
    }

    private Long generatePstSortSeq(Integer bbsId, Long upPstNo) {
        return pstRepository.getNextPstSortSeq(bbsId, upPstNo);
    }
}
