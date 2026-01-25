package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.NO_FORMULA;

@Data
public class Hvct {
    @JsonProperty
    private String hvct_U = NO_FORMULA;
    @JsonProperty private String hvct_V = NO_FORMULA;
    @JsonProperty private String hvct_W = NO_FORMULA;
    @JsonProperty private String hvct_N = NO_FORMULA;
    @JsonProperty private String hvct_Type = NO_FORMULA;
}
