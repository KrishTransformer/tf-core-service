package com.tf.core_service.response.response;

import lombok.Data;

import java.util.List;

@Data
public class Filter {
    private String name;
    private List<Option> options;
}
