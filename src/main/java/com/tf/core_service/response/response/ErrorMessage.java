package com.tf.core_service.response.response;

import lombok.Data;


@Data
public class ErrorMessage {
    private int errorCode;
    private String message;
}