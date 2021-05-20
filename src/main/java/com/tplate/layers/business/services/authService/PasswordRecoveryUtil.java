package com.tplate.layers.business.services.authService;

import java.util.Date;
import java.util.Random;

public class PasswordRecoveryUtil {


    private static final Random RANDOM = new Random();

    private static final Integer EXPIRATION_MIN = 5;

    public static String code (){
        return String.format("%04d", RANDOM.nextInt(10000));
    }

    public static Date expiration(){
        return  new Date(System.currentTimeMillis() + (EXPIRATION_MIN * 60 * 1000));
    }

}
