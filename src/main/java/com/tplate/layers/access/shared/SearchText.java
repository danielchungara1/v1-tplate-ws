package com.tplate.layers.access.shared;

import lombok.Data;

@Data
public class SearchText {

    private String text;

    public String getText() {
        return text == null ? "" : this.text.toLowerCase();
    }

}
