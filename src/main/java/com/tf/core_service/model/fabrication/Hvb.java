package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Hvb {
    @JsonProperty private String hvb_Pos;
    @JsonProperty private Integer hvb_Ph_Erth_Clear;
    @JsonProperty private Integer hvb_tank_Oil_Clear;
    @JsonProperty private String hvb_Type;
    @JsonProperty private Integer hvb_Volt; //EleDes
    @JsonProperty private Integer hvb_Amp; //EleDes
    @JsonProperty private Integer hvb_L1;
    @JsonProperty private Integer hvb_L2;
    @JsonProperty private Integer hvb_Nos;
    @JsonProperty private Integer hvb1_F_Tilt_Ang;
    @JsonProperty private Integer hvb2_F_Tilt_Ang;
    @JsonProperty private Integer hvb3_F_Tilt_Ang;
    @JsonProperty private Integer hvb1_Bush_Rot_Ang;
    @JsonProperty private Integer hvb2_Bush_Rot_Ang;
    @JsonProperty private Integer hvb3_Bush_Rot_Ang;
    @JsonProperty private Double hvb_Ptch;
    @JsonProperty private Integer hvb_VPos;
    @JsonProperty private Double hvb_F_Tilt_Ang;
    @JsonProperty private Double hvb_S_Tilt_Ang;
    @JsonProperty private Double hvb_Sup_V_Pos;
    @JsonProperty private String hvb_Sup_type;
    @JsonProperty private Double hvb_Sup_L;
    @JsonProperty private Integer hvb_Sup_W;
    @JsonProperty private Integer hvb_Sup_Thick;
    @JsonProperty private Integer hvb_Sup_ID;
    @JsonProperty private Integer hvb_Sup_OD;
    @JsonProperty private Integer hvb_Sup_H;
    @JsonProperty private Integer hvb_Sup_Flg_PCD;
    @JsonProperty private Double hvb_Sup_Flg_W;
    @JsonProperty private Integer hvb_Sup_Flg_OD;
    @JsonProperty private Integer hvb_Sup_Flg_ID;
    @JsonProperty private Integer hvb_Sup_Flg_Thick;
    @JsonProperty private Integer hvb_Sup_Flg_Stud_Dia;
    @JsonProperty private Integer hvb_Sup_Flg_Stud_L;
    @JsonProperty private Integer hvb_Sup_Flg_Stud_Nos;
    @JsonProperty private Integer hvb_Tank_On_Wall_DesNo;
    @JsonProperty private Integer hvb_OnTank_Length;
    @JsonProperty private Integer hvb_OnTank_cutout_L;
    @JsonProperty private Integer hvb_OnTank_cutout_W;
    @JsonProperty private Integer hvb_OnTank_cutout_Hpos;
    @JsonProperty private Integer hvb_OnTank_cutout_Vpos;
    @JsonProperty private Boolean hvb_Neutral_Bushing;
    @JsonProperty private Integer hvb_Neutral_HPos;
    @JsonProperty private Integer hvb_Neutral_VPos;
    @JsonProperty private Integer hvb_Ofst;
    @JsonProperty private Integer hvb_neutral_Bush_Rot_Ang;
    @JsonProperty private Integer hvb_Neutral_F_Tilt_Ang;
}

