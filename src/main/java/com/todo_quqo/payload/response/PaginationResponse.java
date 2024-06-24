package com.todo_quqo.payload.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationResponse<T> {

    private List<T> data;

    private Integer pageSize;

    private Long pageNumber;

    private Long totalElements;

    private Long totalPages;

    public static <T> PaginationResponse<T> toPaginationResponse(Page<T> page) {
        PaginationResponse<T> response = new PaginationResponse<>();

        if (!page.isEmpty()) {
            response.setData(page.getContent());
        } else {
            response.setData(new ArrayList<>());
        }
        response.setPageSize(page.getSize());
        response.setPageNumber(Long.parseLong(page.getNumber() + ""));
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(Long.parseLong(page.getTotalPages() + ""));

        return response;
    }

}