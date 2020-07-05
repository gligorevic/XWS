package com.example.AuthService.constants;

import java.util.regex.Pattern;

public class Format {

    public static final Pattern email = Pattern.compile("^[a-zA-Z0-9._-]+@(.+)$");
    public static final Pattern password = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,25}$");
    public static final Pattern name = Pattern.compile("^[a-zA-Z '-]+$");
    public static final Pattern phoneNumber = Pattern.compile("^\\+?[0-9]+$");
}
