
package com.tf.core_service.service.circ2Wdg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.config.FluxDensityConfig;
import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.model.twoWindings.*;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.utils.NumberFormattingUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Service
@Slf4j
public class HvWindingsService {

    @Autowired
    Map<String, Map<Double, Float>> fluxDensityConfig;

    public Map<String, Object> calculateHvWindings(TwoWindings twoWindings, TwoWindingRequest twoWindingRequest) throws JsonProcessingException {

        Map<String, Object> formula = new HashMap<>();

        Map<Double, Float> specificLossMap = fluxDensityConfig.get(twoWindings.getCore().getCoreMaterial());
        Double fluxDensity = twoWindings.getFluxDensity();
        Double lowerFluxDens = NumberFormattingUtils.oneDigitDecimalFloor(fluxDensity - 0.01);
        Double upperFluxDens = NumberFormattingUtils.oneDigitDecimal(fluxDensity);

        double specificLoss = 0;
        if (specificLossMap != null) {
            Float lowerSpecificLoss = specificLossMap.get(lowerFluxDens);
            Float upperSpecificLoss = specificLossMap.get(upperFluxDens);
            specificLoss = TwoWindingsFormulas.getSpecificLoss(fluxDensity, lowerFluxDens, upperFluxDens, lowerSpecificLoss, upperSpecificLoss, twoWindings.getFrequency());
        }

        boolean specificLossBeyondLimits = false;
        if(specificLoss >= 10){specificLossBeyondLimits = true;}

        Integer hvNoOfDuct = twoWindingRequest.getOuterWindings().getDucts() == null? 0: twoWindingRequest.getOuterWindings().getDucts();
        Integer hvDuctThickness = twoWindingRequest.getOuterWindings().getDuctSize();


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

        double hvTurnsPerPhase = TwoWindingsFormulas.getTurnsPerPhase(hvVoltsPerPhase, twoWindings.getVoltsPerTurn(), twoWindingRequest.getOuterWindings().getTurnsPerPhase(), twoWindings.getConnection(), false);

        int hvTurnsAtHighest = (int) Math.floor(hvTurnsPerPhase + hvTurnsPerTap * twoWindings.getTapStepsPositive());

        int hvTurnsAtLowest = (int) Math.floor(hvTurnsPerPhase - hvTurnsPerTap * twoWindings.getTapStepsNegative());

        double hvCurrentPerPhase = TwoWindingsFormulas.getCurrentPerPhase(twoWindings.getKVA(), hvVoltsPerPhase);

        double hvCurrentAtLowest = TwoWindingsFormulas.getCurrentPerPhase(twoWindings.getKVA(), hvLowestTapVoltage);

        double hvEndClearance = TwoWindingsFormulas.getEndClearance(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), twoWindingRequest.getOuterWindings().getEndClearances(), twoWindings.getDryType());

        double hvWindingLength = TwoWindingsFormulas.getWindingLength(twoWindings.getCore().getLimbHt(), hvEndClearance, twoWindings.getPermaWoodRing());
        hvDuctThickness = TwoWindingsFormulas.getDuctSize(hvWindingLength, hvDuctThickness, twoWindings.getDryType());

        double hvConductorCrossSection = TwoWindingsFormulas.getConductorCrossSection(hvCurrentAtLowest, twoWindings.getHVCurrentDensity());
        int hvNoOfConductors = TwoWindingsFormulas.getNumberOfConductors(hvConductorCrossSection, twoWindings.getHVConductorMaterial());
        double hvCrossSecPerConductor = NumberFormattingUtils.twoDigitDecimal(hvConductorCrossSection/hvNoOfConductors);
        boolean hvIsConductorRound = TwoWindingsFormulas.isConductorRound(hvConductorCrossSection);
        int hvId = TwoWindingsFormulas.getID(twoWindings.getCoilDimensions().getLVOD(), lvHvGap);
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
        int hvOd = 0;
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
        double insulationCompression = 0.93;
        double insulationExpasion = 1.07;
        double hvUnpressedWindingLength = 0;

        //XOver Winding calculation variables
        int hvNoOfCoils = 0;
        int hvTurnsPerCoil = 0;
        int hvGapBwCoil = 0;
        int hvWdgLengthPerCoil = 0;
        String hvXOverTurnsLayers = "";

        //Double disc winding varibles
        int hvNoOfDoubleDiscs = 0;

