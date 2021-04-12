package com.tplate.old.util;

import com.tplate.old.models.Minutes;
import com.tplate.old.constants.Time;

public class TimeUtil {
    public static Long toMiliseconds(Minutes minutes){
        return 1L * minutes.getValue()  * Time.SECONDS_IN_MINUTE * Time.MILLISECONDS_IN_MINUTE;
    }
}
