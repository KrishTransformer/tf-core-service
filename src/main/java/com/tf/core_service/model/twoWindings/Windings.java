package com.tf.core_service.model.twoWindings;

import lombok.Data;

@Data
public class Windings {
    public Double turnsPerPhase;
    private Double phaseCurrent = 0.0;
    private Double currentDensity = 0.0;
    private Double condCrossSec = 0.0;
    private String conductorSizes = "";
    public Double condInsulation;
    private String noInParallel = "";
    private Double windingLength = 0.;
    public Double noOfLayers;
    private Double turnsPerLayer = 0.0;
    private Double terminal = 0.0;
    private Double endClearances = 0.0;
    private Double eddyStrayLoss = 0.0;
    private Double tempGradDegC = 0.0;
    public Integer ducts;
    public Integer ductSize;
    private Double insulatedWeight = 0.0;
    private Double bareWeight = 0.0;
    private Double loadLoss = 0.0;
    private Double commRawCond = 0.0;
    public Double interLayerInsulation;
    private String noOfDuctsWidth = "";
    private String turnsLayers;
    private String weightBareInsulated = "";
    public Integer radialParallelCond;
    public Integer axialParallelCond;
    public Double condBreadth;
    public Double condHeight;
    public Double conductorDiameter;
    public Boolean isConductorRound;
    public Boolean isEnamel;
}
