package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Inspect {
    @JsonProperty private Boolean inspect_Cover;
    @JsonProperty private Integer inspect_Cover_Nos;
    @JsonProperty private String inspect_Cover_Type;
    @JsonProperty private Integer inspect_Cover_Dia;
    @JsonProperty private Integer inspect_Cover_L;
    @JsonProperty private Integer inspect_Cover_W;
    @JsonProperty private Integer inspect_Cover_Thick;
    @JsonProperty private Integer inspect_Flg_Dia;
    @JsonProperty private Integer inspect_Flg_L;
    @JsonProperty private Integer inspect_Flg_W;
    @JsonProperty private Integer inspect_Flg_Thick;
    @JsonProperty private Integer inspect_Flg_PCD;
    @JsonProperty private Integer inspect_Cutout_Dia;
    @JsonProperty private Integer inspect_Cutout_L;
    @JsonProperty private Integer inspect_Cutout_W;
    @JsonProperty private Integer inspect_Bolt_Dia;
    @JsonProperty private Integer inspect_Bolt_Nos;
    @JsonProperty private Integer inspect_Bolt_H_Nos;
    @JsonProperty private Integer inspect_Bolt_V_Nos;
    @JsonProperty private Double inspect_Bolt_H_Ptch;
    @JsonProperty private Double inspect_Bolt_V_Ptch;
    @JsonProperty private Double inspect_Cover_1_V_Pos;
    @JsonProperty private Double inspect_Cover_1_H_Pos;
    @JsonProperty private Double inspect_Cover_2_V_Pos;
    @JsonProperty private Double inspect_Cover_2_H_Pos;
    @JsonProperty private Integer inspect_Cover_TypeNo;
}
