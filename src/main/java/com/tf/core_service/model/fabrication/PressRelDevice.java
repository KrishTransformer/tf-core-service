package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PressRelDevice {
    @JsonProperty private boolean prd;
    @JsonProperty private Integer prd_Hole_Dia = 30;
    @JsonProperty private Integer prd_HPos;
    @JsonProperty private Integer prd_VPos;
}
