package com.tf.core_service.model.twoWindings;

import lombok.Data;

@Data
public class EfficiencyAndVr {
    private double efficiencyAtUnity_100;
    private double efficiencyAtUnity_75;
    private double efficiencyAtUnity_50;

    private double voltageRegulation_100;
    private double voltageRegulation_80;
}
