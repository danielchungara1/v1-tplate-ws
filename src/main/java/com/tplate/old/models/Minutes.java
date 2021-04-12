package com.tplate.old.models;

import lombok.Data;

@Data
public class Minutes {
    private Long value;

    public Minutes(Long value) {
        this.value = value;
    }
}
