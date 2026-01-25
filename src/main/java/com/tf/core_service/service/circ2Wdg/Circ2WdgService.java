package com.tf.core_service.service.circ2Wdg;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.formulas.CostDefaultValues;
import com.tf.core_service.formulas.LockedAttributesDefaultValues;
import com.tf.core_service.formulas.twoWdg.TankFormulas;
import com.tf.core_service.model.twoWindings.*;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.utils.CommonFunctions;
import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.utils.NumberFormattingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class Circ2WdgService {

    @Autowired
    LvWindingsService lvWindingsService;

    @Autowired
    HvWindingsService hvWindingsService;

    @Autowired
    TankAndOilServices tankAndOilServices;

    @Autowired
    CommentsService commentsService;


    public TwoWindings calculateForTwoWindingsWithChecks(TwoWindingRequest twoWindingRequest) throws JacksonException {
        System.out.println("calculateForTwoWindingsWithChecks");

        int lvConductorFlag = 0;
        int lvNumberOfLayers = 2;
        while(true) {
            twoWindingRequest.setLvConductorFlag(lvConductorFlag);
            twoWindingRequest.setLvNumberOfLayers(lvNumberOfLayers);
            TwoWindings twoWindings = calculateForTwoWindings(twoWindingRequest);

            double b = (double) twoWindings.getLvFormulas().get("lvBreadth");
            double h = (double) twoWindings.getLvFormulas().get("lvHeight");
            double g = twoWindings.getInnerWindings().getTempGradDegC();

            if (b > h + 2 &&  b<= 6 * h && g <= 14.5) {
                return twoWindings;
            } else {
                if (lvConductorFlag < 2) {
                    lvConductorFlag++;
                } else {
                    lvConductorFlag = 0;
                    lvNumberOfLayers = lvNumberOfLayers + 2;
                }
                if (lvNumberOfLayers > 4) {
                    return twoWindings;
                }
            }
            log.info("breadth = " + b + " , height = " + h + " , gradient = "  + g );
        }


    }



    public TwoWindings calculateForTwoWindings(TwoWindingRequest twoWindingRequest) throws JacksonException {

        twoWindingRequest.setHvConductorFlag(0);
        //TODO: The conductor flag always 0 for HV... :(

        //common variables
        Boolean dryType = twoWindingRequest.getDryType() != null && twoWindingRequest.getDryType();
        DryTempClass dryTempClass = twoWindingRequest.getDryTempClass();

        int frequency = TwoWindingsFormulas.getFrequency(twoWindingRequest.getFrequency());
        double frequencyFactor = frequency != 50 ? (double) frequency /50: 1;
        String coreType = TwoWindingsFormulas.getCoreType(twoWindingRequest.getCore().getCoreType());
        double buildFactor = TwoWindingsFormulas.getBuildFactor(twoWindingRequest.getKVA(), coreType, twoWindingRequest.getBuildFactor());
        double fluxDensity = TwoWindingsFormulas.getFluxDensity(twoWindingRequest.getFluxDensity(), dryType);
        String coreMaterial = TwoWindingsFormulas.getCoreMaterial(twoWindingRequest.getCore().getCoreMaterial());
        double lowVoltage = TwoWindingsFormulas.getLowVoltage(twoWindingRequest.getLowVoltage());
        double highVoltage = TwoWindingsFormulas.getHighVoltage(twoWindingRequest.getHighVoltage());
        EVectorGroup vectorGroup = TwoWindingsFormulas.getVectorGroup(twoWindingRequest.getVectorGroup());

        double kValue = TwoWindingsFormulas.getKValue(twoWindingRequest.getKVA(), twoWindingRequest.getKValue(),
                twoWindingRequest.getLVConductorMaterial(),
                twoWindingRequest.getETransCostType()
        );

        double  lVCurrentDensity = TwoWindingsFormulas.getCurrentDensity(
                twoWindingRequest.getLVConductorMaterial(), twoWindingRequest.getETransCostType(), dryType, dryTempClass, true,
                twoWindingRequest.getLvCurrentDensity()
        );

        double hVCurrentDensity = TwoWindingsFormulas.getCurrentDensity(
                twoWindingRequest.getHVConductorMaterial(), twoWindingRequest.getETransCostType(), dryType, dryTempClass, false,
                twoWindingRequest.getHvCurrentDensity()
        );

        double voltsPerTurn = TwoWindingsFormulas.getVoltsPerTurn(kValue,twoWindingRequest.getKVA());

        Map<String, Object> commonFormulas = new HashMap<>();
        commonFormulas.put("frequency", frequency);
        commonFormulas.put("buildFactor", buildFactor);
        commonFormulas.put("fluxDensity", fluxDensity);
        commonFormulas.put("coreMaterial", coreMaterial);
        commonFormulas.put("lowVoltage", lowVoltage);
        commonFormulas.put("highVoltage", highVoltage);
        commonFormulas.put("vectorGroup", vectorGroup);
        commonFormulas.put("kValue", kValue);
        commonFormulas.put("lVCurrentDensity", lVCurrentDensity);
        commonFormulas.put("hVCurrentDensity", hVCurrentDensity);

        TwoWindings twoWindings = new TwoWindings();

        //From User input

        twoWindings.setDryType(dryType);
        twoWindings.setDryTempClass(dryTempClass);
        CoilDimensions coilDimensions = new CoilDimensions();
        coilDimensions.setCoreGap(twoWindingRequest.getCoilDimensions().getCoreGap());
        coilDimensions.setLVHVGap(twoWindingRequest.getCoilDimensions().getLVHVGap());
        coilDimensions.setHVHVGap(twoWindingRequest.getCoilDimensions().getHVHVGap());
        twoWindings.setCoilDimensions(coilDimensions);

        twoWindings.setDesignId(twoWindingRequest.getDesignId());
        twoWindings.setKVA(twoWindingRequest.getKVA());
        twoWindings.setETransBodyType(twoWindingRequest.getETransBodyType());
        twoWindings.setETransCostType(twoWindingRequest.getETransCostType());
        twoWindings.setLvWindingType(twoWindingRequest.getLvWindingType());
        twoWindings.setHvWindingType(twoWindingRequest.getHvWindingType());
//        twoWindings.setCoreType(coreType);
        twoWindings.setERadiatorType(TankFormulas.getERadiatorType(twoWindingRequest.getERadiatorType()));
        twoWindings.setLVConductorMaterial(twoWindingRequest.getLVConductorMaterial());
        twoWindings.setHVConductorMaterial(twoWindingRequest.getHVConductorMaterial());
        twoWindings.setLvConductorFlag(twoWindingRequest.getLvConductorFlag());
        twoWindings.setHvConductorFlag(twoWindingRequest.getHvConductorFlag());
        twoWindings.setLvNumberOfLayers(twoWindingRequest.getLvNumberOfLayers());
        twoWindings.setKValue(twoWindingRequest.getKValue());
        twoWindings.setIsOLTC(twoWindingRequest.getIsOLTC());
        twoWindings.setIsCSP(twoWindingRequest.getIsCSP());
        if(twoWindings.getKVA() >= 160){
            twoWindings.setTapStepsPercent(Objects.requireNonNullElse(twoWindingRequest.getTapStepsPercent(), 2.5));
            twoWindings.setTapStepsPositive(Objects.requireNonNullElse(twoWindingRequest.getTapStepsPositive(), 2));
            twoWindings.setTapStepsNegative(Objects.requireNonNullElse(twoWindingRequest.getTapStepsNegative(), 2));
        }else {
            twoWindings.setTapStepsPercent(Objects.requireNonNullElse(twoWindingRequest.getTapStepsPercent(), 0.0));
            twoWindings.setTapStepsPositive(Objects.requireNonNullElse(twoWindingRequest.getTapStepsPositive(), 0));
            twoWindings.setTapStepsNegative(Objects.requireNonNullElse(twoWindingRequest.getTapStepsNegative(), 0));
        }
        if(twoWindingRequest.getTank().getTankLoss() != null){
            twoWindings.setTankLoss(twoWindingRequest.getTank().getTankLoss());
        }
        twoWindings.setAmbientTemp(TwoWindingsFormulas.getAmbientTemp(twoWindingRequest.getAmbientTemp()));
        twoWindings.setWindingTemp(TwoWindingsFormulas.getWindingTemp(twoWindingRequest.getWindingTemp()));
        twoWindings.setTopOilTemp(TwoWindingsFormulas.getTopOilTemp(twoWindingRequest.getTopOilTemp()));

        //When given from the User
        Core core = new Core();
        if (twoWindingRequest.getCore() != null) {
            if (twoWindingRequest.getCore().getCoreDia() != null && twoWindingRequest.getCore().getCoreDia() != 0) {
                core.setCoreDia(twoWindingRequest.getCore().getCoreDia());
            }
            if(twoWindings.getKVA() <= 10){
                core.setLimbHt(50.0);
            }
            if (twoWindingRequest.getCore().getLimbHt() != null && twoWindingRequest.getCore().getLimbHt() != 0) {
                core.setLimbHt(twoWindingRequest.getCore().getLimbHt());
            }
        }
        twoWindings.setCore(core);
        twoWindings.setLimitEz(TwoWindingsFormulas.getLimitEz(twoWindings.getKVA(), twoWindingRequest.getLimitEz()));

        Windings innerWindings = new Windings();
        if(twoWindingRequest.getInnerWindings().getCondInsulation() != null){
            innerWindings.setCondInsulation(NumberFormattingUtils.twoDigitDecimal(twoWindingRequest.getInnerWindings().getCondInsulation()));
        }
        if(twoWindingRequest.getInnerWindings().getInterLayerInsulation() != null){
            innerWindings.setCondInsulation(NumberFormattingUtils.twoDigitDecimal(twoWindingRequest.getInnerWindings().getInterLayerInsulation()));
        }
        if(twoWindingRequest.getInnerWindings().getEndClearances() != null){
            innerWindings.setEndClearances(twoWindingRequest.getInnerWindings().getEndClearances());
        }
        if(twoWindingRequest.getInnerWindings().getTurnsPerPhase() != null){
            innerWindings.setTurnsPerPhase(twoWindingRequest.getInnerWindings().getTurnsPerPhase());
        }
        innerWindings.setDucts(twoWindingRequest.getInnerWindings().getDucts());
        innerWindings.setDuctSize(twoWindingRequest.getInnerWindings().getDuctSize());
        innerWindings.setNoOfLayers(twoWindingRequest.getInnerWindings().getNoOfLayers());
        innerWindings.setCondBreadth(twoWindingRequest.getInnerWindings().getCondBreadth());
        innerWindings.setCondHeight(twoWindingRequest.getInnerWindings().getCondHeight());

        innerWindings.setRadialParallelCond(twoWindingRequest.getInnerWindings().getRadialParallelCond());
        innerWindings.setAxialParallelCond(twoWindingRequest.getInnerWindings().getAxialParallelCond());
        innerWindings.setCondBreadth(twoWindingRequest.getInnerWindings().getCondBreadth());
        innerWindings.setCondHeight(twoWindingRequest.getInnerWindings().getCondHeight());
        innerWindings.setConductorDiameter(twoWindingRequest.getInnerWindings().getConductorDiameter());
        innerWindings.setIsConductorRound(twoWindingRequest.getInnerWindings().getIsConductorRound());
        if(twoWindingRequest.getInnerWindings().getIsEnamel() == null){
            innerWindings.setIsEnamel(false);
        }else {
            innerWindings.setIsEnamel(twoWindingRequest.getInnerWindings().getIsEnamel());
        }

        twoWindings.setInnerWindings(innerWindings);

        Windings outerWindings = new Windings();
        if(twoWindingRequest.getOuterWindings().getCondInsulation() != null){
            outerWindings.setCondInsulation(NumberFormattingUtils.twoDigitDecimal(twoWindingRequest.getOuterWindings().getCondInsulation()));
        }
        if(twoWindingRequest.getOuterWindings().getInterLayerInsulation() != null){
            outerWindings.setInterLayerInsulation(NumberFormattingUtils.twoDigitDecimal(twoWindingRequest.getOuterWindings().getInterLayerInsulation()));
        }
        if(twoWindingRequest.getOuterWindings().getEndClearances() != null){
            outerWindings.setEndClearances(twoWindingRequest.getOuterWindings().getEndClearances());
        }
        if(twoWindingRequest.getOuterWindings().getDucts() != null){
            outerWindings.setDucts(twoWindingRequest.getOuterWindings().getDucts());
        }
        if(twoWindingRequest.getOuterWindings().getDuctSize() != null){
            outerWindings.setDuctSize(twoWindingRequest.getOuterWindings().getDuctSize());
        }
        outerWindings.setRadialParallelCond(twoWindingRequest.getOuterWindings().getRadialParallelCond());
        outerWindings.setAxialParallelCond(twoWindingRequest.getOuterWindings().getAxialParallelCond());
        outerWindings.setCondBreadth(twoWindingRequest.getOuterWindings().getCondBreadth());
        outerWindings.setCondHeight(twoWindingRequest.getOuterWindings().getCondHeight());

        outerWindings.setRadialParallelCond(twoWindingRequest.getOuterWindings().getRadialParallelCond());
        outerWindings.setAxialParallelCond(twoWindingRequest.getOuterWindings().getAxialParallelCond());
        outerWindings.setCondBreadth(twoWindingRequest.getOuterWindings().getCondBreadth());
        outerWindings.setCondHeight(twoWindingRequest.getOuterWindings().getCondHeight());
        outerWindings.setConductorDiameter(twoWindingRequest.getOuterWindings().getConductorDiameter());
        outerWindings.setIsConductorRound(twoWindingRequest.getOuterWindings().getIsConductorRound());
        if(twoWindingRequest.getOuterWindings().getIsEnamel() == null){
            outerWindings.setIsEnamel(false);
        }else {
            outerWindings.setIsEnamel(twoWindingRequest.getOuterWindings().getIsEnamel());
        }

        twoWindings.setOuterWindings(outerWindings);

        //From common variables
        twoWindings.setFrequency(frequency);
        twoWindings.setBuildFactor(buildFactor);
        twoWindings.setFluxDensity(fluxDensity);
//        twoWindings.setCoreMaterial(coreMaterial);
        twoWindings.setLowVoltage(lowVoltage);
        twoWindings.setHighVoltage(highVoltage);
        twoWindings.setConnection(vectorGroup);
        twoWindings.setLVCurrentDensity(lVCurrentDensity);
        twoWindings.setHVCurrentDensity(hVCurrentDensity);
        twoWindings.setKValue(kValue);
        twoWindings.setVoltsPerTurn(voltsPerTurn);

        double ek = 0;

        Map<String, Object> hvWindings = new HashMap<>();

        int i = 0;

        while (true) {

            twoWindings.getInnerWindings().setDucts(twoWindingRequest.getInnerWindings().getDucts());
            twoWindings.setInnerWindings(innerWindings);
            twoWindings.getOuterWindings().setDucts(twoWindingRequest.getOuterWindings().getDucts());
            twoWindings.setOuterWindings(outerWindings);

            log.info("Limb Height: " + twoWindings.getCore().getLimbHt());

            //From LV Windings
            Map<String, Object> lvWindings = lvWindingsService.calculateLvWindings(twoWindings, twoWindingRequest);
            twoWindings.setLvFormulas(lvWindings);
            //Form calculations
            innerWindings.setTurnsPerPhase(CommonFunctions.toDouble(lvWindings.get("lvTurnsPerPhase")));
            twoWindings.setPermaWoodRing(CommonFunctions.toInteger(lvWindings.get("permaWoodRing")));
            twoWindings.setVoltsPerTurn(CommonFunctions.toDouble(lvWindings.get("revisedVoltsPerTurn")));
            twoWindings.setFluxDensity(CommonFunctions.toDouble(lvWindings.get("revisedFluxDensity")));
            innerWindings.setTurnsPerPhase(CommonFunctions.toDouble(lvWindings.get("lvTurnsPerPhase")));
            innerWindings.setPhaseCurrent(CommonFunctions.toDouble(lvWindings.get("lvCurrentPerPhase")));
            innerWindings.setCurrentDensity(CommonFunctions.toDouble(lvWindings.get("lVRevisedCurrentDensity")));
            innerWindings.setCondCrossSec(CommonFunctions.toDouble(lvWindings.get("lvTotalCondCrossSection")));
            if (CommonFunctions.toDouble(lvWindings.get("lvBreadth")) == CommonFunctions.toDouble(lvWindings.get("lvHeight"))) {
                innerWindings.setConductorSizes("Round " + CommonFunctions.toDouble(lvWindings.get("lvBreadth")));
            } else {
                innerWindings.setConductorSizes(CommonFunctions.toDouble(lvWindings.get("lvBreadth")) + " X " + CommonFunctions.toDouble(lvWindings.get("lvHeight")));
            }
            innerWindings.setCondInsulation(CommonFunctions.toDouble(lvWindings.get("lvConductorInsulation")));
            innerWindings.setWindingLength(CommonFunctions.toDouble(lvWindings.get("lvWindingLength")));
            innerWindings.setNoOfLayers(CommonFunctions.toDouble(lvWindings.get("lvNumberOfLayers")));
            innerWindings.setTurnsPerLayer(CommonFunctions.toDouble(lvWindings.get("lvTurnsPerLayer")));
            innerWindings.setEndClearances(CommonFunctions.toDouble(lvWindings.get("lvEndClearance")));
            innerWindings.setEddyStrayLoss(CommonFunctions.toDouble(lvWindings.get("%lvStrayLoss")));
            if (CommonFunctions.toInteger(lvWindings.get("lvNumberOfConductors")) > 1) {
                int rad = CommonFunctions.toInteger(lvWindings.get("lvRadialParallelConductors"));
                int axi = CommonFunctions.toInteger(lvWindings.get("lvAxialParallelConductors"));
                int nofCond = CommonFunctions.toInteger(lvWindings.get("lvNumberOfConductors"));
                innerWindings.setNoInParallel("Rad " + rad + " X " + "Axi " + axi + " = " + nofCond);
                System.out.println("Rad " + rad + " X " + "Axi " + axi + " = " + nofCond);
            } else {
                innerWindings.setNoInParallel(CommonFunctions.toInteger(lvWindings.get("lvNumberOfConductors")).toString());
            }
            innerWindings.setTempGradDegC(CommonFunctions.toDouble(lvWindings.get("lvGradient")));
            innerWindings.setDucts(CommonFunctions.toInteger(lvWindings.get("lvNoOfDuct")));
            innerWindings.setDuctSize(CommonFunctions.toInteger(lvWindings.get("lvDuctThickness")));
            innerWindings.setInsulatedWeight(CommonFunctions.toDouble(lvWindings.get("lvInsulatedWeight")));
            innerWindings.setBareWeight(CommonFunctions.toDouble(lvWindings.get("lvBareWeight")));
            innerWindings.setLoadLoss(CommonFunctions.toDouble(lvWindings.get("lvLoadLoss")));
            innerWindings.setInterLayerInsulation(CommonFunctions.toDouble(lvWindings.get("lvInterLayerInsulation")));
            innerWindings.setNoOfDuctsWidth(lvWindings.get("lvNoOfDuct") + " / " + lvWindings.get("lvDuctThickness"));
            if (Objects.equals(twoWindings.getLvWindingType().toString(), "HELICAL")) {
                innerWindings.setTurnsLayers(lvWindings.get("lvTurnsPerLayer").toString());
            } else if (Objects.equals(twoWindings.getLvWindingType().toString(), "DISC")) {
                innerWindings.setTurnsLayers(lvWindings.get("lvDiscArrangement").toString());
            } else if (Objects.equals(twoWindings.getLvWindingType().toString(), "FOIL")) {
                innerWindings.setTurnsLayers(lvWindings.get("lvTurnsPerLayer").toString());
            }
            innerWindings.setWeightBareInsulated(lvWindings.get("lvBareWeight") + " / " + lvWindings.get("lvInsulatedWeight"));

            innerWindings.setRadialParallelCond((Integer) lvWindings.get("lvRadialParallelConductors"));
            innerWindings.setAxialParallelCond((Integer) lvWindings.get("lvAxialParallelConductors"));
            innerWindings.setCondBreadth((Double) lvWindings.get("lvBreadth"));
            innerWindings.setCondHeight((Double) lvWindings.get("lvHeight"));
            innerWindings.setConductorDiameter((Double) lvWindings.get("lvBreadth"));
            innerWindings.setIsConductorRound((Boolean) lvWindings.get("lvIsConductorRound"));
            innerWindings.setIsEnamel((Boolean) lvWindings.get("lvIsEnamel"));

            twoWindings.setInnerWindings(innerWindings);

            core.setArea(CommonFunctions.toDouble(lvWindings.get("netArea")));
            core.setCoreDia(CommonFunctions.toInteger(lvWindings.get("coreDiameter")));
            core.setLimbHt(CommonFunctions.toDouble(lvWindings.get("windowHeight")));
            core.setCoreType(coreType);
            core.setCoreMaterial(coreMaterial);
            twoWindings.setCore(core);

            coilDimensions.setCoreDia(CommonFunctions.toInteger(lvWindings.get("coreDiameter")));
            coilDimensions.setCoreGap(CommonFunctions.toDouble(lvWindings.get("coreGap")));
            coilDimensions.setLVID(CommonFunctions.toInteger(lvWindings.get("lvId")));
            coilDimensions.setLVRadial(CommonFunctions.toInteger(lvWindings.get("lvRadialThickness")));
            coilDimensions.setLVOD(CommonFunctions.toInteger(lvWindings.get("lvOd")));
            twoWindings.setCoilDimensions(coilDimensions);

            //From HV Windings
            hvWindings = hvWindingsService.calculateHvWindings(twoWindings, twoWindingRequest);
            twoWindings.setHvFormulas(hvWindings);
            outerWindings.setTurnsPerPhase(CommonFunctions.toDouble(hvWindings.get("hvTurnsPerPhase")));
//            twoWindings.setHighVoltage(CommonFunctions.toDouble(hvWindings.get("hvVoltsPerPhase")));
            outerWindings.setPhaseCurrent(CommonFunctions.toDouble(hvWindings.get("hvCurrentPerPhase")));
            outerWindings.setCurrentDensity(CommonFunctions.toDouble(hvWindings.get("hVRevisedCurrDenAtLowest")));
            outerWindings.setCondCrossSec(CommonFunctions.toDouble(hvWindings.get("hvTotalCondCrossSection")));
            if (CommonFunctions.toDouble(hvWindings.get("hvBreadth")) == CommonFunctions.toDouble(hvWindings.get("hvHeight"))) {
                outerWindings.setConductorSizes("Round " + CommonFunctions.toDouble(hvWindings.get("hvBreadth")));
            } else {
                outerWindings.setConductorSizes(CommonFunctions.toDouble(hvWindings.get("hvBreadth")) + " X " + CommonFunctions.toDouble(hvWindings.get("hvHeight")));
            }
            outerWindings.setCondInsulation(CommonFunctions.toDouble(hvWindings.get("hvConductorInsulation")));
            outerWindings.setWindingLength(CommonFunctions.toDouble(hvWindings.get("hvWindingLength")));
            outerWindings.setNoOfLayers(CommonFunctions.toDouble(hvWindings.get("hvNumberOfLayers")));
            outerWindings.setTurnsPerLayer(CommonFunctions.toDouble(hvWindings.get("hvTurnsPerLayer")));
            outerWindings.setEndClearances(CommonFunctions.toDouble(hvWindings.get("hvEndClearance")));
            outerWindings.setEddyStrayLoss(CommonFunctions.toDouble(hvWindings.get("%hvStrayLoss")));
            if (CommonFunctions.toInteger(hvWindings.get("hvNoOfConductors")) > 1) {
                int rad = CommonFunctions.toInteger(hvWindings.get("hvRadialParallelConductors"));
                int axi = CommonFunctions.toInteger(hvWindings.get("hvAxialParallelConductors"));
                int nofCond = CommonFunctions.toInteger(hvWindings.get("hvNoOfConductors"));
                outerWindings.setNoInParallel("Rad " + rad + " X " + "Axi " + axi + " = " + nofCond);
                System.out.println("Rad " + rad + " X " + "Axi " + axi + " = " + nofCond);
            } else {
                outerWindings.setNoInParallel(CommonFunctions.toInteger(hvWindings.get("hvNoOfConductors")).toString());
            }
            outerWindings.setTempGradDegC(CommonFunctions.toDouble(hvWindings.get("hvGradient")));
            outerWindings.setDucts(CommonFunctions.toInteger(hvWindings.get("hvNoOfDuct")));
            outerWindings.setDuctSize(CommonFunctions.toInteger(hvWindings.get("hvDuctThickness")));
            outerWindings.setInsulatedWeight(CommonFunctions.toDouble(hvWindings.get("hvInsulatedWeight")));
            outerWindings.setBareWeight(CommonFunctions.toDouble(hvWindings.get("hvBareWeight")));
            outerWindings.setLoadLoss(CommonFunctions.toDouble(hvWindings.get("hvLoadLossAtNormal")));
            outerWindings.setInterLayerInsulation(CommonFunctions.toDouble(hvWindings.get("hvInterLayerInsulation")));
            outerWindings.setNoOfDuctsWidth(hvWindings.get("hvNoOfDuct") + " / " + hvWindings.get("hvDuctThickness"));
            if (Objects.equals(twoWindings.getHvWindingType().toString(), "HELICAL")) {
                outerWindings.setTurnsLayers(hvWindings.get("hvTurnsPerLayer").toString());
            } else if (Objects.equals(twoWindings.getHvWindingType().toString(), "DISC")) {
                outerWindings.setTurnsLayers(hvWindings.get("hvDiscArrangement").toString());
            } else if (Objects.equals(twoWindings.getHvWindingType().toString(), "XOVER")) {
                outerWindings.setTurnsLayers(hvWindings.get("hvXOverTurnsLayers").toString());
            }
            outerWindings.setWeightBareInsulated(hvWindings.get("hvBareWeight") + " / " + hvWindings.get("hvInsulatedWeight"));


            outerWindings.setRadialParallelCond((Integer) hvWindings.get("hvRadialParallelConductors"));
            outerWindings.setAxialParallelCond((Integer) hvWindings.get("hvAxialParallelConductors"));
            outerWindings.setCondBreadth((Double) hvWindings.get("hvBreadth"));
            outerWindings.setCondHeight((Double) hvWindings.get("hvHeight"));
            outerWindings.setConductorDiameter((Double) hvWindings.get("hvBreadth"));
            outerWindings.setIsConductorRound((Boolean) hvWindings.get("hvIsConductorRound"));
            outerWindings.setIsEnamel((Boolean) hvWindings.get("hvIsEnamel"));

            twoWindings.setOuterWindings(outerWindings);

            twoWindings.setTurnsPerTap(CommonFunctions.toDouble(hvWindings.get("hvTurnsPerTap")));

            coilDimensions.setLVHVGap(CommonFunctions.toDouble(hvWindings.get("lvHvGap")));
            coilDimensions.setHVID(CommonFunctions.toInteger(hvWindings.get("hvId")));
            coilDimensions.setHVRadial(CommonFunctions.toInteger(hvWindings.get("hvRadialThickness")));
            coilDimensions.setHVOD(CommonFunctions.toInteger(hvWindings.get("hvOd")));
            coilDimensions.setHVHVGap(CommonFunctions.toDouble(hvWindings.get("hvHvGap")));
            coilDimensions.setActivePartSize(hvWindings.get("activePartSize").toString());
            twoWindings.setCoilDimensions(coilDimensions);

            core.setCenDist(CommonFunctions.toInteger(hvWindings.get("centerDistance")));
            core.setCoreWeight(CommonFunctions.toDouble(hvWindings.get("coreWeight")));
            twoWindings.setCore(core);

            //All for Impedance calculations
            int ampereTurns = (int) TwoWindingsFormulas.ampereTurns(twoWindings.getInnerWindings().getTurnsPerPhase(), twoWindings.getInnerWindings().getPhaseCurrent());
            int lvDuctThickness = CommonFunctions.toInteger(lvWindings.get("lvDuctThickness"));
            int hvDuctThickness = CommonFunctions.toInteger(hvWindings.get("hvDuctThickness"));
            double h1 = TwoWindingsFormulas.h1h2(coilDimensions.getLVRadial(), twoWindings.getInnerWindings().getDucts(), lvDuctThickness, twoWindings.getInnerWindings().getCondInsulation());
            double h2 = TwoWindingsFormulas.h1h2(coilDimensions.getHVRadial(), twoWindings.getOuterWindings().getDucts(), hvDuctThickness, twoWindings.getOuterWindings().getCondInsulation());
            boolean isHelical = Objects.equals(twoWindings.getLvWindingType().toString(), "HELICAL") && Objects.equals(twoWindings.getHvWindingType().toString(), "HELICAL");
            double ls = TwoWindingsFormulas.ls(CommonFunctions.toDouble(lvWindings.get("lvBreadthInsulated")), CommonFunctions.toDouble(hvWindings.get("hvBreadthInsulated")), twoWindings.getInnerWindings().getTurnsPerLayer(), twoWindings.getOuterWindings().getTurnsPerLayer(), CommonFunctions.toInteger(lvWindings.get("lvAxialParallelConductors")), CommonFunctions.toInteger(hvWindings.get("hvAxialParallelConductors")), coilDimensions.getHVOD(), coilDimensions.getLVID(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getInnerWindings().getWindingLength(), twoWindings.getOuterWindings().getWindingLength(), twoWindings.getLvWindingType(),twoWindings.getHvWindingType(),(int) lvWindings.get("lvTransposition"), (int)hvWindings.get("hvNoOfCoils"))[0];
            double l =  TwoWindingsFormulas.ls(CommonFunctions.toDouble(lvWindings.get("lvBreadthInsulated")), CommonFunctions.toDouble(hvWindings.get("hvBreadthInsulated")), twoWindings.getInnerWindings().getTurnsPerLayer(), twoWindings.getOuterWindings().getTurnsPerLayer(), CommonFunctions.toInteger(lvWindings.get("lvAxialParallelConductors")), CommonFunctions.toInteger(hvWindings.get("hvAxialParallelConductors")), coilDimensions.getHVOD(), coilDimensions.getLVID(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getInnerWindings().getWindingLength(), twoWindings.getOuterWindings().getWindingLength(), twoWindings.getLvWindingType(),twoWindings.getHvWindingType(),(int) lvWindings.get("lvTransposition"), (int)hvWindings.get("hvNoOfCoils"))[1];
            double b =  TwoWindingsFormulas.ls(CommonFunctions.toDouble(lvWindings.get("lvBreadthInsulated")), CommonFunctions.toDouble(hvWindings.get("hvBreadthInsulated")), twoWindings.getInnerWindings().getTurnsPerLayer(), twoWindings.getOuterWindings().getTurnsPerLayer(), CommonFunctions.toInteger(lvWindings.get("lvAxialParallelConductors")), CommonFunctions.toInteger(hvWindings.get("hvAxialParallelConductors")), coilDimensions.getHVOD(), coilDimensions.getLVID(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getInnerWindings().getWindingLength(), twoWindings.getOuterWindings().getWindingLength(), twoWindings.getLvWindingType(),twoWindings.getHvWindingType(),(int) lvWindings.get("lvTransposition"), (int)hvWindings.get("hvNoOfCoils"))[2];
            double kR =  TwoWindingsFormulas.ls(CommonFunctions.toDouble(lvWindings.get("lvBreadthInsulated")), CommonFunctions.toDouble(hvWindings.get("hvBreadthInsulated")), twoWindings.getInnerWindings().getTurnsPerLayer(), twoWindings.getOuterWindings().getTurnsPerLayer(), CommonFunctions.toInteger(lvWindings.get("lvAxialParallelConductors")), CommonFunctions.toInteger(hvWindings.get("hvAxialParallelConductors")), coilDimensions.getHVOD(), coilDimensions.getLVID(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getInnerWindings().getWindingLength(), twoWindings.getOuterWindings().getWindingLength(), twoWindings.getLvWindingType(),twoWindings.getHvWindingType(),(int) lvWindings.get("lvTransposition"), (int)hvWindings.get("hvNoOfCoils"))[3];
            double delta = TwoWindingsFormulas.ex(twoWindings.getVoltsPerTurn(), coilDimensions.getLVHVGap(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), h1, h2, ampereTurns, ls, coilDimensions.getLVOD(), frequencyFactor)[0];
            double delta1 = TwoWindingsFormulas.ex(twoWindings.getVoltsPerTurn(), coilDimensions.getLVHVGap(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), h1, h2, ampereTurns, ls, coilDimensions.getLVOD(), frequencyFactor)[1];
            double ds = TwoWindingsFormulas.ex(twoWindings.getVoltsPerTurn(), coilDimensions.getLVHVGap(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), h1, h2, ampereTurns, ls, coilDimensions.getLVOD(), frequencyFactor)[2];
            double ex = TwoWindingsFormulas.ex(twoWindings.getVoltsPerTurn(), coilDimensions.getLVHVGap(), twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getOuterWindings().getCondInsulation(), h1, h2, ampereTurns, ls, coilDimensions.getLVOD(), frequencyFactor)[3];
            double er = TwoWindingsFormulas.er(twoWindings.getInnerWindings().getLoadLoss(), twoWindings.getOuterWindings().getLoadLoss(), twoWindings.getKVA(), twoWindings.getInnerWindings().getPhaseCurrent(), twoWindings.getLowVoltage());
            ek = TwoWindingsFormulas.ek(er, ex);

            commonFormulas.put("ampereTurns",ampereTurns);
            commonFormulas.put("h1",h1);
            commonFormulas.put("h2",h2);
            commonFormulas.put("ls",ls);
            commonFormulas.put("delta",delta);
            commonFormulas.put("delta1",delta1);
            commonFormulas.put("ds",ds);
            commonFormulas.put("ex",ex);
            commonFormulas.put("er",er);
            commonFormulas.put("ek",ek);
            commonFormulas.put("l",l);
            commonFormulas.put("kR", kR);
            commonFormulas.put("b", b);

            log.info("impedanceLoop " + i++);
            log.info("WindowHt " + twoWindings.getCore().getLimbHt());
            log.info("Lv Winding Length " + twoWindings.getInnerWindings().getWindingLength());
            log.info("Ez: " + ek);
            log.info("End Clearance " + twoWindings.getInnerWindings().getEndClearances());

            //Check Impedence
            boolean isEzWithinRange = TwoWindingsFormulas.isEzWithinRange(twoWindings.getLimitEz(), ek, twoWindings.getKVA() <= 10 ? 20 : 5);
            if (isEzWithinRange || twoWindingRequest.getCore().getLimbHt() != null || twoWindingRequest.getInnerWindings().getCondBreadth() != null
                    || twoWindingRequest.getOuterWindings().getCondBreadth() != null || twoWindingRequest.getInnerWindings().getCondHeight() != null||
                    twoWindingRequest.getOuterWindings().getCondHeight() != null || i >= 20
            ) {
                //The Impedance loop does not run if
                    //-LimbHt is changed by user, -Breadth is given by the user - or if the Ez is within limits.
                    //If the iteration 'i' > 100
                break;
            } else {
                double modifiedLimbHt = TwoWindingsFormulas.getModifiedLimbHtForImpedance(ek, twoWindings.getLimitEz(), twoWindings.getCore().getLimbHt(), twoWindings.getKVA());
                core.setLimbHt(modifiedLimbHt);
                twoWindings.getInnerWindings().setCondBreadth(null);
                twoWindings.getInnerWindings().setCondHeight(null);
                twoWindings.getOuterWindings().setCondBreadth(null);
                twoWindings.getOuterWindings().setCondHeight(null);
                twoWindings.getInnerWindings().setDucts(null);
                twoWindings.getOuterWindings().setDucts(null);
                twoWindings.getInnerWindings().setEndClearances(null);
                twoWindings.getInnerWindings().setInterLayerInsulation(null);
                twoWindings.getOuterWindings().setInterLayerInsulation(null);

            }
        }

        twoWindings.setEz(ek);

        Cost cost = new Cost();
        if (twoWindingRequest.getCost()  != null) {
            cost.setCopperCostPerKg(CostDefaultValues.getCuCostPerKg(twoWindingRequest.getCost().getCopperCostPerKg()));
            cost.setAluminiumCostPerKg(CostDefaultValues.getAlCostPerKg(twoWindingRequest.getCost().getAluminiumCostPerKg()));
            cost.setCoreCostPerKg(CostDefaultValues.getCoreCostPerKg(twoWindingRequest.getCost().getCoreCostPerKg()));
            cost.setOilCostPerKg(CostDefaultValues.getOilCostPerKg(twoWindingRequest.getCost().getOilCostPerKg()));
            cost.setSteelCostPerKg(CostDefaultValues.getSteelCostPerKg(twoWindingRequest.getCost().getSteelCostPerKg()));
            cost.setInsulationCostPerKg(CostDefaultValues.getInsulationCostPerKg(twoWindingRequest.getCost().getInsulationCostPerKg()));
            cost.setRadiatorCostPerKg(CostDefaultValues.getRadiatorCostPerKg(twoWindingRequest.getCost().getRadiatorCostPerKg()));
        }
        twoWindings.setCost(cost);

        Map<String,Object> tankAndOil = tankAndOilServices.calculateTankAndOil(twoWindings);
        Tank tank = new Tank();
        tank.setTankLength(CommonFunctions.toInteger(tankAndOil.get("tankLength")));
        tank.setTankWidth(CommonFunctions.toInteger(tankAndOil.get("tankWidth")));
        tank.setTankHeight(CommonFunctions.toInteger(tankAndOil.get("tankHeight")));
        tank.setTankCapacity(CommonFunctions.toDouble(tankAndOil.get("tankCapacity")));
        tank.setTankLidThickness(CommonFunctions.toDouble(tankAndOil.get("tankLidThickness")));
        tank.setTankWallThickness(CommonFunctions.toDouble(tankAndOil.get("tankWallThickness")));
        tank.setTankBottomThickness(CommonFunctions.toDouble(tankAndOil.get("tankBottomThickness")));
        tank.setFrameThickness(CommonFunctions.toDouble(tankAndOil.get("frameThickness")));
        tank.setTankDimension(CommonFunctions.toInteger(tankAndOil.get("tankLength")) + " x " +
                CommonFunctions.toInteger(tankAndOil.get("tankWidth")) + " x " +
                CommonFunctions.toInteger(tankAndOil.get("tankHeight"))
        );
        tank.setOverallDimension(CommonFunctions.toDouble(tankAndOil.get("overallLength")) + " x " +
                CommonFunctions.toDouble(tankAndOil.get("overallWidth")) + " x " +
                CommonFunctions.toDouble(tankAndOil.get("overallHeight"))
        );
        tank.setTankLoss(CommonFunctions.toInteger(hvWindings.get("tankLoss")));
        twoWindings.setTankLoss(CommonFunctions.toInteger(hvWindings.get("tankLoss")));
        twoWindings.setTank(tank);
        twoWindings.setCoreLoss(CommonFunctions.toInteger(hvWindings.get("coreLoss")));
        twoWindings.setLoadLoss(CommonFunctions.toInteger(hvWindings.get("totalLoadLoss")));

        twoWindings.setCommonFormulas(commonFormulas);


        twoWindings.setTankAndOilFormulas(tankAndOil);

        cost.setTotalCondCost(CommonFunctions.toInteger(tankAndOil.get("conductorCost")));
        cost.setTotalCoreCost(CommonFunctions.toInteger(tankAndOil.get("coreCost")));
        cost.setTotalInsCost(CommonFunctions.toInteger(tankAndOil.get("insulationCost")));
        cost.setTotalOilCost(CommonFunctions.toInteger(tankAndOil.get("oilCost")));
        cost.setTotalSteelCost(CommonFunctions.toInteger(tankAndOil.get("steelCost")));
        cost.setTotalRadiatorCost(CommonFunctions.toInteger(tankAndOil.get("radiatorCost")));
        cost.setCapitalCost(CommonFunctions.toInteger(tankAndOil.get("capitalCost")));
        twoWindings.setCost(cost);

        EfficiencyAndVr efficiencyAndVr = new EfficiencyAndVr();
        efficiencyAndVr.setEfficiencyAtUnity_100(TwoWindingsFormulas.getEfficiencyPercentage(twoWindings.getKVA(), (int) hvWindings.get("totalLoadLoss"), (int) hvWindings.get("coreLoss"), 1.0, 1.0));
        efficiencyAndVr.setEfficiencyAtUnity_75(TwoWindingsFormulas.getEfficiencyPercentage(twoWindings.getKVA(), (int) hvWindings.get("totalLoadLoss"), (int) hvWindings.get("coreLoss"), 0.75, 1.0));
        efficiencyAndVr.setEfficiencyAtUnity_50(TwoWindingsFormulas.getEfficiencyPercentage(twoWindings.getKVA(), (int) hvWindings.get("totalLoadLoss"), (int) hvWindings.get("coreLoss"), 0.5, 1.0));
        efficiencyAndVr.setVoltageRegulation_100(TwoWindingsFormulas.getVoltageRegulation((double) commonFormulas.get("er"), (double) commonFormulas.get("ex"), 1.0));
        efficiencyAndVr.setVoltageRegulation_80(TwoWindingsFormulas.getVoltageRegulation((double) commonFormulas.get("er"), (double) commonFormulas.get("ex"), 0.8));
        twoWindings.setEfficiencyAndVr(efficiencyAndVr);

        twoWindings.setLvTestVoltage(TwoWindingsFormulas.getTestAndImpTest(twoWindings.getLowVoltage())[0]);
        twoWindings.setLvImpulseVoltage(TwoWindingsFormulas.getTestAndImpTest(twoWindings.getLowVoltage())[1]);
        twoWindings.setHvTestVoltage(TwoWindingsFormulas.getTestAndImpTest(twoWindings.getHighVoltage())[0]);
        twoWindings.setHvImpulseVoltage(TwoWindingsFormulas.getTestAndImpTest(twoWindings.getHighVoltage())[1]);

        twoWindings.setInsCoreLv(TwoWindingsFormulas.getCoreLvIns(twoWindings.getLvWindingType(), twoWindings.getCoilDimensions().getCoreGap()));
        twoWindings.setInsLvHv(TwoWindingsFormulas.getLvHvIns(twoWindings.getLvWindingType(), twoWindings.getHvWindingType(), twoWindings.getCoilDimensions().getLVHVGap()));
        twoWindings.setInsHvHv(TwoWindingsFormulas.getHvHvIns(twoWindings.getLvWindingType(), twoWindings.getHvWindingType(), twoWindings.getCoilDimensions().getHVHVGap()));

        twoWindings.setLossesAt50Percent(TwoWindingsFormulas.getLossAt50Percent(twoWindings.getCoreLoss(), twoWindings.getTankLoss(), twoWindings.getInnerWindings().getLoadLoss(), twoWindings.getOuterWindings().getLoadLoss()));
        twoWindings.setLossesAt100Percent(TwoWindingsFormulas.getLossAt100Percent(twoWindings.getCoreLoss(), twoWindings.getTankLoss(), twoWindings.getInnerWindings().getLoadLoss(), twoWindings.getOuterWindings().getLoadLoss()));

        twoWindings.setNLCurrentPercentage(TwoWindingsFormulas.getNLCurrentPercentage(core.getCoreWeight(), (double) hvWindings.get("specificLoss"), twoWindings.getKVA()));

        twoWindings.setComments(commentsService.getComments(twoWindings));

        LockedAttributes locks = twoWindingRequest.getLockedAttributes();

        twoWindings.setLockedAttributes(LockedAttributesDefaultValues.checkLockedAttributes(locks));

        return twoWindings;
    }
}
