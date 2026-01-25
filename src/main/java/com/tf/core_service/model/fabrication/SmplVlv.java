package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.SKIPPED;

@Data
public class SmplVlv {
    @JsonProperty
    private Boolean smpl_Vlv;
    @JsonProperty private Integer smpl_Vlv_Nos;
    @JsonProperty private String smpl_Vlv_Type  = SKIPPED;
//    @JsonProperty private Integer smpl_Vlv_Pipe_ID;
//    @JsonProperty private Double smpl_Vlv_Pipe_OD;
//    @JsonProperty private Integer smpl_Vlv_Pipe_L;
    @JsonProperty private Integer smpl_Vlv1_Hpos;
    @JsonProperty private Integer smpl_Vlv1_Vpos;
//    @JsonProperty private Integer smpl_Vlv2_Hpos;
//    @JsonProperty private Integer smpl_Vlv2_Vpos;
//    @JsonProperty private Integer smpl_Vlv3_Hpos;
//    @JsonProperty private Integer smpl_Vlv3_Vpos;
}
