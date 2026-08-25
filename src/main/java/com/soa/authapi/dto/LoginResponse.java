package com.soa.authapi.dto;

public record LoginResponse(
    String token,
    String type,
    Long expiresIn,
    String username,
    String role
) {}
