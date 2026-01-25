package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Lvb {

    @JsonProperty private String lvb_Pos;
    @JsonProperty private Integer lvb_tank_Oil_Clear;
    @JsonProperty private String lvb_Type;
    @JsonProperty private Integer lvb_Volt; //EleDes
    @JsonProperty private Integer lvb_Amp; //EleDes
    @JsonProperty private Integer lvb_L1;
    @JsonProperty private Integer lvb_L2;
    @JsonProperty private String lvb_Bolt_Dia; // changed the datatype to string to print "NO_FORMULA_GIVEN"
    @JsonProperty private String lvb_Cap_Dia; // changed the datatype to string to print "NO_FORMULA_GIVEN"
    @JsonProperty private Integer lvb_Nos;
    @JsonProperty private Double lvb_Ptch;
    @JsonProperty private Integer lvb_Side_Pitch;
    @JsonProperty private Integer lvb_Center_Pitch;
    @JsonProperty private Integer lvb_VPos;
    @JsonProperty private String lvb_F_Tilt_Ang; // changed the datatype to string to print "NO_FORMULA_GIVEN"
    @JsonProperty private Double lvb_S_Tilt_Ang;
    @JsonProperty private Double lvb_Sup_V_Pos;
    @JsonProperty private String lvb_Sup_type;
    @JsonProperty private Double lvb_Sup_L;
    @JsonProperty private Integer lvb_Sup_W;
    @JsonProperty private Integer lvb_Sup_Thick;
    @JsonProperty private Integer lvb_Sup_ID;
    @JsonProperty private Integer lvb_Sup_OD;
    @JsonProperty private Integer lvb_Sup_H;
    @JsonProperty private Integer lvb_Sup_Flg_PCD;
    @JsonProperty private Double lvb_Sup_Flg_W;
    @JsonProperty private Integer lvb_Sup_Flg_OD;
    @JsonProperty private Integer lvb_Sup_Flg_ID;
    @JsonProperty private Integer lvb_Sup_Flg_Thick;
    @JsonProperty private Integer lvb_Sup_Flg_Stud_Dia;
    @JsonProperty private Integer lvb_Sup_Flg_Stud_L;
    @JsonProperty private Integer lvb_Sup_Flg_Stud_Nos;

    @JsonProperty private String lvb_Ph_Erth_Clear; // changed the datatype to string to print "NO_FORMULA_GIVEN"
    @JsonProperty private Integer lvb_Tank_On_Wall_DesNo;
    @JsonProperty private Integer lvb_OnTank_Length;
    @JsonProperty private Integer lvb_OnTank_cutout_W;
    @JsonProperty private Integer lvb_OnTank_cutout_L;
    @JsonProperty private Integer lvb_OnTank_cutout_Hpos;
    @JsonProperty private Integer lvb_OnTank_cutout_Vpos;
    @JsonProperty private Integer lvb_offset;

}
