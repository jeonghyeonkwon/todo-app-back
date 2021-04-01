package com.givejeong.todo.dto.board;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ListFormDto<T> {
    private int totalPage;
    private long totalElements;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;
    private T list;
    public ListFormDto(Page<T> page , T list){
        this.totalPage = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
        this.list  = list;
    }
}
