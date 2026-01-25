package com.tf.core_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Configurations {
    public static String TENANTS_DOCUMENT_NAME = "tenants";
    public static int RANDOM_STRING_LENGTH = 5;
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static Map<String, String> DEFAULT_HEADERS = new HashMap<>();

    static {
        DEFAULT_HEADERS.put("Content-Type","application/json");
    }

}