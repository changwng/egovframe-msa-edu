package org.egovframe.cloud.cmsservice.domain.attachment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.common.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import java.util.List;

import static org.egovframe.cloud.cmsservice.domain.attachment.QAtflMng.atflMng;

/**
 * org.egovframe.cloud.cmsservice.domain.attachment.AtflMngRepositoryImpl
 * <p>
 * 첨부파일 Querydsl 구현 클래스
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
@RequiredArgsConstructor
public class AtflMngRepositoryImpl implements AtflMngRepositoryCustom {

    /**
     * DML 생성을위한 Querydsl 팩토리 클래스
     */
    private final JPAQueryFactory queryFactory;

    /**
     * code로 첨부파일 검색
     *
     * @param atflCd
     * @return
     */
    @Override
    public List<AtflMng> findByCode(String atflCd) {
        return queryFactory.selectFrom(atflMng)
                 .where(atflMng.atflMngId.atflCd.eq(atflCd), atflMng.delYn.eq("N"))  // isDelete 대신 delYn 사용)
                .orderBy(atflMng.atflMngId.atflSn.asc())
                .fetch();
    }

    /**
     * 첨부파일 복합키 seq 조회하여 생성
     *
     * @param atflCd
     * @return
     */
    @Override
    public AtflMngId getId(String atflCd   ) {
        Integer seq = queryFactory.select(
                        atflMng.atflMngId.atflSn.max()
                   )
                .from(atflMng)
                .where(atflMng.atflMngId.atflCd.eq(atflCd))
                .fetchOne();

        if (seq == null) {
            seq = 0;
        }

        return AtflMngId.builder()
                .atflCd(atflCd)
                .atflSn(seq+1)
                .build();

    }

    /**
     * 첨부파일 목록 검색
     *
     * @param requestDto 요청 DTO
     * @param pageable 페이지 정보
     * @return Page<AtflMng> 페이지 첨부파일 목록
     */
    @Override
    public Page<AtflMng> search(RequestDto requestDto, Pageable pageable) {
        JPAQuery<AtflMng> query = queryFactory
                .selectFrom(atflMng)
                .where(
                        searchCondition(requestDto),
                        atflMng.delYn.eq("N")
                );

        if (pageable != null) {
            query.offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(query.fetch(), pageable,
                () -> queryFactory
                        .selectFrom(atflMng)
                        .where(
                                searchCondition(requestDto),
                                atflMng.delYn.eq("N")
                        )
                        .fetch().size());
    }

    /**
     * 검색 조건
     *
     * @param requestDto 요청 DTO
     * @return BooleanExpression 검색조건
     */
    private BooleanExpression searchCondition(RequestDto requestDto) {
        if (!StringUtils.hasText(requestDto.getKeywordType()) || !StringUtils.hasText(requestDto.getKeyword())) {
            return null;
        }

        String keywordType = requestDto.getKeywordType();
        String keyword = requestDto.getKeyword();

        if ("id".equals(keywordType)) { // 이전 버전 호환성
            return atflMng.atflMngId.atflCd.containsIgnoreCase(keyword);
        }else if ("atflCd".equals(keywordType)) {
            return atflMng.atflMngId.atflCd.containsIgnoreCase(keyword);
        } else if ("name".equals(keywordType)) {
            return atflMng.orgnlFileNm.containsIgnoreCase(keyword);
        }else if ("orgnlFileNm".equals(keywordType)) {
            return atflMng.orgnlFileNm.containsIgnoreCase(keyword);
        } else if ("lnkgDmnNm".equals(keywordType)) {
            return atflMng.lnkgDmnNm.containsIgnoreCase(keyword);
        }

        return null;
    }
}
