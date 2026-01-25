package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExpVent {
    //These var below are the ones that will implement NB's Drawings.
    @JsonProperty
    private boolean exp_Vent;
    @JsonProperty private Integer exp_Vent_H;
    @JsonProperty private Boolean exp_Vent_With_OI;
    @JsonProperty private Integer exp_Vent_Hpos;
    @JsonProperty private Integer exp_Vent_Vpos;
    @JsonProperty private Integer exp_Vent_Hole_Dia = 62;


    //The ones Below this line are the ones that was done by Suveer sir.
//    @JsonProperty private Integer exp_Vent_ID;
//    @JsonProperty private Double exp_Vent_Thick;
//    @JsonProperty private Integer exp_Vent_Flg_W;
//    @JsonProperty private Integer exp_Vent_Flg_Thick;
//    @JsonProperty private Integer exp_Vent_Flg_Hole_Dia;
//    @JsonProperty private Integer exp_Vent_Flg_Hole_Nos;
//    @JsonProperty private Double exp_Vent_Flg_Hole_PCD;
//    @JsonProperty private String exp_Vent_Type;
//    @JsonProperty private Integer exp_Vent_Flg_Pipe1_L;
//    @JsonProperty private Integer exp_Vent_Flg_Pipe2_L;
//    @JsonProperty private Integer exp_Vent_Olg_L1;
//    @JsonProperty private Integer exp_Vent_Olg_Stud_Row_Nos;
//    @JsonProperty private Integer exp_Vent_Olg_L2;
//    @JsonProperty private Integer exp_Vent_Olg_W;
//    @JsonProperty private Integer exp_Vent_Olg_W2;
//    @JsonProperty private Integer exp_Vent_Olg_Stud_Dia;
//    @JsonProperty private Integer exp_Vent_Olg_Stud_L;
//    @JsonProperty private Integer exp_Vent_Olg_Sup_L;
//    @JsonProperty private Integer exp_Vent_Olg_Sup_W;
//    @JsonProperty private Double exp_Vent_Olg_Sup_Thick;
//    @JsonProperty private Integer exp_Vent_Olg_Sup_Pipe_ID;
//    @JsonProperty private Integer exp_Vent_Olg_Sup_Pipe_OD;
//    @JsonProperty private Integer exp_Vent_Olg_Sup_Pipe_L;
//    @JsonProperty private Integer exp_Vent_Olg_Sup_Pipe_Pitch;
}
