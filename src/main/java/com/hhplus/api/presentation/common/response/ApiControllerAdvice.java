package com.hhplus.api.presentation.common.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponseHeader> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ApiResponseHeader(500, "서버 에러 발생!"));
    }
}
