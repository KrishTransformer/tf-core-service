package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.NO_FORMULA;
import static com.tf.core_service.utils.Constants.SKIPPED;

@Data
public class Lvcb {
    @JsonProperty
    private boolean lvcb;
    @JsonProperty private Double lvcb_L1;
    @JsonProperty private Double lvcb_W1;
    @JsonProperty private Integer lvcb_HPos;
    @JsonProperty private Integer lvcb_VPos;
    @JsonProperty private Integer lvcb_CutOutL;
    @JsonProperty private Integer lvcb_CutOutW;
    @JsonProperty private Integer lvcb_Pkt_Type;
    @JsonProperty private Integer lvcb_Type;

//    @JsonProperty private Integer lvcb_Front_Flg_Vpos;
//    @JsonProperty private Integer lvcb_Thick;
//    @JsonProperty private String lvcb_Gland_Nos = SKIPPED;
//    @JsonProperty private Double lvcb_L2;
//    @JsonProperty private Double lvcb_H1;
//    @JsonProperty private Double lvcb_H2;
//    @JsonProperty private Double lvcb_H3;
//    @JsonProperty private Double lvcb_W2;
//    @JsonProperty private Double lvcb_W3;
//    @JsonProperty private Double lvcb_Flg_Box_L;
//    @JsonProperty private Double lvcb_Flg_Box_H;
//    @JsonProperty private Integer lvcb_Flg_Box_W;
//    @JsonProperty private Integer lvcb_Flg_Box_Thick;
//    @JsonProperty private Integer lvcb_Back_Flg_W;
//    @JsonProperty private Integer lvcb_Back_Flg_Thick;
//    @JsonProperty private Integer lvcb_Back_Flg_Bolt_Dia;
//    @JsonProperty private Integer lvcb_Back_Flg_Bolt_H_Nos;
//    @JsonProperty private Integer lvcb_Back_Flg_Bolt_V_Nos;
//    @JsonProperty private Integer lvcb_Back_Flg_Bolt_H_Pch;
//    @JsonProperty private Integer lvcb_Back_Flg_Bolt_V_Pch;
//    @JsonProperty private Integer lvcb_Front_Flg_W;
//    @JsonProperty private Integer lvcb_Front_Flg_Thick;
//    @JsonProperty private Integer lvcb_Flg_Box_Vpos;
//    @JsonProperty private Integer lvcb_Front_Cvr_Thick;
//    @JsonProperty private Integer lvcb_Front_Cvr_Bolt_Dia;
//    @JsonProperty private String lvcb_Front_Flg_Bolt_H_Nos  = NO_FORMULA;
//    @JsonProperty private String lvcb_Front_Flg_Bolt_V_Nos  = NO_FORMULA;
//    @JsonProperty private String lvcb_Front_Flg_Bolt_H_Pch  = NO_FORMULA;
//    @JsonProperty private String lvcb_Front_Flg_Bolt_V_Pch  = NO_FORMULA;
//    @JsonProperty private String lvcb_Gplate_Flg_W = SKIPPED;
//    @JsonProperty private String lvcb_Gplate_Flg_Thick = SKIPPED;
//    @JsonProperty private String lvcb_Gplate_L = SKIPPED;
//    @JsonProperty private String lvcb_Gplate_W = SKIPPED;
//    @JsonProperty private String lvcb_Gplate_Hole_Dia = SKIPPED;
//    @JsonProperty private String lvcb_Gplate_Hole_Ptch = SKIPPED;
//    @JsonProperty private String lvcb_Sup_Thick = SKIPPED;
//    @JsonProperty private String lvcb_Sup_L = SKIPPED;
//    @JsonProperty private String lvcb_Sup_W = SKIPPED;
//    @JsonProperty private String lvcb_Sup_H = SKIPPED;
//    @JsonProperty private String lvcb_Sup_Flg_W = SKIPPED;
//    @JsonProperty private String lvcb_Sup_Flg_Thick = SKIPPED;
//    @JsonProperty private String lvcb_Sup_Fplate_Thick = SKIPPED;
//    @JsonProperty private String lvcbn = SKIPPED;
//    @JsonProperty private String lvcb_Pos = NO_FORMULA;
//    @JsonProperty private String lvcb_DC = SKIPPED;
//    @JsonProperty private String lvcb_cable_Runs_Nos = SKIPPED;
}
