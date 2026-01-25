package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.SKIPPED;

@Data
public class DrainVlv {
    @JsonProperty
    private Boolean drain_Vlv;
    @JsonProperty private Integer drain_Vlv_Nos;
    @JsonProperty private Integer drain_Vlv_Type;
    @JsonProperty private Double drain_Vlv1_HPos;
    @JsonProperty private Double drain_Vlv2_HPos = 0.0;
    @JsonProperty private Integer drain_Vlv_VPos;

//    @JsonProperty private Integer drain_Vlv_Pipe_ID;
//    @JsonProperty private Double drain_Vlv_Pipe_OD;
//    @JsonProperty private Double drain_Vlv_Pipe_L;
//    @JsonProperty private Integer drain_Vlv_Flg_OD;
//    @JsonProperty private Integer drain_Vlv_Flg_ID;
//    @JsonProperty private Integer drain_Vlv_Flg_PCD;
//    @JsonProperty private Integer drain_Vlv_Flg_Thick;
//    @JsonProperty private Integer drain_Vlv_Flg_Bolt_Dia;
//    @JsonProperty private Integer drain_Vlv_Flg_Bolt_Nos;
}
