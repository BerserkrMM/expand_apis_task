package com.example.expand_apis_task.config.consts;

import io.jsonwebtoken.SignatureAlgorithm;

public interface SecurityConstants {

    long JWT_EXPIRATION = 480000;

    SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

}
