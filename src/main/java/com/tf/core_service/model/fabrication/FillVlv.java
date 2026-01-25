package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.tf.core_service.utils.Constants.SKIPPED;

@Data
public class FillVlv {
    @JsonProperty
    private Boolean fill_Vlv;
    @JsonProperty private Integer fill_Vlv_Nos;
    @JsonProperty private Double fill_Vlv_HPos;
    @JsonProperty private Integer fill_Vlv_VPos;
    @JsonProperty private Integer fill_Vlv_Type;

//    @JsonProperty private String fill_Vlv_Type = SKIPPED;
//    @JsonProperty private Integer fill_Vlv_Pipe_ID;
//    @JsonProperty private Double fill_Vlv_Pipe_OD;
//    @JsonProperty private Double fill_Vlv_Pipe_L;
//    @JsonProperty private Integer fill_Vlv_Flg_ID;
//    @JsonProperty private Integer fill_Vlv_Flg_OD;
//    @JsonProperty private Integer fill_Vlv_Flg_PCD;
//    @JsonProperty private Integer fill_Vlv_Flg_Thick;
//    @JsonProperty private Integer fill_Vlv_Flg_Bolt_Dia;
//    @JsonProperty private Integer fill_Vlv_Flg_Bolt_Nos;
}
