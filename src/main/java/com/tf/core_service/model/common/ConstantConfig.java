package com.tf.core_service.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConstantConfig implements Serializable {
    private String key;
    private String value;
}
