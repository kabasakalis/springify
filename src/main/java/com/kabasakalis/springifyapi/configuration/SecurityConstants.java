package com.kabasakalis.springifyapi.configuration;

public class SecurityConstants {

    public static final String AUDIENCE = "Springify Users";
    public static final String ISSUER = "com.srpingify.kabasakalis";
    public static final long EXPIRATION_TIME_FROM_NOW_HOURS = 192; // 8 days
    public static final String[] PERMIT_ALL_PATHS = new String[]{
            "/login",
            "/sign-up"
    };
    public static final String[] ADMIN_PATHS = new String[]{
            "/users/**",
            "/roles/**"
    };

    public static final String[] MODERATOR_PATHS = new String[]{
            "/genres/**",
            "/artists/**",
            "/albums/**",
            "/playlists/**",
    };

}
