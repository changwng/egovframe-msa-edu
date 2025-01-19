package org.egovframe.cloud.cmsservice.service.bbs;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngResponseDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngSaveRequestDto;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMngRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BbsMngService {

    private final BbsMngRepository bbsMngRepository;

    @Transactional(readOnly = true)
    public Page<BbsMngResponseDto> findAll(String searchText, Pageable pageable) {
        return bbsMngRepository.findAll(pageable)
                .map(BbsMngResponseDto::new);
    }

    @Transactional(readOnly = true)
    public BbsMngResponseDto findById(String bbsId) {
        BbsMng entity = findBbsMngById(bbsId);
        return new BbsMngResponseDto(entity);
    }

    @Transactional
    public String save(BbsMngSaveRequestDto requestDto) {
        String bbsId = generateBbsId();
        BbsMng bbsMng = requestDto.toEntity();
        bbsMng = bbsMngRepository.save(bbsMng);
        return bbsMng.getBbsId();
    }

    @Transactional
    public String update(String bbsId, BbsMngSaveRequestDto requestDto) {
        BbsMng entity = findBbsMngById(bbsId);
        
        entity.update(
            requestDto.getBbsTypeCd(),
            requestDto.getBbsNm(),
            requestDto.getBbsExpln(),
            requestDto.getRefUseYn(),
            requestDto.getAtflUseYn(),
            requestDto.getAtflMaxCnt(),
            requestDto.getAtflMaxFlsz(),
            requestDto.getAnsUseYn(),
            requestDto.getCmntUseYn(),
            requestDto.getDgstfnEvlUseYn(),
            requestDto.getSmsUseYn(),
            requestDto.getUpndFixUseYn(),
            requestDto.getPstgPrdUseYn(),
            requestDto.getPopupUseYn()
        );
        
        return entity.getBbsId();
    }

    @Transactional
    public void delete(String bbsId) {
        BbsMng entity = findBbsMngById(bbsId);
        bbsMngRepository.delete(entity);
    }

    private BbsMng findBbsMngById(String bbsId) {
        return bbsMngRepository.findById(bbsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다. bbsId=" + bbsId));
    }

    private String generateBbsId() {
        return "BBS_" + UUID.randomUUID().toString().substring(0, 6);
    }
}
