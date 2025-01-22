package org.egovframe.cloud.cmsservice.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PstRepositoryCustom {
    Page<Pst> findAllBySearchCondition(Integer bbsId, String searchType, String searchKeyword, Pageable pageable);
    Long getNextPstNo(Integer bbsId);
    Long getNextPstSeqNo(Integer bbsId);
    Long getNextPstSortSeq(Integer bbsId, Long upPstNo);
}
