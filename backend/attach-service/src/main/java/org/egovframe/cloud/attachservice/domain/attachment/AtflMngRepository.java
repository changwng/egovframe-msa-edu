package org.egovframe.cloud.attachservice.domain.attachment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * org.egovframe.cloud.attachservice.domain.attachment.AtflMngRepository
 * <p>
 * 첨부파일 레포지토리 인터페이스
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
public interface AtflMngRepository extends JpaRepository<AtflMng, AtflMngId>, AtflMngRepositoryCustom {
    
    /**
     * 첨부파일 ID로 첨부파일 조회
     *
     * @param atflId 첨부파일 ID
     * @return Optional<AtflMng> 첨부파일 Optional
     */
    Optional<AtflMng> findByAtflId(String atflId);

    /**
     * 연계 도메인 ID로 첨부파일 목록 조회
     *
     * @param lnkgDmnId 연계 도메인 ID
     * @return List<AtflMng> 첨부파일 목록
     */
    Optional<AtflMng> findByLnkgDmnId(String lnkgDmnId);
}