        if (Objects.equals(twoWindings.getHvWindingType().toString(), "HELICAL")){
            if(hvNoOfConductors == 1){
                if(hvIsConductorRound){
                    hvBreadth = TwoWindingsFormulas.getRoundCondDia(hvConductorCrossSection, twoWindingRequest.getOuterWindings().getCondBreadth(), twoWindings.getHVConductorMaterial());
                    hvRevisedCondCrossSection = NumberFormattingUtils.twoDigitDecimal(Math.PI * Math.pow(hvBreadth,2) / 4);
                    hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(),
                            twoWindings.getHighVoltage(),
                            hvIsConductorRound,
                            twoWindings.getConnection(), hvIsEnamel, twoWindingRequest.getOuterWindings().getCondInsulation(), twoWindings.getDryType()
                    );
                    hvHeight = hvBreadth;
                    hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                    hvHeightInsulated =  hvBreadthInsulated;
                    hvTotalCondCrossSection = hvRevisedCondCrossSection;
                }
                else{
                    hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(),
                            twoWindings.getHighVoltage(),
                            hvIsConductorRound,
                            twoWindings.getConnection(), hvIsEnamel, twoWindingRequest.getOuterWindings().getCondInsulation(), twoWindings.getDryType());
                    if(twoWindingRequest.getOuterWindings().getCondHeight() != null){
                        hvHeight = twoWindingRequest.getOuterWindings().getCondHeight();
                    }else {
                        hvHeight = 1.6;
                    }
                    hvHeightInsulated = hvHeight + hvConductorInsulation;
                    if(twoWindingRequest.getOuterWindings().getCondBreadth() != null){
                        hvBreadth = twoWindingRequest.getOuterWindings().getCondBreadth();
                    }else {
                        hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                    }
                    hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                    hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                    if(twoWindingRequest.getOuterWindings().getCondBreadth() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
                        while (hvBreadth > 6 * hvHeight){
                            hvHeight = NumberFormattingUtils.oneDigitDecimal(hvHeight + 0.1);
                            hvHeightInsulated = hvHeight + hvConductorInsulation;
                            hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                            hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                            hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                            if(hvBreadth <= 6 * hvHeight){break;}
                        }
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
                if(twoWindingRequest.getOuterWindings().getCondHeight() != null){
                    hvHeight = twoWindingRequest.getOuterWindings().getCondHeight();
                }else{
                    hvHeight = 1.6;
                }
                hvHeightInsulated = hvHeight + hvConductorInsulation;
                if(twoWindingRequest.getOuterWindings().getCondBreadth() != null){
                    hvBreadth = twoWindingRequest.getOuterWindings().getCondBreadth();
                    if(twoWindingRequest.getOuterWindings().getCondHeight() == null){
                        hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
                        hvHeightInsulated = hvHeight + hvConductorInsulation;
                    }
                }else {
                    hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                }
                hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                if(twoWindingRequest.getOuterWindings().getCondBreadth() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
                    while (hvBreadth > 6 * hvHeight){
                        hvHeight = NumberFormattingUtils.oneDigitDecimal(hvHeight + 0.1);
                        hvHeightInsulated = hvHeight + hvConductorInsulation;
                        hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                        hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                        hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                        if(hvBreadth <= 6 * hvHeight){break;};
                    }
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
            hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers, hvInterlayerInsulation,hvNoOfDuct,hvDuctThickness, false);
            hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
            hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
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
            if(twoWindingRequest.getOuterWindings().getDucts() == null){
                while(hvGradient >= gradientLimit){
                    if(hvNoOfDuct > hvNumberOfLayers){break;}
                    hvNoOfDuct = hvNoOfDuct + 1;
                    hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,hvInterlayerInsulation,hvNoOfDuct,hvDuctThickness, false);
                    hvId = TwoWindingsFormulas.getID(twoWindings.getCoilDimensions().getLVOD(), lvHvGap);
                    hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
                    hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
                    hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
                    hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
                    hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
                    hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
                    hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
                    hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
                    hvStrayLoss = TwoWindingsFormulas.getStrayLoss(hvBreadth,hvBreadthInsulated,hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation, twoWindings.getHVConductorMaterial(), hvNumberOfLayers, 0, hvIsConductorRound);
                    hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
                    hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
                    hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) +2, hvWindingLength, hvTransposition, hvLmt, twoWindings.getDryType());
                    log.info("hvNumberOfLayers " + hvNumberOfLayers + " Ducts " + hvNoOfDuct + " Gradient = " + hvGradient);
                }
            }
        }
        else if (Objects.equals(twoWindings.getHvWindingType().toString(), "DISC")){
            hvIsConductorRound = false;
            hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getHighVoltage(), false, twoWindings.getConnection(), hvIsEnamel, twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getDryType());
            hvRadialParallelConductors = twoWindingRequest.getOuterWindings().getRadialParallelCond() != null ? twoWindingRequest.getOuterWindings().getRadialParallelCond() : hvNoOfConductors;
            hvAxialParallelConductors = TwoWindingsFormulas.getAxialParallelConductors(hvNoOfConductors, hvRadialParallelConductors, twoWindings.getOuterWindings().getAxialParallelCond());
            hvNoOfConductors = hvRadialParallelConductors * hvAxialParallelConductors;
            hvCrossSecPerConductor = NumberFormattingUtils.twoDigitDecimal(hvConductorCrossSection/hvNoOfConductors);
            if(twoWindingRequest.getOuterWindings().getCondBreadth() != null){
                hvBreadth = twoWindingRequest.getOuterWindings().getCondBreadth();
            }else{
                hvBreadth = 14;
            }
            if(twoWindingRequest.getOuterWindings().getCondHeight() != null){
                hvHeight = twoWindingRequest.getOuterWindings().getCondHeight();
                if(twoWindingRequest.getOuterWindings().getCondBreadth() == null){
                    hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                }
            }else {
                hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
            }
            if(twoWindingRequest.getOuterWindings().getCondBreadth() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
                while (hvBreadth > 6 * hvHeight){
                    hvBreadth = hvBreadth - 0.1;
                    hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
                    if(hvBreadth <= 6 * hvHeight){break;}
                }
            }
            hvBreadth = NumberFormattingUtils.oneDigitDecimal(hvBreadth);
            hvBreadthInsulated = hvBreadth + hvConductorInsulation;
            hvNoOfDiscs = NumberFormattingUtils.nextEvenInteger(hvWindingLength / ((hvBreadthInsulated * hvAxialParallelConductors) + hvDiscDuctsSize));
            int originalHvNoOfDiscs = hvNoOfDiscs;
            hvTurnsPerDisc = (double) hvTurnsAtHighest / hvNoOfDiscs;
            while(NumberFormattingUtils.twoDigitDecimalPart(hvTurnsPerDisc) < 0.7){
                hvNoOfDiscs = hvNoOfDiscs + 2;
                hvTurnsPerDisc = (double) hvTurnsAtHighest / hvNoOfDiscs;
                if(NumberFormattingUtils.twoDigitDecimalPart(hvTurnsPerDisc) >= 0.7){break;}
            }
            hvBreadthInsulated = ((hvWindingLength / hvNoOfDiscs) - (hvDiscDuctsSize * insulationCompression)) / hvAxialParallelConductors;
            hvBreadth = NumberFormattingUtils.oneDigitDecimal(hvBreadthInsulated - (hvConductorInsulation * insulationCompression));
            hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
            if(hvBreadth < 5 && hvHeight > 1.7){
                hvNoOfDiscs = originalHvNoOfDiscs;
                while(NumberFormattingUtils.twoDigitDecimalPart(hvTurnsPerDisc) < 0.7){
                    hvNoOfDiscs = hvNoOfDiscs - 2;
                    hvTurnsPerDisc = (double) hvTurnsAtHighest / hvNoOfDiscs;
                    if(NumberFormattingUtils.twoDigitDecimalPart(hvTurnsPerDisc) >= 0.7){break;}
                }
            }
            hvBreadthInsulated = ((hvWindingLength / hvNoOfDiscs) - (hvDiscDuctsSize * insulationCompression)) / hvAxialParallelConductors;
            hvBreadth = NumberFormattingUtils.oneDigitDecimal(hvBreadthInsulated - (hvConductorInsulation * insulationCompression));
            hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
            hvBreadthInsulated = NumberFormattingUtils.oneDigitDecimal(hvBreadth + hvConductorInsulation);
            hvWindingLength = TwoWindingsFormulas.getDiscWindingLength(hvBreadth,hvConductorInsulation,insulationCompression,hvNoOfDiscs,hvDiscDuctsSize);

            hvTurnsPerDisc = Math.ceil(hvTurnsPerDisc);
