package org.egovframe.cloud.cmsservice.domain.post;

import com.google.common.base.CaseFormat;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.post.dto.*;
import org.egovframe.cloud.cmsservice.domain.bbs.QBbsMng;
import org.egovframe.cloud.common.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.egovframe.cloud.cmsservice.api.bbs.dto.*;

import static org.egovframe.cloud.cmsservice.domain.post.QPst.pst;

@RequiredArgsConstructor
public class PstRepositoryImpl implements PstRepositoryCustom {

    /**
     * DML 생성을위한 Querydsl 팩토리 클래스
     */
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 쿼리 및 DML 절 생성을 위한 팩토리 클래스
     */
    private final SQLQueryFactory sqlQueryFactory;



    //===================.  이전 체크 로직을 위해 생성한 부분 25.01.23 ============

    /**
     * 게시물 페이지 목록 조회
     *
     * @param bbsId      게시판 번호
     * @param delYn      삭제 여부
     * @param requestDto 요청 DTO
     * @param pageable   페이지 정보
     * @return Page<PstListResponseDto> 페이지 게시물 목록 응답 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PstListResponseDto> findPage(Integer bbsId, String delYn, RequestDto requestDto, Pageable pageable) {
        //  getCommentCountExpression(deleteAt)))

        JPQLQuery<PstListResponseDto> query = jpaQueryFactory
                .select(new QPstListResponseDto(
                        QPst.pst.pstId.bbsId,
                        QPst.pst.pstId.pstNo,
                        QPst.pst.pstTtl,
                        QPst.pst.pstCn,
                        pst.inqNocs,
                        pst.refUrl,
                        pst.popupYn,
                        pst.upndFixYn,
                        pst.prdPstgYn,
                        pst.pstSeqNo,
                        pst.upPstNo,
                        pst.ansLoc,
                        pst.pstSortSeq,
                        pst.pstPswd,
                        pst.delYn,
                        pst.delRsn,
                        pst.wrtrNm,
                        pst.wrtrCplc,
                        pst.atflCd,
                        pst.regDt
                        ))
                .from(QPst.pst)
                .innerJoin(QBbsMng.bbsMng).on(pst.pstId.bbsId.eq(QBbsMng.bbsMng.bbsId))
                .fetchJoin()
               /* .leftJoin(QUser.user).on(QPst.pst.rgtr.eq(QUser.user.userId)) // 사용자 명은 컬럼에 pst.wrtrNm 에 있다
                .fetchJoin()*/
                .where(pst.pstId.bbsId.eq(bbsId)
                        .and(getBooleanExpression(requestDto.getKeywordType(), requestDto.getKeyword()))
                        .and(getBooleanExpression("delYn", delYn)));

