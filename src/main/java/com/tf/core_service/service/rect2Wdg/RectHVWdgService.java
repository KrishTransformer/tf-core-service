package com.tf.core_service.service.rect2Wdg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.model.twoWindings.TwoWindings;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.utils.NumberFormattingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class RectHVWdgService {

    @Autowired
    Map<String, Map<Double, Float>> fluxDensityConfig;

    public Map<String, Object> calculateRectHvWdg (TwoWindings twoWindings, TwoWindingRequest twoWindingRequest) throws JsonProcessingException {

        Map<String, Object> formula = new HashMap<>();

        Map<Double, Float> specificLossMap = fluxDensityConfig.get(twoWindings.getCore().getCoreMaterial());
        Double fluxDensity = twoWindings.getFluxDensity();
        Double lowerFluxDens = NumberFormattingUtils.oneDigitDecimalFloor(fluxDensity);
        Double upperFluxDens = NumberFormattingUtils.oneDigitDecimal(fluxDensity);

        double specificLoss = 0;
        if (specificLossMap != null) {
            Float lowerSpecificLoss = specificLossMap.get(lowerFluxDens);
            Float upperSpecificLoss = specificLossMap.get(upperFluxDens);
            specificLoss = TwoWindingsFormulas.getSpecificLoss(fluxDensity, lowerFluxDens, upperFluxDens, lowerSpecificLoss, upperSpecificLoss, twoWindings.getFrequency());
        }

        Integer hvNoOfDuct = twoWindings.getOuterWindings().getDucts() == null? 0: twoWindings.getOuterWindings().getDucts();
        Integer hvDuctThickness = twoWindings.getOuterWindings().getDuctSize();


        double lvHvGap = TwoWindingsFormulas.getLvHvGap(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), twoWindings.getCoilDimensions().getLVHVGap(), twoWindings.getDryType());

        //From here onwards starts the HV Winding Design!!
        double hvHeight = 0;
        double hvHeightInsulated =0;
        double hvBreadth = 0;
        double hvBreadthInsulated =0;
        int hvRadialParallelConductors = 1;
        int hvAxialParallelConductors = 1;
        int hvTransposition = 0;
        double hvTotalCondCrossSection = 0;
        int hvConductorFlag = twoWindings.getHvConductorFlag();

        double hvVoltsPerPhase = TwoWindingsFormulas.getHvVoltsPerPhase(twoWindings.getHighVoltage(), twoWindings.getConnection());

        double hvStepVoltage = TwoWindingsFormulas.hvStepVoltage(hvVoltsPerPhase, twoWindings.getTapStepsPercent());

        double hvTurnsPerTap = NumberFormattingUtils.twoDigitDecimal(hvStepVoltage/ twoWindings.getVoltsPerTurn());

        double hvHighestTapVoltage = hvVoltsPerPhase + hvStepVoltage * twoWindings.getTapStepsPositive();

        double hvLowestTapVoltage = hvVoltsPerPhase - hvStepVoltage * twoWindings.getTapStepsNegative();

        double hvTurnsPerPhase = TwoWindingsFormulas.getTurnsPerPhase(hvVoltsPerPhase, twoWindings.getVoltsPerTurn(), twoWindings.getOuterWindings().getTurnsPerPhase(), twoWindings.getConnection(), false);

        if(twoWindings.getOuterWindings().getTurnsPerPhase() != null){
            hvVoltsPerPhase = Math.ceil(hvTurnsPerPhase * twoWindings.getVoltsPerTurn());
            hvStepVoltage = TwoWindingsFormulas.hvStepVoltage(hvVoltsPerPhase, twoWindings.getTapStepsPercent());
            hvTurnsPerTap = NumberFormattingUtils.twoDigitDecimal(hvStepVoltage/ twoWindings.getVoltsPerTurn());
            hvHighestTapVoltage = hvVoltsPerPhase + hvStepVoltage * twoWindings.getTapStepsPositive();
            hvLowestTapVoltage = hvVoltsPerPhase - hvStepVoltage * twoWindings.getTapStepsNegative();
            hvTurnsPerPhase = TwoWindingsFormulas.getTurnsPerPhase(hvVoltsPerPhase, twoWindings.getVoltsPerTurn(), twoWindings.getOuterWindings().getTurnsPerPhase(), twoWindings.getConnection(), false);
        }

        int hvTurnsAtHighest = (int) Math.floor(hvTurnsPerPhase + hvTurnsPerTap * twoWindings.getTapStepsPositive());

        int hvTurnsAtLowest = (int) Math.floor(hvTurnsPerPhase - hvTurnsPerTap * twoWindings.getTapStepsNegative());

        double hvCurrentPerPhase = TwoWindingsFormulas.getCurrentPerPhase(twoWindings.getKVA(), hvVoltsPerPhase);

        double hvCurrentAtLowest = TwoWindingsFormulas.getCurrentPerPhase(twoWindings.getKVA(), hvLowestTapVoltage);

        double hvEndClearance = TwoWindingsFormulas.getEndClearance(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), twoWindings.getOuterWindings().getEndClearances(),twoWindings.getDryType());

        double hvWindingLength = TwoWindingsFormulas.getWindingLength(twoWindings.getCore().getLimbHt(), hvEndClearance, twoWindings.getPermaWoodRing());
        hvDuctThickness = TwoWindingsFormulas.getDuctSize(twoWindings.getKVA(), hvWindingLength, hvDuctThickness,twoWindings.getDryType());

        double hvConductorCrossSection = TwoWindingsFormulas.getConductorCrossSection(hvCurrentAtLowest, twoWindings.getHVCurrentDensity());
        int hvNoOfConductors = TwoWindingsFormulas.getNumberOfConductors(hvConductorCrossSection, twoWindings.getHVConductorMaterial());
        double hvCrossSecPerConductor = NumberFormattingUtils.twoDigitDecimal(hvConductorCrossSection/hvNoOfConductors);
        boolean hvIsConductorRound = TwoWindingsFormulas.isConductorRound(hvConductorCrossSection);
        int hvIdWidth = TwoWindingsFormulas.getID(twoWindings.getRectCoilDimensions().getLvOdWidth(), lvHvGap);
        int hvIdDepth = TwoWindingsFormulas.getID(twoWindings.getRectCoilDimensions().getLvOdDepth(), lvHvGap);
        int hvOdDepth = 0;
        int hvOdWidth = 0;
        double hvRevisedCondCrossSection = 0;
        double hvConductorInsulation = 0;
        boolean hvIsEnamel = twoWindings.getHighVoltage() <= 33000 && twoWindings.getOuterWindings().getIsEnamel();
        double hvTurnsPerLayer = 0;
        double hvNumberOfLayersRough = 0;
        double hvNumberOfLayers = 0;
        double hVRevisedCurrDenAtNormal = 0;
        double hVRevisedCurrDenAtLowest = 0;
        double hvNewWindingLength = 0;
        double hvInterlayerInsulation = 0;
        int hvRadialThickness = 0;
        double hvLmt = 0;
        double hvWireLength = 0;
        double hvR75 = 0;
        double hvR26 = 0;
        double hvBareWeight = 0;
        double hvInsulatedWeight = 0;
        double hvProcurementWeight = 0;
        double hvStrayLoss = 0;
        double hvLoadLossAtLowest = 0;
        double hvLoadLossAtNormal = 0;
        double hvGradient = 0;
        double gradientLimit = TwoWindingsFormulas.getGradientLimit(twoWindings.getDryType(), twoWindings.getDryTempClass());
        double hvPartialDuct = 0;
        double hvFullDuct = 0;

        //Disc Winding calculation variables
        double hvDiscDuctsSize = TwoWindingsFormulas.getDiscDuctSize(twoWindings.getHighVoltage(), Boolean.FALSE, twoWindings.getConnection());
        int hvNoOfDiscs = 0;
        double hvTurnsPerDisc = 0;
        int hvNoOfSpacers = 0;
        int hvWidthOfSpacer = 0;
        int hvSpacersToBeRemoved = 0;
        int hvHalfDisc = 0;
        int hvFullDisc = 0;
        int hvPartialDisc = 0;
        String hvDiscArrangement = "";
        double v0 = 0;
        double psi =0;
        double rW = 0;
        double windingTemp = twoWindings.getWindingTemp();
        double ambientTemp = twoWindings.getAmbientTemp();

        //XOver Winding calculation variables
        int hvNoOfCoils = 0;
        int hvTurnsPerCoil = 0;
        int hvGapBwCoil = 0;
        int hvWdgLengthPerCoil = 0;
        String hvXOverTurnsLayers = "";

        //Rectangular Core HV Design
        if (Objects.equals(twoWindings.getHvWindingType().toString(), "HELICAL")){
            if(hvNoOfConductors == 1){
                if(hvIsConductorRound){
                    hvBreadth = hvIsConductorRound? NumberFormattingUtils.oneDigitDecimal(TwoWindingsFormulas.getDiameter(hvConductorCrossSection)) : 0.0;
                    hvRevisedCondCrossSection = NumberFormattingUtils.twoDigitDecimal(Math.PI * Math.pow(hvBreadth,2) / 4);
                    hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(),
                            twoWindings.getHighVoltage(),
                            hvIsConductorRound,
                            twoWindings.getConnection(), hvIsEnamel, twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getDryType()
                    );
                    hvHeight = hvBreadth;
                    hvBreadthInsulated = TwoWindingsFormulas.getHeightInsulated(hvBreadth, hvConductorInsulation);
                    hvHeightInsulated =  TwoWindingsFormulas.getHeightInsulated(hvBreadth, hvConductorInsulation);
                    hvTotalCondCrossSection = hvRevisedCondCrossSection;
                }
                else{
                    hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(),
                            twoWindings.getHighVoltage(),
                            hvIsConductorRound,
                            twoWindings.getConnection(), hvIsEnamel, twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getDryType());
                    hvHeight = 1.6;
                    hvHeightInsulated =TwoWindingsFormulas.getHeightInsulated(hvHeight, hvConductorInsulation);
                    hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                    hvBreadthInsulated =TwoWindingsFormulas.getHeightInsulated(hvBreadth,hvConductorInsulation);
                    hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                    while (hvBreadth > 6 * hvHeight){
                        hvHeight = NumberFormattingUtils.oneDigitDecimal(hvHeight + 0.1);
                        hvHeightInsulated =TwoWindingsFormulas.getHeightInsulated(hvHeight, hvConductorInsulation);
                        hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                        hvBreadthInsulated =TwoWindingsFormulas.getHeightInsulated(hvBreadth,hvConductorInsulation);
                        hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                        if(hvBreadth <= 6 * hvHeight){break;}
                    }
                    hvTotalCondCrossSection = hvRevisedCondCrossSection;
                }
            }
            else{
                hvRadialParallelConductors = TwoWindingsFormulas.getRadialParallelConductors(hvNoOfConductors, hvConductorFlag, twoWindings.getOuterWindings().getRadialParallelCond());
                hvAxialParallelConductors = TwoWindingsFormulas.getAxialParallelConductors(hvNoOfConductors, hvRadialParallelConductors, twoWindings.getOuterWindings().getAxialParallelCond());
                hvNoOfConductors = hvRadialParallelConductors * hvAxialParallelConductors;
                hvCrossSecPerConductor = NumberFormattingUtils.twoDigitDecimal(hvConductorCrossSection/hvNoOfConductors);
                hvTransposition = hvRadialParallelConductors > 1 ? 20 : 0;
                hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getHighVoltage(), hvIsConductorRound, twoWindings.getConnection(), hvIsEnamel, twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getDryType());
                hvHeight = 1.6;
                hvHeightInsulated =TwoWindingsFormulas.getHeightInsulated(hvHeight, hvConductorInsulation);
                hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                hvBreadthInsulated =TwoWindingsFormulas.getHeightInsulated(hvBreadth,hvConductorInsulation);
                hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                while (hvBreadth > 6 * hvHeight){
                    hvHeight = NumberFormattingUtils.oneDigitDecimal(hvHeight + 0.1);
                    hvHeightInsulated =TwoWindingsFormulas.getHeightInsulated(hvHeight, hvConductorInsulation);
                    hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                    hvBreadthInsulated =TwoWindingsFormulas.getHeightInsulated(hvBreadth,hvConductorInsulation);
                    hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                    if(hvBreadth <= 6 * hvHeight){break;};
                }
                hvTotalCondCrossSection = hvRevisedCondCrossSection * hvNoOfConductors;
            }
            hvTurnsPerLayer = Math.floor(hvWindingLength / (hvBreadthInsulated * hvAxialParallelConductors));

            hvNumberOfLayersRough = hvTurnsAtHighest / hvTurnsPerLayer;

            //We will be getting number of layers in terms of decimal. We will reduce the T/L by 1 and check if the last layer is more than 50%.
            while (hvNumberOfLayersRough % 1 <= 0.5){
                hvTurnsPerLayer -= 1;
                hvNumberOfLayersRough = hvTurnsAtHighest / hvTurnsPerLayer;
                if(hvNumberOfLayersRough % 1 > 0.5){break;};
            }

            hvNumberOfLayers = NumberFormattingUtils.twoDigitDecimal(hvNumberOfLayersRough);
            hVRevisedCurrDenAtNormal = NumberFormattingUtils.threeDigitDecimal(hvCurrentPerPhase/hvTotalCondCrossSection);
            hVRevisedCurrDenAtLowest = NumberFormattingUtils.threeDigitDecimal(hvCurrentAtLowest/hvTotalCondCrossSection);
            hvNewWindingLength = NumberFormattingUtils.nextInteger((hvTurnsPerLayer + 1) * (hvBreadthInsulated * hvAxialParallelConductors));
            hvEndClearance = hvEndClearance - (int) Math.floor(hvNewWindingLength - hvWindingLength);
            hvWindingLength = hvNewWindingLength;
            hvInterlayerInsulation = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn(), hvTurnsPerLayer, hvConductorInsulation, hvIsEnamel, twoWindings.getOuterWindings().getInterLayerInsulation(), twoWindings.getDryType());
            if(hvNoOfDuct > hvNumberOfLayers - 1){
                hvNoOfDuct =(int) hvNumberOfLayers - 1;
            }
            hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,hvInterlayerInsulation,hvNoOfDuct,hvDuctThickness, false);
            hvOdWidth = TwoWindingsFormulas.getOD(hvIdWidth, hvRadialThickness);
            hvOdDepth = TwoWindingsFormulas.getOD(hvIdDepth, hvRadialThickness);
            hvLmt = TwoWindingsFormulas.getRectLMT(hvIdWidth, hvIdDepth, hvOdWidth, hvOdDepth, hvRadialThickness, hvIsConductorRound);
            hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
            hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
            hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
            hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
            hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
            hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
            hvStrayLoss = TwoWindingsFormulas.getStrayLoss(hvBreadth,hvBreadthInsulated,hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation, twoWindings.getHVConductorMaterial(), hvNumberOfLayers, hvTransposition, hvIsConductorRound);
            hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
            hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
            hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) + 2, hvWindingLength, hvTransposition, hvLmt, twoWindings.getDryType());
            //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
            if(twoWindings.getOuterWindings().getDucts() == null){
                while(hvGradient >= gradientLimit && hvPartialDuct <= 6){
                    if(hvPartialDuct > hvNumberOfLayers){break;}
                    hvPartialDuct = hvPartialDuct + 1;
                    hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,hvInterlayerInsulation,hvNoOfDuct,hvDuctThickness, false);
                    hvOdWidth = TwoWindingsFormulas.getOD(hvIdWidth, hvRadialThickness);
                    hvOdDepth = TwoWindingsFormulas.getOD(hvIdDepth, hvRadialThickness);
                    hvLmt = TwoWindingsFormulas.getRectLMT(hvIdWidth, hvIdDepth, hvOdWidth, hvOdDepth, hvRadialThickness, hvIsConductorRound);
                    hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
                    hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
                    hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
                    hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
                    hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvBreadth,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
                    hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
                    hvStrayLoss = TwoWindingsFormulas.getStrayLoss(hvBreadth,hvBreadthInsulated,hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation, twoWindings.getHVConductorMaterial(), hvNumberOfLayers, 0, hvIsConductorRound);
                    hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
                    hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
                    hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) +2, hvWindingLength, hvTransposition, hvLmt, twoWindings.getDryType());
                    log.info("hvNumberOfLayers " + hvNumberOfLayers + " Ducts " + hvNoOfDuct + " Gradient = " + hvGradient);
                }
                if(hvGradient >= gradientLimit){
                    hvPartialDuct = 0;
                    while(hvGradient >= gradientLimit && hvFullDuct <= 6){
                        if(hvFullDuct > hvNumberOfLayers){break;}
                        hvFullDuct = hvFullDuct + 1;
                        hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,hvInterlayerInsulation,hvNoOfDuct,hvDuctThickness, false);
                        hvOdWidth = TwoWindingsFormulas.getOD(hvIdWidth, hvRadialThickness);
                        hvOdDepth = TwoWindingsFormulas.getOD(hvIdDepth, hvRadialThickness);
                        hvLmt = TwoWindingsFormulas.getRectLMT(hvIdWidth, hvIdDepth, hvOdWidth, hvOdDepth, hvRadialThickness, hvIsConductorRound);
                        hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
                        hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
                        hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
                        hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
                        hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvBreadth,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
                        hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
                        hvStrayLoss = TwoWindingsFormulas.getStrayLoss(hvBreadth,hvBreadthInsulated,hvHeight,hvTurnsPerLayer,1, 1, hvConductorInsulation, twoWindings.getHVConductorMaterial(), hvNumberOfLayers, 0, hvIsConductorRound);
                        hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
                        hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
                        hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) +2, hvWindingLength, hvTransposition, hvLmt, twoWindings.getDryType());
                        log.info("hvNumberOfLayers " + hvNumberOfLayers + " Ducts " + hvNoOfDuct + " Gradient = " + hvGradient);
                    }
                }
            }
        }

        //All the code below this line is for the rest of the core calculations.
        double hvHvGap = TwoWindingsFormulas.getHvHVGap(twoWindings.getKVA(), twoWindings.getLowVoltage(),twoWindings.getHighVoltage(), twoWindings.getConnection(), twoWindings.getCoilDimensions().getHVHVGap(), twoWindings.getDryType());

        int centerDistance = TwoWindingsFormulas.getCenterDistance(hvOdWidth, hvHvGap);

        hvHvGap = centerDistance - hvOdWidth;

        double coreLength = TwoWindingsFormulas.getCoreLength(twoWindings.getCore().getCoreDia(), twoWindings.getCore().getLimbHt() ,centerDistance);

        double coreWeight = TwoWindingsFormulas.getCoreWeight(coreLength, twoWindings.getCore().getArea());

        //Here the specific loss is taken as 1.3 considering that the flux Density is 1.7333T. and that the material is Nip M4.
        int coreLoss = TwoWindingsFormulas.getCoreLoss(coreWeight, twoWindings.getBuildFactor(), specificLoss);

        int tankLoss = TwoWindingsFormulas.getTankLoss(twoWindings.getKVA(), twoWindings.getInnerWindings().getPhaseCurrent(), twoWindings.getLowVoltage(), twoWindings.getTank().getTankLoss(), twoWindings.getDryType());
        int totalLoadLoss = NumberFormattingUtils.nextInteger(twoWindings.getInnerWindings().getLoadLoss() + hvLoadLossAtNormal + tankLoss);
        double kW55 = TwoWindingsFormulas.getKw55(coreLoss, twoWindings.getInnerWindings().getLoadLoss(), hvLoadLossAtLowest, tankLoss, twoWindings.getInnerWindings().getTempGradDegC(), twoWindings.getOuterWindings().getTempGradDegC());
        //TODO: Build factor revise

        //Active Part Size Calc
        int activePartLength = 2 * centerDistance + hvOdWidth;
        int activePartHeight = (int)(2 * twoWindings.getCore().getCoreDia() + twoWindings.getCore().getLimbHt());
        String activePartSize = activePartLength + " L X " + hvOdDepth  + " W X " + activePartHeight + " H mm";

        //All for HV Winding
        formula.put("lvHvGap", lvHvGap);
        formula.put("hvVoltsPerPhase", hvVoltsPerPhase);
        formula.put("hvTurnsPerPhase", hvTurnsPerPhase);
        formula.put("hvTurnsAtLowest", hvTurnsAtLowest);
        formula.put("hvTurnsAtHighest", hvTurnsAtHighest);
        formula.put("hvTurnsPerTap", hvTurnsPerTap);
        formula.put("hvEndClearance", hvEndClearance);
        formula.put("hvCurrentPerPhase", hvCurrentPerPhase);
        formula.put("hvCurrentAtLowest", hvCurrentAtLowest);
        formula.put("hVRevisedCurrDenAtNormal", hVRevisedCurrDenAtNormal);
        formula.put("hVRevisedCurrDenAtLowest", hVRevisedCurrDenAtLowest);
        formula.put("hvNumberOfLayers", hvNumberOfLayers);
        formula.put("hvTurnsPerLayer", hvTurnsPerLayer);
        formula.put("hvDiscArrangement", hvDiscArrangement);
        formula.put("hvConductorDia",hvBreadth);
        formula.put("hvConductorCrossSection", hvConductorCrossSection);
        formula.put("hvIsConductorRound", hvIsConductorRound);
        formula.put("hvConductorInsulation", hvConductorInsulation);
        formula.put("hvIsEnamel", hvIsEnamel);
        formula.put("hvRevisedCondCrossSection", hvRevisedCondCrossSection);
        formula.put("hvTotalCondCrossSection", hvTotalCondCrossSection);
        formula.put("hvInterLayerInsulation", hvInterlayerInsulation);
        formula.put("hvRadialThickness", hvRadialThickness);
        formula.put("hvIdWidth", hvIdWidth);
        formula.put("hvIdDepth", hvIdDepth);
        formula.put("hvOdWidth", hvOdWidth);
        formula.put("hvOdDepth", hvOdDepth);
        formula.put("hvLmt", hvLmt);
        formula.put("hvWireLength", hvWireLength);
        formula.put("hvR75", hvR75);
        formula.put("hvR26", hvR26);
        formula.put("hvBareWeight", hvBareWeight);
        formula.put("hvInsulatedWeight", hvInsulatedWeight);
        formula.put("hvProcurementWeight",hvProcurementWeight);
        formula.put("%hvStrayLoss", hvStrayLoss);
        formula.put("hvLoadLossAtNormal", hvLoadLossAtNormal);
        formula.put("hvGradient", hvGradient);
        formula.put("hvBreadth", hvBreadth);
        formula.put("hvBreadthInsulated", hvBreadthInsulated);
        formula.put("hvHeight", hvHeight);
        formula.put("hvHeightInsulated", hvHeightInsulated);
        formula.put("hvNoOfConductors", hvNoOfConductors);
        formula.put("hvLoadLossAtLowest", hvLoadLossAtLowest);
        formula.put("hvWindingLength", hvWindingLength);
        formula.put("hvNoOfDuct", hvNoOfDuct);
        formula.put("hvDuctThickness", hvDuctThickness);
        formula.put("hvAxialParallelConductors", hvAxialParallelConductors);
        formula.put("hvRadialParallelConductors", hvRadialParallelConductors);
        formula.put("activePartSize", activePartSize);
        formula.put("hvNoOfSpacers", hvNoOfSpacers);
        formula.put("hvWidthOfSpacer", hvWidthOfSpacer);
        formula.put("v0", v0);
        formula.put("psi", psi);
        formula.put("rW", rW);
        formula.put("hvTurnsPerCoil", hvTurnsPerCoil);
        formula.put("hvNoOfCoils", hvNoOfCoils);
        formula.put("hvGapBwCoil", hvGapBwCoil);
        formula.put("hvWdgLengthPerCoil", hvWdgLengthPerCoil);
        formula.put("hvXOverTurnsLayers", hvXOverTurnsLayers);

        //All the formula.puts for Core and related information is done below.
        formula.put("hvHvGap", hvHvGap);
        formula.put("centerDistance", centerDistance);
        formula.put("coreLength", coreLength);
        formula.put("coreWeight", coreWeight);
        formula.put("coreLoss", coreLoss);
        formula.put("tankLoss", tankLoss);
        formula.put("totalLoadLoss", totalLoadLoss);
        formula.put("kW55", kW55);
        return formula;
    }
}
