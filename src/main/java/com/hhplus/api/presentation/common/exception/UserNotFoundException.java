package com.hhplus.api.presentation.common.exception;

import com.hhplus.api.presentation.common.response.ApiResponseHeader;

public class UserNotFoundException extends RuntimeException {

    private ApiResponseHeader header;

    public UserNotFoundException(ApiResponseHeader header) {
        this.header = header;
    }
}
