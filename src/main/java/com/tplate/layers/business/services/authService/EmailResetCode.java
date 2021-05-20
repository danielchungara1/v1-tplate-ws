package com.tplate.layers.business.services.authService;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class EmailResetCode implements IEmail {

    private String to;

    private Map<String, Object> data;

    private static String RESET_CODE = "resetCode";

    public static String SUBJECT = "Reset Code for change password.";

    // Custom Setter for Builder
    public static class EmailResetCodeBuilder {

        public EmailResetCode.EmailResetCodeBuilder data(String resetCode){
            this.data = ImmutableMap.<String, Object>builder()
                    .put(RESET_CODE, resetCode)
                    .build();
            return this;
        }

    }

    @Override
    public String getSubject() {
        return SUBJECT;
    }

}
