package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoreFoot {
    @JsonProperty private Double coreFoot_H;
    @JsonProperty private Integer coreFoot_Thick1;
    @JsonProperty private Double coreFoot_Thick2;
    @JsonProperty private Integer coreFoot_L;
    @JsonProperty private Integer coreFoot_W;
    @JsonProperty private Integer coreFoot_Stud_Hole_Dia;
    @JsonProperty private Integer coreFoot_Stud_Hole_Nos;
    @JsonProperty private Integer coreFoot_Nos;
}
