package com.example.expand_apis_task.config;

import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityConstants {

    private SecurityConstants(){}
    public static final long JWT_EXPIRATION = 70000; //ms
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

}
