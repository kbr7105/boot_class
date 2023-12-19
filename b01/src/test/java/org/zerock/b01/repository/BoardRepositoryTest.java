package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title"+i)
                    .content("content"+i)
                    .writer("writer"+(i%10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("bno>>"+result.getBno());
        });
    }

    @Test
    public void testSelect(){
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        //Board board = boardRepository.findById(bno).orElseThrow();
        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;
        Board board = boardRepository.findById(bno).orElseThrow(); //select
        board.change("update","update contents");
        boardRepository.save(board); //select update jpa에서는 조회 후 기존값과 다르면 자동으로 값을 업데이트
    }

    @Test
    public void testDelete(){
        boardRepository.deleteById(100L);
    }

    @Test
    public void testGetList(){
        boardRepository.findAll().forEach(list->log.info(list));
        //List<Board> list = boardRepository.findAll();
        //for(Board b : list)log.info(b);
    }

    @Test
    public void testPaging(){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        log.info(result.getTotalElements());//전체 개수
        log.info(result.getTotalPages());//페이지 개수
        result.getContent().forEach(list->log.info(list));

    }

    @Test
    public void testWriter(){
        List<Board> board = boardRepository.findByWriter("writer9");
        board.forEach(list->log.info(list));
    }

    @Test
    public void testTitleAndWriter(){
        List<Board> board = boardRepository.findByTitleAndWriter("title9","writer9");
        board.forEach(list->log.info(list));
    }

    @Test
    public void testWriterLike(){
        List<Board> board = boardRepository.findByTitleLike("%1%");
        board.forEach(list->log.info(list));
    }

    @Test
    public void testWriter2(){
        List<Board> board = boardRepository.findByWriter2("writer9");
        board.forEach(list->log.info(list));
    }


    @Test
    public void testTitleQuery(){
        boardRepository.findByTitle("1").forEach(list->log.info(list));
    }

    @Test
    public void testKeyword(){
       Pageable pageable = (Pageable) PageRequest.of(0,10, Sort.by("bno").descending());
       Page<Board> result = boardRepository.findByKeyword("1",pageable);
       log.info(result.getTotalElements());
       log.info(result.getTotalPages());
       result.getContent().forEach(board -> log.info(board));
    }
}
