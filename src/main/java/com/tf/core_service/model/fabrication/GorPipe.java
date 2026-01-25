package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GorPipe {
    @JsonProperty public Boolean buchholz_Relay;
    @JsonProperty public Boolean single_Valve;
    @JsonProperty public Boolean valve_Type1;
    @JsonProperty public Integer gorPipe_Type;
    @JsonProperty public Integer buchholz_Type;
    @JsonProperty public Integer gorPipe_Hpos;
    @JsonProperty public Integer gorPipe_Vpos;
    @JsonProperty public Double gorPipe_Ass_L;
    @JsonProperty public Double gorPipe_Ass_H;
    @JsonProperty public Integer gorPipe_CutOut_Dia;
}
