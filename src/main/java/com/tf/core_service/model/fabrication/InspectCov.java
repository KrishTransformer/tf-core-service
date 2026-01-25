package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InspectCov {
    @JsonProperty Boolean inspectCov;
    @JsonProperty Integer inspectCov_Rem_Space;
    @JsonProperty Integer inspectCov_L;
    @JsonProperty Integer inspectCov_W;
    @JsonProperty Integer inspectCov_Dia;
    @JsonProperty Integer inspectCov_Hpos;
    @JsonProperty Integer inspectCov_Vpos;
    @JsonProperty Integer inspectCov_Des_No;
    @JsonProperty String inspectCov_Type;
}
