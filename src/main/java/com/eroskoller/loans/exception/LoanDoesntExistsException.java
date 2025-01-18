package com.eroskoller.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanDoesntExistsException extends RuntimeException{

    public LoanDoesntExistsException(String message) {
        super(message);
    }
}
