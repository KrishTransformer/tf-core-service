package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.NO_FORMULA;
import static com.tf.core_service.utils.Constants.SKIPPED;

@Data
public class Hvcb {
    @JsonProperty private boolean hvcb;
    @JsonProperty private Double hvcb_L1;
    @JsonProperty private Double hvcb_W1;
    @JsonProperty private Double hvcb_HPos;
    @JsonProperty private Double hvcb_VPos;
    @JsonProperty private Integer hvcb_CutOutL;
    @JsonProperty private Integer hvcb_CutOutW;
    @JsonProperty private Integer hvcb_Pkt_Type;
    @JsonProperty private Integer hvcb_Type;

//    @JsonProperty private Integer hvcb_Flg_Box_Vpos;
//    @JsonProperty private Integer hvcb_Front_Flg_W;
//    @JsonProperty private Integer hvcb_Front_Flg_Vpos;
//    @JsonProperty private Integer hvcb_Thick;
//    @JsonProperty private String hvcb_Gland_Nos = SKIPPED;
//    @JsonProperty private Double hvcb_L2;
//    @JsonProperty private Double hvcb_H1;
//    @JsonProperty private Double hvcb_H2;
//    @JsonProperty private Double hvcb_H3;
//    @JsonProperty private Double hvcb_W2;
//    @JsonProperty private Double hvcb_W3;
//    @JsonProperty private Double hvcb_Flg_Box_L;
//    @JsonProperty private Double hvcb_Flg_Box_H;
//    @JsonProperty private Integer hvcb_Flg_Box_W;
//    @JsonProperty private Integer hvcb_Flg_Box_Thick;
//    @JsonProperty private Integer hvcb_Back_Flg_W;
//    @JsonProperty private Integer hvcb_Back_Flg_Thick;
//    @JsonProperty private Integer hvcb_Back_Flg_Bolt_Dia;
//    @JsonProperty private Integer hvcb_Back_Flg_Bolt_H_Nos;
//    @JsonProperty private Integer hvcb_Back_Flg_Bolt_V_Nos;
//    @JsonProperty private Integer hvcb_Back_Flg_Bolt_H_Pch;
//    @JsonProperty private Integer hvcb_Back_Flg_Bolt_V_Pch;
//    @JsonProperty private Integer hvcb_Front_Flg_Thick;
//    @JsonProperty private Integer hvcb_Front_Cvr_Thick;
//    @JsonProperty private Integer hvcb_Front_Cvr_Bolt_Dia;
//    @JsonProperty private String hvcb_Front_Flg_Bolt_H_Nos = NO_FORMULA;
//    @JsonProperty private String hvcb_Front_Flg_Bolt_V_Nos = NO_FORMULA;
//    @JsonProperty private String hvcb_Front_Flg_Bolt_H_Pch = NO_FORMULA;
//    @JsonProperty private String hvcb_Front_Flg_Bolt_V_Pch = NO_FORMULA;
//    @JsonProperty private String hvcb_Gplate_Flg_W = SKIPPED;
//    @JsonProperty private String hvcb_Gplate_Flg_Thick = SKIPPED;
//    @JsonProperty private String hvcb_Gplate_L = SKIPPED;
//    @JsonProperty private String hvcb_Gplate_W = SKIPPED;
//    @JsonProperty private String hvcb_Gplate_Hole_Dia = SKIPPED;
//    @JsonProperty private String hvcb_Gplate_Hole_Ptch = SKIPPED;
//    @JsonProperty private String hvcb_Sup_Thick = SKIPPED;
//    @JsonProperty private String hvcb_Sup_L = SKIPPED;
//    @JsonProperty private String hvcb_Sup_W = SKIPPED;
//    @JsonProperty private String hvcb_Sup_H = SKIPPED;
//    @JsonProperty private String hvcb_Sup_Flg_W = SKIPPED;
//    @JsonProperty private String hvcb_Sup_Flg_Thick = SKIPPED;
//    @JsonProperty private String hvcb_Sup_Fplate_Thick = SKIPPED;
//    @JsonProperty private String hvcbn = SKIPPED;
//    @JsonProperty private String hvcb_Pos;
//    @JsonProperty private String hvcb_DC = SKIPPED;
}
