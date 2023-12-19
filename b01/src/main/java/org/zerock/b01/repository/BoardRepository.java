package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.List;

//기본 쿼리
public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {  //table, 기본키 타입

    

    List<Board> findByWriter(String Writer);

    List<Board> findByTitleAndWriter(String Title, String Writer);

    List<Board> findByTitleLike(String Title);

    @Query("select b from Board b where b.writer = :writer")//jpql
    //@Query(value = "select * from board where title like %:title% order by bno desc", nativeQuery = true)
    List<Board> findByWriter2(@Param("writer")String writer);

    @Query("select b from Board b where b.title like %:title% order by b.bno desc")
    List<Board> findByTitle(@Param("title")String title);

    @Query("select b from Board b where b.title like concat('%',:keyword,'%') ")
    Page<Board> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


}
