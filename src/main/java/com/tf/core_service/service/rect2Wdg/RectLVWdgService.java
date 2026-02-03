package com.tf.core_service.service.rect2Wdg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.formulas.twoWdg.TwoWindingsFormulas;
import com.tf.core_service.model.twoWindings.TwoWindings;
import com.tf.core_service.request.TwoWindingRequest;
import com.tf.core_service.utils.NumberFormattingUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class RectLVWdgService {
    public Map<String, Object> calculateRectLvWdg(TwoWindings twoWindings, TwoWindingRequest twoWindingRequest) throws JsonProcessingException {
        Map<String, Object> formula = new HashMap<>();

        int lvConductorFlag = twoWindings.getLvConductorFlag();
        Integer lvNoOfDuct = twoWindings.getInnerWindings().getDucts() != null ? twoWindings.getInnerWindings().getDucts() : 0;
        int lvEndClearance1;
        double lvBreadth = 0;
        double lvHeight = 0;
        double lvBreadthInsulated = 0;
        double lvHeightInsulated = 0;
        double lVRevisedCurrentDensity = 0;
        double lvTotalCondCrossSection = 0;
        int lvRadialParallelConductors = 0;
        int lvAxialParallelConductors = 0;
        int lvTransposition = 0;
        double lvLmt = 0;
        int lvIdWidth = 0;
        int lvIdDepth = 0;
        int lvOdWidth = 0;
        int lvOdDepth = 0;
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
        double coreGap = 0;
        double lvNumberOfLayers = 0;
        double lvTurnsPerLayer = 0;
        double lvConductorCrossSection = 0;
        double lvConductorInsulation = 0;
        boolean lvIsEnamel = twoWindings.getLowVoltage() <= 33000 && twoWindings.getInnerWindings().getIsEnamel();
        double lvCrossSecPerConductor = 0;
        double lvRevisedCondCrossSection = 0;
        int lvNumberOfConductors = 0;
        Integer lvDuctThickness = twoWindings.getInnerWindings().getDuctSize();
        String lvDiscArrangement = "";
        int lvFullDuct = 0;
        int lvPartialDuct = 0;
        double gradientLimit = TwoWindingsFormulas.getGradientLimit(twoWindings.getDryType(), twoWindings.getDryTempClass());

        double lvVoltsPerPhase = TwoWindingsFormulas.getLvVoltsPerPhase(twoWindings.getLowVoltage(), twoWindings.getConnection());
        //For now, I have let the lvTurnsPerPhase take any input that the user gives. Here, Even the core dia changes on changing teh lvTurnsPerPhase
        double lvTurnsPerPhase = TwoWindingsFormulas.getTurnsPerPhase(lvVoltsPerPhase, twoWindings.getVoltsPerTurn(), twoWindings.getInnerWindings().getTurnsPerPhase(), twoWindings.getConnection(), true);
        double revisedVoltsPerTurn = TwoWindingsFormulas.getRevisedVoltsPerTurn(lvVoltsPerPhase, lvTurnsPerPhase, twoWindings.getConnection());
        double netArea = TwoWindingsFormulas.getNetArea(revisedVoltsPerTurn, twoWindings.getFrequency(), twoWindings.getFluxDensity());

        double revisedFluxDensity = NumberFormattingUtils.fourDigitDecimal(revisedVoltsPerTurn / (4.44 * twoWindings.getFrequency() * netArea * Math.pow(10, -6)));

        double grossArea = NumberFormattingUtils.next5or0Integer(netArea / 0.94);

        //TODO: Get User Input for Core Frame.
        int coreWidth = NumberFormattingUtils.nextInteger(Math.pow(grossArea / 2, 0.5));
        int coreDepth = NumberFormattingUtils.nextInteger(grossArea / coreWidth);
        int coreDiameter = NumberFormattingUtils.nextInteger(TwoWindingsFormulas.getCoreDiameter(grossArea, twoWindings.getCore().getCoreDia()));
        String coreFrame = coreWidth + " X " + coreDepth;

        double leastKVal = TwoWindingsFormulas.getPossibleKValue(twoWindings.getKVA(), twoWindings.getLVConductorMaterial(), twoWindings.getETransCostType())[0];
        double maxKValue = TwoWindingsFormulas.getPossibleKValue(twoWindings.getKVA(), twoWindings.getLVConductorMaterial(), twoWindings.getETransCostType())[1];

        double lvCurrentPerPhase = TwoWindingsFormulas.getCurrentPerPhase(twoWindings.getKVA(), lvVoltsPerPhase);

        double windowHeight = TwoWindingsFormulas.getWindowHeight(twoWindings.getKValue(), coreDiameter, twoWindings.getLVConductorMaterial(), twoWindings.getCore().getLimbHt(), twoWindings.getDryType());

        double possibleWindowHeightMin = TwoWindingsFormulas.getWindowHeight(maxKValue, coreDiameter, twoWindings.getLVConductorMaterial(), null, twoWindings.getDryType());
        double possibleWindowHeightMax = TwoWindingsFormulas.getWindowHeight(leastKVal, coreDiameter, twoWindings.getLVConductorMaterial(), null, twoWindings.getDryType());
        String possibleWindowHeightRange = "Note: The possible Window Height range for core dia = " + coreDiameter + "mm is " + possibleWindowHeightMin + "mm to " + possibleWindowHeightMax + "mm";

        double lvEndClearance = TwoWindingsFormulas.getLvEndClearance(twoWindings.getKVA(), twoWindings.getConnection(), twoWindings.getInnerWindings().getEndClearances(), twoWindings.getDryType(), twoWindings.getLowVoltage(), twoWindings.getHighVoltage());
        int permaWoodRing = TwoWindingsFormulas.getPermaWoodRing(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getDryType());

        double lvWindingLength = TwoWindingsFormulas.getWindingLength(windowHeight, lvEndClearance, permaWoodRing);

        lvDuctThickness = TwoWindingsFormulas.getDuctSize(lvWindingLength, lvDuctThickness,twoWindings.getDryType());

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
        double psi = 0;
        double rW = 0;
        double windingTemp = twoWindings.getWindingTemp();
        double ambientTemp = twoWindings.getAmbientTemp();
        boolean lvIsConductorRound = false;

        //Foil Winding Variables
        double lvEndStrip = 0;

        //Lv Helical Winding for Rectangular Core!! //24.01.25
        if (Objects.equals(twoWindings.getLvWindingType().toString(), "HELICAL")) {
            lvNumberOfLayers = twoWindings.getLvNumberOfLayers();//TwoWindingsFormulas.getNumberOfLayers(1);
            lvTurnsPerLayer = TwoWindingsFormulas.getLvTurnsPerLayer(lvTurnsPerPhase, lvNumberOfLayers);
            lvConductorCrossSection = TwoWindingsFormulas.getConductorCrossSection(lvCurrentPerPhase, twoWindings.getLVCurrentDensity());
            lvNumberOfConductors = TwoWindingsFormulas.getNumberOfConductors(lvConductorCrossSection, twoWindings.getLVConductorMaterial());
            lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection, lvNumberOfConductors);
            lvIsConductorRound = TwoWindingsFormulas.isConductorRound(lvConductorCrossSection);
            if(lvIsConductorRound){
                lvBreadth = lvIsConductorRound? NumberFormattingUtils.oneDigitDecimal(TwoWindingsFormulas.getDiameter(lvConductorCrossSection)) : 0.0;
                lvRevisedCondCrossSection = NumberFormattingUtils.twoDigitDecimal(Math.PI * Math.pow(lvBreadth,2) / 4);
                lvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(),
                        twoWindings.getLowVoltage(),
                        lvIsConductorRound,
                        twoWindings.getConnection(), lvIsEnamel, twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getDryType()
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
                lvEndClearance = windowHeight - lvWindingLength;
                lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase/(lvRevisedCondCrossSection * lvNumberOfConductors));

                //All the code above this line from window height should be iterated for the condFlag++ so that we get height less than 4mm.
                lvTotalCondCrossSection = TwoWindingsFormulas.getActualConductorXSec(lvRevisedCondCrossSection, lvNumberOfConductors);

                lvInterLayerInsulation = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn()
                        , lvTurnsPerLayer, lvConductorInsulation, lvIsEnamel, twoWindings.getInnerWindings().getInterLayerInsulation(), twoWindings.getDryType());

                if(lvNoOfDuct > (int) lvNumberOfLayers - 1){
                    lvNoOfDuct = (int) lvNumberOfLayers - 1;
                }
                lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, 1,
                        lvNumberOfLayers, lvInterLayerInsulation, lvFullDuct, lvDuctThickness, true);

                coreGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getCoilDimensions().getCoreGap(), twoWindings.getDryType());
                lvIdWidth = TwoWindingsFormulas.getID(coreWidth, coreGap);
                lvIdDepth = TwoWindingsFormulas.getID(coreDepth, coreGap);
                lvOdWidth = TwoWindingsFormulas.getOD(lvIdWidth, lvRadialThickness);
                lvOdDepth = TwoWindingsFormulas.getOD(lvIdDepth, lvRadialThickness);
                lvLmt = TwoWindingsFormulas.getRectLMT(lvIdWidth, lvIdDepth, lvOdWidth, lvOdDepth, lvRadialThickness, lvIsConductorRound);
                lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);
                lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                        , twoWindings.getLVConductorMaterial());
                lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth,lvHeight
                        ,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth,lvBreadthInsulated,lvHeight,lvTurnsPerLayer
                        ,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                        , lvNumberOfLayers, 0, lvIsConductorRound);
                lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss,(lvNoOfDuct * 2) + 2, lvWindingLength, lvTransposition, lvLmt, twoWindings.getDryType(), true);

                //Below is a loop done for getting the gradient within 14.5 by increasing the number of ducts b/w layers
                //TODO: We are assuming that we are not going to have a partial duct when there is a full duct and vise versa. WE NEED ADVICE FROM TVR.
                if(twoWindings.getInnerWindings().getDucts() == null){
                    while(lvGradient >= gradientLimit && lvPartialDuct < lvNumberOfLayers - 1){
                        lvPartialDuct = lvPartialDuct + 1;
                        lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                lvNumberOfLayers, lvInterLayerInsulation, 0, 0, true);
                        coreGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getCoilDimensions().getCoreGap(), twoWindings.getDryType());
                        lvIdWidth = TwoWindingsFormulas.getID(coreWidth, coreGap);
                        lvIdDepth = TwoWindingsFormulas.getID(coreDepth, coreGap);
                        lvOdWidth = TwoWindingsFormulas.getOD(lvIdWidth, lvRadialThickness);
                        lvOdDepth = TwoWindingsFormulas.getOD(lvIdDepth, lvRadialThickness + (lvPartialDuct * lvDuctThickness));
                        lvLmt = TwoWindingsFormulas.getRectLMT(lvIdWidth, lvIdDepth, lvOdWidth, lvOdDepth, lvRadialThickness, lvIsConductorRound);
                        lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                        lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);
                        lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                        lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                                , twoWindings.getLVConductorMaterial());
                        lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth
                                ,lvHeight,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                        lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                        lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth,lvBreadthInsulated,lvHeight,lvTurnsPerLayer
                                ,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                                , lvNumberOfLayers, 0, lvIsConductorRound);
                        lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight
                                ,twoWindings.getLVCurrentDensity(),lvStrayLoss);
                        lvGradient = TwoWindingsFormulas.getLvGradientWithPartialDuct(lvLoadLoss,(lvNoOfDuct * 2) + 2, lvWindingLength, lvLmt);
                    }
                    if(lvGradient >= gradientLimit){
                        lvPartialDuct = 0;
                        while(lvGradient >= gradientLimit && lvFullDuct < lvNumberOfLayers - 1){
                            lvFullDuct = lvFullDuct + 1;
                            lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                    lvNumberOfLayers, lvInterLayerInsulation, lvFullDuct, lvDuctThickness, true);
                            coreGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getCoilDimensions().getCoreGap(), twoWindings.getDryType());
                            lvIdWidth = TwoWindingsFormulas.getID(coreWidth, coreGap);
                            lvIdDepth = TwoWindingsFormulas.getID(coreDepth, coreGap);
                            lvOdWidth = TwoWindingsFormulas.getOD(lvIdWidth, lvRadialThickness);
                            lvOdDepth = TwoWindingsFormulas.getOD(lvIdDepth, lvRadialThickness );
                            lvLmt = TwoWindingsFormulas.getRectLMT(lvIdWidth, lvIdDepth, lvOdWidth, lvOdDepth, lvRadialThickness, lvIsConductorRound);
                            lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                            lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);
                            lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                            lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                                    , twoWindings.getLVConductorMaterial());
                            lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth
                                    ,lvHeight,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                            lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                            lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth,lvBreadthInsulated,lvHeight,lvTurnsPerLayer
                                    ,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                                    , lvNumberOfLayers, 0, lvIsConductorRound);
                            lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight
                                    ,twoWindings.getLVCurrentDensity(),lvStrayLoss);
                            lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss,(lvNoOfDuct * 2) + 2, lvWindingLength, lvTransposition, lvLmt, twoWindings.getDryType(), true);
                        }
                    }
                }

            }
            else {
                lvRadialParallelConductors = TwoWindingsFormulas.getRadialParallelConductors(lvNumberOfConductors, lvConductorFlag, twoWindings.getInnerWindings().getRadialParallelCond());

                lvAxialParallelConductors = TwoWindingsFormulas.getAxialParallelConductors(lvNumberOfConductors, lvRadialParallelConductors, twoWindings.getInnerWindings().getAxialParallelCond());
                lvNumberOfConductors = lvRadialParallelConductors * lvAxialParallelConductors;
                lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvConductorCrossSection,lvNumberOfConductors);
                lvTransposition = lvRadialParallelConductors > 1 ? 20 : 0;

                lvBreadthInsulated = TwoWindingsFormulas.getBi(lvWindingLength, lvTurnsPerLayer, lvAxialParallelConductors, lvTransposition, lvRadialParallelConductors);
                lvConductorInsulation = TwoWindingsFormulas.getConductorInsulation(twoWindings.getKVA(), twoWindings.getLowVoltage(), lvIsConductorRound, twoWindings.getConnection(), lvIsEnamel, twoWindings.getInnerWindings().getCondInsulation(), twoWindings.getDryType());

                lvBreadth = TwoWindingsFormulas.getBreadth(lvBreadthInsulated, lvConductorInsulation, lvRadialParallelConductors);
                lvBreadthInsulated = lvBreadth + lvConductorInsulation;

                if(twoWindings.getInnerWindings().getRadialParallelCond() == null && twoWindings.getInnerWindings().getAxialParallelCond() == null){
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
                    }
                }

                lvHeight = TwoWindingsFormulas.getHeight(lvCrossSecPerConductor, lvBreadth);

                lvHeightInsulated = TwoWindingsFormulas.getHeightInsulated(lvHeight, lvConductorInsulation);

                lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);

                lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / (lvRevisedCondCrossSection * lvNumberOfConductors));

                while (lVRevisedCurrentDensity > twoWindings.getLVCurrentDensity() && lvBreadth < 14.4) {
                    lvBreadth = lvBreadth + 0.1;
                    lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
                    lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / (lvRevisedCondCrossSection * lvNumberOfConductors));
                    lvBreadthInsulated = lvBreadth + lvConductorInsulation;
                    lvEndClearance1 = (int) Math.floor(windowHeight - (lvBreadthInsulated * (lvTurnsPerLayer + 1) * lvAxialParallelConductors));
                    // we can reduce the end clearance by 3 mm only as below that it will be a neck value so limited the difference to 3mm
                    // (ex: for 10mm end clearance we can reduce it up-to 7mm only.);
                    if ((lvEndClearance - lvEndClearance1) > 3 || lvBreadth > 14.4) {
                        break;
                    }
                    lvEndClearance = lvEndClearance1;
                }

                lvWindingLength = windowHeight - 2 * lvEndClearance - permaWoodRing;

                while (lVRevisedCurrentDensity > twoWindings.getLVCurrentDensity() && lvHeight < 4.5) {
                    lvHeight = lvHeight + 0.1;
                    lvRevisedCondCrossSection = TwoWindingsFormulas.getRevisedConductorCrossSection(lvBreadth, lvHeight);
                    lVRevisedCurrentDensity = NumberFormattingUtils.threeDigitDecimal(lvCurrentPerPhase / lvRevisedCondCrossSection);
                    lvHeightInsulated = TwoWindingsFormulas.getHeightInsulated(lvHeight, lvConductorInsulation);
                    if (lvHeight > 4.5) {
                        break;
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
                        , lvTurnsPerLayer, lvConductorInsulation, lvIsEnamel, twoWindings.getInnerWindings().getInterLayerInsulation(),twoWindings.getDryType());

                if(lvNoOfDuct > (int) lvNumberOfLayers - 1){
                    lvNoOfDuct = (int) lvNumberOfLayers - 1;
                }
                lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                        lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, true);

                coreGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getCoilDimensions().getCoreGap(), twoWindings.getDryType());

                lvWindingLength = NumberFormattingUtils.nextInteger(lvBreadthInsulated * (lvTurnsPerLayer + 1) * lvAxialParallelConductors);
                lvEndClearance = windowHeight - lvWindingLength - lvTransposition;
                lvIdWidth = TwoWindingsFormulas.getID(coreWidth, coreGap);
                lvIdDepth = TwoWindingsFormulas.getID(coreDepth, coreGap);
                lvOdWidth = TwoWindingsFormulas.getOD(lvIdWidth, lvRadialThickness);
                lvOdDepth = TwoWindingsFormulas.getOD(lvIdDepth, lvRadialThickness);
                lvLmt = TwoWindingsFormulas.getRectLMT(lvIdWidth, lvIdDepth, lvOdWidth, lvOdDepth, lvRadialThickness, lvIsConductorRound);

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
                if(twoWindings.getInnerWindings().getRadialParallelCond() == null && twoWindings.getInnerWindings().getAxialParallelCond() == null){
                    while (lvStrayLoss > 10) {
                        lvRadialParallelConductors = lvRadialParallelConductors + 1;
                        lvNumberOfConductors = lvAxialParallelConductors * lvRadialParallelConductors;
                        lvCrossSecPerConductor = TwoWindingsFormulas.getXSecPerConductor(lvTotalCondCrossSection, lvNumberOfConductors);
                        lvBreadthInsulated = TwoWindingsFormulas.getBi(lvWindingLength, lvTurnsPerLayer, lvAxialParallelConductors,lvTransposition, lvRadialParallelConductors);
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

                        lvInterLayerInsulation = TwoWindingsFormulas.getInterLayerInsulation(twoWindings.getVoltsPerTurn(), lvTurnsPerLayer, lvConductorInsulation, lvIsEnamel, twoWindings.getInnerWindings().getInterLayerInsulation(), twoWindings.getDryType());

                        lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                lvNumberOfLayers, lvInterLayerInsulation, lvNoOfDuct, lvDuctThickness, true);

                        lvWindingLength = NumberFormattingUtils.nextInteger(lvBreadthInsulated * (lvTurnsPerLayer + 1) * lvAxialParallelConductors);
                        lvEndClearance = windowHeight - lvWindingLength - lvTransposition;
                        lvIdWidth = TwoWindingsFormulas.getID(coreWidth, coreGap);
                        lvIdDepth = TwoWindingsFormulas.getID(coreDepth, coreGap);
                        lvOdWidth = TwoWindingsFormulas.getOD(lvIdWidth, lvRadialThickness);
                        lvOdDepth = TwoWindingsFormulas.getOD(lvIdDepth, lvRadialThickness);
                        lvLmt = TwoWindingsFormulas.getRectLMT(lvIdWidth, lvIdDepth, lvOdWidth, lvOdDepth, lvRadialThickness, lvIsConductorRound);

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
                if(twoWindings.getInnerWindings().getDucts() == null){
                    while (lvGradient >= 14.5 && lvPartialDuct < lvNumberOfLayers - 1) {
                        lvPartialDuct = lvPartialDuct + 1;
                        lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                lvNumberOfLayers, lvInterLayerInsulation, 0, 0, true);
                        coreGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getCoilDimensions().getCoreGap(), twoWindings.getDryType());
                        lvIdWidth = TwoWindingsFormulas.getID(coreWidth, coreGap);
                        lvIdDepth = TwoWindingsFormulas.getID(coreDepth, coreGap);
                        lvOdWidth = TwoWindingsFormulas.getOD(lvIdWidth, lvRadialThickness);
                        lvOdDepth = TwoWindingsFormulas.getOD(lvIdDepth, lvRadialThickness + (lvPartialDuct * lvDuctThickness));
                        lvLmt = TwoWindingsFormulas.getRectLMT(lvIdWidth, lvIdDepth, lvOdWidth, lvOdDepth, lvRadialThickness, lvIsConductorRound);
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
                    if(lvGradient >= gradientLimit){
                        lvPartialDuct = 0;
                        while(lvGradient >= gradientLimit && lvFullDuct < lvNumberOfLayers - 1){
                            lvFullDuct = lvFullDuct + 1;
                            lvRadialThickness = TwoWindingsFormulas.getRadialThickness(lvHeightInsulated, lvRadialParallelConductors,
                                    lvNumberOfLayers, lvInterLayerInsulation, lvFullDuct, lvDuctThickness, true);
                            coreGap = TwoWindingsFormulas.getCoreLvGap(twoWindings.getKVA(), twoWindings.getLowVoltage(), twoWindings.getCoilDimensions().getCoreGap(), twoWindings.getDryType());
                            lvIdWidth = TwoWindingsFormulas.getID(coreWidth, coreGap);
                            lvIdDepth = TwoWindingsFormulas.getID(coreDepth, coreGap);
                            lvOdWidth = TwoWindingsFormulas.getOD(lvIdWidth, lvRadialThickness);
                            lvOdDepth = TwoWindingsFormulas.getOD(lvIdDepth, lvRadialThickness );
                            lvLmt = TwoWindingsFormulas.getRectLMT(lvIdWidth, lvIdDepth, lvOdWidth, lvOdDepth, lvRadialThickness, lvIsConductorRound);
                            lvWireLength = TwoWindingsFormulas.getWireLength(lvLmt, lvTurnsPerPhase, 3, lvNumberOfConductors);
                            lvR75 = TwoWindingsFormulas.getR75(twoWindings.getLVConductorMaterial(), lvLmt, lvTurnsPerPhase,lvTotalCondCrossSection);
                            lvR26 = TwoWindingsFormulas.getR26(lvR75, twoWindings.getLVConductorMaterial());
                            lvBareWeight = TwoWindingsFormulas.getBareWeight(lvLmt,lvTurnsPerPhase, lvTotalCondCrossSection
                                    , twoWindings.getLVConductorMaterial());
                            lvInsulatedWeight = TwoWindingsFormulas.getInsulatedWeight(lvBreadthInsulated,lvHeightInsulated,lvBreadth
                                    ,lvHeight,twoWindings.getLVConductorMaterial(), lvBareWeight, lvIsEnamel);
                            lvProcurementWeight = TwoWindingsFormulas.getProcurementWeight(lvInsulatedWeight, lvNumberOfConductors);
                            lvStrayLoss = TwoWindingsFormulas.getStrayLoss(lvBreadth,lvBreadthInsulated,lvHeight,lvTurnsPerLayer
                                    ,lvRadialParallelConductors, lvAxialParallelConductors, lvConductorInsulation, twoWindings.getLVConductorMaterial()
                                    , lvNumberOfLayers, 0, lvIsConductorRound);
                            lvLoadLoss = TwoWindingsFormulas.getLoadLoss(twoWindings.getLVConductorMaterial(), lvBareWeight
                                    ,twoWindings.getLVCurrentDensity(),lvStrayLoss);
                            lvGradient = TwoWindingsFormulas.getLvGradient(lvLoadLoss,(lvNoOfDuct * 2) + 2, lvWindingLength, lvTransposition, lvLmt, twoWindings.getDryType(), true);
                        }
                    }
                }
            }
        }

            double lvHvGap = TwoWindingsFormulas.getLvHvGap(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection(), twoWindings.getCoilDimensions().getLVHVGap(), twoWindings.getDryType());
            //The formula.put are all written below here to avoid writing it multiple times.

            formula.put("lvVoltsPerPhase", lvVoltsPerPhase);
            formula.put("lvTurnsPerPhase", lvTurnsPerPhase);
            formula.put("revisedVoltsPerTurn", revisedVoltsPerTurn);
            formula.put("netArea", netArea);
            formula.put("grossArea", grossArea);
            formula.put("coreDiameter", coreDiameter);
            formula.put("coreWidth", coreWidth);
            formula.put("coreDepth", coreDepth);
            formula.put("coreFrame", coreFrame);
            formula.put("lvCurrentPerPhase", lvCurrentPerPhase);
            formula.put("windowHeight", windowHeight);
            formula.put("possibleWindowHeightMin", possibleWindowHeightMin);
            formula.put("possibleWindowHeightMax", possibleWindowHeightMax);
            formula.put("possibleWindowHeightRange", possibleWindowHeightRange);
            formula.put("lvEndClearance", lvEndClearance);
            formula.put("permaWoodRing", permaWoodRing);
            formula.put("lvWindingLength", lvWindingLength);
            formula.put("lvNumberOfLayers", lvNumberOfLayers);
            formula.put("lvTurnsPerLayer", lvTurnsPerLayer);
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
            formula.put("lvTransposition", lvTransposition);
            formula.put("lvRevisedCondCrossSection", lvRevisedCondCrossSection);
            formula.put("lvTotalCondCrossSection", lvTotalCondCrossSection);
            formula.put("lVRevisedCurrentDensity", lVRevisedCurrentDensity);
            formula.put("lvInterLayerInsulation", lvInterLayerInsulation);
            formula.put("lvRadialThickness", lvRadialThickness);
            formula.put("coreGap", coreGap);
            formula.put("lvIdWidth", lvIdWidth);
            formula.put("lvIdDepth", lvIdDepth);
            formula.put("lvOdWidth", lvOdWidth);
            formula.put("lvOdDepth", lvOdDepth);
            formula.put("lvLmt", lvLmt);
            formula.put("lvWireLength", lvWireLength);
            formula.put("lvR75", lvR75);
            formula.put("lvR26", lvR26);
            formula.put("lvBareWeight", lvBareWeight);
            formula.put("lvInsulatedWeight", lvInsulatedWeight);
            formula.put("lvProcurementWeight", lvProcurementWeight);
            formula.put("%lvStrayLoss", lvStrayLoss);
            formula.put("lvLoadLoss", lvLoadLoss);
            formula.put("lvGradient", lvGradient);
            formula.put("lvPartialDuct", lvPartialDuct);
            formula.put("lvFullDuct", lvFullDuct);
            formula.put("lvHvGap", lvHvGap);
            formula.put("lvNoOfDuct", lvNoOfDuct);
            formula.put("lvDuctThickness", lvDuctThickness);
            formula.put("lvNoOfSpacers", lvNoOfSpacers);
            formula.put("lvWidthOfSpacer", lvWidthOfSpacer);
            formula.put("v0", v0);
            formula.put("psi", psi);
            formula.put("rW", rW);

            return formula;
    }
}
