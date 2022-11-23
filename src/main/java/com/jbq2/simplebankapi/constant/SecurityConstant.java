package com.jbq2.simplebankapi.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 3_600_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String BLUE_PIG_BANK = "Blue Pig Bank";
    public static final String BLUE_PIG_BANK_ADMINISTRATION = "Blue Pig Bank Administrator";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

    /* note that this will probably change; more public urls will be added */
    public static final String[] PUBLIC_URLS = {"/api/v1/login", "/api/v1/register"};
}
