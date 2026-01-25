package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.NO_FORMULA;

@Data
public class Lvct {
    @JsonProperty
    private String lcvt_U = NO_FORMULA;
    @JsonProperty private String lcvt_V  = NO_FORMULA;
    @JsonProperty private String lcvt_W = NO_FORMULA;
    @JsonProperty private String lcvt_N = NO_FORMULA;
    @JsonProperty private String lcvt_Type = NO_FORMULA;
}
