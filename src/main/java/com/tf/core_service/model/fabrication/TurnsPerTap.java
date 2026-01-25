package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class TurnsPerTap {
    private Map<String, Integer> turnsPerTap = new LinkedHashMap<>();

    @JsonValue
    public Map<String, Integer> getTurnsPerTap() {
        return turnsPerTap;
    }
}
