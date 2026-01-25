package com.tf.core_service.model.fabrication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Printouts {
    @JsonProperty private Integer kva;
    @JsonProperty private Integer voltsAtHv;
    @JsonProperty private Integer voltsAtLv;
    @JsonProperty private Double amperesHv;
    @JsonProperty private Double amperesLv;
    @JsonProperty private String phasesHv;
    @JsonProperty private String phasesLv;
    @JsonProperty private Integer frequency;
    @JsonProperty private Double impedance;
    @JsonProperty private String vectorGroup;
    @JsonProperty private  Integer topOilTemp;
    @JsonProperty private Integer windingTemp;
    @JsonProperty private String coolingType;
    @JsonProperty private Integer weightsOfActivePart;
    @JsonProperty private Integer oilWeight;
    @JsonProperty private Integer totalOil;
    @JsonProperty private String basicInsulationLevelHV;
    @JsonProperty private  String basicInsulationLevelLV;
    @JsonProperty private Double ampsHV;
    @JsonProperty private Double ampsLV;
    @JsonProperty private String tappingHVVariations;
    @JsonProperty private Integer lossesAt50;
    @JsonProperty private Integer lossesAt100;
    @JsonProperty private  Integer weightOfTankAndAcc;
}