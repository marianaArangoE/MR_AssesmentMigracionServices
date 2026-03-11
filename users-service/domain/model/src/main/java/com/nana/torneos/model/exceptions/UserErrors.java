package com.nana.torneos.model.exceptions;
public class UserErrors {

    public static BusinessException emailAlreadyExists() {
        return new BusinessException(
                "Email already registered",
                "EMAIL_ALREADY_EXISTS",
                409
        );
    }
    public static BusinessException idAlreadyExists() {
        return new BusinessException(
                "Id already registered",
                "ID_ALREADY_EXISTS",
                409
        );
    }


    public static BusinessException userNotFound() {
        return new BusinessException(
                "User not found",
                "USER_NOT_FOUND",
                404
        );
    }
    public static BusinessException idIsRequired() {
        return new BusinessException(
                "Id cant be empty",
                "ID_NOT_FOUND",
                404
        );
    }

    public static BusinessException invalidCredentials() {
        return new BusinessException (
                "INVALID_CREDENTIALS",
                "Email or password is incorrect",
                401
        );
    }
    public static Throwable invalidToken() {
        return new BusinessException(
                "INVALID_TOKEN",
                "Token is invalid or expired",
                401
        );
    }
    public static Throwable nicknameAlreadyExists() {
        return new BusinessException(
                "NICKNAME_ALREADY_EXISTS",
                "Nickname is already taken",
                409
        );
    }
}