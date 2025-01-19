package org.egovframe.cloud.cmsservice.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PstRepositoryCustom {
    Page<Pst> findAllBySearchCondition(String bbsId, String searchType, String searchKeyword, Pageable pageable);
    Long getNextPstNo(String bbsId);
    Long getNextPstSeqNo(String bbsId);
    Long getNextPstSortSeq(String bbsId, Long upPstNo);
}
