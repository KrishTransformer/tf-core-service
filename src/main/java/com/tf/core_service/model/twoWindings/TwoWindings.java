package com.tf.core_service.model.twoWindings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tf.core_service.utils.Constants;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//@JsonPropertyOrder (value = {"eTransType", "eTransDesignType"})
@Data
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class TwoWindings {

    public String designId;

    //start of input
    @JsonProperty("eTransBodyType") private ETransBodyType eTransBodyType = ETransBodyType.CIRCULAR;
    @JsonProperty("eTransCostType") private ETransCostType eTransCostType = ETransCostType.ECONOMIC;
    @JsonProperty("kVA") private Double kVA;
    @JsonProperty private Boolean dryType;
    @JsonProperty private DryTempClass dryTempClass;
    @JsonProperty ("lvWindingType") private EWindingType lvWindingType = EWindingType.HELICAL;
    @JsonProperty ("hvWindingType") private EWindingType hvWindingType = EWindingType.HELICAL;

    @JsonProperty private Double lowVoltage = 0.0;
    @JsonProperty private Double highVoltage = 0.0;

    @JsonProperty("lvCurrentDensity") public double lVCurrentDensity;
    @JsonProperty("hvCurrentDensity") public double hVCurrentDensity;
    @JsonProperty("lVConductorMaterial") private String lVConductorMaterial = Constants.COPPER;
    @JsonProperty("hVConductorMaterial") private String hVConductorMaterial = Constants.COPPER;

    @JsonProperty("lVLimbs") private String lVLimbs = "Series";
    @JsonProperty("hVLimbs") private String hVLimbs = "Series";

    @JsonProperty private Integer frequency;
    @JsonProperty("vectorGroup") private EVectorGroup connection;
    @JsonProperty ("eRadiatorType") private ERadiatorType eRadiatorType = ERadiatorType.RADIATOR;

    @JsonProperty private Double buildFactor;
    @JsonProperty private double fluxDensity = 0.0;

    @JsonProperty public Double tapStepsPercent;
    @JsonProperty public Integer tapStepsPositive;
    @JsonProperty public Integer tapStepsNegative;
    @JsonProperty private  double voltsPerTurn = 0;
    @JsonProperty private double turnsPerTap = 0;
    @JsonProperty private Integer lvConductorFlag = 0;
    @JsonProperty private Integer hvConductorFlag = 0;
    @JsonProperty private double lvNumberOfLayers = 0.0;
    @JsonProperty private Boolean isOLTC;
    @JsonProperty private Boolean isCSP;

    @JsonProperty private Integer tankLoss = 0;
    @JsonProperty private Integer loadLoss = 0;
    @JsonProperty private Integer coreLoss = 0;
    @JsonProperty private Integer lossesAt50Percent = 0;
    @JsonProperty private Integer lossesAt100Percent = 0;
    @JsonProperty public Double limitEz;
    @JsonProperty private double ez = 0.0;

    //Temperatures
    @JsonProperty private Integer ambientTemp = 0;
    @JsonProperty private Integer windingTemp = 0;
    @JsonProperty private Integer topOilTemp = 0;

    @JsonProperty private Core core = new Core();
    @JsonProperty private Tank tank = new Tank();
    @JsonProperty private Windings innerWindings = new Windings();
    @JsonProperty private Windings outerWindings = new Windings();
    @JsonProperty private CoilDimensions coilDimensions = new CoilDimensions();
    @JsonProperty private RectCoilDimensions rectCoilDimensions = new RectCoilDimensions();
    @JsonProperty private Cost cost = new Cost();

//    @JsonProperty private String coreLVClr = "0";
//    @JsonProperty("lVHVClr") private String lVHVClr = "0";
//    @JsonProperty("hVHVGap") private String hVHVGap = "0";


    //just for calculations
    @JsonProperty private Integer permaWoodRing = 0;
    @JsonProperty private Double kValue = 0.0;

    public Map<String, Object> commonFormulas = new HashMap<>();
    public Map<String, Object> lvFormulas = new HashMap<>();
    public Map<String, Object> hvFormulas = new HashMap<>();
    public Map<String, Object> tankAndOilFormulas = new HashMap<>();

    @JsonProperty private EfficiencyAndVr efficiencyAndVr = new EfficiencyAndVr();

    @JsonProperty private int hvImpulseVoltage;
    @JsonProperty private int hvTestVoltage;
    @JsonProperty private int lvImpulseVoltage;
    @JsonProperty private int lvTestVoltage;

    @JsonProperty private String insCoreLv;
    @JsonProperty private String insLvHv;
    @JsonProperty private String insHvHv;

    @JsonProperty private double nLCurrentPercentage;
    @JsonProperty private Comments comments;
    @JsonProperty private LockedAttributes lockedAttributes;
}
