package com.tf.core_service.model.twoWindings;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Core {
    private String coreType  = "0";
    private Integer coreDia;
    private Double limbHt;
    private double cenDist = 0.0;
    private double area = 0.0;
    private String blade = "0";
    private double wKgGrade = 0.0;
    private double fluxDensity = 0.0;
    private double coreWeight = 0.0;
    private String coreMaterial;
}
