package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Hv {

    @JsonProperty
    private Integer hv_Ph_Ph;
    @JsonProperty private Integer hv_Ph_Erth;
    @JsonProperty private Integer Hv_Ph_Ph_Incl_cap;
    @JsonProperty private Double hv_ID;
    @JsonProperty private Double hv_OD;
    @JsonProperty private Double hv_Wdg_L;
    @JsonProperty private Integer hv_Tank_Clearence;
}
