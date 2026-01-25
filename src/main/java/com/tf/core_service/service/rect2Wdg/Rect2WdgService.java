package com.tf.core_service.service.rect2Wdg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.formulas.twoWdg.TankFormulas;
import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.model.twoWindings.*;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.utils.CommonFunctions;
import com.tf.core_service.utils.NumberFormattingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class Rect2WdgService {

    @Autowired
    RectLVWdgService rectLVWdgService;

    @Autowired
    RectHVWdgService rectHVWdgService;

    public TwoWindings calculate2Wdg(TwoWindingRequest twoWindingRequest) throws JsonProcessingException {
        //common variables
        Boolean dryType = twoWindingRequest.getDryType() != null && twoWindingRequest.getDryType();
        DryTempClass dryTempClass = twoWindingRequest.getDryTempClass();
        int frequency = TwoWindingsFormulas.getFrequency(twoWindingRequest.getFrequency());
        double frequencyFactor = frequency != 50 ? (double) frequency /50: 1;
        double buildFactor = TwoWindingsFormulas.getBuildFactor(twoWindingRequest.getKVA(), "PRIME", twoWindingRequest.getBuildFactor());
        double fluxDensity = TwoWindingsFormulas.getFluxDensity(twoWindingRequest.getFluxDensity(),dryType);
        double lowVoltage = TwoWindingsFormulas.getLowVoltage(twoWindingRequest.getLowVoltage());
        double highVoltage = TwoWindingsFormulas.getHighVoltage(twoWindingRequest.getHighVoltage());
        EVectorGroup vectorGroup = TwoWindingsFormulas.getVectorGroup(twoWindingRequest.getVectorGroup());

        double kValue = TwoWindingsFormulas.getKValue(twoWindingRequest.getKVA(), twoWindingRequest.getKValue(),
                twoWindingRequest.getLVConductorMaterial(),
                twoWindingRequest.getETransCostType()
        );

        double  lVCurrentDensity = TwoWindingsFormulas.getCurrentDensity(
                twoWindingRequest.getLVConductorMaterial(), twoWindingRequest.eTransCostType, dryType, dryTempClass, true,
                twoWindingRequest.getLvCurrentDensity()
        );

        double hVCurrentDensity = TwoWindingsFormulas.getCurrentDensity(
                twoWindingRequest.getHVConductorMaterial(), twoWindingRequest.eTransCostType, dryType, dryTempClass, false,
                twoWindingRequest.getHvCurrentDensity()
        );

        double voltsPerTurn = TwoWindingsFormulas.getVoltsPerTurn(kValue,twoWindingRequest.getKVA());

        Map<String, Object> commonFormulas = new HashMap<>();
        commonFormulas.put("frequency", frequency);
        commonFormulas.put("buildFactor", buildFactor);
        commonFormulas.put("fluxDensity", fluxDensity);
        commonFormulas.put("lowVoltage", lowVoltage);
        commonFormulas.put("highVoltage", highVoltage);
        commonFormulas.put("vectorGroup", vectorGroup);
        commonFormulas.put("kValue", kValue);
        commonFormulas.put("lVCurrentDensity", lVCurrentDensity);
        commonFormulas.put("hVCurrentDensity", hVCurrentDensity);

        TwoWindings twoWindings = new TwoWindings();
        twoWindings.setDryType(dryType);
        twoWindings.setDryTempClass(dryTempClass);
        twoWindings.setCommonFormulas(commonFormulas);
        //From User input
        twoWindings.setKVA(twoWindingRequest.getKVA());
        twoWindings.setETransBodyType(twoWindingRequest.getETransBodyType());
        twoWindings.setETransCostType(twoWindingRequest.getETransCostType());
        twoWindings.setLvWindingType(twoWindingRequest.getLvWindingType());
        twoWindings.setHvWindingType(twoWindingRequest.getHvWindingType());
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
        if(twoWindingRequest.getOuterWindings().getTurnsPerPhase() != null){
            outerWindings.setTurnsPerPhase(twoWindingRequest.getOuterWindings().getTurnsPerPhase());
        }
        if(twoWindingRequest.getOuterWindings().getDucts() != null){
            outerWindings.setDucts(twoWindingRequest.getOuterWindings().getDucts());
        }
        if(twoWindingRequest.getOuterWindings().getDuctSize() != null){
            outerWindings.setDuctSize(twoWindingRequest.getOuterWindings().getDuctSize());
        }
        if(twoWindingRequest.getOuterWindings().getIsEnamel() == null){
            outerWindings.setIsEnamel(false);
        }else {
            outerWindings.setIsEnamel(twoWindingRequest.getOuterWindings().getIsEnamel());
        }
        twoWindings.setOuterWindings(outerWindings);

        Core core = new Core();
        if (twoWindingRequest.getCore() != null) {
            if (twoWindingRequest.getCore().getCoreDia() != null && twoWindingRequest.getCore().getCoreDia() != 0) {
                core.setCoreDia(twoWindingRequest.getCore().getCoreDia());
            }
            if (twoWindingRequest.getCore().getLimbHt() != null && twoWindingRequest.getCore().getLimbHt() != 0) {
                core.setLimbHt(twoWindingRequest.getCore().getLimbHt());
            }
        }
        twoWindings.setCore(core);

        //From common variables
        twoWindings.setFrequency(frequency);
        twoWindings.setBuildFactor(buildFactor);
        twoWindings.setFluxDensity(fluxDensity);
        twoWindings.setLowVoltage(lowVoltage);
        twoWindings.setHighVoltage(highVoltage);
        twoWindings.setConnection(vectorGroup);
        twoWindings.setLVCurrentDensity(lVCurrentDensity);
        twoWindings.setHVCurrentDensity(hVCurrentDensity);
        twoWindings.setKValue(kValue);
        twoWindings.setVoltsPerTurn(voltsPerTurn);

        Map<String, Object> lvWindings = rectLVWdgService.calculateRectLvWdg(twoWindings, twoWindingRequest);
        //Form calculations
        innerWindings.setTurnsPerPhase(CommonFunctions.toDouble(lvWindings.get("lvTurnsPerPhase")));
        twoWindings.setPermaWoodRing(CommonFunctions.toInteger(lvWindings.get("permaWoodRing")));
        twoWindings.setVoltsPerTurn(CommonFunctions.toDouble(lvWindings.get("revisedVoltsPerTurn")));
        innerWindings.setTurnsPerPhase(CommonFunctions.toDouble(lvWindings.get("lvTurnsPerPhase")));
        innerWindings.setPhaseCurrent(CommonFunctions.toDouble(lvWindings.get("lvCurrentPerPhase")));
        innerWindings.setCurrentDensity(CommonFunctions.toDouble(lvWindings.get("lVRevisedCurrentDensity")));
        innerWindings.setCondCrossSec(CommonFunctions.toDouble(lvWindings.get("lvTotalCondCrossSection")));
        if(CommonFunctions.toDouble(lvWindings.get("lvBreadth")) == CommonFunctions.toDouble(lvWindings.get("lvHeight"))){
            innerWindings.setConductorSizes("Round " + CommonFunctions.toDouble(lvWindings.get("lvBreadth")));
        }else {
            innerWindings.setConductorSizes(CommonFunctions.toDouble(lvWindings.get("lvBreadth")) + " X " + CommonFunctions.toDouble(lvWindings.get("lvHeight")));
        }
        innerWindings.setCondInsulation(CommonFunctions.toDouble(lvWindings.get("lvConductorInsulation")));
        innerWindings.setWindingLength(CommonFunctions.toDouble(lvWindings.get("lvWindingLength")));
        innerWindings.setNoOfLayers(CommonFunctions.toDouble(lvWindings.get("lvNumberOfLayers")));
        innerWindings.setTurnsPerLayer(CommonFunctions.toDouble(lvWindings.get("lvTurnsPerLayer")));
        innerWindings.setEndClearances(CommonFunctions.toDouble(lvWindings.get("lvEndClearance")));
        innerWindings.setEddyStrayLoss(CommonFunctions.toDouble(lvWindings.get("%lvStrayLoss")));
        if(CommonFunctions.toInteger(lvWindings.get("lvNumberOfConductors")) > 1){
            int rad = CommonFunctions.toInteger(lvWindings.get("lvRadialParallelConductors"));
            int axi = CommonFunctions.toInteger(lvWindings.get("lvAxialParallelConductors"));
            int nofCond = CommonFunctions.toInteger(lvWindings.get("lvNumberOfConductors"));
            innerWindings.setNoInParallel("Rad " + rad + " X " + "Axi " + axi + " = "+ nofCond);
            log.info("Rad " + rad + " X " + "Axi " + axi + " = "+ nofCond);
        }else {
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
        if(Objects.equals(twoWindings.getLvWindingType().toString(), "HELICAL")){
            innerWindings.setTurnsLayers(lvWindings.get("lvTurnsPerLayer").toString());
        } else if (Objects.equals(twoWindings.getLvWindingType().toString(), "DISC")){
            innerWindings.setTurnsLayers(lvWindings.get("lvDiscArrangement").toString());
        } else if (Objects.equals(twoWindings.getLvWindingType().toString(), "FOIL")) {
            innerWindings.setTurnsLayers(lvWindings.get("lvTurnsPerLayer").toString());
        }
        innerWindings.setWeightBareInsulated(lvWindings.get("lvBareWeight") + " / " + lvWindings.get("lvInsulatedWeight"));
        innerWindings.setIsEnamel((Boolean) lvWindings.get("lvIsEnamel"));
        twoWindings.setInnerWindings(innerWindings);
        twoWindings.setLvFormulas(lvWindings);

        Map<String, Object> hvWindings = rectHVWdgService.calculateRectHvWdg(twoWindings, twoWindingRequest);
        outerWindings.setTurnsPerPhase(CommonFunctions.toDouble(hvWindings.get("hvTurnsPerPhase")));
        twoWindings.setHvFormulas(hvWindings);

        RectCoilDimensions rectCoilDimensions = new RectCoilDimensions();
        rectCoilDimensions.setCoreWidth(CommonFunctions.toInteger(lvWindings.get("coreWidth")));
        rectCoilDimensions.setCoreDepth(CommonFunctions.toInteger(lvWindings.get("coreDepth")));
        rectCoilDimensions.setCoreGap(CommonFunctions.toDouble(lvWindings.get("coreGap")));
        rectCoilDimensions.setLvIdWidth(CommonFunctions.toInteger(lvWindings.get("lvIdWidth")));
        rectCoilDimensions.setLvIdDepth(CommonFunctions.toInteger(lvWindings.get("lvIdDepth")));
        rectCoilDimensions.setLVRadial(CommonFunctions.toInteger(lvWindings.get("lvRadialThickness")));
        rectCoilDimensions.setLvOdWidth(CommonFunctions.toInteger(lvWindings.get("lvOdWidth")));
        rectCoilDimensions.setLvOdDepth(CommonFunctions.toInteger(lvWindings.get("lvOdDepth")));
        rectCoilDimensions.setLVHVGap(CommonFunctions.toDouble(lvWindings.get("lvHvGap")));
        rectCoilDimensions.setHvIdWidth(CommonFunctions.toInteger(lvWindings.get("hvIdWidth")));
        rectCoilDimensions.setHvIdDepth(CommonFunctions.toInteger(lvWindings.get("hvIdDepth")));
        rectCoilDimensions.setHvRadial(CommonFunctions.toInteger(lvWindings.get("hvRadialThickness")));
        rectCoilDimensions.setHvOdWidth(CommonFunctions.toInteger(lvWindings.get("hvOdWidth")));
        rectCoilDimensions.setHvOdDepth(CommonFunctions.toInteger(lvWindings.get("hvOdDepth")));
        twoWindings.setRectCoilDimensions(rectCoilDimensions);

        return twoWindings;
    }


}
