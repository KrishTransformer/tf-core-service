package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BuchRely {
    @JsonProperty
    private Boolean buch_Rely;
    @JsonProperty private Integer buch_Rely_L;
    @JsonProperty private Integer buch_Rely_W;
    @JsonProperty private Integer buch_Rely_H;
    @JsonProperty private Integer buch_Rely_Flg_OD;
    @JsonProperty private Integer buch_Rely_Flg_PCD;
    @JsonProperty private Integer buch_Rely_Flg_Thick;
    @JsonProperty private Integer buch_Rely_Flg_Bolt_Dia;
    @JsonProperty private Integer buch_Rely_Flg_Bolt_Nos;
    @JsonProperty private Boolean buch_Rely_Vlv;
    @JsonProperty private Integer buch_Rely_Vlv_Nos;
    @JsonProperty private Integer buch_Rely_Vlv_H;
    @JsonProperty private Integer buch_Rely_Vlv_L;
    @JsonProperty private Integer buch_Rely_Vlv_Flg_OD;
    @JsonProperty private Integer buch_Rely_Vlv_Flg_PCD;
    @JsonProperty private Integer buch_Rely_Vlv_Flg_Thick;
    @JsonProperty private Integer buch_Rely_Vlv_Flg_Bolt_Dia;
    @JsonProperty private Integer buch_Rely_Vlv_Flg_Bolt_Nos;
}
