package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    //시작페이지, 마지막 페이지
    private int start;
    private int end;

    //이전페이지, 다음페이지
    private boolean prev;
    private boolean next;

    //전체 데이터
    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){
        if(total <=0){
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page/10.0));
        this.start = this.end -9;
        int last = (int)(Math.ceil((total/(double)size)));

        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end*this.size;
    }
}
