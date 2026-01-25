package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class TapCurrent {
    private Map<String, Double> tapCurrent = new LinkedHashMap<>();

    @JsonValue
    public Map<String, Double> getTapCurrent() {
        return tapCurrent;
    }
}
