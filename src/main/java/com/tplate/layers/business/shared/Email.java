package com.tplate.layers.business.shared;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Builder
@Data
public class Email {
    private String to;
    private String resetCode;
    private String subject;
    private Map<String, Object> data;
}
