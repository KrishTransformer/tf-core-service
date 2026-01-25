package com.tf.core_service.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tf.core_service.model.core.ECoreBladeType;
import com.tf.core_service.model.fabrication.Fabrication;
import com.tf.core_service.model.files.LomBooleans;
import com.tf.core_service.model.files.LomDouble;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LomRequest {
    public boolean isTrue;

    //We will provide a boolean that will indicate if the material is present or not.
    //All the material booleans are listed below.
    public LomBooleans lomBooleans;

    public LomDouble lomQuantity;
    public LomDouble lomRate;
    public LomDouble lomCost;

}
