package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Cf {
    @JsonProperty
    private Integer cf_StayRod_Hole_Dia;
    @JsonProperty private Integer cf_StayRod_Hole_Nos;
    @JsonProperty private Double cf_StayRod_Hole_Pitch;
    @JsonProperty private Integer cf_TieRod_Hole_Dia;
    @JsonProperty private Integer cf_StayRod_Plate_L;
    @JsonProperty private Double cf_StayRod_Plate_H;
    @JsonProperty private Integer cf_StayRod_Plate_Thick;
    @JsonProperty private Integer cf_Yoke_Hole_Dia;
    @JsonProperty private Integer cf_YokeRod_Plate_L;
    @JsonProperty private Integer cf_YokeRod_Plate_H;
    @JsonProperty private Double cf_YokeRod_Plate_Thick;
    @JsonProperty private Integer cf_Cond_CutOut_W;
    @JsonProperty private Double cf_Stiff_Thick;
    @JsonProperty private Integer cf_tie_Stiff_Space;
    @JsonProperty private Integer cf_tie_Stiff_Pitch;
    @JsonProperty private Double cf_Wind_Sup_F_Rad;
    @JsonProperty private Integer cf_Wind_Sup_F_W;
    @JsonProperty private Double cf_Wind_Sup_F_Thick;
    @JsonProperty private Integer cf_Wind_Sup_F_Stiff_W;
    @JsonProperty private Double cf_Wind_Sup_F_Stiff_Thick;
    @JsonProperty private Integer cf_Wind_Sup_F_Stiff_Hole_Dia;
    @JsonProperty private Integer cf_Wind_Sup_F_Stiff_Hole_Nos;
    @JsonProperty private Integer cf_Wind_Sup_F_Stiff_Nos;
    @JsonProperty private Integer cf_Wind_Sup_F_Stiff_Space;
    @JsonProperty private Integer cf_Wind_Sup_F_Stiff_Pitch;
    @JsonProperty private Double cf_Wind_Sup_S_W;
    @JsonProperty private Integer cf_Wind_Sup_S_L;
    @JsonProperty private Double cf_Wind_Sup_S_Rad;
    @JsonProperty private Double cf_Wind_Sup_S_Thick;
    @JsonProperty private Integer cf_Wind_Sup_S_Stiff_W;
    @JsonProperty private Integer cf_Wind_Sup_S_Stiff_H;
    @JsonProperty private Double cf_Wind_Sup_S_Stiff_Thick;
    @JsonProperty private Double cf_Wind_Sup_S_Stiff_Cham;
    @JsonProperty private Integer cf_Lift_Lug_L1;
    @JsonProperty private Double cf_Lift_Lug_L2;
    @JsonProperty private Integer cf_Lift_Lug_H;
    @JsonProperty private Double cf_Lift_Lug_Thick;
    @JsonProperty private Double cf_Lift_Lug_Hole_Dia;
    @JsonProperty private Integer cf_StayRod_Hole_Clear;

}
