package com.binark.school.usermanagement.utils;

import java.util.UUID;

public class RandomPasswordGenerator {

    public static String getRandomPassword() {
        String password = UUID.randomUUID().toString();

        return password.length() <= 32 ? password : password.substring(0, 31);
    }
}
