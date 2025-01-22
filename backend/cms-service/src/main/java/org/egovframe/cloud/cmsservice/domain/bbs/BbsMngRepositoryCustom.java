package org.egovframe.cloud.cmsservice.domain.bbs;

import org.egovframe.cloud.common.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.egovframe.cloud.cmsservice.api.bbs.dto.*;

/**
 * org.egovframe.cloud.boardservice.domain.board.BbsMngRepositoryCustom
 * <p>
 * 게시판 Querydsl 인터페이스
 *
 * @author 표준프레임워크센터 jooho
 * @version 1.0
 * @since 2021/07/26
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2021/07/26    jooho       최초 생성
 * </pre>
 */

public interface BbsMngRepositoryCustom {

    /**
     * 게시판 페이지 목록 조회
     *
     * @param requestDto 게시판 목록 요청 DTO
     * @param pageable   페이지 정보
     * @return Page<BbsMngListResponseDto> 페이지 게시판 목록 응답 DTO
     */

    Page<BbsMngListResponseDto> findPage(RequestDto requestDto, Pageable pageable);

    /**
     * 게시판 목록 조회
     *
     * @param boardNos 게시판 번호 목록
     * @return List<BoardResponseDto> 게시판 상세 응답 DTO List
     */
    List<BbsMngResponseDto> findAllByBoardNoIn(List<Integer> boardNos);

}
