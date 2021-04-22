package com.tplate.layers.access.dtos;

import lombok.Data;

import java.util.List;

/**
 * Output DTO. Must not have javax validation.
 */

@Data
public class ResponsePageDto<T> {
    private List<T> content;

    // General Metadata
    private Integer totalPages;
    private Integer totalElements;

    // Page Metadata
    private Integer numberOfElements;
    private Integer size;
    private Integer number;
    private boolean first;
    private boolean last;
    private boolean empty;
}
