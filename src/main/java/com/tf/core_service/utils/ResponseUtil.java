package com.tf.core_service.utils;

import com.tf.core_service.response.response.ErrorMessage;


public class ResponseUtil {

    public static ErrorMessage getErrorResponse(String errorMessage) {
        return getErrorResponse(null, errorMessage);
    }
    public static ErrorMessage getErrorResponse(Integer errorCode, String errorMessage) {
        ErrorMessage error = new ErrorMessage();
        if (errorCode != null) {
            error.setErrorCode(errorCode);
        }
        error.setMessage(errorMessage);
        return error;
    }

}