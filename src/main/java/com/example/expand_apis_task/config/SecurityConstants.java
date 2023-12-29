package com.example.expand_apis_task.config;

import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityConstants {

    private SecurityConstants(){}
    public static final long JWT_EXPIRATION = 140000; //ms
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

}
