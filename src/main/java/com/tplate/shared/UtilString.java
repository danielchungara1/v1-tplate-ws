package com.tplate.shared;

import java.util.Arrays;
import java.util.regex.Pattern;

public class UtilString {

    /**
     * Truncate a string until reaching substring.
     * If not match then returns default string.
     *
     * @param str        string to truncate
     * @param substring  substring expression
     * @param defaultStr default string
     * @return truncated string or defaultStr in case not match
     */
    public static String truncateBySubstringOrElseReturnDefaultString(String str, String substring, String defaultStr) {

        if (str != null && substring != null) {

            int index = str.indexOf(substring);
            if (index == -1) {
                return defaultStr;
            } else {
                return str.substring(0, index).trim();
            }

        } else {
            return defaultStr;
        }

    }
}
