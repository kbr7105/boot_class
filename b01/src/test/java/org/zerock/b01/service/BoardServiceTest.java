package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO boardDTO = BoardDTO.builder()
                .title("테스트 제목")
                .content("test")
                .writer("test writer")
                .build();
        Long bno = boardService.register(boardDTO);
        log.info("bno>>"+bno);
    }

    @Test
    public void testReadOne(){
        BoardDTO boardDTO = boardService.readOne(201L);
        log.info("boardDto>>>"+boardDTO);
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(201L)
                .title("수정제목")
                .content("수정내용")
                .build();
        boardService.modify(boardDTO);
    }

    @Test
    public void testDelete(){
        boardService.remove(201L);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO= boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }

}