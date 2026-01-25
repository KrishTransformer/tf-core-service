package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Lid {
    @JsonProperty
    private Integer lid_Thick;  //EleDes
    @JsonProperty private Integer lid_Extra_Projection;
    @JsonProperty private Integer lid_L;
    @JsonProperty private Integer lid_W;
    @JsonProperty private Integer lid_Bolt_Hole_Dia;
    @JsonProperty private Integer lid_Slope_Ang;


}
