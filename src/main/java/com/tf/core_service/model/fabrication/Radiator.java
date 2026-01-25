package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.SKIPPED;

@Data
public class Radiator {
    //radiator
    @JsonProperty
    private String radiator_Type;
    @JsonProperty private String radiator_F_Type;
    @JsonProperty private String radiator_B_Type;
    @JsonProperty private Integer radiator_CC; //EleDes
    @JsonProperty private Integer radiator_Min_Bottom_Clearence;
    @JsonProperty private Integer radiator_CC_onTank;
    @JsonProperty private Integer radiator_W; //EleDes
    @JsonProperty private Double radiator_offset_Dist;
    @JsonProperty private Integer radiator_Fin_Nos; //EleDes
    @JsonProperty private String rad_Ptch_Calc_Mode;
    @JsonProperty private Integer radiator_Fin_Pitch;
    @JsonProperty private Integer radiator_Fin1_Pos;
    @JsonProperty private Integer radiator_Nos; //EleDes
    @JsonProperty private Integer radiator_Fin_Thick;
    @JsonProperty private Integer radiator_FL_Nos;
    @JsonProperty private Integer radiator_FR_Nos;
    @JsonProperty private Integer radiator_BL_Nos;
    @JsonProperty private Integer radiator_BR_Nos;
    @JsonProperty private Integer radiator_Left_Nos;
    @JsonProperty private Integer radiator_Right_Nos;
    @JsonProperty private Integer radiator_Min_Gap;
    @JsonProperty private Double radiator_F_Pitch;
    @JsonProperty private Double radiator_B_Pitch;
    @JsonProperty private Boolean radiator_Vlv;
    @JsonProperty private Integer radiator_Flg_W;
    @JsonProperty private Integer radiator_Flg_H;
    @JsonProperty private Integer radiator_Flg_Thick;
    @JsonProperty private Integer radiator_Flg_Hole_Dia;
    @JsonProperty private Integer radiator_Flg_Hole_Nos;
    @JsonProperty private Integer radiator_Flg_Hole_PCD;
    @JsonProperty private Integer radiator_Flg_VPos;
    @JsonProperty private Integer radiator_Vlv_Thick;
    @JsonProperty private Integer radiator_Head_Dia;
    @JsonProperty private Integer radiator_Head_L;
    @JsonProperty private Double radiator_Head_Thick;
    @JsonProperty private Double radiator_Fin_Sht_Thick;
    @JsonProperty private Double radiator_FL_HPos;
    @JsonProperty private Double radiator_BL_Hpos;
    @JsonProperty private Double radiator_FR_Hpos;
    @JsonProperty private Double radiator_BR_Hpos;
    @JsonProperty private Integer radiator_Vpos;
    @JsonProperty private Integer radiator_F_Vpos;
    @JsonProperty private Integer radiator_B_Vpos;
    @JsonProperty private String  radiator_Pipe_L = SKIPPED;
    @JsonProperty private Boolean radiator_Gneck;
    @JsonProperty private Double radiator_Gneck_dim_A;
    @JsonProperty private Integer radiator_Gneck_dim_B;
    @JsonProperty private Double radiator_Gneck_dim_C;
    @JsonProperty private Integer radiator_Gneck_dim_D;
    @JsonProperty private Integer radiator_Ext_H;
    @JsonProperty private Integer radiator_Ext_W;
    @JsonProperty private Integer radiator_Ext_Thick;
    @JsonProperty private Double radiator_Ext_FL_L;
    @JsonProperty private Double radiator_Ext_BL_L;
    @JsonProperty private Double radiator_Ext_FR_L;
    @JsonProperty private Double radiator_Ext_BR_L;
    @JsonProperty private Integer radiator_Lift_Lug_L;
    @JsonProperty private Integer radiator_Lift_Lug_H;
    @JsonProperty private Integer radiator_Lift_Lug_Thick;
    @JsonProperty private Double radiator_Lift_Lug_Hole_Dia;
}
