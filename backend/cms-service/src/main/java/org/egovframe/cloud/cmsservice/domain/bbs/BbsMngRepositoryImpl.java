package org.egovframe.cloud.cmsservice.domain.bbs;

import static com.querydsl.core.types.Projections.constructor;

import com.google.common.base.CaseFormat;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.common.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.egovframe.cloud.cmsservice.api.bbs.dto.*;

import java.util.List;

/**
 * org.egovframe.cloud.boardservice.domain.board.BbsMngRepositoryImpl
 * <p>
 * 게시판 Querydsl 구현 클래스
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
@RequiredArgsConstructor
public class BbsMngRepositoryImpl implements BbsMngRepositoryCustom {

    /**
     * DML 생성을위한 Querydsl 팩토리 클래스
     */
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 게시판 페이지 목록 조회
     * 가급적 Entity 보다는 Dto를 리턴 - Entity 조회시 hibernate 캐시, 불필요 컬럼 조회, oneToOne N+1 문제 발생
     *
     * @param requestDto 요청 DTO
     * @param pageable   페이지 정보
     * @return Page<BbsMngListResponseDto> 페이지 게시판 목록 응답 DTO
     * Integer bbsId, String bbsTypeCd, String bbsNm, String bbsExpln, Integer atflMaxCnt
     *                                   , Long atflMaxFlsz , LocalDateTime regDt, String rgtr
     */
    @Override
    public Page<BbsMngListResponseDto> findPage(RequestDto requestDto, Pageable pageable) {
        JPQLQuery<BbsMngListResponseDto> query = jpaQueryFactory
                .select(constructor(BbsMngListResponseDto.class,
                        QBbsMng.bbsMng.bbsId,
                        QBbsMng.bbsMng.bbsTypeCd,
                        QBbsMng.bbsMng.bbsNm,
                        QBbsMng.bbsMng.bbsExpln,
                        QBbsMng.bbsMng.atflMaxCnt,
                        QBbsMng.bbsMng.atflMaxFlsz,
                        QBbsMng.bbsMng.regDt,
                        QBbsMng.bbsMng.rgtr
                ))
                .from(QBbsMng.bbsMng)
                //.leftJoin(QCode.code).on(QBoard.board.skinTypeCode.eq(QCode.code.codeId).and(QCode.code.parentCodeId.eq("skin_type_code")))
                .fetchJoin()
                .where(getBooleanExpressionKeyword(requestDto));

        //정렬
        pageable.getSort().stream().forEach(sort -> {
            Order order = sort.isAscending() ? Order.ASC : Order.DESC;
            String property = sort.getProperty();

            Path<Object> target = Expressions.path(Object.class, QBbsMng.bbsMng, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, property));
            @SuppressWarnings({ "rawtypes", "unchecked" })
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, target);
            query.orderBy(orderSpecifier);
        });

        QueryResults<BbsMngListResponseDto> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()) //페이징
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }



    /**
     * 게시판 목록 조회
     *
     * @param boardNos 게시판 번호 목록
     * @return List<BoardResponseDto> 게시판 상세 응답 DTO List
     */
    @Override
    public List<BbsMngResponseDto> findAllByBoardNoIn(List<Integer> boardNos) {
        return jpaQueryFactory
                .select(new QBbsMngResponseDto(
                        QBbsMng.bbsMng.bbsId,
                        QBbsMng.bbsMng.bbsTypeCd,
                        QBbsMng.bbsMng.bbsNm,
                        QBbsMng.bbsMng.bbsExpln,
                        QBbsMng.bbsMng.refUseYn,
                        QBbsMng.bbsMng.atflUseYn,
                        QBbsMng.bbsMng.atflMaxCnt,
                        QBbsMng.bbsMng.atflMaxFlsz,
                        QBbsMng.bbsMng.ansUseYn,
                        QBbsMng.bbsMng.cmntUseYn,
                        QBbsMng.bbsMng.dgstfnEvlUseYn,
                        QBbsMng.bbsMng.smsUseYn,
                        QBbsMng.bbsMng.upndFixUseYn,
                        QBbsMng.bbsMng.pstgPrdUseYn,
                        QBbsMng.bbsMng.popupUseYn
                ))
                .from(QBbsMng.bbsMng)
                // .leftJoin(QCode.code).on(QBoard.board.skinTypeCode.eq(QCode.code.codeId).and(QCode.code.parentCodeId.eq("skin_type_code")))
                //  .fetchJoin()
                .where(QBbsMng.bbsMng.bbsId.in(boardNos))
                .orderBy(QBbsMng.bbsMng.bbsId.asc())
                .fetchResults().getResults();
    }
    /**
     * 요청 DTO로 동적 검색 표현식 리턴
     *
     * @param requestDto 요청 DTO
     * @return BooleanExpression 검색 표현식
     */
    private BooleanExpression getBooleanExpressionKeyword(RequestDto requestDto) {
        if (requestDto.getKeyword() == null || "".equals(requestDto.getKeyword())) return null;

        switch (requestDto.getKeywordType()) {
            case "bbsNm": // 게시판 명
                return QBbsMng.bbsMng.bbsNm.containsIgnoreCase(requestDto.getKeyword());
            default:
                return null;
        }
    }

}