//            if(twoWindingRequest.getOuterWindings().getCondBreadth() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
//                hvBreadthInsulated = ((hvWindingLength / hvNoOfDiscs) - hvDiscDuctsSize) / hvAxialParallelConductors;
//                hvBreadth = NumberFormattingUtils.oneDigitDecimal(hvBreadthInsulated - hvConductorInsulation);
//                hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
//            }
            hvHeightInsulated = hvHeight + hvConductorInsulation;
            hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
            hvTotalCondCrossSection = hvRevisedCondCrossSection * hvNoOfConductors;

            int totalTurnsPossible = (int) (hvTurnsPerDisc * hvNoOfDiscs);
            int excessTurns = totalTurnsPossible - hvTurnsAtHighest;
            hvNoOfSpacers = TwoWindingsFormulas.getSpacersAndWidth(hvId)[0];
            hvWidthOfSpacer = TwoWindingsFormulas.getSpacersAndWidth(hvId)[1];
            hvSpacersToBeRemoved = hvNoOfSpacers * excessTurns;
            hvSpacersToBeRemoved = hvSpacersToBeRemoved - hvNoOfDiscs; //Default 1 spacer per disc is gone
            //When the hvSpacersToBeRemoved is less than no of discs then default 1 spacer per disc will be removed so, no extra spacers to be removed.
            int balanceSpacersInLastDisc = 0;
            if(hvSpacersToBeRemoved <= 0){
                hvSpacersToBeRemoved = 0;
            }else{
                hvHalfDisc = (int) Math.floor((double) hvSpacersToBeRemoved / ((double) (hvNoOfSpacers/2) -1));
                hvSpacersToBeRemoved = hvSpacersToBeRemoved - (hvHalfDisc * ((hvNoOfSpacers/2) -1));
            }
            if(hvSpacersToBeRemoved == 0){
                hvPartialDisc = 0;
                hvFullDisc = hvNoOfDiscs - hvHalfDisc;
            }else{
                hvPartialDisc = 1;
                hvFullDisc = hvNoOfDiscs - hvHalfDisc - hvPartialDisc;
                balanceSpacersInLastDisc = hvNoOfSpacers - hvSpacersToBeRemoved - 1;
            }
            String partialDiscStr =hvPartialDisc > 0 ? " + " + hvPartialDisc + "(" + balanceSpacersInLastDisc + "/" + hvNoOfSpacers + ")" : "";
            hvDiscArrangement = hvFullDisc + "F " + " + " + hvHalfDisc + "H " + partialDiscStr;

            hvTurnsPerLayer = hvNoOfDiscs;
            hvNumberOfLayers = hvTurnsPerDisc;
            hVRevisedCurrDenAtNormal = NumberFormattingUtils.threeDigitDecimal(hvCurrentPerPhase/hvTotalCondCrossSection);
            hVRevisedCurrDenAtLowest = NumberFormattingUtils.threeDigitDecimal(hvCurrentAtLowest/hvTotalCondCrossSection);
            hvWindingLength = TwoWindingsFormulas.getDiscWindingLength(hvBreadth,hvConductorInsulation,insulationCompression,hvNoOfDiscs,hvDiscDuctsSize);
            hvUnpressedWindingLength = TwoWindingsFormulas.getDiscWindingLength(hvBreadth,hvConductorInsulation,1,hvNoOfDiscs,hvDiscDuctsSize);
            hvEndClearance = Math.floor((twoWindings.getCore().getLimbHt() - hvWindingLength - twoWindings.getPermaWoodRing()));
            hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
            if(hvRadialThickness >= 65 && hvNoOfDuct == 0){
                hvNoOfDuct = 1;
                if(twoWindings.getKVA() <= 5000){
                    hvDuctThickness = TwoWindingsFormulas.getDuctSize(399.0, twoWindingRequest.getOuterWindings().getDuctSize(), twoWindings.getDryType());
                    hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
                }else{
                    hvDuctThickness = TwoWindingsFormulas.getDuctSize(499.0, twoWindingRequest.getOuterWindings().getDuctSize(), twoWindings.getDryType());
                    hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
                }
            }
            hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
            hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
            hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
            hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
            hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
            hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
            hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
            hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
            hvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(hvBreadth, hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation,twoWindings.getHVConductorMaterial(),hvNumberOfLayers,hvWindingLength);
            if(twoWindingRequest.getOuterWindings().getRadialParallelCond() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
                while(hvStrayLoss > 10){
                    hvRadialParallelConductors +=1;
                    hvNoOfConductors = hvRadialParallelConductors * hvAxialParallelConductors;
                    hvCrossSecPerConductor = NumberFormattingUtils.twoDigitDecimal(hvConductorCrossSection/hvNoOfConductors);
                    hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
                    hvHeightInsulated = hvHeight + hvConductorInsulation;
                    hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                    hvTotalCondCrossSection = hvRevisedCondCrossSection * hvNoOfConductors;
                    hVRevisedCurrDenAtNormal = NumberFormattingUtils.threeDigitDecimal(hvCurrentPerPhase/hvTotalCondCrossSection);
                    hVRevisedCurrDenAtLowest = NumberFormattingUtils.threeDigitDecimal(hvCurrentAtLowest/hvTotalCondCrossSection);
                    hvWindingLength = TwoWindingsFormulas.getDiscWindingLength(hvBreadth,hvConductorInsulation,insulationCompression,hvNoOfDiscs,hvDiscDuctsSize);
                    hvUnpressedWindingLength = TwoWindingsFormulas.getDiscWindingLength(hvBreadth,hvConductorInsulation,1,hvNoOfDiscs,hvDiscDuctsSize);
                    hvEndClearance = Math.floor((twoWindings.getCore().getLimbHt() - hvWindingLength - twoWindings.getPermaWoodRing()));
                    hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
                    if(hvRadialThickness >= 65 && hvNoOfDuct == 0){
                        hvNoOfDuct = 1;
                        if(twoWindings.getKVA() <= 5000){
                            hvDuctThickness = TwoWindingsFormulas.getDuctSize(399.0, twoWindingRequest.getOuterWindings().getDuctSize(), twoWindings.getDryType());
                            hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
                        }else{
                            hvDuctThickness = TwoWindingsFormulas.getDuctSize(499.0, twoWindingRequest.getOuterWindings().getDuctSize(), twoWindings.getDryType());
                            hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
                        }
                    }
                    hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
                    hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
                    hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
                    hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
                    hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
                    hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
                    hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
                    hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
                    hvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(hvBreadth, hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation,twoWindings.getHVConductorMaterial(),hvNumberOfLayers,hvWindingLength);
                }
            }
            hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
            hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
            hvDuctThickness = twoWindings.getKVA() <= 5000 ? 3 : 4;
            v0 = TwoWindingsFormulas.getV0(hVRevisedCurrDenAtLowest,hvCrossSecPerConductor,hvStrayLoss,hvHeightInsulated,windingTemp,ambientTemp);
            psi = TwoWindingsFormulas.getPsi(hvBreadthInsulated,hvRadialThickness,hvDuctThickness,hvNoOfDuct);
            rW = TwoWindingsFormulas.getRw(v0, psi,hvConductorInsulation);
            hvGradient = NumberFormattingUtils.oneDigitDecimal(v0 * psi * rW);
            //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
            if(twoWindingRequest.getOuterWindings().getDucts() == null){
                while(hvGradient >= gradientLimit){
                    if(hvNoOfDuct > 1){break;}
                    hvNoOfDuct = hvNoOfDuct + 1;
                    if(twoWindings.getKVA() <= 5000){
                        hvDuctThickness = TwoWindingsFormulas.getDuctSize(399.0, hvDuctThickness, twoWindings.getDryType());
                        hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
                    }else{
                        hvDuctThickness = TwoWindingsFormulas.getDuctSize(499.0, hvDuctThickness, twoWindings.getDryType());
                        hvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(hvHeight, hvRadialParallelConductors, hvConductorInsulation, insulationExpasion, (int) hvNumberOfLayers, hvNoOfDuct, hvDuctThickness);
                    }
                    hvId = TwoWindingsFormulas.getID(twoWindings.getCoilDimensions().getLVOD(), lvHvGap);
                    hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
                    hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
                    hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
                    hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
                    hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
                    hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
                    hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
                    hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
                    hvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(hvBreadth, hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation,twoWindings.getHVConductorMaterial(),hvNumberOfLayers,hvWindingLength);
                    hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
                    hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
                    hvDuctThickness = twoWindings.getKVA() <= 5000 ? 3 : 4;
                    v0 = TwoWindingsFormulas.getV0(hVRevisedCurrDenAtLowest,hvCrossSecPerConductor,hvStrayLoss,hvHeightInsulated,windingTemp,ambientTemp);
                    psi = TwoWindingsFormulas.getPsi(hvBreadthInsulated,hvRadialThickness,hvDuctThickness,hvNoOfDuct);
                    rW = TwoWindingsFormulas.getRw(v0, psi,hvConductorInsulation);
                    hvGradient = NumberFormattingUtils.oneDigitDecimal(v0 * psi * rW);
                    log.info("hvNumberOfLayers " + hvNumberOfLayers + " Ducts " + hvNoOfDuct + " Gradient = " + hvGradient);
                }
            }
        }
        else if (Objects.equals(twoWindings.getHvWindingType().toString(), "XOVER")){
            hvNoOfCoils = TwoWindingsFormulas.getNoOfCoils(twoWindings.getHighVoltage(), null);
            hvTurnsPerCoil = NumberFormattingUtils.halfUp((double) hvTurnsAtHighest / hvNoOfCoils);
            hvTurnsAtHighest = hvTurnsPerCoil * hvNoOfCoils;
            hvGapBwCoil = TwoWindingsFormulas.getGapBetweenCoils(twoWindings.getKVA(), twoWindings.getHighVoltage());
            hvWdgLengthPerCoil = TwoWindingsFormulas.getWindingLengthPerCoil(hvWindingLength, hvGapBwCoil, hvNoOfCoils);
            hvDuctThickness = TwoWindingsFormulas.getDuctSize((double) hvWdgLengthPerCoil, twoWindingRequest.getOuterWindings().getDuctSize(), twoWindings.getDryType());
            if(hvNoOfConductors == 1){
                if(hvIsConductorRound){
                    hvBreadth = TwoWindingsFormulas.getRoundCondDia(hvConductorCrossSection, twoWindingRequest.getOuterWindings().getCondBreadth(), twoWindings.getHVConductorMaterial());
                    hvRevisedCondCrossSection = NumberFormattingUtils.twoDigitDecimal(Math.PI * Math.pow(hvBreadth,2) / 4);
                    hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(),
                            twoWindings.getHighVoltage(),
                            hvIsConductorRound,
                            twoWindings.getConnection(), hvIsEnamel, twoWindingRequest.getOuterWindings().getCondInsulation(), twoWindings.getDryType()
                    );
                    hvHeight = hvBreadth;
                    hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                    hvHeightInsulated =  hvBreadthInsulated;
                    hvTotalCondCrossSection = hvRevisedCondCrossSection;
                }
                else{
                    hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(),
                            twoWindings.getHighVoltage(),
                            hvIsConductorRound,
                            twoWindings.getConnection(), hvIsEnamel, twoWindingRequest.getOuterWindings().getCondInsulation(), twoWindings.getDryType());
                    if(twoWindingRequest.getOuterWindings().getCondHeight() != null){
                        hvHeight = twoWindingRequest.getOuterWindings().getCondHeight();
                    }else {
                        hvHeight = 1.6;
                    }
                    hvHeightInsulated = hvHeight + hvConductorInsulation;
                    if(twoWindingRequest.getOuterWindings().getCondBreadth() != null){
                        hvBreadth = twoWindingRequest.getOuterWindings().getCondBreadth();
                        if(twoWindingRequest.getOuterWindings().getCondHeight() == null){
                            hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
                            hvHeightInsulated = hvHeight + hvConductorInsulation;
                        }
                    }else {
                        hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                    }
                    hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                    hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                    if(twoWindingRequest.getOuterWindings().getCondBreadth() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
                        while (hvBreadth > 6 * hvHeight){
                            hvHeight = NumberFormattingUtils.oneDigitDecimal(hvHeight + 0.1);
                            hvHeightInsulated = hvHeight + hvConductorInsulation;
                            hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                            hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                            hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                            if(hvBreadth <= 6 * hvHeight){break;}
                        }
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
                if(twoWindingRequest.getOuterWindings().getCondHeight() != null){
                    hvHeight = twoWindingRequest.getOuterWindings().getCondHeight();
                }else {
                    hvHeight = 1.6;
                }
                hvHeightInsulated = hvHeight + hvConductorInsulation;
                if(twoWindingRequest.getOuterWindings().getCondBreadth() != null){
                    hvBreadth = twoWindingRequest.getOuterWindings().getCondBreadth();
                }else {
                    hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                }
                hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                if(twoWindingRequest.getOuterWindings().getCondBreadth() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
                    while (hvBreadth > 6 * hvHeight){
                        hvHeight = NumberFormattingUtils.oneDigitDecimal(hvHeight + 0.1);
                        hvHeightInsulated = hvHeight + hvConductorInsulation;
                        hvBreadth = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvHeight);
                        hvBreadthInsulated = hvBreadth + hvConductorInsulation;
                        hvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
                        if(hvBreadth <= 6 * hvHeight){break;};
                    }
                }
                hvTotalCondCrossSection = hvRevisedCondCrossSection * hvNoOfConductors;
            }
            hVRevisedCurrDenAtNormal = NumberFormattingUtils.threeDigitDecimal(hvCurrentPerPhase / hvTotalCondCrossSection);
            hVRevisedCurrDenAtLowest = NumberFormattingUtils.threeDigitDecimal(hvCurrentAtLowest / hvTotalCondCrossSection);
            hvTurnsPerLayer = Math.floor(hvWdgLengthPerCoil / (hvBreadthInsulated * hvAxialParallelConductors)) - 1;
            hvNumberOfLayersRough = hvTurnsPerCoil / hvTurnsPerLayer;
            while (NumberFormattingUtils.twoDigitDecimalPart(hvNumberOfLayersRough) < 0.5){
                hvTurnsPerLayer = hvTurnsPerLayer - 1;
                hvNumberOfLayersRough = hvTurnsPerCoil / hvTurnsPerLayer;
            }
            hvNumberOfLayers = Math.ceil(hvNumberOfLayersRough);
            hvWdgLengthPerCoil = NumberFormattingUtils.nextInteger(hvBreadthInsulated * hvAxialParallelConductors * (hvTurnsPerLayer + 1));
            hvGapBwCoil = (int) Math.floor((hvWindingLength - (hvWdgLengthPerCoil * hvNoOfCoils)) / hvNoOfCoils);
            hvWindingLength = (hvWdgLengthPerCoil * hvNoOfCoils) + (hvGapBwCoil * (hvNoOfCoils - 1));
            hvEndClearance = twoWindings.getCore().getLimbHt() - hvWindingLength;

            int hvTurnsAtLastLayer = (int) (hvTurnsPerCoil - ((hvNumberOfLayers - 1) * hvTurnsPerLayer));
            hvXOverTurnsLayers = (int) hvNumberOfLayers - 1 + " X " + (int) hvTurnsPerLayer + " + 1 X " + hvTurnsAtLastLayer;

            hvInterlayerInsulation = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn(), hvTurnsPerLayer, hvConductorInsulation, hvIsEnamel, twoWindingRequest.getOuterWindings().getInterLayerInsulation(), twoWindings.getDryType());
            hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvNoOfConductors, hvNumberOfLayers, hvInterlayerInsulation, hvNoOfDuct, hvDuctThickness, false);
            //TODO: Here, we may have to take 10% tolerance for Radial thickness. Not sure if this is required for a circular core Wdg. Ask TVR.
            hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
            hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
            hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
            hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
            hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
            hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
            hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
            hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
            hvStrayLoss = TwoWindingsFormulas.getStrayLossForXOver(hvBreadth, hvHeight, hvTurnsPerLayer, hvNoOfCoils, hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation, twoWindings.getHVConductorMaterial(), hvNumberOfLayers, hvWindingLength, hvIsConductorRound);
            hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
            hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
            hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) + 4, hvWindingLength, hvTransposition, hvLmt, twoWindings.getDryType());
            //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
            if(twoWindingRequest.getOuterWindings().getDucts() == null){
                while(hvGradient >= gradientLimit){
                    if(hvNoOfDuct > hvNumberOfLayers){break;}
                    hvNoOfDuct = hvNoOfDuct + 1;
                    hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers, hvInterlayerInsulation, hvNoOfDuct, hvDuctThickness, false);
                    hvId = TwoWindingsFormulas.getID(twoWindings.getCoilDimensions().getLVOD(), lvHvGap);
                    hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
                    hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
                    hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
                    hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
                    hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
                    hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
                    hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
                    hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
                    hvStrayLoss = TwoWindingsFormulas.getStrayLossForXOver(hvBreadth, hvHeight, hvTurnsPerLayer, hvNoOfCoils, hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation, twoWindings.getHVConductorMaterial(), hvNumberOfLayers, hvWindingLength, hvIsConductorRound);
                    hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
                    hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
                    hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) + 4, hvWindingLength, hvTransposition, hvLmt, twoWindings.getDryType());
                    log.info("hvNumberOfLayers " + hvNumberOfLayers + " Ducts " + hvNoOfDuct + " Gradient = " + hvGradient);
                }
            }
        }
        else if (Objects.equals(twoWindings.getHvWindingType().toString(), "DOUBLE_DISC")){
            hvNoOfConductors = NumberFormattingUtils.nextInteger(hvConductorCrossSection / 51.14);
            hvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getHighVoltage(), false, twoWindings.getConnection(), hvIsEnamel, twoWindings.getOuterWindings().getCondInsulation(), twoWindings.getDryType());
            hvCrossSecPerConductor = NumberFormattingUtils.twoDigitDecimal(hvConductorCrossSection/hvNoOfConductors);
            if(twoWindingRequest.getOuterWindings().getCondBreadth() != null){
                hvBreadth = twoWindingRequest.getOuterWindings().getCondBreadth();
            }else{
                hvBreadth = 14;
            }
            if(twoWindingRequest.getOuterWindings().getCondHeight() != null){
                hvHeight = twoWindingRequest.getOuterWindings().getCondHeight();
            }else {
                hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
            }
            if(twoWindingRequest.getOuterWindings().getCondBreadth() == null && twoWindingRequest.getOuterWindings().getCondHeight() == null){
                while (hvBreadth > 6 * hvHeight){
                    hvBreadth = hvBreadth - 0.1;
                    hvHeight = TwoWindingsFormulas.getHeight(hvCrossSecPerConductor, hvBreadth);
                    if(hvBreadth <= 6 * hvHeight){break;}
                }
            }
            hvBreadth = NumberFormattingUtils.oneDigitDecimal(hvBreadth);
            hvBreadthInsulated = hvBreadth + hvConductorInsulation;
            hvConductorCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(hvBreadth, hvHeight);
            hvNoOfDiscs = NumberFormattingUtils.nextEvenInteger(hvWindingLength / ((hvBreadthInsulated * hvAxialParallelConductors) + hvDiscDuctsSize));
            hvNoOfDoubleDiscs = hvNoOfDiscs / 2;
            hvNoOfConductors = NumberFormattingUtils.nextInteger((double) hvNoOfConductors /hvNoOfDoubleDiscs) * hvNoOfDoubleDiscs;
            hvRadialParallelConductors = hvNoOfConductors/hvNoOfDoubleDiscs;
            hvAxialParallelConductors = 1;
            hvTotalCondCrossSection = hvNoOfConductors * hvConductorCrossSection;
            hVRevisedCurrDenAtNormal = hvCurrentPerPhase/ hvTotalCondCrossSection;
            hvWindingLength = NumberFormattingUtils.nextInteger(((hvBreadthInsulated * hvAxialParallelConductors) + hvDiscDuctsSize) * hvNoOfDiscs);
            hvEndClearance = Math.floor((twoWindings.getCore().getLimbHt() - hvWindingLength + twoWindings.getPermaWoodRing()));
            hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,0, hvNoOfDuct, hvDuctThickness,false);
            if(hvRadialThickness >= 65 && hvNoOfDuct == 0){
                hvNoOfDuct = 1;
                if(twoWindings.getKVA() <= 5000){
                    hvDuctThickness = TwoWindingsFormulas.getDuctSize(399.0, twoWindingRequest.getOuterWindings().getDuctSize(), twoWindings.getDryType());
                    hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,0, hvNoOfDuct, hvDuctThickness,false);
                }else{
                    hvDuctThickness = TwoWindingsFormulas.getDuctSize(499.0, twoWindingRequest.getOuterWindings().getDuctSize(), twoWindings.getDryType());
                    hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,0, hvNoOfDuct, hvDuctThickness, false);
                }
            }
            hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
            hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
            hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
            hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
            hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
            hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
            hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
            hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
            hvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(hvBreadth, hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation,twoWindings.getHVConductorMaterial(),hvNumberOfLayers,hvWindingLength);
            hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss));
            hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss));
            hvDuctThickness = twoWindings.getKVA() <= 5000 ? 3 : 4;
            hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) + 2, hvWindingLength, hvTransposition, hvLmt, twoWindings.getDryType());
            //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
            if(twoWindingRequest.getOuterWindings().getDucts() == null){
                while(hvGradient >= gradientLimit){
                    if(hvNoOfDuct > hvNumberOfLayers){break;}
                    hvNoOfDuct = hvNoOfDuct + 1;
                    if(twoWindings.getKVA() <= 5000){
                        hvDuctThickness = TwoWindingsFormulas.getDuctSize(399.0, hvDuctThickness, twoWindings.getDryType());
                        hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,0, hvNoOfDuct, hvDuctThickness, false);
                    }else{
                        hvDuctThickness = TwoWindingsFormulas.getDuctSize(499.0, hvDuctThickness, twoWindings.getDryType());
                        hvRadialThickness = TwoWindingsFormulas.getRadialThickness(hvHeightInsulated, hvRadialParallelConductors, hvNumberOfLayers,0, hvNoOfDuct, hvDuctThickness, false);
                    }
                    hvId = TwoWindingsFormulas.getID(twoWindings.getCoilDimensions().getLVOD(), lvHvGap);
                    hvOd = TwoWindingsFormulas.getOD(hvId, hvRadialThickness);
                    hvLmt = TwoWindingsFormulas.getLMT(hvId, hvOd);
                    hvWireLength = TwoWindingsFormulas.getWireLength(hvLmt, hvTurnsPerPhase, 3, hvNoOfConductors);
                    hvR75 = TwoWindingsFormulas.getR75(twoWindings.getHVConductorMaterial(), hvLmt, hvTurnsPerPhase,hvTotalCondCrossSection);
                    hvR26 = TwoWindingsFormulas.getR26(hvR75, twoWindings.getHVConductorMaterial());
                    hvBareWeight = TwoWindingsFormulas.getBareWeight(hvLmt,hvTurnsAtHighest, hvTotalCondCrossSection, twoWindings.getHVConductorMaterial());
                    hvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(hvBreadthInsulated,hvHeightInsulated,hvBreadth,hvHeight,twoWindings.getHVConductorMaterial(), hvBareWeight, hvIsEnamel);
                    hvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(hvInsulatedWeight, hvNoOfConductors);
                    hvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(hvBreadth, hvHeight,hvTurnsPerLayer,hvRadialParallelConductors, hvAxialParallelConductors, hvConductorInsulation,twoWindings.getHVConductorMaterial(),hvNumberOfLayers,hvWindingLength);
                    hvLoadLossAtNormal = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtNormal, hvStrayLoss) * ((double) hvTurnsPerPhase /hvTurnsAtHighest));
                    hvLoadLossAtLowest = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getLoadLoss(twoWindings.getHVConductorMaterial(), hvBareWeight, hVRevisedCurrDenAtLowest, hvStrayLoss) * ((double) hvTurnsAtLowest /hvTurnsAtHighest));
                    hvDuctThickness = twoWindings.getKVA() <= 5000 ? 3 : 4;
                    hvGradient = TwoWindingsFormulas.getHvGradient(hvLoadLossAtLowest, (hvNoOfDuct * 2) + 2, hvWindingLength, hvTransposition, hvLmt,twoWindings.getDryType());
                    log.info("hvNumberOfLayers " + hvNumberOfLayers + " Ducts " + hvNoOfDuct + " Gradient = " + hvGradient);
                }
            }
        }

        int noOfSteps = twoWindings.getTapStepsPositive() + twoWindings.getTapStepsNegative() + 1;
        ArrayList<Integer> turnsPerTap = TwoWindingsFormulas.getTurnsAtTap(twoWindings.getHighVoltage(), noOfSteps, twoWindings.getTapStepsNegative(), twoWindings.getTapStepsPercent(), twoWindings.getVoltsPerTurn());
        turnsPerTap.remove(noOfSteps-1);
        turnsPerTap.add(hvTurnsAtHighest);
        ArrayList<Integer> tapVoltages = TwoWindingsFormulas.getTapVoltages(twoWindings.getHighVoltage(), twoWindings.getTapStepsNegative(), twoWindings.getTapStepsPositive(),twoWindings.getTapStepsPercent());
        ArrayList<Double> tapCurrent = TwoWindingsFormulas.getTapCurrents(noOfSteps, tapVoltages, twoWindings.getKVA());

        //All the code below this line is for the rest of the core calculations.
        double hvHvGap = TwoWindingsFormulas.getHvHVGap(twoWindings.getKVA(), twoWindings.getLowVoltage(),twoWindings.getHighVoltage(), twoWindings.getConnection(), twoWindings.getCoilDimensions().getHVHVGap(), twoWindings.getDryType());

        int centerDistance = TwoWindingsFormulas.getCenterDistance(hvOd, hvHvGap);

        hvHvGap = centerDistance - hvOd;

        double coreLength = TwoWindingsFormulas.getCoreLength(twoWindings.getCore().getCoreDia(), twoWindings.getCore().getLimbHt() ,centerDistance);

        double coreWeight = TwoWindingsFormulas.getCoreWeight(coreLength, twoWindings.getCore().getArea());

        //Here the specific loss is taken as 1.3 considering that the flux Density is 1.7333T. and that the material is Nip M4.
        int coreLoss = TwoWindingsFormulas.getCoreLoss(coreWeight, twoWindings.getBuildFactor(), specificLoss);

        int tankLoss = TwoWindingsFormulas.getTankLoss(twoWindings.getKVA(), twoWindings.getInnerWindings().getPhaseCurrent(), twoWindings.getLowVoltage(), twoWindingRequest.getTank().getTankLoss(), twoWindings.getDryType());
        int totalLoadLoss = NumberFormattingUtils.nextInteger(twoWindings.getInnerWindings().getLoadLoss() + hvLoadLossAtNormal + tankLoss);
        double kW55 = TwoWindingsFormulas.getKw55(coreLoss, twoWindings.getInnerWindings().getLoadLoss(), hvLoadLossAtLowest, tankLoss, twoWindings.getInnerWindings().getTempGradDegC(), twoWindings.getOuterWindings().getTempGradDegC());

        //Active Part Size Calc
        int activePartLength = 2 * centerDistance + hvOd;
        int activePartHeight = (int)(2 * twoWindings.getCore().getCoreDia() + twoWindings.getCore().getLimbHt());
        String activePartSize = activePartLength + " L X " + hvOd  + " W X " + activePartHeight + " H mm";

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
        formula.put("hvDiscDuctsSize", hvDiscDuctsSize);
        formula.put("hvDiscArrangement", hvDiscArrangement);
        formula.put("hvNoOfDiscs", hvNoOfDiscs);
        formula.put("hvNoOfDoubleDiscs", hvNoOfDoubleDiscs);
        formula.put("hvConductorDia",hvBreadth);
        formula.put("hvConductorCrossSection", hvConductorCrossSection);
        formula.put("hvIsConductorRound", hvIsConductorRound);
        formula.put("hvConductorInsulation", hvConductorInsulation);
        formula.put("hvIsEnamel", hvIsEnamel);
        formula.put("hvRevisedCondCrossSection", hvRevisedCondCrossSection);
        formula.put("hvTotalCondCrossSection", hvTotalCondCrossSection);
        formula.put("hvInterLayerInsulation", hvInterlayerInsulation);
        formula.put("hvRadialThickness", hvRadialThickness);
        formula.put("hvId", hvId);
        formula.put("hvOd", hvOd);
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
        formula.put("hvUnpressedWindingLength",hvUnpressedWindingLength);
        formula.put("hvTransposition", hvTransposition);
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
        formula.put("turnsPerTap", turnsPerTap);
        formula.put("tapVoltages", tapVoltages);
        formula.put("tapCurrent", tapCurrent);

        //All the formula.puts for Core and related information is done below.
        formula.put("hvHvGap", hvHvGap);
        formula.put("centerDistance", centerDistance);
        formula.put("coreLength", coreLength);
        formula.put("coreWeight", coreWeight);
        formula.put("coreLoss", coreLoss);
        formula.put("tankLoss", tankLoss);
        formula.put("totalLoadLoss", totalLoadLoss);
        formula.put("kW55", kW55);
        formula.put("specificLoss", specificLoss);
        formula.put("specificLossBeyondLimits", specificLossBeyondLimits);

        return formula;

    }
}
