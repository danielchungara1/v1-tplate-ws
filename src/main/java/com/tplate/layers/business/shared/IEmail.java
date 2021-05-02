package com.tplate.layers.business.shared;

import java.util.Map;

public interface IEmail {

    String getTo();
    Map<String, Object> getData();
    String getSubject();

}
