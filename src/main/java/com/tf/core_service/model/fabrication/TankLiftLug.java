package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TankLiftLug {
    @JsonProperty
    private Boolean tank_LiftLug;
    @JsonProperty private String tank_LiftLug_Type;
    @JsonProperty private Double tank_LiftLug_L1;
    @JsonProperty private Double tank_LiftLug_L2;
    @JsonProperty private Double tank_LiftLug_L3;
    @JsonProperty private Double tank_LiftLug_L4;
    @JsonProperty private Double tank_LiftLug_H1;
    @JsonProperty private Double tank_LiftLug_H2;
    @JsonProperty private Double tank_LiftLug_H3;
    @JsonProperty private Integer tank_LiftLug_Thick;
    @JsonProperty private Double tank_LiftLug_Hole_Dia;
    @JsonProperty private Integer tank_LiftLug_Rod_Dia;
    @JsonProperty private Double tank_LiftLug_Chamfer;
    @JsonProperty private Double tank_LiftLug_Sup_H;
    @JsonProperty private Integer tank_LiftLug_Sup_W;
    @JsonProperty private Integer tank_LiftLug_Sup_Thick;
    @JsonProperty private Integer tank_LiftLug_Nos;
    @JsonProperty private Double tank_LiftLug_Vpos;
    @JsonProperty private Integer tank_LiftLug_Hpos;
    @JsonProperty private Integer tank_Lifting_lug_TypeNo;
}
