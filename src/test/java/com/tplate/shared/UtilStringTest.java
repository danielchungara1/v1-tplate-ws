package com.tplate.shared;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.*;

class UtilStringTest {

    @Test
    void truncateByRegexOrElseReturnDefaultString() {

        String str = "JSON parse error: Unrecognized field (class Tplate.Security.LoginController.class";
        String subString = "(class";
        String defaultStr = "Invalid Json";

        assertThat(
                UtilString.truncateBySubstringOrElseReturnDefaultString(str, subString, defaultStr)
        )
                .isEqualTo("JSON parse error: Unrecognized field");

        ;
    }
}