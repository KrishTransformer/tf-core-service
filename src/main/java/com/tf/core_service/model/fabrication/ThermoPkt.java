package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ThermoPkt {
    @JsonProperty Boolean thermoPkt1;
    @JsonProperty Boolean thermoPkt2;
    @JsonProperty Integer thermoPkt1_Hpos;
    @JsonProperty Integer thermoPkt1_Vpos;
    @JsonProperty Integer thermoPkt2_Hpos;
    @JsonProperty Integer thermoPkt2_Vpos;
    @JsonProperty Integer thermoPkt1_Hole_Dia = 23;
    @JsonProperty Integer thermoPkt2_Hole_Dia = 23;
}
