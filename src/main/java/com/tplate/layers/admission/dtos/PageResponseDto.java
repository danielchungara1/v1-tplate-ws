package com.tplate.layers.admission.dtos;

import com.tplate.layers.admission.dtos.role.RoleResponseDto;
import lombok.Data;

import java.util.List;

/**
 * Output DTO. Must not have javax validation.
 */

@Data
public class PageResponseDto<T> {
    private List<T> content;

    // General Metadata
    private Integer totalPages;
    private Integer totalElements;
    private Integer numberOfElements;

    // Page Metadata
    private Integer size;
    private Integer number;
    private boolean first;
    private boolean last;
    private boolean empty;
}
