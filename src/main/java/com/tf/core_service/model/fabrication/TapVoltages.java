package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class TapVoltages {
    private Map<String, Integer> tapVoltages = new LinkedHashMap<>();

    @JsonValue
    public Map<String, Integer> getTapVoltages() {
        return tapVoltages;
    }
}
