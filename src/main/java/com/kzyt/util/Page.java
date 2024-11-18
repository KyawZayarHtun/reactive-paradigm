package com.kzyt.util;

import lombok.Getter;

import java.util.List;

@Getter
public class Page<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final long filterElements;
    private final int totalPages;

    public Page(List<T> content, int page, int size, long totalElements, long filterElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.filterElements = filterElements;
        this.totalPages = (int) Math.ceil((double) filterElements / size);
    }

}