        //정렬
        pageable.getSort().stream().forEach(sort -> {
            Order order = sort.isAscending() ? Order.ASC : Order.DESC;
            String property = sort.getProperty();
            Path<?> parent;
            if ("bbs_id".equals(property) || "pst_no".equals(property)){
                parent = QPst.pst.pstId; // 복합키로 됨
            }
            else {
                parent = pst; // 게시물 객체로됨
            }

            Path<Object> target = Expressions.path(Object.class, parent, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, property));
            @SuppressWarnings({ "unchecked", "rawtypes" })
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, target);
            query.orderBy(orderSpecifier);
        });

        QueryResults<PstListResponseDto> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()) //페이징
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
    /**
     * 엔티티 속성별 동적 검색 표현식 리턴
     *
     * @param attributeName  속성 명
     * @param attributeValue 속성 값
     * @return BooleanExpression 검색 표현식
     */
    private BooleanExpression getBooleanExpression(String attributeName, Object attributeValue) {
        if (attributeValue == null || "".equals(attributeValue.toString())) return null;

        switch (attributeName) {
            case "rgtr": // 사용자 id
                return pst.rgtr.eq((String) attributeValue);
            /*case "ipAddr": // ip 주소
                return QPostsRead.postsRead.ipAddr.eq((String) attributeValue);*/
            case "delYn": // 삭제 여부
                return pst.delYn.eq((String) attributeValue);
            case "postsData": // 게시물 제목 + 내용
                return pst.pstTtl.containsIgnoreCase((String) attributeValue).or(pst.pstCn.containsIgnoreCase((String) attributeValue));
            case "postsTitle": // 게시물 제목
                return pst.pstTtl.containsIgnoreCase((String) attributeValue);
            case "postsContent": // 게시물 내용
                return pst.pstCn.containsIgnoreCase((String) attributeValue);
            case "postsNoLt": // 게시물 번호
                return pst.pstId.pstNo.lt((Integer) attributeValue);
            case "postsNoGt": // 게시물 번호
                return pst.pstId.pstNo.gt((Integer) attributeValue);
            default:
                return null;
        }
    }
        /**
         * 댓글 수 표현식
         *
         * @param deleteAt 삭제 여부
         * @return SimpleExpression<Long> 댓글 수 표현식
         */
   /* private SimpleExpression<Long> getCommentCountExpression(Integer deleteAt) {
        BooleanExpression deleteAtExpression = null;
        if (deleteAt != null) {
            deleteAtExpression = deleteAt == 0 ? QComment.comment.deleteAt.eq(0) : QComment.comment.deleteAt.ne(0);
        }

        return Expressions.as(new CaseBuilder()
                        .when(QBoard.board.commentUseAt.eq(true))
                        .then(JPAExpressions.select(ExpressionUtils.count(QComment.comment.commentId.commentNo))
                                .from(QComment.comment)
                                .where(QComment.comment.commentId.postsId.boardNo.eq(QPst.pst.postsId.boardNo)
                                        .and(QComment.comment.commentId.postsId.postsNo.eq(QPst.pst.postsId.postsNo))
                                        .and(QComment.comment.commentId.postsId.postsNo.eq(QPst.pst.postsId.postsNo))
                                        .and(deleteAtExpression)))
                        .otherwise(0L)
                , "commentCount");
    }*/




    /**
     * 게시판별 최근 게시물 목록 조회
     * <p>
     * JPQL 은 from 절에서 서브쿼리를 사용할 수 없어서 SQLQueryFactory 를 사용해 Native SQL 로 조회
     * MySQL8 부터는 ROW_NUMBER, RANK 함수를 지원, 탬플릿에서는 MySQL5.7 로 개발해서 mysql 변수를 사용하는 방법으로 조회
     * MySQL 문법이 포함되어있어서 다른 DBMS 를 사용하는 경우 수정 필요
     * <p>
     * 인프런 김영한 강사는 추천하지 않는다.
     * SqlQueryFactory는 저는 권장하지 않습니다. DB에서 메타데이터를 다 뽑아내서 생성해야 하는데... 너무 복잡하고 기능에 한계도 많습니다.
     * 따라서 JPA와 JPA용 Querydsl을 최대한 사용하고, 그래도 잘 안되는 부분은 네이티브 쿼리를 사용하는 것이 더 좋다 생각합니다.
     * <p>
     * 게시판별로 반복하여 게시물 조회하는 방법 등으로 JPQL 만을 사용해서 조회 가능
     *
     * @param boardNos   게시판 번호 목록
     * @param postsCount 게시물 수
     * @return List<PostsSimpleResponseDto> 게시물 응답 DTO List
     */
    @Transactional(readOnly = true)
    @Override
    public List<PstSimpleResponseDto> findAllByBoardNosLimitCount(List<Integer> boardNos, Integer postsCount) {
        // path 정의
        Path<Pst> postsPath = Expressions.path(Pst.class, "pst");
        NumberPath<Integer> boardNoPath = Expressions.numberPath(Integer.class, postsPath, "bbs_id");
        NumberPath<Long> postsNoPath = Expressions.numberPath(Long.class, postsPath, "pst_no");

        // 게시판번호, 로우넘 변수
        StringPath varPath = Expressions.stringPath("v");
        SQLQuery<Tuple> varSql = SQLExpressions.select(
                Expressions.stringTemplate("@boardNo := 0"),
                Expressions.stringTemplate("@rn := 0"));

        // 게시물 조회
        SQLQuery<Tuple> rownumSql = SQLExpressions
                .select(boardNoPath,
                        postsNoPath,
                        Expressions.stringPath(postsPath, "pst_ttl"),
                        Expressions.stringPath(postsPath, "pst_cn"),
                        Expressions.datePath(LocalDateTime.class, postsPath, "reg_dt"),
                        Expressions.stringTemplate("(CASE @boardNo WHEN pst.bbs_id THEN @rn := @rn + 1 ELSE @rn := 1 END)").as("rn"),
                        Expressions.stringTemplate("(@boardNo := pst.bbs_id)").as("boardNo"))
                .from(pst, postsPath)
                .innerJoin(varSql, varPath)
                .where(boardNoPath.in(boardNos)
                        .and(Expressions.stringPath( postsPath, "del_yn").eq("N")))
                .orderBy(boardNoPath.asc(), postsNoPath.desc());

        // 최근 게시물 조회
        return sqlQueryFactory
                .select(new QPstSimpleResponseDto(boardNoPath,
                        postsNoPath,
                        Expressions.stringPath(postsPath, "pst_ttl"),
                        Expressions.stringPath(postsPath, "pst_cn"),
                        Expressions.datePath(LocalDateTime.class, postsPath, "reg_dt")))
                .from(rownumSql, postsPath)
                .where(Expressions.numberPath(Integer.class, postsPath, "rn").loe(postsCount))
                .fetch();
    }

    /**
     * 게시물 상세 조회
     *
     * @param bbsId 게시판 번호
     * @param pstNo 게시물 번호
     * @param userId  사용자 id
     * @param ipAddr  ip 주소
     * @return PostsResponseDto 게시물 상세 응답 DTO
     */
    @Override
    @Transactional
    public PstResponseDto findById(Integer bbsId, Long pstNo, String userId, String ipAddr) {
        return jpaQueryFactory
                .select(
                        // 게시물
                        new QPstResponseDto(
                                pst.pstId.bbsId ,
                                pst.pstId.pstNo ,
                                pst.pstTtl  ,
                                pst.pstCn   ,
                                pst.inqNocs ,
                                pst.refUrl  ,
                                pst.popupYn ,
                                pst.upndFixYn ,
                                pst.prdPstgYn ,
                                pst.pstSeqNo  ,
                                pst.upPstNo   ,
                                pst.ansLoc    ,
                                pst.pstSortSeq ,
                                pst.pstPswd,
                                pst.delYn  ,
                                pst.delRsn ,
                                pst.wrtrNm ,
                                pst.wrtrCplc ,
                                pst.atflCd   ,
                                pst.regDt    ,
                                new QBbsMngResponseDto(
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
                                        QBbsMng.bbsMng.popupUseYn)
                                // 댓글 수
                                // getCommentCountExpression(),
                                // 조회 사용자의 게시물 조회 수(조회 수 증가 확인 용)
                                 ))
                .from(pst) // 게시물
                .innerJoin(QBbsMng.bbsMng).on(pst.pstId.bbsId.eq(QBbsMng.bbsMng.bbsId)) // 게시판
               // .leftJoin(QUser.user).on(QPosts.posts.createdBy.eq(QUser.user.userId)) // 생성자
                .fetchJoin()
                .where(pst.pstId.bbsId.eq(bbsId)
                        .and(pst.pstId.pstNo.eq(pstNo)))
                .fetchOne();
    }

    /**
     * 이전 게시물 조회
     *
     * @param boardNo    게시판 번호
     * @param postsNo    게시물 번호
     * @param gap        차이 -1: 이전, 1: 이후
     * @param delYn   삭제 여부
     * @param requestDto 요청 DTO
     * @return List<PostsSimpleResponseDto> 게시물 상세 응답 DTO List
     */
    @Override
    public List<PstSimpleResponseDto> findNearPst(Integer boardNo, Long postsNo, long gap, String delYn, RequestDto requestDto) {
        return jpaQueryFactory
                .select(new QPstSimpleResponseDto(
                        pst.pstId.bbsId,
                        pst.pstId.pstNo,
                        pst.pstTtl,
                        pst.pstCn,
                        pst.regDt ))
                .from(pst)
                .where(pst.pstId.bbsId.eq(boardNo)
                        .and(getBooleanExpression(requestDto.getKeywordType(), requestDto.getKeyword()))
                        .and(getBooleanExpression("delYn", delYn))
                        .and(getBooleanExpression(gap < 0 ? "postsNoLt" : "postsNoGt", postsNo)))
                .orderBy(
                        //gap < 0 ? QPosts.posts.noticeAt.asc() : QPosts.posts.noticeAt.desc(),
                        pst.pstId.bbsId.asc(),
                        gap < 0 ? pst.pstId.pstNo.desc() : pst.pstId.pstNo.asc())
                .limit((gap < 0 ? -1 : 1) * gap)
                // .fetchFirst() // 단건 리턴
                .fetch();
    }

    /**
     * 다음 게시물 번호 조회
     *
     * @param bbsId 게시판 번호
     * @return Integer 다음 게시물 번호
     */
    @Override
    public Long findNextPstNo(Integer bbsId) {
        return jpaQueryFactory
                .select(pst.pstId.pstNo.max().add(1).coalesce(1L))
                .from(pst)
                .where(pst.pstId.bbsId.eq(bbsId))
                .fetchOne();
    }

        /**
         * 게시물 조회 수 증가
         *
         * @param bbsId 게시판 번호
         * @param pstNo 게시물 번호
         * @return Long 처리 건수
         */
        @Override
        public Long updateReadCount (Integer bbsId, Long pstNo){
            return jpaQueryFactory.update(pst)
                    .set(pst.inqNocs, pst.inqNocs.add(1L))
                    .where(pst.pstId.bbsId.eq(bbsId)
                            .and(pst.pstId.pstNo.eq(pstNo)))
                    .execute();
        }

        /**
         * 게시물 삭제 여부 수정
         *
         * @param posts  게시물 정보(게시판번호, 게시물번호배열)
         * @param delYn  삭제 여부
         * @param userId 사용자 id
         * @return Long 처리 건수
         */
        @Override
    public Long updateDelYn(Map<Integer, List<Long>> posts, String  delYn, String userId) {
        long updateCount = 0L;

        Iterator<Integer> iterator = posts.keySet().iterator();
        while (iterator.hasNext()) {
            Integer bbsId = iterator.next();

            List<Long> postsNoList = posts.get(bbsId);

            updateCount += jpaQueryFactory.update(pst)
                            .set(pst.delYn, delYn)
                            .set(pst.updr, userId)
                            .set(pst.updtDt, LocalDateTime.now())
                            .where(pst.pstId.bbsId.eq(bbsId)
                                    .and(pst.pstId.pstNo.in(postsNoList)))
                            .execute();
        }

        return updateCount;
    }
