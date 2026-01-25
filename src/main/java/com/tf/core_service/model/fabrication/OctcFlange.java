package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OctcFlange {
    @JsonProperty private Boolean isoctc;
    @JsonProperty private Integer octcFlange_HPos;
    @JsonProperty private Integer octcFlange_VPos;
}
