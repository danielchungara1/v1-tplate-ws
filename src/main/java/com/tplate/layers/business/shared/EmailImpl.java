package com.tplate.layers.business.shared;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class EmailImpl implements IEmail {

    private String to;

    private Map<String, Object> data;

    // Custom Setter for Builder
    public static class EmailImplBuilder {

        public EmailImpl.EmailImplBuilder data(String resetCode){
            this.data = ImmutableMap.<String, Object>builder()
                    .put("resetCode", resetCode)
                    .build();
            return this;
        }

    }

    @Override
    public String getSubject() {
        return "Reset Code for change password.";
    }

}
