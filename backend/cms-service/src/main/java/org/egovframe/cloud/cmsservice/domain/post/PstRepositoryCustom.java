package org.egovframe.cloud.cmsservice.domain.post;

import org.egovframe.cloud.cmsservice.api.post.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.egovframe.cloud.common.dto.RequestDto;
import java.util.List;
import java.util.Map;

public interface PstRepositoryCustom {
    /**
     * 게시물 페이지 목록 조회
     *
     * @param bbsId    게시판 번호
     * @param delYn   삭제 여부
     * @param requestDto 요청 DTO
     * @param pageable   페이지 정보
     * @return Page<PstListResponseDto> 페이지 게시물 목록 응답 DTO
     */
    Page<PstListResponseDto> findPage(Integer bbsId, String delYn, RequestDto requestDto, Pageable pageable);

    /**
     * 게시판별 최근 게시물 목록 조회
     *
     * @param bbsIds   게시판 번호 목록
     * @param postsCount 게시물 수
     * @return List<PstSimpleResponseDto> 게시물 응답 DTO List
     */
    List<PstSimpleResponseDto> findAllByBoardNosLimitCount(List<Integer> bbsIds, Integer postsCount);

    /**
     * 게시물 상세 조회
     *
     * @param bbsId  게시판 번호
     * @param pstNo  게시물 번호
     * @param userId   사용자 id
     * @param ipAddr   ip 주소
     * @return PstResponseDto 게시물 상세 응답 DTO
     */
    PstResponseDto findById(Integer bbsId, Long pstNo, String userId, String ipAddr);

    /**
     * 근처 게시물 조회
     *
     * @param bbsId    게시판 번호
     * @param pstNo    게시물 번호
     * @param gap        차이 -1: 이전, 1: 이후
     * @param delYn   삭제 여부
     * @param requestDto 요청 DTO
     * @return List<PstSimpleResponseDto> 게시물 상세 응답 DTO List
     */
    List<PstSimpleResponseDto> findNearPst(Integer bbsId,  Long pstNo, long gap, String delYn, RequestDto requestDto);

    /**
     * 다음 게시물 번호 조회
     *
     * @param bbsId 게시판 번호
     * @return Integer 다음 게시물 번호
     */
    Long findNextPstNo(Integer bbsId);

    /**
     * 게시물 조회 수 증가
     *
     * @param bbsId 게시판 번호
     * @param pstNo 게시물 번호
     * @return Long 처리 건수
     */
    Long updateReadCount(Integer bbsId, Long pstNo);

    /**
     * 게시물 삭제 여부 수정
     *
     * @param posts    게시물 정보(게시판번호, 게시물번호배열)
     * @param delYn 삭제 여부
     * @param userId   사용자 id
     * @return Long 처리 건수
     */
    Long updateDelYn(Map<Integer, List<Long>> posts, String delYn, String userId);


    //===================.  이전 체크 로직을 위해 생성한 부분 25.01.23 ============
    Page<Pst> findAllBySearchCondition(Integer bbsId, String searchType, String searchKeyword, Pageable pageable);
    Long getNextPstNo(Integer bbsId);
    Long getNextPstSeqNo(Integer bbsId);
    Long getNextPstSortSeq(Integer bbsId, Long upPstNo);
}
