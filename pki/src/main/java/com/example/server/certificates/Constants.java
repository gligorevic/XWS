package com.example.server.certificates;

import java.util.regex.Pattern;

public class Constants {
    public static final String keystoreFilePathRoot = "./files:keystoreRoot.p12";
    public static final String keystoreFilePathCA = "./files:keystoreCA.p12";
    public static final String keystoreFilePathEnd = "./files:keystoreEnd.p12";
    public static final String password = "password";
    public static final Pattern emailFormat = Pattern.compile("^[a-zA-Z0-9._-]+@(.+)$");
    public static final Pattern commonNameFormat = Pattern.compile("^[a-zA-Z0-9 '-]+$");
}
