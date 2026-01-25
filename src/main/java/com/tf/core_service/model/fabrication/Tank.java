package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Tank {
    @JsonProperty private Integer tank_L; //EleDes
    @JsonProperty private Integer tank_W; //EleDes
    @JsonProperty private Integer tank_H; //EleDes
    @JsonProperty private Integer tank_Thick; //EleDes
    @JsonProperty private String tank_Build_Type;
    @JsonProperty private Integer tank_Bot_Thick;  //EleDes
    @JsonProperty private Integer tank_Bot_L;
    @JsonProperty private Integer tank_Bot_W;
    @JsonProperty private Integer tank_Flg_Thick;  //EleDes
    @JsonProperty private Integer tank_Flg_W;
    @JsonProperty private Integer tank_Flg_L1;
    @JsonProperty private Integer tank_Flg_L2;
    @JsonProperty private Integer tank_Flg_Bolt_Dia;
    @JsonProperty private Integer tank_Flg_Bolt_H_Nos;
    @JsonProperty private Integer tank_Flg_Bolt_V_Nos;
    @JsonProperty private Integer tank_Flg_Bolt_H_Pch;
    @JsonProperty private Integer tank_Flg_Bolt_V_Pch;
    @JsonProperty private Integer tank_Clearence;
}
