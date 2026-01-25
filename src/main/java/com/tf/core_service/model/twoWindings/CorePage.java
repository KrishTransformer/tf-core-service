package com.tf.core_service.model.twoWindings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tf.core_service.model.core.CoreStack;
import com.tf.core_service.model.core.ECoreBladeType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class CorePage {
    //Here we mention the variables that needs to be exported to the API.
    //The following variables are for the Core Page.
    @JsonProperty private Double coreDia = 0.0;
    @JsonProperty private Integer minimumStepWidth = 0;
    @JsonProperty private Integer numberOfSteps = 0;
    @JsonProperty private Integer fixtureStepWidth = 0;
    @JsonProperty private double coreArea = 0.0;
    @JsonProperty private double designedCoreArea = 0.0;
    @JsonProperty private ECoreBladeType eCoreBladeType;

    @JsonProperty private double coreWeight = 0.0;
    @JsonProperty public Map<String, Object> coreFormulas = new HashMap<>();
    @JsonProperty public List<CoreStack> bldStacks = new ArrayList<>();
    @JsonProperty public Double[][] centerLimbStacking = new Double[20][6];
    @JsonProperty public Double[][] sideLimbStacking = new Double[20][6];
    @JsonProperty public Double[][] yokeStacking = new Double[20][6];
    @JsonProperty public Double[][] singleNotchStacking = new Double[20][6];
    @JsonProperty public Double[][] doubleNotchStacking = new Double[20][6];
}
