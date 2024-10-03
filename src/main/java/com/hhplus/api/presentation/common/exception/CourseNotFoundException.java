package com.hhplus.api.presentation.common.exception;

import com.hhplus.api.presentation.common.response.ApiResponseHeader;

public class CourseNotFoundException extends RuntimeException {

    private ApiResponseHeader header;
    public CourseNotFoundException(ApiResponseHeader header) {
        this.header = header;
    }
}
