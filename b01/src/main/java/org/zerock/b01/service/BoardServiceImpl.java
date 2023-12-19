package org.zerock.b01.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Long register(BoardDTO boardDTO) {
        //modelmapper(data, mapping type)
        Board board = modelMapper.map(boardDTO, Board.class);
//        Board board = boardDTOTOEntitiy(boardDTO);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Board board = boardRepository.findById(bno).orElseThrow();
        BoardDTO boardDTO = modelMapper.map(board,BoardDTO.class);
        //BoardDTO boardDTO= entitiyToDto(board);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getBno()).orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());

        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        //paging 에 필요한 조건들 미리 세팅
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        //페이징
        Page<Board> result= boardRepository.searchAll(types,keyword,pageable);
//        result.getTotalPages();
//        result.getContent();
        //전체 페이지 컨텐츠를 형변환 시켜주기 위해 stream 사용
        List<BoardDTO> dtoList = result.getContent().stream().map(board->modelMapper.map(board,BoardDTO.class)).collect(Collectors.toList());

        //리턴에 builder(with all)생성자 사용-> 페이징 계산
        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build(); //PageResponseDTO<BoardDTO>
    }
}
