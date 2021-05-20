package com.tplate.layers.business.services.authService;

import java.util.Map;

public interface IEmail {

    String getTo();
    Map<String, Object> getData();
    String getSubject();

}
