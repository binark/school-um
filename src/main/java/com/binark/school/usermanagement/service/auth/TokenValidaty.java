package com.binark.school.usermanagement.service.auth;

/**
 * Token validity time constants in millisecond
 */
public class TokenValidaty {

    // One hour validity for normal user
    public static final long NORMAL = 3600000l;

    // One day validity for remember me users
    public static final long REMEMBERME = 3600000*24l;
}
