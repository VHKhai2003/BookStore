package com.example.bookstore.utils;

public class ValidationUtils {
    public static boolean notBlankValidate(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean validatePhone(String phone) {
        // temporary
        // modify later :>
        return phone != null && phone.trim().length() > 0;
    }
}
