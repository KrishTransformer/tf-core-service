package com.tf.core_service.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.tf.core_service.model.twoWindings.*;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwoWindingRequest {
    public String designId;
    public Double kVA;
    public Boolean dryType;
    public ETransBodyType eTransBodyType;
    public ETransCostType eTransCostType;
    public DryTempClass dryTempClass;
    public EWindingType lvWindingType;
    public EWindingType hvWindingType;
    public String lVConductorMaterial;
    public String hVConductorMaterial;
    public Double lvCurrentDensity;
    public Double hvCurrentDensity;
    private Double buildFactor;
    private Double fluxDensity;
    private EVectorGroup vectorGroup;
    private Integer frequency;
    public Double tapStepsPercent;
    public Integer tapStepsPositive;
    public Integer tapStepsNegative;
    private Integer lowVoltage;
    private Integer highVoltage;
    private Integer lvConductorFlag;
    private Integer hvConductorFlag;
    private Integer lvNumberOfLayers;
    private Integer hvNumberOfLayers;
    public double kValue;
    public ERadiatorType eRadiatorType;
    public ETerminalType lvTerminalType;
    public ETerminalType hvTerminalType;
    public Boolean isOLTC; //If this is true, then OLTC; else OCTC
    public Boolean isCSP;
    public Double limitEz;
    public Core core;
    public Cost cost;
    public Windings innerWindings;
    public Windings outerWindings;
    public Tank tank;
    public Integer ambientTemp;
    public Integer windingTemp;
    public Integer topOilTemp;
    public Integer radiatorWidth;
    public CoilDimensions coilDimensions;
    public LockedAttributes lockedAttributes;


}
