package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Mog {
    @JsonProperty
    private Boolean mog;
    @JsonProperty private String mog_Pos;
    @JsonProperty private Integer mog_Size;
    @JsonProperty private String mog_Model;
    @JsonProperty private Double mog_Flg_Pipe_OD;
    @JsonProperty private Integer mog_Flg_Pipe_ID;
    @JsonProperty private Integer mog_Flg_Pipe_L;
    @JsonProperty private Integer mog_Flg_OD;
    @JsonProperty private Integer mog_Flg_ID;
    @JsonProperty private Integer mog_Flg_Thick;
    @JsonProperty private Double mog_Flg_PCD;
    @JsonProperty private Integer mog_Flg_Bolt_Dia;
    @JsonProperty private Integer mog_Flg_Bolt_L;
    @JsonProperty private Integer mog_Flg_Bolt_Count;
    @JsonProperty private Integer mog_Tlt_Ang;
}
