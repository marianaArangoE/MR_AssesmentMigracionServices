package com.nana.torneos.model.security;
public interface TokenProvider {
    String generateToken(Integer userId, String email);
}