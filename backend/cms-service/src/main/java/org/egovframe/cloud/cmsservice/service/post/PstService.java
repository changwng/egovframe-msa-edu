package org.egovframe.cloud.cmsservice.service.post;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.post.dto.PstResponseDto;
import org.egovframe.cloud.cmsservice.api.post.dto.PstSaveRequestDto;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMngRepository;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;
import org.egovframe.cloud.cmsservice.domain.post.PstRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PstService {

    private final PstRepository pstRepository;
    private final BbsMngRepository bbsMngRepository;

    @Transactional(readOnly = true)
    public Page<PstResponseDto> findAll(Integer bbsId, Pageable pageable) {
        // TODO: Implement findAll with bbsId filter
        return pstRepository.findAll(pageable)
                .map(PstResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<PstResponseDto> findAll(Integer bbsId, String searchType, String searchKeyword, Pageable pageable) {
        return pstRepository.findAllBySearchCondition(bbsId, searchType, searchKeyword, pageable)
                .map(PstResponseDto::new);
    }

    @Transactional(readOnly = true)
    public PstResponseDto findById(Integer bbsId, Long pstNo) {
        Pst entity = findPstById(bbsId, pstNo);
        entity.increaseInqNocs(); // 조회수 증가
        return new PstResponseDto(entity);
    }

    @Transactional
    public PstId save(Integer bbsId, PstSaveRequestDto requestDto) {
        BbsMng bbsMng = findBbsMngById(bbsId);

        // 첨부파일 사용 여부 확인
        if (requestDto.getAtflCd() != null && !bbsMng.isAtflAllowed()) {
            throw new IllegalStateException("첨부파일을 사용할 수 없는 게시판입니다.");
        }

        // 게시물 번호 생성
        Long pstNo = generatePstNo(bbsId);
        PstId pstId = new PstId(bbsId, pstNo);

        // 게시물 순서 계산
        Long pstSeqNo = generatePstSeqNo(bbsId);
        Long pstSortSeq = generatePstSortSeq(bbsId);

        Pst pst = requestDto.toEntity(bbsMng, pstId, pstSeqNo, pstSortSeq);
        pst = pstRepository.save(pst);
        return pst.getPstId();
    }

    @Transactional
    public PstId reply(Integer bbsId, Long upPstNo, PstSaveRequestDto requestDto) {
        // 상위 게시물 조회
        Pst parentPst = findPstById(bbsId, upPstNo);
        BbsMng bbsMng = parentPst.getBbsMng();

        // 답변 사용 여부 확인
        if (!bbsMng.isAnsAllowed()) {
            throw new IllegalStateException("답변을 사용할 수 없는 게시판입니다.");
        }

        // 첨부파일 사용 여부 확인
        if (requestDto.getAtflCd() != null && !bbsMng.isAtflAllowed()) {
            throw new IllegalStateException("첨부파일을 사용할 수 없는 게시판입니다.");
        }

        // 게시물 번호 생성
        Long pstNo = generatePstNo(bbsId);
        PstId pstId = new PstId(bbsId, pstNo);

        // 게시물 순서 계산
        Long pstSeqNo = generatePstSeqNo(bbsId);
        Long pstSortSeq = generatePstSortSeq(bbsId, upPstNo);

        // 답변 위치 계산 (부모 답변 위치 + 1)
        Integer ansLoc = parentPst.getAnsLoc() + 1;

        Pst pst = requestDto.toEntity(bbsMng, pstId, pstSeqNo, pstSortSeq);
        pst.setUpPstNo(upPstNo);
        pst.setAnsLoc(ansLoc);
        
        pst = pstRepository.save(pst);
        return pst.getPstId();
    }

    @Transactional
    public PstId update(Integer bbsId, Long pstNo, PstSaveRequestDto requestDto) {
        Pst entity = findPstById(bbsId, pstNo);
        BbsMng bbsMng = entity.getBbsMng();

        // 첨부파일 사용 여부 확인
        if (requestDto.getAtflCd() != null && !bbsMng.isAtflAllowed()) {
            throw new IllegalStateException("첨부파일을 사용할 수 없는 게시판입니다.");
        }

        entity.update(
            requestDto.getPstTtl(),
            requestDto.getPstCn(),
            requestDto.getRefUrl(),
            requestDto.getPopupYn(),
            requestDto.getUpndFixYn(),
            requestDto.getPrdPstgYn(),
            requestDto.getWrtrNm(),
            requestDto.getWrtrCplc(),
            requestDto.getAtflCd()
        );

        return entity.getPstId();
    }

    @Transactional
    public void delete(Integer bbsId, Long pstNo, String delRsn) {
        Pst entity = findPstById(bbsId, pstNo);
        entity.delete(delRsn);
    }

    private Pst findPstById(Integer bbsId, Long pstNo) {
        return pstRepository.findById(new PstId(bbsId, pstNo))
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. bbsId=" + bbsId + ", pstNo=" + pstNo));
    }

    private BbsMng findBbsMngById(Integer bbsId) {
        return bbsMngRepository.findById(bbsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. bbsId=" + bbsId));
    }

    private Long generatePstNo(Integer bbsId) {
        return pstRepository.getNextPstNo(bbsId);
    }

    private Long generatePstSeqNo(Integer bbsId) {
        return pstRepository.getNextPstSeqNo(bbsId);
    }

    private Long generatePstSortSeq(Integer bbsId) {
        return pstRepository.getNextPstSortSeq(bbsId, null);
    }

    private Long generatePstSortSeq(Integer bbsId, Long upPstNo) {
        return pstRepository.getNextPstSortSeq(bbsId, upPstNo);
    }
}
