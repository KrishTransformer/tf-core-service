package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FabricationCore {
    @JsonProperty
    private String core_Fixture_Type;
    @JsonProperty private Integer core_Fixture_H;
    @JsonProperty private Integer core_Fixture_W;
    @JsonProperty private Integer core_Fixture_L;
    @JsonProperty private Double core_Fixture_Thick;
    @JsonProperty private Integer core_Bottom_Clearence;
    @JsonProperty private Integer core_Dia;
}
