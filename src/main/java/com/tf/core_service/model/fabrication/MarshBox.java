package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarshBox {
    @JsonProperty private Boolean MarshBox;
    @JsonProperty private Integer MarshBox_HPos;
    @JsonProperty private Integer MarshBox_VPos;
}
