package com.tf.core_service.service.circ2Wdg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.model.twoWindings.*;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.utils.NumberFormattingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class LvWindingsService {
    public Map<String, Object> calculateLvWindings(TwoWindings twoWindings, TwoWindingRequest twoWindingRequest) throws JsonProcessingException {

        double coreGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getCoilDimensions().getCoreGap(), twoWindings.getDryType());
        int lvLayerIncrement;
        int lvConductorFlag = twoWindings.getLvConductorFlag();
        Integer lvNoOfDuct = twoWindingRequest.getInnerWindings().getDucts() != null ? twoWindingRequest.getInnerWindings().getDucts() :0;
        int lvEndClearance1;
        Map<String, Object> formula = new HashMap<>();
        double lvBreadth = 0;
        double lvHeight = 0;
        double lvBreadthInsulated = 0;
        double lvHeightInsulated = 0;
        double lVRevisedCurrentDensity = 0;
        double lvTotalCondCrossSection = 0;
        int lvRadialParallelConductors = 1;
        int lvAxialParallelConductors = 1;
        int lvTransposition = 0;
        double lvLmt = 0;
        int lvOd = 0;
        int lvId = 0;
        int lvRadialThickness = 0;
        double lvInterLayerInsulation = 0;
        double lvWireLength = 0;
        double lvBareWeight = 0;
        double lvInsulatedWeight = 0;
        double lvProcurementWeight = 0;
        double lvR75 = 0;
        double lvR26 = 0;
        double lvStrayLoss = 0;
        double lvLoadLoss = 0;
        double lvGradient = 0;
        double lvNumberOfLayers = 0;
        double lvTurnsPerLayer = 0;
        double lvConductorCrossSection = 0;
        double lvConductorInsulation = 0;
        boolean lvIsEnamel = twoWindings.getLowVoltage() <= 33000 && twoWindings.getInnerWindings().getIsEnamel();
        double lvCrossSecPerConductor = 0;
        double lvRevisedCondCrossSection = 0;
        int lvNumberOfConductors = 0;
        Integer lvDuctThickness = twoWindings.getInnerWindings().getDuctSize();
        int coreDiaPossibleMin = 0;
        int coreDiaPossibleMax = 0;
        String lvDiscArrangement = "";

        double lvVoltsPerPhase = TwoWindingsFormulas.getLvVoltsPerPhase(twoWindings.getLowVoltage(), twoWindings.getConnection());
        //For now, I have let the lvTurnsPerPhase take any input that the user gives. Here, Even the core dia changes on changing teh lvTurnsPerPhase
        double lvTurnsPerPhase = TwoWindingsFormulas.getTurnsPerPhase(lvVoltsPerPhase, twoWindings.getVoltsPerTurn(), twoWindings.getInnerWindings().getTurnsPerPhase(), twoWindings.getConnection(), true);
        double revisedVoltsPerTurn = TwoWindingsFormulas.getRevisedVoltsPerTurn(lvVoltsPerPhase,lvTurnsPerPhase, twoWindings.getConnection());
        double netArea = TwoWindingsFormulas.getNetArea(revisedVoltsPerTurn, twoWindings.getFrequency(), twoWindings.getFluxDensity());

        double revisedFluxDensity = 0;

        double grossArea = TwoWindingsFormulas.getGrossCoreArea(netArea, TwoWindingsFormulas.getDiameter(netArea), null);

        Integer coreDiameter = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getCoreDiameter(grossArea, twoWindings.getCore().getCoreDia()));

        if(twoWindings.getKVA() <= 1950 && Objects.equals(twoWindings.getETransCostType(),ETransCostType.ECONOMIC)){
            coreDiameter = TwoWindingsFormulas.getHRGCoreDia(twoWindings.getKVA(), twoWindings.getDryType());
            grossArea = TwoWindingsFormulas.getGrossCoreArea(null, null, coreDiameter);
            netArea = TwoWindingsFormulas.getRevisedNetArea(grossArea, coreDiameter);
            revisedVoltsPerTurn = TwoWindingsFormulas.getReversedRevisedVoltsPerTurn( netArea, twoWindings.getFrequency(), twoWindings.getFluxDensity());
            lvTurnsPerPhase = TwoWindingsFormulas.getTurnsPerPhase(lvVoltsPerPhase, revisedVoltsPerTurn, twoWindings.getInnerWindings().getTurnsPerPhase(), twoWindings.getConnection(), true);
            revisedVoltsPerTurn = TwoWindingsFormulas.getRevisedVoltsPerTurn(lvVoltsPerPhase,lvTurnsPerPhase, twoWindings.getConnection());
        }

        if (twoWindings.getCore().getCoreDia() != null) {
            coreDiameter = twoWindings.getCore().getCoreDia();
            grossArea = TwoWindingsFormulas.getGrossCoreArea(null, null, coreDiameter);
            netArea = TwoWindingsFormulas.getRevisedNetArea(grossArea, coreDiameter);
            revisedVoltsPerTurn = TwoWindingsFormulas.getReversedRevisedVoltsPerTurn( netArea, twoWindings.getFrequency(), twoWindings.getFluxDensity());
            lvTurnsPerPhase = TwoWindingsFormulas.getTurnsPerPhase(lvVoltsPerPhase, revisedVoltsPerTurn, twoWindings.getInnerWindings().getTurnsPerPhase(), twoWindings.getConnection(), true);
            revisedVoltsPerTurn = TwoWindingsFormulas.getRevisedVoltsPerTurn(lvVoltsPerPhase,lvTurnsPerPhase, twoWindings.getConnection());
        }

        revisedFluxDensity = NumberFormattingUtils.fourDigitDecimal(revisedVoltsPerTurn / (4.44 * twoWindings.getFrequency() * netArea * Math.pow(10, -6)));

        double leastKVal = TwoWindingsFormulas.getPossibleKValue(twoWindings.getKVA(), twoWindings.getLVConductorMaterial(), twoWindings.getETransCostType())[0];
        double maxKValue = TwoWindingsFormulas.getPossibleKValue(twoWindings.getKVA(), twoWindings.getLVConductorMaterial(), twoWindings.getETransCostType())[1];
        double voltsPerTurn1 = leastKVal * Math.sqrt(twoWindings.getKVA());
        double netArea1 = TwoWindingsFormulas.getNetArea(voltsPerTurn1, twoWindings.getFrequency(), twoWindings.getFluxDensity());
        double grossArea1 = TwoWindingsFormulas.getGrossCoreArea(netArea1, TwoWindingsFormulas.getDiameter(netArea1), null );
        coreDiaPossibleMin = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getCoreDiameter(grossArea1, null));
        voltsPerTurn1 = maxKValue * Math.sqrt(twoWindings.getKVA());
        netArea1 = TwoWindingsFormulas.getNetArea(voltsPerTurn1, twoWindings.getFrequency(), twoWindings.getFluxDensity());
        grossArea1 = TwoWindingsFormulas.getGrossCoreArea(netArea1, TwoWindingsFormulas.getDiameter(netArea1), null);
        coreDiaPossibleMax = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getCoreDiameter(grossArea1, null));
        String possibleCoreDiaRange = "Note: The possible core dia range for " + twoWindings.getKVA() + " kVA is " + coreDiaPossibleMin + "mm to " + coreDiaPossibleMax + "mm";

        double lvCurrentPerPhase = TwoWindingsFormulas.getCurrentPerPhase(twoWindings.getKVA(), lvVoltsPerPhase);

        double windowHeight = TwoWindingsFormulas.getWindowHeight(twoWindings.getKValue(), coreDiameter, twoWindings.getLVConductorMaterial(), twoWindings.getCore().getLimbHt(), twoWindings.getDryType());

        double possibleWindowHeightMin = TwoWindingsFormulas.getWindowHeight(maxKValue, coreDiameter, twoWindings.getLVConductorMaterial(), null, twoWindings.getDryType());
        double possibleWindowHeightMax = TwoWindingsFormulas.getWindowHeight(leastKVal, coreDiameter, twoWindings.getLVConductorMaterial(), null, twoWindings.getDryType());
        String possibleWindowHeightRange = "Note: The possible Window Height range for core dia = " + coreDiameter + "mm is " + possibleWindowHeightMin + "mm to " + possibleWindowHeightMax + "mm";

        double lvEndClearance = TwoWindingsFormulas.getLvEndClearance(twoWindings.getKVA(), twoWindings.getConnection(), twoWindings.getInnerWindings().getEndClearances(), twoWindings.getDryType(), twoWindings.getHighVoltage());

        int permaWoodRing = TwoWindingsFormulas.getPermaWoodRing(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getDryType());

        double lvWindingLength = TwoWindingsFormulas.getWindingLength(windowHeight, lvEndClearance, permaWoodRing);

        lvDuctThickness = TwoWindingsFormulas.getDuctSize(lvWindingLength, lvDuctThickness, twoWindings.getDryType());

        //Disc Winding calculation variables
        double lvDiscDuctsSize = TwoWindingsFormulas.getDiscDuctSize(twoWindings.getLowVoltage(), Boolean.TRUE, twoWindings.getConnection());
        int lvNoOfDiscs = 0;
        double lvTurnsPerDisc = 0;
        int lvNoOfSpacers = 0;
        int lvWidthOfSpacer = 0;
        int lvSpacersToBeRemoved = 0;
        int lvHalfDisc = 0;
        int lvFullDisc = 0;
        int lvPartialDisc = 0;
        double v0 = 0;
        double psi =0;
        double rW = 0;
        double windingTemp = twoWindings.getWindingTemp();
        double ambientTemp = twoWindings.getAmbientTemp();
        boolean lvIsConductorRound = false;
        double insulationCompression = 0.93;
        double lvUnpressedWindingLength = 0;
        double insulationExpansion = 1.07;
        double gradientLimit = TwoWindingsFormulas.getGradientLimit(twoWindings.getDryType(), twoWindings.getDryTempClass());
        //Foil Winding Variables
        double lvEndStrip = 0;

        if(Objects.equals(twoWindings.getLvWindingType().toString(), "HELICAL")){

            if(twoWindings.getInnerWindings().getNoOfLayers() != null && twoWindings.getInnerWindings().getNoOfLayers() != 0){
                lvNumberOfLayers = twoWindings.getInnerWindings().getNoOfLayers();
            }else {
                lvNumberOfLayers = twoWindings.getLvNumberOfLayers();
            }

            lvTurnsPerLayer = TwoWindingsFormulas.getLvTurnsPerLayer(lvTurnsPerPhase, lvNumberOfLayers);

            lvConductorCrossSection = TwoWindingsFormulas.getConductorCrossSection(lvCurrentPerPhase, twoWindings.getLVCurrentDensity());

            lvNumberOfConductors = TwoWindingsFormulas.getNumberOfConductors(lvConductorCrossSection, twoWindings.getLVConductorMaterial());

            lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);

            lvIsConductorRound = TwoWindingsFormulas.isConductorRound(lvConductorCrossSection);
            if(lvIsConductorRound){
                lvBreadth = TwoWindingsFormulas.getRoundCondDia(lvConductorCrossSection, twoWindingRequest.getInnerWindings().getCondBreadth(), twoWindings.getLVConductorMaterial());
                lvRevisedCondCrossSection = NumberFormattingUtils.twoDigitDecimal(Math.PI * Math.pow(lvBreadth,2) / 4);
                lvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getLowVoltage(), lvIsConductorRound,
                        twoWindings.getConnection(), lvIsEnamel, twoWindingRequest.getInnerWindings().getCondInsulation(), twoWindings.getDryType()
                );
                lvHeight = lvBreadth;
                lvBreadthInsulated = TwoWindingsFormulas.getHeightInsulated(lvBreadth, lvConductorInsulation);
                lvHeightInsulated =  TwoWindingsFormulas.getHeightInsulated(lvBreadth, lvConductorInsulation);
                lvTurnsPerLayer = Math.floor(lvWindingLength / lvBreadthInsulated);

                double lvNumberOfLayersRough = lvTurnsPerPhase / lvTurnsPerLayer;

                //We will be getting number of layers in terms of decimal. We will reduce the T/L by 1 and check if the last layer is more than 50%.
                while (lvNumberOfLayersRough % 1 <= 0.5){
                    lvTurnsPerLayer -= 1;
                    lvNumberOfLayersRough = lvTurnsPerPhase / lvTurnsPerLayer;
                    break;
                }

                lvNumberOfLayers = NumberFormattingUtils.twoDigitDecimal(lvNumberOfLayersRough);
                lvWindingLength = NumberFormattingUtils.nextInteger(lvBreadthInsulated * (lvTurnsPerLayer + 1));
                lvEndClearance = (windowHeight - lvWindingLength);
                lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase/(lvRevisedCondCrossSection * lvNumberOfConductors));

                //All the code above this line from window height should be iterated for the condFlag++ so that we get height less than 4mm.
                lvTotalCondCrossSection = TwoWindingsFormulas.getActualConductorXSec(lvRevisedCondCrossSection, lvNumberOfConductors);

                lvInterLayerInsulation = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn()
                        , lvTurnsPerLayer, lvConductorInsulation, lvIsEnamel, twoWindingRequest.getInnerWindings().getInterLayerInsulation(), twoWindings.getDryType());

                if(lvNoOfDuct > (int) lvNumberOfLayers - 1){
                    lvNoOfDuct = (int) lvNumberOfLayers - 1;
                }
                lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, 1,
                        lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, true);

                lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);

                lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);

                lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);

                lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);

                lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);

                lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());

                lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                        , twoWindings.getLVConductorMaterial());

                lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight
                        ,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);

                lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);

                lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth,lvBreadthInsulated,lvHeight,lvTurnsPerLayer
                        ,1,1, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                        , lvNumberOfLayers, 0, lvIsConductorRound);

                lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight
                        ,twoWindings.getLVCurrentDensity(),lvStrayLoss);

                lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss,((lvNoOfDuct * 2) + 2), lvWindingLength, lvTransposition, lvLmt, twoWindings.getDryType(), true);

                //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
                if(twoWindingRequest.getInnerWindings().getDucts() == null){
                    while(lvGradient >= 14.5 && lvNoOfDuct < lvNumberOfLayers - 1){
                        lvNoOfDuct = lvNoOfDuct + 1;
                        lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, true);
                        lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);
                        lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);
                        lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);
                        lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                        lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);
                        lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                        lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                                , twoWindings.getLVConductorMaterial());
                        lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth
                                ,lvHeight,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                        lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                        lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth,lvBreadthInsulated,lvHeight,lvTurnsPerLayer
                                ,1,1,lvConductorInsulation, twoWindings.getLVConductorMaterial()
                                , lvNumberOfLayers, 0, lvIsConductorRound);
                        lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight
                                ,twoWindings.getLVCurrentDensity(),lvStrayLoss);
                        lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss,(lvNoOfDuct * 2) + 2, lvWindingLength, lvTransposition, lvLmt, twoWindings.getDryType(), true);
                    }
                }

            }
            else {
                lvRadialParallelConductors = TwoWindingsFormulas.getRadialParallelConductors(lvNumberOfConductors, lvConductorFlag, twoWindingRequest.getInnerWindings().getRadialParallelCond());
                lvAxialParallelConductors = TwoWindingsFormulas.getAxialParallelConductors(lvNumberOfConductors, lvRadialParallelConductors, twoWindingRequest.getInnerWindings().getAxialParallelCond());
                lvNumberOfConductors = lvRadialParallelConductors * lvAxialParallelConductors;
                lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
                lvTransposition = lvRadialParallelConductors > 1 ? 30 : 0;
                lvBreadthInsulated = TwoWindingsFormulas.getBi(lvWindingLength, lvTurnsPerLayer, lvAxialParallelConductors, lvTransposition, lvRadialParallelConductors);
                lvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getLowVoltage(), lvIsConductorRound, twoWindings.getConnection(), lvIsEnamel, twoWindingRequest.getInnerWindings().getCondInsulation(), twoWindings.getDryType());

                //This is for taking the User Input for Cond Breadth.
                if(twoWindingRequest.getInnerWindings().getCondBreadth() != null){
                    lvBreadth = twoWindingRequest.getInnerWindings().getCondBreadth();
                    lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                    lvWindingLength = NumberFormattingUtils.nextInteger(lvBreadthInsulated * lvAxialParallelConductors * (lvTurnsPerLayer + 1));
                    windowHeight = lvWindingLength + lvTransposition + lvEndClearance + permaWoodRing;
                }else{
                    lvBreadth = TwoWindingsFormulas.getBreadth(lvBreadthInsulated, lvConductorInsulation, lvRadialParallelConductors);
                    lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                }

                if(twoWindingRequest.getInnerWindings().getRadialParallelCond() == null && twoWindingRequest.getInnerWindings().getAxialParallelCond() == null && twoWindingRequest.getInnerWindings().getCondBreadth() == null){
                    while(lvBreadthInsulated > 14.5){
                        if (lvBreadthInsulated >= 14.2 && lvBreadthInsulated <= 15) {
                            //Here, we are adjusting the window height in such a way that the bi is within 14.4
                            windowHeight = NumberFormattingUtils.nextInteger(windowHeight - ((lvBreadthInsulated - 14.4) * (lvTurnsPerLayer + 1) * lvAxialParallelConductors));
                            lvWindingLength = TwoWindingsFormulas.getWindingLength(windowHeight, lvEndClearance, permaWoodRing);
                            lvBreadthInsulated = TwoWindingsFormulas.getBi(lvWindingLength, lvTurnsPerLayer, lvAxialParallelConductors, lvTransposition, lvRadialParallelConductors);
                            lvBreadth = TwoWindingsFormulas.getBreadth(lvBreadthInsulated, lvConductorInsulation, lvRadialParallelConductors);
                            lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                        } else if (lvBreadthInsulated > 15) {
                            //If the lvBi is more than 15, we will increase 1 axial conductor.
                            lvAxialParallelConductors = lvAxialParallelConductors + 1;
                            lvNumberOfConductors = lvAxialParallelConductors * lvRadialParallelConductors;
                            lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection, lvNumberOfConductors);
                            lvBreadthInsulated = TwoWindingsFormulas.getBi(lvWindingLength, lvTurnsPerLayer, lvAxialParallelConductors, lvTransposition, lvRadialParallelConductors);
                            lvBreadth = TwoWindingsFormulas.getBreadth(lvBreadthInsulated, lvConductorInsulation, lvRadialParallelConductors);
                            lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                        }
                        if(lvBreadthInsulated <= 14.5){break;}
                    }
                }

                //This is for taking the User Input for Cond Height.
                if(twoWindingRequest.getInnerWindings().getCondHeight() != null){
                    lvHeight = twoWindingRequest.getInnerWindings().getCondHeight();
                    if(twoWindingRequest.getInnerWindings().getCondBreadth() == null){
                        lvBreadth = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvHeight);
                        lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                    }
                } else{
                    lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
                }

                lvHeightInsulated = TwoWindingsFormulas.getHeightInsulated(lvHeight, lvConductorInsulation);

                lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);

                lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / (lvRevisedCondCrossSection * lvNumberOfConductors));

                if(twoWindingRequest.getInnerWindings().getCondBreadth() == null){
                    while (lVRevisedCurrentDensity > twoWindings.getLVCurrentDensity() && lvBreadth < 14.4) {
                        lvBreadth = NumberFormattingUtils.oneDigitDecimal(lvBreadth + 0.1);
                        lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
                        lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / (lvRevisedCondCrossSection * lvNumberOfConductors));
                        lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                        lvEndClearance1 = (int) Math.floor((windowHeight - (lvBreadthInsulated * (lvTurnsPerLayer + 1) * lvAxialParallelConductors)) / 2);
                        // we can reduce the end clearance by 3 mm only as below that it will be a neck value so limited the difference to 3mm
                        // (ex: for 10mm end clearance we can reduce it up-to 7mm only.);
                        if ((lvEndClearance - lvEndClearance1) > 3 || lvBreadth > 14.4) {
                            break;
                        }
                        lvEndClearance = lvEndClearance1;
                    }
                }

                lvWindingLength = windowHeight - lvEndClearance - permaWoodRing -lvTransposition;

                if(twoWindingRequest.getInnerWindings().getCondHeight() == null){
                    while (lVRevisedCurrentDensity > twoWindings.getLVCurrentDensity() && lvHeight < 4.5) {
                        lvHeight = NumberFormattingUtils.oneDigitDecimal(lvHeight + 0.1);
                        lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
                        lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / lvRevisedCondCrossSection);
                        lvHeightInsulated = TwoWindingsFormulas.getHeightInsulated(lvHeight, lvConductorInsulation);
                        if (lvHeight > 4.5) {
                            break;
                        }
                    }
                }

                lvTotalCondCrossSection = TwoWindingsFormulas.getActualConductorXSec(lvRevisedCondCrossSection, lvNumberOfConductors);

                lvTransposition = TwoWindingsFormulas.getTransposition(lvBreadthInsulated, lvWindingLength, lvTransposition, lvTurnsPerLayer
                        , lvRadialParallelConductors, lvAxialParallelConductors);

                lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);

                lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / (lvRevisedCondCrossSection * lvNumberOfConductors));

                //All the code above this line from window height should be iterated for the condFlag++ so that we get height less than 4mm.
                lvTotalCondCrossSection = TwoWindingsFormulas.getActualConductorXSec(lvRevisedCondCrossSection, lvNumberOfConductors);

                lvInterLayerInsulation = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn()
                        , lvTurnsPerLayer, lvConductorInsulation, lvIsEnamel, twoWindingRequest.getInnerWindings().getInterLayerInsulation(), twoWindings.getDryType());

                if(lvNoOfDuct > (int) lvNumberOfLayers - 1){
                    lvNoOfDuct = (int) lvNumberOfLayers - 1;
                }
                lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                        lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, true);

                lvWindingLength = NumberFormattingUtils.nextInteger(lvBreadthInsulated * (lvTurnsPerLayer + 1) * lvAxialParallelConductors);
                lvEndClearance = windowHeight - lvWindingLength - lvTransposition - permaWoodRing;
                lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);

                lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);

                lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);

                lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);

                lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase, lvTotalCondCrossSection);

                lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());

                lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt, lvTurnsPerPhase, lvTotalCondCrossSection
                        , twoWindings.getLVConductorMaterial());

                lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated, lvHeightInsulated, lvBreadth, lvHeight
                        , twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);

                lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);

                lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth, lvBreadthInsulated, lvHeight, lvTurnsPerLayer
                        , lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                        , lvNumberOfLayers, lvTransposition, lvIsConductorRound);


                //If the stray loss is more tha 10%, increase the number of Radial conductors by 1 or more (max 6), without altering bi.
                if(twoWindingRequest.getInnerWindings().getRadialParallelCond() == null && twoWindingRequest.getInnerWindings().getAxialParallelCond() == null){
                    while (lvStrayLoss > 10) {
                        lvRadialParallelConductors = lvRadialParallelConductors + 1;
                        lvTransposition = 20;
                        lvNumberOfConductors = lvAxialParallelConductors * lvRadialParallelConductors;
                        lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvTotalCondCrossSection, lvNumberOfConductors);
                        lvBreadthInsulated = TwoWindingsFormulas.getBi(lvWindingLength, lvTurnsPerLayer, lvAxialParallelConductors, lvTransposition, lvRadialParallelConductors);
                        lvBreadth = TwoWindingsFormulas.getBreadth(lvBreadthInsulated, lvConductorInsulation, lvRadialParallelConductors);
                        lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                        lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
                        lvHeightInsulated = TwoWindingsFormulas.getHeightInsulated(lvHeight, lvConductorInsulation);
                        lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
                        lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / (lvRevisedCondCrossSection * lvNumberOfConductors));

                        lvTransposition = TwoWindingsFormulas.getTransposition(lvBreadthInsulated, lvWindingLength, lvTransposition, lvTurnsPerLayer, lvRadialParallelConductors, lvAxialParallelConductors);

                        lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);

                        lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / (lvRevisedCondCrossSection * lvNumberOfConductors));

                        //All the code above this line from window height should be iterated for the condFlag++ so that we get height less than 4mm.
                        lvTotalCondCrossSection = TwoWindingsFormulas.getActualConductorXSec(lvRevisedCondCrossSection, lvNumberOfConductors);

                        lvInterLayerInsulation = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn(), lvTurnsPerLayer, lvConductorInsulation, lvIsEnamel, twoWindingRequest.getInnerWindings().getInterLayerInsulation(), twoWindings.getDryType());

                        lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, true);

                        lvWindingLength = NumberFormattingUtils.nextInteger(lvBreadthInsulated * (lvTurnsPerLayer + 1) * lvAxialParallelConductors);
                        lvEndClearance = windowHeight - lvWindingLength - lvTransposition - permaWoodRing;
                        lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);

                        lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);

                        lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);

                        lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);

                        lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase, lvTotalCondCrossSection);

                        lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());

                        lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt, lvTurnsPerPhase, lvTotalCondCrossSection
                                , twoWindings.getLVConductorMaterial());

                        lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated, lvHeightInsulated
                                , lvBreadth, lvHeight, twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                        lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                        lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth, lvBreadthInsulated, lvHeight, lvTurnsPerLayer
                                , lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                                , lvNumberOfLayers, lvTransposition, lvIsConductorRound);
                        if (lvRadialParallelConductors > 6) {
                            break;
                        }
                    }
                }

                lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight
                        , lVRevisedCurrentDensity, lvStrayLoss);

                lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss, (lvNoOfDuct * 2) + 2, lvWindingLength, lvTransposition, lvLmt, twoWindings.getDryType(), true);

                //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
                if(twoWindingRequest.getInnerWindings().getDucts() == null){
                    while (lvGradient >= gradientLimit && lvNoOfDuct < lvNumberOfLayers - 1) {
                        lvNoOfDuct = lvNoOfDuct + 1;
                        lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, true);
                        lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);
                        lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);
                        lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);
                        lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                        lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase, lvTotalCondCrossSection);
                        lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                        lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt, lvTurnsPerPhase, lvTotalCondCrossSection
                                , twoWindings.getLVConductorMaterial());
                        lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated, lvHeightInsulated, lvBreadth
                                , lvHeight, twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                        lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                        lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth, lvBreadthInsulated, lvHeight, lvTurnsPerLayer
                                , lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                                , lvNumberOfLayers, lvTransposition, lvIsConductorRound);
                        lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight
                                , lVRevisedCurrentDensity, lvStrayLoss);
                        lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss, (lvNoOfDuct * 2) + 2, lvWindingLength, lvTransposition, lvLmt, twoWindings.getDryType(), true);
                    }
                }
            }
        }
        else if(Objects.equals(twoWindings.getLvWindingType().toString(), "DISC")){
            if(twoWindings.getLowVoltage() >= 11000){
                lvEndClearance = TwoWindingsFormulas.getLvEndClearance(twoWindings.getKVA(), twoWindings.getConnection(), twoWindings.getInnerWindings().getEndClearances(), twoWindings.getDryType(), twoWindings.getHighVoltage());
                lvWindingLength = TwoWindingsFormulas.getWindingLength(windowHeight, lvEndClearance, permaWoodRing);
            }
            lvConductorCrossSection = TwoWindingsFormulas.getConductorCrossSection(lvCurrentPerPhase, twoWindings.getLVCurrentDensity());
            lvNumberOfConductors = TwoWindingsFormulas.getNumberOfConductors(lvConductorCrossSection, twoWindings.getLVConductorMaterial());
            lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
            lvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getLowVoltage(), false, twoWindings.getConnection(), lvIsEnamel, twoWindingRequest.getInnerWindings().getCondInsulation(), twoWindings.getDryType());
            lvRadialParallelConductors = twoWindingRequest.getInnerWindings().getRadialParallelCond() == null ? lvNumberOfConductors : twoWindingRequest.getInnerWindings().getRadialParallelCond();
            lvAxialParallelConductors = TwoWindingsFormulas.getAxialParallelConductors(lvNumberOfConductors, lvRadialParallelConductors, twoWindingRequest.getInnerWindings().getAxialParallelCond());
            lvNumberOfConductors = lvRadialParallelConductors * lvAxialParallelConductors;
            lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
            if(twoWindingRequest.getInnerWindings().getCondBreadth() != null){
                lvBreadth = twoWindingRequest.getInnerWindings().getCondBreadth();
                lvBreadthInsulated = lvBreadth + lvConductorInsulation;
            }else {
                lvBreadth = 14;
                lvBreadthInsulated = lvBreadth + lvConductorInsulation;
            }
            if(twoWindingRequest.getInnerWindings().getCondHeight() != null){
                lvHeight = twoWindingRequest.getInnerWindings().getCondHeight();
                if(twoWindingRequest.getInnerWindings().getCondBreadth() == null){
                    lvBreadth = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvHeight);
                    lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                }
            }else {
                lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
            }

            if(twoWindingRequest.getInnerWindings().getCondBreadth() == null && twoWindingRequest.getInnerWindings().getCondHeight() == null){
                while (lvBreadth > 6 * lvHeight){
                    lvBreadth = lvBreadth - 0.1;
                    lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
                    lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                    if(lvBreadth <= 6 * lvHeight){break;}
                }
            }
            lvNoOfDiscs = NumberFormattingUtils.nextEvenInteger(lvWindingLength / ((lvBreadthInsulated * lvAxialParallelConductors) + lvDiscDuctsSize));
            lvTurnsPerDisc = (double) lvTurnsPerPhase / lvNoOfDiscs;
            while(NumberFormattingUtils.twoDigitDecimalPart(lvTurnsPerDisc) < 0.7){
                lvNoOfDiscs = lvNoOfDiscs + 2;
                lvTurnsPerDisc = (double) lvTurnsPerPhase / lvNoOfDiscs;
                if(NumberFormattingUtils.twoDigitDecimalPart(lvTurnsPerDisc) >= 0.7){break;}
            }
            lvTurnsPerDisc = Math.ceil(lvTurnsPerDisc);
            if(twoWindingRequest.getInnerWindings().getCondHeight() == null && twoWindingRequest.getInnerWindings().getCondBreadth() == null){
                lvBreadthInsulated = NumberFormattingUtils.oneDigitDecimal(((lvWindingLength / lvNoOfDiscs) - (lvDiscDuctsSize * insulationCompression)) / lvAxialParallelConductors);
                lvBreadth = NumberFormattingUtils.oneDigitDecimalFloor(lvBreadthInsulated - (lvConductorInsulation * insulationCompression));
                lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
            }
            lvHeightInsulated = TwoWindingsFormulas.getHeightInsulated(lvHeight, lvConductorInsulation);
            lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
            lvTotalCondCrossSection = NumberFormattingUtils.twoDigitDecimal(lvRevisedCondCrossSection * lvNumberOfConductors);

            int totalTurnsPossible = (int) (lvTurnsPerDisc * lvNoOfDiscs);
            lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);
            int excessTurns = (int) (totalTurnsPossible - lvTurnsPerPhase);
            lvNoOfSpacers = TwoWindingsFormulas.getSpacersAndWidth(lvId)[0];
            lvWidthOfSpacer = TwoWindingsFormulas.getSpacersAndWidth(lvId)[1];
            lvSpacersToBeRemoved = lvNoOfSpacers * excessTurns;
            lvSpacersToBeRemoved = lvSpacersToBeRemoved - lvNoOfDiscs; //Default 1 spacer per disc is gone
            //When the lvSpacersToBeRemoved is less than no of discs then default 1 spacer per disc will be removed so, no extra spacers to be removed.
            int balanceSpacersInLastDisc = 0;
            if(lvSpacersToBeRemoved <= 0){
                lvSpacersToBeRemoved = 0;
            }else{
                lvHalfDisc = (int) Math.floor((double) lvSpacersToBeRemoved / ((double) (lvNoOfSpacers/2) -1));
                lvSpacersToBeRemoved = lvSpacersToBeRemoved - (lvHalfDisc * ((lvNoOfSpacers/2) -1));
            }
            if(lvSpacersToBeRemoved == 0){
                lvPartialDisc = 0;
                lvFullDisc = lvNoOfDiscs - lvHalfDisc;
            }else{
                lvPartialDisc = 1;
                lvFullDisc = lvNoOfDiscs - lvHalfDisc - lvPartialDisc;
                balanceSpacersInLastDisc = lvNoOfSpacers - lvSpacersToBeRemoved -1;
            }
            String partialDiscStr =lvPartialDisc > 0 ? " + " + lvPartialDisc + "(" + balanceSpacersInLastDisc + "/" + lvNoOfSpacers + ")" : "";
            lvDiscArrangement = lvFullDisc + "F " + " + " + lvHalfDisc + "H " + partialDiscStr;

            lvTurnsPerLayer = lvNoOfDiscs;
            lvNumberOfLayers = lvTurnsPerDisc;

            lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase/(lvRevisedCondCrossSection * lvNumberOfConductors));
            lvWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, insulationCompression, lvNoOfDiscs, lvDiscDuctsSize);
            lvUnpressedWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, 1, lvNoOfDiscs, lvDiscDuctsSize);
            windowHeight = lvWindingLength + lvTransposition + lvEndClearance + permaWoodRing;
            lvEndClearance = Math.floor(windowHeight - (lvWindingLength + permaWoodRing));
            lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
            if(lvRadialThickness >= 65 && twoWindingRequest.getInnerWindings().getDucts() == null){
                lvNoOfDuct = 1;
                    if(twoWindings.getInnerWindings().getDuctSize() == null){
                        lvDuctThickness = twoWindings.getKVA() <= 5000 ? 3 : 4;
                    }else{
                        lvDuctThickness = twoWindings.getInnerWindings().getDuctSize() > 3 ? twoWindings.getInnerWindings().getDuctSize() : 3;
                    }
                lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
            }

            lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);

            lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);

            lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);

            lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);

            lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());

            lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                    , twoWindings.getLVConductorMaterial());

            lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight
                    ,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);

            lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);

            lvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(lvBreadth, lvHeight,lvTurnsPerLayer,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation,twoWindings.getLVConductorMaterial(),lvNumberOfLayers,lvWindingLength);
            if(twoWindingRequest.getInnerWindings().getRadialParallelCond() == null && twoWindingRequest.getInnerWindings().getCondHeight() == null){
                while(lvStrayLoss > 10.0){
                    lvRadialParallelConductors +=1;
                    lvNumberOfConductors = lvRadialParallelConductors * lvAxialParallelConductors;
                    lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
                    lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
                    lvHeightInsulated = lvHeight + lvConductorInsulation;
                    lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
                    lvTotalCondCrossSection = NumberFormattingUtils.twoDigitDecimal(lvRevisedCondCrossSection * lvNumberOfConductors);
                    lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase/(lvRevisedCondCrossSection * lvNumberOfConductors));
                    lvWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, insulationCompression, lvNoOfDiscs, lvDiscDuctsSize);
                    lvUnpressedWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, 1, lvNoOfDiscs, lvDiscDuctsSize);
                    windowHeight = lvWindingLength + lvTransposition + lvEndClearance + permaWoodRing;
                    lvEndClearance = Math.floor(windowHeight - (lvWindingLength + permaWoodRing));
                    lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
                    if(lvRadialThickness >= 65 && twoWindingRequest.getInnerWindings().getDucts() == null){
                        lvNoOfDuct = 1;
                        if(twoWindings.getInnerWindings().getDuctSize() == null){
                            lvDuctThickness = twoWindings.getKVA() <= 5000 ? 3 : 4;
                        }else{
                            lvDuctThickness = twoWindings.getInnerWindings().getDuctSize() > 3 ? twoWindings.getInnerWindings().getDuctSize() : 3;
                        }
                        lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
                    }
                    lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);

                    lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);

                    lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);

                    lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);

                    lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);

                    lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());

                    lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                            , twoWindings.getLVConductorMaterial());

                    lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight
                            ,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);

                    lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);

                    lvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(lvBreadth, lvHeight,lvTurnsPerLayer,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation,twoWindings.getLVConductorMaterial(),lvNumberOfLayers,lvWindingLength);

                }

            }
            lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight, lVRevisedCurrentDensity, lvStrayLoss);
            if(twoWindings.getInnerWindings().getDuctSize() == null){
                lvDuctThickness = twoWindings.getKVA() <= 5000 ? 3 : 4;
            }else{
                lvDuctThickness = twoWindings.getInnerWindings().getDuctSize() > 3 ? twoWindings.getInnerWindings().getDuctSize() : 3;
            }
            v0 = TwoWindingsFormulas.getV0(lVRevisedCurrentDensity,lvRevisedCondCrossSection,lvStrayLoss,lvHeightInsulated,windingTemp,ambientTemp);
            psi = TwoWindingsFormulas.getPsi(lvBreadthInsulated,lvRadialThickness,lvDuctThickness,lvNoOfDuct);
            rW = TwoWindingsFormulas.getRw(v0, psi,lvConductorInsulation);
            lvGradient = NumberFormattingUtils.oneDigitDecimal(v0 * psi * rW);
            //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
            if(twoWindingRequest.getInnerWindings().getDucts() == null){
                while(lvGradient >= gradientLimit){
                    if(lvNoOfDuct > 1){break;}
                    lvNoOfDuct = lvNoOfDuct + 1;
                    if(twoWindings.getKVA() <= 5000){
                        if(twoWindings.getInnerWindings().getDuctSize() == null){
                            lvDuctThickness = 3;
                        }else{
                            lvDuctThickness = twoWindings.getInnerWindings().getDuctSize() > 3 ? twoWindings.getInnerWindings().getDuctSize() : 3;
                        }
                        lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
                    }else{
                        if(twoWindings.getInnerWindings().getDuctSize() == null){
                            lvDuctThickness = 4;
                        }else{
                            lvDuctThickness = twoWindings.getInnerWindings().getDuctSize() > 4 ? twoWindings.getInnerWindings().getDuctSize() : 4;
                        }
                        lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
                    }
                    lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);
                    lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);
                    lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);
                    lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                    lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);
                    lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                    lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection, twoWindings.getLVConductorMaterial());
                    lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                    lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                    lvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(lvBreadth, lvHeight,lvTurnsPerLayer,lvRadialParallelConductors,lvAxialParallelConductors, lvConductorInsulation,twoWindings.getLVConductorMaterial(),lvNumberOfLayers,lvWindingLength);
                    lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight, lVRevisedCurrentDensity, lvStrayLoss) * ((double) lvTurnsPerPhase /lvTurnsPerPhase);
                    v0 = TwoWindingsFormulas.getV0(lVRevisedCurrentDensity,lvCrossSecPerConductor,lvStrayLoss,lvHeightInsulated,windingTemp,ambientTemp);
                    psi = TwoWindingsFormulas.getPsi(lvBreadthInsulated,lvRadialThickness,lvDuctThickness,lvNoOfDuct);
                    rW = TwoWindingsFormulas.getRw(v0, psi,lvConductorInsulation);
                    lvGradient = NumberFormattingUtils.oneDigitDecimal(v0 * psi * rW);
                }
            }
        }
        else if(Objects.equals(twoWindings.getLvWindingType().toString(), "FOIL")){
            lvTurnsPerLayer = 1;
            lvNumberOfLayers = lvTurnsPerPhase;
            lvEndStrip = TwoWindingsFormulas.getFoilEndStrip(lvWindingLength);
            lvBreadth = TwoWindingsFormulas.getFoilLength(lvWindingLength, twoWindingRequest.getInnerWindings().getCondBreadth(), lvEndStrip);
            lvWindingLength = lvBreadth + lvEndStrip * 2;
            windowHeight = lvWindingLength + lvEndClearance + permaWoodRing;
            lvNumberOfConductors = 1;
            lvBreadthInsulated = lvBreadth;
            lvConductorCrossSection = TwoWindingsFormulas.getConductorCrossSection(lvCurrentPerPhase, twoWindings.getLVCurrentDensity());
            if(twoWindingRequest.getInnerWindings().getCondHeight() != null){
                lvHeight = twoWindingRequest.getInnerWindings().getCondHeight();
            }else {
                lvHeight = NumberFormattingUtils.oneDigitDecimal(lvConductorCrossSection/lvBreadth);
            }
            lvHeightInsulated = lvHeight;
            lvConductorInsulation = 0.0;
            lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
            lvTotalCondCrossSection = lvRevisedCondCrossSection;
            lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase/ lvRevisedCondCrossSection);
            lvInterLayerInsulation = twoWindingRequest.getInnerWindings().getInterLayerInsulation() == null ? 0.1:
                                    twoWindingRequest.getInnerWindings().getInterLayerInsulation();
            lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, 1, lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, false);
            lvRadialThickness = NumberFormattingUtils.halfUp(lvRadialThickness * 1.05);
            lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);
            lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);
            lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);
            lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt, lvTurnsPerPhase, lvRevisedCondCrossSection, twoWindings.getLVConductorMaterial());
            lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
            lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase, lvRevisedCondCrossSection);
            lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
            lvInsulatedWeight = lvBareWeight;
            lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, 1);
            lvStrayLoss = TwoWindingsFormulas.getStrayLossForFoil(lvHeight, twoWindings.getLVConductorMaterial(), lvNumberOfLayers);
            lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight, lVRevisedCurrentDensity, lvStrayLoss);
            lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss, (lvNoOfDuct * 2) + 2, lvBreadth, lvTransposition, lvLmt, twoWindings.getDryType(), true);
            if(twoWindingRequest.getInnerWindings().getDucts() == null){
                while (lvGradient >= gradientLimit && lvNoOfDuct < lvNumberOfLayers - 1) {
                    lvNoOfDuct = lvNoOfDuct + 1;
                    lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, 1, lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, false);
                    lvRadialThickness = NumberFormattingUtils.halfUp(lvRadialThickness * 1.1);
                    lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);
                    lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);
                    lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);
                    lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt, lvTurnsPerPhase, lvRevisedCondCrossSection, twoWindings.getLVConductorMaterial());
                    lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                    lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase, lvRevisedCondCrossSection);
                    lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                    lvInsulatedWeight = lvBareWeight;
                    lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, 1);
                    lvStrayLoss = TwoWindingsFormulas.getStrayLossForFoil(lvHeight, twoWindings.getLVConductorMaterial(), lvNumberOfLayers);
                    lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight, lVRevisedCurrentDensity, lvStrayLoss);
                    lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss, (lvNoOfDuct * 2) + 2, lvBreadth, lvTransposition, lvLmt, twoWindings.getDryType(), true);
                }
            }
        }
        else if(Objects.equals(twoWindings.getLvWindingType().toString(), "LAYERDISC")){
            lvEndClearance = TwoWindingsFormulas.getLvEndClearance(twoWindings.getKVA(), twoWindings.getConnection(), twoWindings.getInnerWindings().getEndClearances(), twoWindings.getDryType(), twoWindings.getHighVoltage());
            lvWindingLength = TwoWindingsFormulas.getWindingLength(windowHeight, lvEndClearance, permaWoodRing);
            lvNumberOfLayers = 1;
            lvNoOfDuct = 0;
            lvDuctThickness = 0;
            lvTurnsPerLayer = TwoWindingsFormulas.getLvTurnsPerLayer(lvTurnsPerPhase, lvNumberOfLayers);
            lvConductorCrossSection = TwoWindingsFormulas.getConductorCrossSection(lvCurrentPerPhase, twoWindings.getLVCurrentDensity());
            lvNumberOfConductors = TwoWindingsFormulas.getNumberOfConductors(lvConductorCrossSection, twoWindings.getLVConductorMaterial());
            lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
            lvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getLowVoltage(), false, twoWindings.getConnection(), lvIsEnamel, twoWindingRequest.getInnerWindings().getCondInsulation(), twoWindings.getDryType());
            lvRadialParallelConductors = twoWindingRequest.getInnerWindings().getRadialParallelCond() == null ? lvNumberOfConductors : twoWindingRequest.getInnerWindings().getRadialParallelCond();
            lvAxialParallelConductors = TwoWindingsFormulas.getAxialParallelConductors(lvNumberOfConductors, lvRadialParallelConductors, twoWindingRequest.getInnerWindings().getAxialParallelCond());
            lvNumberOfConductors = lvRadialParallelConductors * lvAxialParallelConductors;
            lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
            if(twoWindingRequest.getInnerWindings().getCondBreadth() != null){
                lvBreadth = twoWindingRequest.getInnerWindings().getCondBreadth();
                lvBreadthInsulated = lvBreadth + lvConductorInsulation;
            }else{
                lvBreadthInsulated = NumberFormattingUtils.oneDigitDecimal(((lvWindingLength / lvTurnsPerLayer) - (lvDiscDuctsSize * insulationCompression)) / lvAxialParallelConductors);
                lvBreadth = NumberFormattingUtils.oneDigitDecimalFloor(lvBreadthInsulated - (lvConductorInsulation * insulationCompression));
                lvBreadthInsulated = lvBreadth + lvConductorInsulation;
            }
            if(twoWindingRequest.getInnerWindings().getCondHeight() != null){
                lvHeight = twoWindingRequest.getInnerWindings().getCondHeight();
                if(twoWindingRequest.getInnerWindings().getCondBreadth() == null){
                    lvBreadthInsulated = NumberFormattingUtils.oneDigitDecimal(((lvWindingLength / lvTurnsPerLayer) - (lvDiscDuctsSize * insulationCompression)) / lvAxialParallelConductors);
                    lvBreadth = NumberFormattingUtils.oneDigitDecimalFloor(lvBreadthInsulated - (lvConductorInsulation * insulationCompression));
                    lvBreadthInsulated = lvBreadth + lvConductorInsulation;

                }
            }else {
                lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
            }
            lvHeightInsulated = TwoWindingsFormulas.getHeightInsulated(lvHeight, lvConductorInsulation);
            lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
            lvTotalCondCrossSection = NumberFormattingUtils.twoDigitDecimal(lvRevisedCondCrossSection * lvNumberOfConductors);
            lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase/(lvRevisedCondCrossSection * lvNumberOfConductors));
            lvWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, insulationCompression, (int)lvTurnsPerLayer, lvDiscDuctsSize);
            lvUnpressedWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, 1, (int)lvTurnsPerLayer, lvDiscDuctsSize);
            windowHeight = lvWindingLength + lvTransposition + lvEndClearance + permaWoodRing;
            lvEndClearance = Math.floor(windowHeight - (lvWindingLength + permaWoodRing));
            lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
            lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);

            lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);

            lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);

            lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);

            lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());

            lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                    , twoWindings.getLVConductorMaterial());

            lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight
                    ,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
            lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);

            lvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(lvBreadth, lvHeight,lvTurnsPerLayer,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation,twoWindings.getLVConductorMaterial(),lvNumberOfLayers,lvWindingLength);



            //If the stray loss is more tha 10%, increase the number of Radial conductors by 1 or more (max 6), without altering bi.
            if(twoWindingRequest.getInnerWindings().getRadialParallelCond() == null && twoWindingRequest.getInnerWindings().getCondHeight() == null){
                while(lvStrayLoss > 10.0){
                    lvRadialParallelConductors +=1;
                    lvNumberOfConductors = lvRadialParallelConductors * lvAxialParallelConductors;
                    lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
                    lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);
                    lvHeightInsulated = lvHeight + lvConductorInsulation;
                    lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
                    lvTotalCondCrossSection = NumberFormattingUtils.twoDigitDecimal(lvRevisedCondCrossSection * lvNumberOfConductors);
                    lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase/(lvRevisedCondCrossSection * lvNumberOfConductors));
                    lvWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, insulationCompression, (int)lvTurnsPerLayer, lvDiscDuctsSize);
                    lvUnpressedWindingLength = TwoWindingsFormulas.getDiscWindingLength(lvBreadth, lvConductorInsulation, 1, (int)lvTurnsPerLayer, lvDiscDuctsSize);
                    windowHeight = lvWindingLength + lvTransposition + lvEndClearance + permaWoodRing;
                    lvEndClearance = Math.floor(windowHeight - (lvWindingLength + permaWoodRing));
                    lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
                    lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);

                    lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);

                    lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);

                    lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);

                    lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);

                    lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());

                    lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                            , twoWindings.getLVConductorMaterial());

                    lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight
                            ,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);

                    lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);

                    lvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(lvBreadth, lvHeight,lvTurnsPerLayer,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation,twoWindings.getLVConductorMaterial(),lvNumberOfLayers,lvWindingLength);
                    if(lvRadialParallelConductors > 32){break;}
                }

            }
            lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight, lVRevisedCurrentDensity, lvStrayLoss);
            v0 = TwoWindingsFormulas.getV0(lVRevisedCurrentDensity,lvRevisedCondCrossSection,lvStrayLoss,lvHeightInsulated,windingTemp,ambientTemp);
            psi = TwoWindingsFormulas.getPsi(lvBreadthInsulated,lvRadialThickness,lvDuctThickness,lvNoOfDuct);
            rW = TwoWindingsFormulas.getRw(v0, psi,lvConductorInsulation);
            lvGradient = NumberFormattingUtils.oneDigitDecimal(v0 * psi * rW);
            if(twoWindingRequest.getInnerWindings().getDucts() == null){
                while(lvGradient >= gradientLimit){
                    if(lvNoOfDuct > 1){break;}
                    lvNoOfDuct = lvNoOfDuct + 1;
                    if(twoWindings.getKVA() <= 5000){
                        if(twoWindings.getInnerWindings().getDuctSize() == null){
                            lvDuctThickness = 3;
                        }else{
                            lvDuctThickness = twoWindings.getInnerWindings().getDuctSize() > 3 ? twoWindings.getInnerWindings().getDuctSize() : 3;
                        }
                        lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
                    }else{
                        if(twoWindings.getInnerWindings().getDuctSize() == null){
                            lvDuctThickness = 4;
                        }else{
                            lvDuctThickness = twoWindings.getInnerWindings().getDuctSize() > 4 ? twoWindings.getInnerWindings().getDuctSize() : 4;
                        }

                        lvRadialThickness = TwoWindingsFormulas.getDiscRadialThickness(lvHeight, lvRadialParallelConductors, lvConductorInsulation, insulationExpansion, (int) lvNumberOfLayers, lvNoOfDuct, lvDuctThickness);
                    }
                    lvId = TwoWindingsFormulas.getID(coreDiameter, coreGap);
                    lvOd = TwoWindingsFormulas.getOD(lvId, lvRadialThickness);
                    lvLmt = TwoWindingsFormulas.getLMT(lvId, lvOd);
                    lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                    lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);
                    lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                    lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection, twoWindings.getLVConductorMaterial());
                    lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                    lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                    lvStrayLoss = TwoWindingsFormulas.getStrayLossForDisc(lvBreadth, lvHeight,lvTurnsPerLayer,lvRadialParallelConductors,lvAxialParallelConductors, lvConductorInsulation,twoWindings.getLVConductorMaterial(),lvNumberOfLayers,lvWindingLength);
                    lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight, lVRevisedCurrentDensity, lvStrayLoss) * ((double) lvTurnsPerPhase /lvTurnsPerPhase);
                    v0 = TwoWindingsFormulas.getV0(lVRevisedCurrentDensity,lvCrossSecPerConductor,lvStrayLoss,lvHeightInsulated,windingTemp,ambientTemp);
                    psi = TwoWindingsFormulas.getPsi(lvBreadthInsulated,lvRadialThickness,lvDuctThickness,lvNoOfDuct);
                    rW = TwoWindingsFormulas.getRw(v0, psi,lvConductorInsulation);
                    lvGradient = NumberFormattingUtils.oneDigitDecimal(v0 * psi * rW);
                }
            }


        }

        if(windowHeight % 5 != 0){
            int windowHtRoundOff = (int) (5 - windowHeight % 5);
            windowHeight = windowHeight + windowHtRoundOff;
            lvEndClearance = lvEndClearance + windowHtRoundOff;
        }
        double lvHvGap = TwoWindingsFormulas.getLvHvGap(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), twoWindings.getCoilDimensions().getLVHVGap(), twoWindings.getDryType());


        //The formula.put are all written below here to avoid writing it multiple times.

        formula.put("revisedFluxDensity", revisedFluxDensity);
        formula.put("lvVoltsPerPhase", lvVoltsPerPhase);
        formula.put("lvTurnsPerPhase", lvTurnsPerPhase);
        formula.put("revisedVoltsPerTurn", revisedVoltsPerTurn);
        formula.put("netArea", netArea);
        formula.put("grossArea", grossArea);
        formula.put("coreDiameter", coreDiameter);
        formula.put("coreDiaPossibleMin", coreDiaPossibleMin);
        formula.put("coreDiaPossibleMax", coreDiaPossibleMax);
        formula.put("possibleCoreDiaRange", possibleCoreDiaRange);
        formula.put("lvCurrentPerPhase", lvCurrentPerPhase);
        formula.put("windowHeight", windowHeight);
        formula.put("possibleWindowHeightMin", possibleWindowHeightMin);
        formula.put("possibleWindowHeightMax", possibleWindowHeightMax);
        formula.put("possibleWindowHeightRange", possibleWindowHeightRange);
        formula.put("lvEndClearance", lvEndClearance);
        formula.put("permaWoodRing", permaWoodRing);
        formula.put("lvWindingLength", lvWindingLength);
        formula.put("lvUnpressedWindingLength",lvUnpressedWindingLength);
        formula.put("lvNumberOfLayers", lvNumberOfLayers);
        formula.put("lvTurnsPerLayer", lvTurnsPerLayer);
        formula.put("lvDiscDuctsSize", lvDiscDuctsSize);
        formula.put("lvDiscArrangement", lvDiscArrangement);
        formula.put("lvConductorCrossSection", lvConductorCrossSection);
        formula.put("lvIsConductorRound", lvIsConductorRound);
        formula.put("lvNumberOfConductors", lvNumberOfConductors);
        formula.put("lvCrossSecPerConductor", lvCrossSecPerConductor);
        formula.put("lvRadialParallelConductors", lvRadialParallelConductors);
        formula.put("lvAxialParallelConductors", lvAxialParallelConductors);
        formula.put("lvBreadthInsulated", lvBreadthInsulated);
        formula.put("lvConductorInsulation", lvConductorInsulation);
        formula.put("lvIsEnamel", lvIsEnamel);
        formula.put("lvBreadth", lvBreadth);
        formula.put("lvHeight", lvHeight);
        formula.put("lvHeightInsulated", lvHeightInsulated);
        formula.put("lvEndStrip", lvEndStrip);
        formula.put("lvConductorFlag", lvConductorFlag);
        formula.put("lvTransposition",lvTransposition);
        formula.put("lvRevisedCondCrossSection", lvRevisedCondCrossSection);
        formula.put("lvTotalCondCrossSection",lvTotalCondCrossSection);
        formula.put("lVRevisedCurrentDensity",lVRevisedCurrentDensity);
        formula.put("lvInterLayerInsulation", lvInterLayerInsulation);
        formula.put("lvRadialThickness", lvRadialThickness);
        formula.put("coreGap", coreGap);
        formula.put("lvId", lvId);
        formula.put("lvOd", lvOd);
        formula.put("lvLmt", lvLmt);
        formula.put("lvWireLength", lvWireLength);
        formula.put("lvR75", lvR75);
        formula.put("lvR26", lvR26);
        formula.put("lvBareWeight", lvBareWeight);
        formula.put("lvInsulatedWeight", lvInsulatedWeight);
        formula.put("lvProcurementWeight",lvProcurementWeight);
        formula.put("%lvStrayLoss", lvStrayLoss);
        formula.put("lvLoadLoss", lvLoadLoss);
        formula.put("lvGradient", lvGradient);
        formula.put("lvHvGap", lvHvGap);
        formula.put("lvNoOfDuct", lvNoOfDuct);
        formula.put("lvDuctThickness", lvDuctThickness);
        formula.put("lvNoOfSpacers", lvNoOfSpacers);
        formula.put("lvWidthOfSpacer", lvWidthOfSpacer);
        formula.put("v0", v0);
        formula.put("psi", psi);
        formula.put("rW", rW);
        formula.put("gradientLimit", gradientLimit);

        return formula;

    }
}
