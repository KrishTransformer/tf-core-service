package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Jackpad {
    @JsonProperty private Boolean jackPad;
    @JsonProperty private Integer jackPad_Type;
    @JsonProperty private Integer jackpad_HPos;
    @JsonProperty private Integer jackpad_VPos;
}
