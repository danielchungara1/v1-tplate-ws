package com.tplate.layers.access.filters;

import lombok.Data;

@Data
public class SearchText {

    private String text;

    public String getText() {
        return text == null ? "" : this.text.toLowerCase();
    }

}
