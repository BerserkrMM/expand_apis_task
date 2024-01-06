package com.example.expand_apis_task.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

    public static final long JWT_EXPIRATION = 240000;

    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private SecurityConstants(){}
}
