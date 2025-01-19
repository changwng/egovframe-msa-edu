package org.egovframe.cloud.cmsservice.domain.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.egovframe.cloud.cmsservice.domain.post.QPst.pst;

@RequiredArgsConstructor
public class PstRepositoryImpl implements PstRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Pst> findAllBySearchCondition(String bbsId, String searchType, String searchKeyword, Pageable pageable) {
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

        List<Pst> content = queryFactory
                .selectFrom(pst)
                .where(builder)
                .orderBy(pst.upndFixYn.desc(), pst.pstSortSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(pst.count())
                .from(pst)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    @Override
    public Long getNextPstNo(String bbsId) {
        Long maxPstNo = queryFactory
                .select(pst.pstId.pstNo.max())
                .from(pst)
                .where(pst.pstId.bbsId.eq(bbsId))
                .fetchOne();
        return maxPstNo != null ? maxPstNo + 1 : 1L;
    }

    @Override
    public Long getNextPstSeqNo(String bbsId) {
        Long maxPstSeqNo = queryFactory
                .select(pst.pstSeqNo.max())
                .from(pst)
                .where(pst.pstId.bbsId.eq(bbsId))
                .fetchOne();
        return maxPstSeqNo != null ? maxPstSeqNo + 1 : 1L;
    }

    @Override
    public Long getNextPstSortSeq(String bbsId, Long upPstNo) {
        if (upPstNo == null) {
            // 새글인 경우
            Long maxPstSortSeq = queryFactory
                    .select(pst.pstSortSeq.max())
                    .from(pst)
                    .where(pst.pstId.bbsId.eq(bbsId))
                    .fetchOne();
            return maxPstSortSeq != null ? maxPstSortSeq + 1 : 1L;
        } else {
            // 답글인 경우
            Long parentSortSeq = queryFactory
                    .select(pst.pstSortSeq)
                    .from(pst)
                    .where(pst.pstId.bbsId.eq(bbsId)
                            .and(pst.pstId.pstNo.eq(upPstNo)))
                    .fetchOne();

            if (parentSortSeq == null) {
                throw new IllegalArgumentException("상위 게시물이 존재하지 않습니다.");
            }

            // 같은 그룹 내 마지막 답글의 정렬 순서 조회
            Long lastChildSortSeq = queryFactory
                    .select(pst.pstSortSeq.max())
                    .from(pst)
                    .where(pst.pstId.bbsId.eq(bbsId)
                            .and(pst.upPstNo.eq(upPstNo)))
                    .fetchOne();

            return lastChildSortSeq != null ? lastChildSortSeq + 1 : parentSortSeq + 1;
        }
    }
}
