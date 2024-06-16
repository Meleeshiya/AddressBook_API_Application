package com.propeltech.addressbook.util;

import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_NUMBER_FORMAT = "^\\d{10}$";

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_FORMAT, email);
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && Pattern.matches(PHONE_NUMBER_FORMAT, phone);
    }
}

