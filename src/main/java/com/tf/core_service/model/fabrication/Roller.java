package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Roller {
    @JsonProperty
    private boolean roller;
    @JsonProperty private String roller_Type;
    @JsonProperty private Integer roller_Dia;
    @JsonProperty private Integer roller_Thick;
    @JsonProperty private Integer roller_Hub_Dia;
    @JsonProperty private Integer roller_Hub_L;
    @JsonProperty private Integer roller_Flg_Dia;
    @JsonProperty private Integer roller_Flg_Thick;
    @JsonProperty private Integer roller_Shaft_Dia;
    @JsonProperty private Double roller_Shaft_L;
    @JsonProperty private Double roller_Shaft_Vpos;
    @JsonProperty private Integer roller_Sup_L;
    @JsonProperty private Integer roller_Sup_W;
    @JsonProperty private Double roller_Sup_H;
    @JsonProperty private Integer roller_Sup_Thick;

    @JsonProperty private Double roller_Guage;
    @JsonProperty private String foundation_Type;
}
