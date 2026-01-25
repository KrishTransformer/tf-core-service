package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LidLiftLug {

    @JsonProperty
    private Integer lid_LiftLug_L1;
    @JsonProperty private Integer lid_LiftLug_L2;
    @JsonProperty private Integer lid_LiftLug_H;
    @JsonProperty private Integer lid_LiftLug_Thick;
    @JsonProperty private Integer lid_LiftLug_Hole_Dia;
    @JsonProperty private Integer lid_LiftLug_Rod_Dia;
    @JsonProperty private Integer lid_LiftLug_Fillet_Rad;
    @JsonProperty private Integer lid_LiftLug_Vpos;
    @JsonProperty private Integer lid_LiftLug_Hpos;

}
