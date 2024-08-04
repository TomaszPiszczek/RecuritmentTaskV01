package com.example.recruitmenttaskv01.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private final String status;
    private final String message;
}
