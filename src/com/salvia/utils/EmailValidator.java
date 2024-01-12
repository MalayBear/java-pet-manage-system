package com.salvia.utils;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    // 校验字符串是否为 邮箱字符串
    public static boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }
}

