package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import static com.tf.core_service.utils.Constants.NO_FORMULA;
import static com.tf.core_service.utils.Constants.SKIPPED;

@Data
public class Cons {
    @JsonProperty private boolean conseravator;
    @JsonProperty private boolean cons_Detach;
    @JsonProperty private String cons_Pos;
    @JsonProperty private String cons_Mounting;
    @JsonProperty private Double cons_V_Pos;
    @JsonProperty private Double cons_H_Pos;
    @JsonProperty private Integer cons_Vol; //EleDes
    @JsonProperty private Integer cons_Dia; //EleDes
    @JsonProperty private Integer cons_L; //EleDes
    @JsonProperty private Integer cons_Thick;
    @JsonProperty private Integer cons_Flg_Thick;
    @JsonProperty private Integer cons_Flg_W;
    @JsonProperty private Integer cons_EPlate_Dia;
    @JsonProperty private Integer cons_EPlate_Thick;
    @JsonProperty private Integer cons_EPlate_Hole_Dia;
    @JsonProperty private Integer cons_EPlate_Hole_Nos;
    @JsonProperty private Integer cons_EPlate_Hole_PCD;
//    @JsonProperty private Double cons_Pipe_Slope;
//    @JsonProperty private Double cons_Pipe_Bend_Rad;
//    @JsonProperty private Double cons_Pipe_Bend_BFactor;
//    @JsonProperty private Double cons_Pipe_Bend_Compensate;
//    @JsonProperty private Double cons_Pipe_A;
//    @JsonProperty private Double cons_Pipe_B;
//    @JsonProperty private Double cons_Pipe_C;
//    @JsonProperty private Double cons_Pipe_D;
//    @JsonProperty private Double cons_Pipe_E;
//    @JsonProperty private Integer cons_Pipe_H_Pos;
//    @JsonProperty private Double cons_Pipe_L;
//    @JsonProperty private Integer cons_Pipe_ID;
//    @JsonProperty private Double cons_Pipe_OD;
//    @JsonProperty private Integer cons_Pipe_Flg_OD;
//    @JsonProperty private Integer cons_Pipe_Flg_PCD;
//    @JsonProperty private Integer cons_Pipe_Flg_Thick;
//    @JsonProperty private Integer cons_Pipe_Flg_Bolt_Dia;
//    @JsonProperty private Integer cons_Pipe_Flg_Bolt_Nos;
    @JsonProperty private Integer cons_Olg_Nos;
    @JsonProperty private Integer cons_Olg_L1;
    @JsonProperty private Integer cons_Olg_W1;
    @JsonProperty private Integer cons_Olg_W2;
    @JsonProperty private Integer cons_Olg_Thick;
    @JsonProperty private Integer cons_Olg_L2;
    @JsonProperty private Integer cons_Olg_Stud_Dia;
    @JsonProperty private Integer cons_Olg_Stud_L;
    @JsonProperty private Integer cons_Olg_Stud_Row_Nos;
    @JsonProperty private Integer cons_Olg_Sup_W;
    @JsonProperty private Double cons_Olg_Sup_Thick;
    @JsonProperty private Integer cons_Olg_Sup_Pipe_ID;
    @JsonProperty private Integer cons_Olg_Sup_Pipe_OD;
    @JsonProperty private Integer cons_Olg_Sup_Pipe_L;
    @JsonProperty private Integer cons_Olg_Sup_L;
    @JsonProperty private Double cons_Olg_Sup_Pipe_Pitch;

    //Breather Parameters
    @JsonProperty private Boolean cons_Breath;
    @JsonProperty private Integer cons_Breath_H;
    @JsonProperty private Integer cons_Breath_Dia;
    @JsonProperty private Double cons_Breath_Wt;

    @JsonProperty private Integer cons_Drain_ID;
    @JsonProperty private Double cons_Drain_OD;
    @JsonProperty private Double cons_Drain_L;
    @JsonProperty private Integer cons_Filler_ID;
    @JsonProperty private Double cons_Filler_OD;
    @JsonProperty private Double cons_Filler_L;
    @JsonProperty private String  cons_Air_release_ID = SKIPPED;
    @JsonProperty private String cons_Air_release_OD = SKIPPED;
    @JsonProperty private String cons_Air_release_L = SKIPPED;
//    @JsonProperty private String cons_Sup_Sect;
//    @JsonProperty private Integer cons_Sup_Sect_L1;
//    @JsonProperty private Integer cons_Sup_Sect_L2;
//    @JsonProperty private Integer cons_Sup_Plt_Thick;
//    @JsonProperty private Integer Cons_Sup_BPlate_W;
//    @JsonProperty private Integer cons_Sup_Hole_Dim;
//    @JsonProperty private String cons_Sup_Hole_Nos = NO_FORMULA;
//    @JsonProperty private Integer cons_Sup_Hole_Pitch;
    @JsonProperty private Integer cons_Logo_Plate_L;
    @JsonProperty private Double cons_Logo_Plate_W;
    @JsonProperty private Integer cons_Logo_Plate_H;
    @JsonProperty private Integer cons_Logo_Plate_Thick;

    // New Variables (NB)

    @JsonProperty private Integer cons_Sup_L;
    @JsonProperty private Integer cons_Sup_Bracket_W;

}
