package com.tf.core_service.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreStackRequest {
    public Integer stepNo;
    public Integer width;
    public Integer stack;
}
