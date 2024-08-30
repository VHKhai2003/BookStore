package com.example.bookstore.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private Integer status;
    private String message;

    public CustomException(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
