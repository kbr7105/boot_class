package org.zerock.b01.service;

import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    //boarddto -> board 로 형변환 default 로 override 하지않고 바로 사용할 수 잇도록 함
    //유지보수때 추가되는 메소드의 경우 default로 다른 메소드에서 사용여부를 자동 결정할 수 잇도록 해줌
    default Board boardDTOTOEntitiy(BoardDTO boardDTO){
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        return board;
    }

    BoardDTO readOne(Long bno);

    default BoardDTO entitiyToDto(Board board){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getTitle())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
        return boardDTO;
    }

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
