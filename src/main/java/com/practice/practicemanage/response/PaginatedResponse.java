package com.practice.practicemanage.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> data;
    private long totalElements;
    private int totalPages;

    public PaginatedResponse(List<T> data, long totalElements, int totalPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

}
