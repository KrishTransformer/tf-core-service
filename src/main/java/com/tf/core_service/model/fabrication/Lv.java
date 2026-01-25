package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Lv {

    @JsonProperty
    private Double lv_Cond_Rad;
    @JsonProperty private Double lv_Cond_Ax;
    @JsonProperty private Double lv_Cond_W;
    @JsonProperty private Double lv_Cond_H;
    @JsonProperty private Integer lv_Ph_Ph;
    @JsonProperty private Integer lv_Ph_Erth;
    @JsonProperty private Double lv_Volts;
    @JsonProperty private Double lv_ID;
    @JsonProperty private Double lv_OD;
    @JsonProperty private Double lv_Wdg_L;
    @JsonProperty private Integer lv_Tank_Clearence;
}
