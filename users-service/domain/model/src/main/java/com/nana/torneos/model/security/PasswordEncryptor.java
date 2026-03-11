package com.nana.torneos.model.security;
public interface PasswordEncryptor {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
