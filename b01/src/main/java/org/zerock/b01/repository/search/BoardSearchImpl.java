package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board; //객체 생성
        JPQLQuery<Board> query = from(board); //select * from board
        query.where(board.title.contains("1")); //where title like %'1'%

        //paging
        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> list = query.fetch();
        Long count = query.fetchCount();
        log.info("count>>"+count);
        list.forEach(board1->log.info(board1));
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);
        //조건 연산자
        if((types != null && types.length>0)&& keyword!= null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c"  :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case  "w" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0L)); //> greaterThan
        log.info("query=>"+query);
        //페이징
        this.getQuerydsl().applyPagination(pageable,query);

        List<Board> list = query.fetch();
        Long count = query.fetchCount();

        log.info("count>>"+count);
        list.forEach(board1->log.info(board1));
        return new PageImpl<>(list, pageable, count);
    }
}
