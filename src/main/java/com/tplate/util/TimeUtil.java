package com.tplate.util;

import com.tplate.constants.Time;
import com.tplate.models.Minutes;

public class TimeUtil {
    public static Long toMiliseconds(Minutes minutes){
        return 1L * minutes.getValue()  * Time.SECONDS_IN_MINUTE * Time.MILLISECONDS_IN_MINUTE;
    }
}
