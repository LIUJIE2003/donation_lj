package com.donation_wf.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MD5Util {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encrypt(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return encoder.encode(input);
    }

    public static boolean verify(String rawPassword, String encryptedPassword) {
        if (rawPassword == null || encryptedPassword == null) {
            return false;
        }
        return encoder.matches(rawPassword, encryptedPassword);
    }
}
