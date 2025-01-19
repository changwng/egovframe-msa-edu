package org.egovframe.cloud.cmsservice.service.comment;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.comment.dto.PstCmntResponseDto;
import org.egovframe.cloud.cmsservice.api.comment.dto.PstCmntSaveRequestDto;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmnt;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmntId;
import org.egovframe.cloud.cmsservice.domain.comment.PstCmntRepository;
import org.egovframe.cloud.cmsservice.domain.post.Pst;
import org.egovframe.cloud.cmsservice.domain.post.PstId;
import org.egovframe.cloud.cmsservice.domain.post.PstRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PstCmntService {

    private final PstCmntRepository pstCmntRepository;
    private final PstRepository pstRepository;

    @Transactional(readOnly = true)
    public Page<PstCmntResponseDto> findAll(String bbsId, Long pstNo, Pageable pageable) {
        // TODO: Implement findAll with bbsId and pstNo filter
        return pstCmntRepository.findAll(pageable)
                .map(PstCmntResponseDto::new);
    }

    @Transactional(readOnly = true)
    public PstCmntResponseDto findById(String bbsId, Long pstNo, Long cmntNo) {
        PstCmnt entity = findPstCmntById(bbsId, pstNo, cmntNo);
        return new PstCmntResponseDto(entity);
    }

    @Transactional
    public PstCmntId save(String bbsId, Long pstNo, PstCmntSaveRequestDto requestDto) {
        Pst pst = findPstById(bbsId, pstNo);
        BbsMng bbsMng = pst.getBbsMng();

        // 댓글 사용 여부 확인
        if (!bbsMng.isCmntAllowed()) {
            throw new IllegalStateException("댓글을 사용할 수 없는 게시판입니다.");
        }

        // 댓글 번호 생성
        Long cmntNo = generateCmntNo(bbsId, pstNo);
        PstCmntId pstCmntId = new PstCmntId(bbsId, pstNo, cmntNo);

        PstCmnt pstCmnt = requestDto.toEntity(pstCmntId, pst);
        pstCmnt = pstCmntRepository.save(pstCmnt);
        return pstCmnt.getPstCmntId();
    }

    @Transactional
    public PstCmntId update(String bbsId, Long pstNo, Long cmntNo, PstCmntSaveRequestDto requestDto) {
        PstCmnt entity = findPstCmntById(bbsId, pstNo, cmntNo);
        
        entity.update(
            requestDto.getCmntCn(),
            requestDto.getWrtrNm()
        );

        return entity.getPstCmntId();
    }

    @Transactional
    public void delete(String bbsId, Long pstNo, Long cmntNo) {
        PstCmnt entity = findPstCmntById(bbsId, pstNo, cmntNo);
        entity.delete();
    }

    private PstCmnt findPstCmntById(String bbsId, Long pstNo, Long cmntNo) {
        return pstCmntRepository.findById(new PstCmntId(bbsId, pstNo, cmntNo))
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. bbsId=" + bbsId + ", pstNo=" + pstNo + ", cmntNo=" + cmntNo));
    }

    private Pst findPstById(String bbsId, Long pstNo) {
        return pstRepository.findById(new PstId(bbsId, pstNo))
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. bbsId=" + bbsId + ", pstNo=" + pstNo));
    }

    private Long generateCmntNo(String bbsId, Long pstNo) {
        // TODO: Implement comment number generation logic
        return 1L;
    }
}
