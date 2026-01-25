package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Lift_Cons {
    @JsonProperty
    private Integer lift_Cons_L;
    @JsonProperty private Integer lift_Cons_W;
    @JsonProperty private Double lift_Cons_Thick;
    @JsonProperty private Double lift_Cons_Hole_Dia;
    @JsonProperty private String lift_Cons_Type;
}
