package org.egovframe.cloud.attachservice.domain.attachment;

import org.egovframe.cloud.common.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * org.egovframe.cloud.attachservice.domain.attachment.AtflMngRepositoryCustom
 * <p>
 * 첨부파일 Querydsl 사용자 정의 인터페이스
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
public interface AtflMngRepositoryCustom {

    List<AtflMng> findByCode(String atflCd);
    AtflMngId getId(String attachmentCode);
    /**
     * 첨부파일 목록 검색
     *
     * @param requestDto 요청 DTO
     * @param pageable 페이지 정보
     * @return Page<AtflMng> 페이지 첨부파일 목록
     */
    Page<AtflMng> search(RequestDto requestDto, Pageable pageable);
}
