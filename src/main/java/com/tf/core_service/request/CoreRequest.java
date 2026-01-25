package com.tf.core_service.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tf.core_service.model.core.ECoreBladeType;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreRequest {
    TwoWindingRequest twoWindingRequest = new TwoWindingRequest();
    public Integer coreDiameter;
    public Integer numberOfSteps;
    public Integer minimumStepWidth;
    public Double stackingFactor;
    public String typeOfCoreLamination;
    public Integer fixtureStepWidth;
    public List<CoreStackRequest> coreStackRequestList;
    public List<CoreStackRequest> prevCoreStackRequestList;
    public Double limbHt;
    public Double cenDist;
    public ECoreBladeType eCoreBladeType;
}
