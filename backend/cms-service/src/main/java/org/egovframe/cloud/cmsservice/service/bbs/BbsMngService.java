package org.egovframe.cloud.cmsservice.service.bbs;

import lombok.RequiredArgsConstructor;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngListResponseDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngResponseDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngSaveRequestDto;
import org.egovframe.cloud.cmsservice.api.bbs.dto.BbsMngUpdateRequestDto;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMng;
import org.egovframe.cloud.cmsservice.domain.bbs.BbsMngRepository;
import org.egovframe.cloud.common.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.egovframe.cloud.common.dto.RequestDto;
import org.egovframe.cloud.common.exception.BusinessMessageException;
import org.egovframe.cloud.common.service.AbstractService;
import java.util.List;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BbsMngService extends AbstractService {

    private final BbsMngRepository bbsMngRepository;

    @Transactional(readOnly = true)
    public Page<BbsMngResponseDto> findAll(String searchText, Pageable pageable) {
        return bbsMngRepository.findAll(pageable)
                .map(BbsMngResponseDto::new);
    }

    @Transactional(readOnly = true)
    public BbsMngResponseDto findByIdDto(Integer bbsId) {
        BbsMng entity = findBbsMngById(bbsId);
        return new BbsMngResponseDto(entity);
    }

    @Transactional
    public BbsMngResponseDto save(BbsMngSaveRequestDto requestDto) {

        BbsMng bbsMng = bbsMngRepository.save(requestDto.toEntity());
        return new BbsMngResponseDto(bbsMng);
    }

    @Transactional
    public BbsMngResponseDto update(Integer bbsId, BbsMngUpdateRequestDto  requestDto) {
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
        
        return new BbsMngResponseDto(entity);
    }
    /**
     * 게시판 삭제
     *
     * @param bbsId 게시판 번호
     */
    @Transactional
    public void delete(Integer bbsId) {
        BbsMng entity = findBbsMngById(bbsId);
        bbsMngRepository.delete(entity);
    }

    private BbsMng findBbsMngById(Integer bbsId) {

        return bbsMngRepository.findById(bbsId)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("valid.notexists.format", new Object[]{getMessage("board")})));


    }


    /**
     * 조회 조건에 일치하는 게시판 페이지 목록 조회
     *
     * @param requestDto 요청 DTO
     * @param pageable   페이지 정보
     * @return Page<BbsMngListResponseDto> 페이지 게시판 목록 응답 DTO
     */
    public Page<BbsMngListResponseDto> findPage(RequestDto requestDto, Pageable pageable) {
        return bbsMngRepository.findPage(requestDto, pageable);
    }
    /**
     * 게시판 목록 조회
     *
     * @param boardNos 게시판 번호 목록
     * @return List<BbsMngResponseDto> 게시판 상세 응답 DTO List
     */
    public List<BbsMngResponseDto> findAllByBoardNos(List<Integer> boardNos) {
        return bbsMngRepository.findAllByBoardNoIn(boardNos);
    }


    /**
     * 게시판 단건 조회
     *
     * @param bbsId 게시판 번호
     * @return BbsMngResponseDto 게시판 응답 DTO
     */
    public BbsMngResponseDto findById(Integer bbsId) {
        BbsMng entity = findBoard(bbsId);

        return new BbsMngResponseDto(entity);
    }


    /**
     * 게시판 번호로 게시판 엔티티 조회
     *
     * @param bbsId 게시판 번호
     * @return Board 게시판 엔티티
     */
    private BbsMng findBoard(Integer bbsId) throws EntityNotFoundException {
        return bbsMngRepository.findById(bbsId)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("valid.notexists.format", new Object[]{getMessage("board")})));
    }

}