//===================.  이전 체크 로직을 위해 생성한 부분 25.01.23 ============

    @Override
        public Page<Pst> findAllBySearchCondition (Integer bbsId, String searchType, String searchKeyword, Pageable
        pageable){
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(pst.pstId.bbsId.eq(bbsId));
            builder.and(pst.delYn.eq("N"));

            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                switch (searchType) {
                    case "title":
                        builder.and(pst.pstTtl.contains(searchKeyword));
                        break;
                    case "content":
                        builder.and(pst.pstCn.contains(searchKeyword));
                        break;
                    case "writer":
                        builder.and(pst.wrtrNm.contains(searchKeyword));
                        break;
                    case "all":
                    default:
                        builder.andAnyOf(
                                pst.pstTtl.contains(searchKeyword),
                                pst.pstCn.contains(searchKeyword),
                                pst.wrtrNm.contains(searchKeyword)
                        );
                        break;
                }
            }

            List<Pst> content = jpaQueryFactory
                    .selectFrom(pst)
                    .where(builder)
                    //.orderBy(pst.upndFixYn.desc(), pst.pstSortSeq.desc())
                    .orderBy(pst.pstId.pstNo.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            Long total = jpaQueryFactory
                    .select(pst.count())
                    .from(pst)
                    .where(builder)
                    .fetchOne();

            return new PageImpl<>(content, pageable, total != null ? total : 0L);
        }

        @Override
        public Long getNextPstNo (Integer bbsId){
            Long maxPstNo = jpaQueryFactory
                    .select(pst.pstId.pstNo.max())
                    .from(pst)
                    .where(pst.pstId.bbsId.eq(bbsId))
                    .fetchOne();
            return maxPstNo != null ? maxPstNo + 1 : 1L;
        }

        @Override
        public Long getNextPstSeqNo (Integer bbsId){
            Long maxPstSeqNo = jpaQueryFactory
                    .select(pst.pstSeqNo.max())
                    .from(pst)
                    .where(pst.pstId.bbsId.eq(bbsId))
                    .fetchOne();
            return maxPstSeqNo != null ? maxPstSeqNo + 1 : 1L;
        }

        @Override
        public Long getNextPstSortSeq (Integer bbsId, Long upPstNo){
            if (upPstNo == null) {
                // 새글인 경우
                Long maxPstSortSeq = jpaQueryFactory
                        .select(pst.pstSortSeq.max())
                        .from(pst)
                        .where(pst.pstId.bbsId.eq(bbsId))
                        .fetchOne();
                return maxPstSortSeq != null ? maxPstSortSeq + 1 : 1L;
            } else {
                // 답글인 경우
                Long parentSortSeq = jpaQueryFactory
                        .select(pst.pstSortSeq)
                        .from(pst)
                        .where(pst.pstId.bbsId.eq(bbsId)
                                .and(pst.pstId.pstNo.eq(upPstNo)))
                        .fetchOne();

                if (parentSortSeq == null) {
                    throw new IllegalArgumentException("상위 게시물이 존재하지 않습니다.");
                }

                // 같은 그룹 내 마지막 답글의 정렬 순서 조회
                Long lastChildSortSeq = jpaQueryFactory
                        .select(pst.pstSortSeq.max())
                        .from(pst)
                        .where(pst.pstId.bbsId.eq(bbsId)
                                .and(pst.upPstNo.eq(upPstNo)))
                        .fetchOne();

                return lastChildSortSeq != null ? lastChildSortSeq + 1 : parentSortSeq + 1;
            }
        }
}
