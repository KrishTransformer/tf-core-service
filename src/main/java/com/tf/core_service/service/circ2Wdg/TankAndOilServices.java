package com.tf.core_service.service.circ2Wdg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tf.core_service.formulas.twoWdg.TankFormulas;
import com.tf.core_service.model.twoWindings.TwoWindings;
import com.tf.core_service.utils.CommonFunctions;
import com.tf.core_service.utils.Constants;
import com.tf.core_service.utils.NumberFormattingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class TankAndOilServices {

    public Map<String, Object> calculateTankAndOil(TwoWindings twoWindings) throws JsonProcessingException{



        Map<String, Object> formula = new HashMap<>();
        Map<String, Object> lvWindings = twoWindings.getLvFormulas();
        Map<String, Object> hvWindings = twoWindings.getHvFormulas();

        int largestBlade = TankFormulas.getLargestBlade(twoWindings.getCore().getCoreDia());
        int yokeInsulation = TankFormulas.getYokeInsulation(twoWindings.getKVA());

        boolean isCSP = twoWindings.getIsCSP();

        int tankLength = TankFormulas.getTankLength(twoWindings.getCoilDimensions().getHVOD(), twoWindings.getCore().getCenDist(), twoWindings.getHighVoltage(), twoWindings.getKVA(), twoWindings.getIsOLTC());
        int tankWidth = TankFormulas.getTankWidth(twoWindings.getCoilDimensions().getHVOD(), twoWindings.getHighVoltage(), twoWindings.getKVA());
        int tankHeight = TankFormulas.getTankHeight(twoWindings.getCore().getLimbHt(), largestBlade, twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getIsOLTC(), twoWindings.getTapStepsPercent());
        double tankCapacity = TankFormulas.getTankCapacity(tankLength, tankWidth, tankHeight);
        double tankWeight = tankCapacity * 0.6;

        int connectionGap = TankFormulas.getConnectionGap(twoWindings.getHighVoltage());
        int wdgTankGap = TankFormulas.getWdgToTankGap(twoWindings.getHighVoltage(), twoWindings.getKVA());
        int topYokeCoverGap = TankFormulas.getTopYokeToCover(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getIsOLTC());

        double tankWallThickness = TankFormulas.getTankWallThickness(twoWindings.getKVA());
        double tankLidThickness = TankFormulas.getLidThickness(twoWindings.getKVA());
        double tankBottomThickness = TankFormulas.getTankBottomThickness(twoWindings.getKVA());
        double frameThickness = TankFormulas.getFrameThickness(twoWindings.getKVA());

        //Weights of all the materials
        double weightLvCond = CommonFunctions.toDouble(lvWindings.get("lvProcurementWeight"));
        double weightHvCond = CommonFunctions.toDouble(hvWindings.get("hvProcurementWeight"));
        double weightCore = NumberFormattingUtils.nextInteger(twoWindings.getCore().getCoreWeight() * 1.02);
        double lvConnectionWeight = TankFormulas.getConnectionWeight(CommonFunctions.toDouble(lvWindings.get("lvTotalCondCrossSection")), twoWindings.getLVConductorMaterial(), 300);
        double hvConnectionWeight = TankFormulas.getConnectionWeight(CommonFunctions.toDouble(hvWindings.get("hvTotalCondCrossSection")), twoWindings.getHVConductorMaterial(), 1000);
        double tapInsWeight = TankFormulas.getTapInsWeight(twoWindings.getOuterWindings().getBareWeight(), twoWindings.getOuterWindings().getInsulatedWeight(),
                CommonFunctions.toInteger(twoWindings.getHvFormulas().get("hvTurnsAtHighest")),CommonFunctions.toInteger(twoWindings.getHvFormulas().get("hvTurnsAtLowest")));
        int tapLeadWeight = TankFormulas.getTapLeadWeight(twoWindings.getOuterWindings().getCondCrossSec(), twoWindings.getHVConductorMaterial(),
                twoWindings.getIsOLTC(), twoWindings.getCore().getCenDist(), twoWindings.getCore().getLimbHt(),
                twoWindings.getCoilDimensions().getHVOD(), twoWindings.getCore().getCoreDia(), twoWindings.getTapStepsPositive(),
                twoWindings.getTapStepsNegative(), twoWindings.getOuterWindings().getCondInsulation());
        int totalConnectionWeight = NumberFormattingUtils.nextInteger(tapInsWeight + tapLeadWeight + lvConnectionWeight + hvConnectionWeight);
        double channelWeight = TankFormulas.getChannelWeight(largestBlade, (double) tankLength / 1000);
        double insulationWeight = TankFormulas.getInsulationWt(twoWindings.getKVA(), twoWindings.getHighVoltage(), twoWindings.getConnection());
        //TODO: the displacement volume will change depending on the density of the insulation. What if one is Enamel and the other is Paper?
        double totalConductorWeight = NumberFormattingUtils.nextInteger(weightLvCond + weightHvCond + lvConnectionWeight + hvConnectionWeight);

        //Displacement Oil Calculations for all the material.
        double volumeLvCond = twoWindings.getLVConductorMaterial().equals(Constants.COPPER)
                ? TankFormulas.displacementVolume(twoWindings.getInnerWindings().getBareWeight(), 8.89)
                : TankFormulas.displacementVolume(twoWindings.getInnerWindings().getBareWeight(), 2.703);
        double volumeHvCond = twoWindings.getLVConductorMaterial().equals(Constants.COPPER)
                ? TankFormulas.displacementVolume(twoWindings.getOuterWindings().getBareWeight(), 8.89)
                : TankFormulas.displacementVolume(twoWindings.getOuterWindings().getBareWeight(), 2.703);
        double volumeCore = TankFormulas.displacementVolume(weightCore, 7.65);
        double volumeLvConnectionWeight = twoWindings.getLVConductorMaterial().equals(Constants.COPPER)
                ? TankFormulas.displacementVolume(lvConnectionWeight, 8.89)
                : TankFormulas.displacementVolume(lvConnectionWeight, 2.703);
        double volumeHvConnectionWeight = twoWindings.getLVConductorMaterial().equals(Constants.COPPER)
                ? TankFormulas.displacementVolume(hvConnectionWeight, 8.89)
                : TankFormulas.displacementVolume(hvConnectionWeight, 2.703);
        double volumeConnectionWeight = NumberFormattingUtils.twoDigitDecimal(volumeLvConnectionWeight + volumeHvConnectionWeight);
        double volumeChannel = TankFormulas.displacementVolume(channelWeight, 7.85);
        double volumeInsulation = TankFormulas.displacementVolume(insulationWeight, 1);

        double displacementVolume = Math.floor(volumeCore + volumeLvCond + volumeHvCond + volumeConnectionWeight + volumeChannel + volumeInsulation);

        //Heat dissipated by TankWalls
        double heatDisByTankWalls = 0;
        double heatToBeDissipated = CommonFunctions.toDouble(hvWindings.get("kW55"));
        double topOilTemperature = 0;
        double radiatorArea = 0;
        int radiatorHeight = 0;
        int radiatorWidth = 0;
        int radiatorSection = 0;
        double areaPerFin = 0;
        int noOfFins = 0;
        int noOfRadiators = 0;
        double corrugationArea = TankFormulas.getCorrugationArea(CommonFunctions.toDouble(hvWindings.get("kW55")),twoWindings.getETransCostType());
        int corrugationSlitsOnLength = 0;
        int corrugationSlitsOnWidth = 0;
        int totalCorrugationSlits = 0;
        double pipeArea = 0;
        double pipeLength = 0;
        String coolingStatement = "";
        double oilInRadiators = 0;
        double totalRadiatorWeight = 0;
        Integer noOfFinsPerRadiator = 0;

        //This is where, we changes Oil Cooling Type
        if(Objects.equals(twoWindings.getERadiatorType().toString(), "RADIATOR")){
            log.info("Is Radiator");
            heatDisByTankWalls = TankFormulas.getHeatDisByTankWall(tankLength,tankWidth,tankHeight);
            heatToBeDissipated = heatToBeDissipated - heatDisByTankWalls;
            //All about Radiators (Fin type only)
            topOilTemperature = TankFormulas.getTopOilTemperature(twoWindings.getInnerWindings().getTempGradDegC(), twoWindings.getOuterWindings().getTempGradDegC());
            radiatorArea = TankFormulas.getRadiatorArea(heatToBeDissipated, topOilTemperature);
            radiatorHeight = TankFormulas.getRadiatorHeight(tankHeight, largestBlade, yokeInsulation);
            radiatorWidth = TankFormulas.getRadiatorWidth(radiatorHeight, null);
            areaPerFin = radiatorWidth * radiatorHeight * 2 * Math.pow(10, -6); //Heat radiates from 2 surfaces of radiators
            noOfFins = NumberFormattingUtils.nextEvenInteger(radiatorArea/areaPerFin);
            radiatorSection = TankFormulas.getRadiatorSection(noOfFins)[0];
            noOfRadiators = TankFormulas.getRadiatorSection(noOfFins)[1];
            noOfFins = TankFormulas.getRadiatorSection(noOfFins)[2]; //This is the revised no of fins corresponding to the radiator arrangement.
            oilInRadiators = NumberFormattingUtils.nextInteger(noOfFins * radiatorHeight * radiatorWidth * Math.pow(10,-4) * 0.1);
            totalRadiatorWeight = NumberFormattingUtils.nextInteger(TankFormulas.getTotalRadiatorWeight(radiatorHeight, radiatorWidth, radiatorSection, noOfRadiators));
            coolingStatement = "L X W = " + radiatorHeight + " X " + radiatorWidth + " : " + noOfRadiators + " X " + radiatorSection;
            noOfFinsPerRadiator = noOfFins / noOfRadiators;
        }
        else if (Objects.equals(twoWindings.getERadiatorType().toString(), "PIPES")) {
            log.info("Is Pipes");
            pipeArea = Math.PI * Math.pow(38.1, 2) / 4;
            pipeLength = NumberFormattingUtils.oneDigitDecimal(corrugationArea / pipeArea);
            oilInRadiators = NumberFormattingUtils.nextInteger(Math.pow(33.33, 2) * Math.PI / 4 * pipeLength * Math.pow(10,-5));
            totalRadiatorWeight = NumberFormattingUtils.nextInteger(279.61 * pipeLength * 7.85 * Math.pow(10, -6)); //279.61 = (pi/4) * 38.1^2 - 33.1^2
            coolingStatement = "38.1mm Pipe or  56.2 mm elliptic pipe of Length " + pipeLength + "Mtrs";

        }
        else if (Objects.equals(twoWindings.getERadiatorType().toString(), "CORRUGATION")) {
            log.info("Is Corrugated");
            corrugationSlitsOnLength = TankFormulas.getCorrugationSlits(tankLength);
            corrugationSlitsOnWidth = TankFormulas.getCorrugationSlits(tankWidth);
            totalCorrugationSlits = corrugationSlitsOnLength + corrugationSlitsOnWidth;
            areaPerFin = corrugationArea/totalCorrugationSlits;
            radiatorHeight = TankFormulas.getRadiatorHeight(tankHeight,largestBlade,yokeInsulation);
            radiatorWidth = TankFormulas.getDepthOfCorrugation(areaPerFin, radiatorHeight, totalCorrugationSlits);
            noOfFins = totalCorrugationSlits;
            oilInRadiators = NumberFormattingUtils.nextInteger(noOfFins * areaPerFin * 0.1);
            totalRadiatorWeight = NumberFormattingUtils.nextInteger(radiatorHeight * radiatorWidth * totalCorrugationSlits * 2 * 1.25 * 7.85 * Math.pow(10, -6));
            coolingStatement = "L X W = " + radiatorHeight + " X " + radiatorWidth + "\n" + "No. Corrugation on Length X 2 = " + corrugationSlitsOnLength + "\n" + "No. Corrugation on Width X 2 = " + corrugationSlitsOnWidth;
        }

        log.info(coolingStatement);
        // TODO: Here, we are implementing the OLTC only as an external instrument since, we are within 132KV. Once we go beyond, we might want to look into the tank dim.
        double[] oltcSpec = TankFormulas.getOltcSpec(twoWindings.getHighVoltage(), twoWindings.getTapStepsPositive(), twoWindings.getTapStepsNegative());
        double oltcWeight = oltcSpec[0];
        double oltcCurrent = oltcSpec[1];
        double oltcOil = oltcSpec[2];
        double oltcLength = oltcSpec[3];
        double oltcBreadth = oltcSpec[4];
        double oltcHeight = oltcSpec[5];
        //I have not added OLTC Make as we are not having OLTC internally.

        //Finding the total Oil required in the following. The result is found in liters
        double oilInTank = NumberFormattingUtils.nextInteger(tankCapacity - displacementVolume);

        double oilInConservator = 0;
        if(!isCSP){
            oilInConservator = TankFormulas.getConservatorOil(oilInTank, oilInRadiators);
        }
        double totalOil = oilInRadiators + oilInConservator + oilInTank;

        double conservatorCapacity = 0;
        double conservatorDia = 0;
        double conservatorLength = 0;
        if(!isCSP){
            //Conservator Design
            conservatorCapacity = TankFormulas.getConservatorCapacity(twoWindings.getKVA(), totalOil);
            conservatorDia = TankFormulas.getConservatorDia(conservatorCapacity);
            conservatorLength = TankFormulas.getConservatorLength(conservatorCapacity, conservatorDia);
        }

        //Weights of all other than the active part is given below
        double totalSteelWeight = NumberFormattingUtils.nextInteger(TankFormulas.getTotalSteelWeight(twoWindings.getHighVoltage(), twoWindings.getKVA(), twoWindings.getConnection()));

        //Net Oil (tank, radiator, conservator and OLTC)
        double netOil = twoWindings.getIsOLTC() ? totalOil + oltcOil : totalOil;
        totalOil = netOil;
        double oilWeight = NumberFormattingUtils.nextInteger(netOil * 0.89);

        //Bushing Sepcs
        int bushingHeight = twoWindings.getHighVoltage() >  11000 ? 420 : 300;
        double lvBushingVoltage = TankFormulas.getBushingVoltageAndHeight(twoWindings.getLowVoltage())[0];
        double hvBushingVoltage = TankFormulas.getBushingVoltageAndHeight(twoWindings.getHighVoltage())[0];
        double lvBushingCurrent = TankFormulas.getBushingCurrent(CommonFunctions.toDouble(twoWindings.lvFormulas.get("lvCurrentPerPhase")), twoWindings.getConnection(), Boolean.TRUE);
        double hvBushingCurrent = TankFormulas.getBushingCurrent(CommonFunctions.toDouble(twoWindings.hvFormulas.get("hvCurrentPerPhase")), twoWindings.getConnection(), Boolean.FALSE);
        double lvBushingHeight = TankFormulas.getBushingVoltageAndHeight(twoWindings.getLowVoltage())[1];
        double hvBushingHeight = TankFormulas.getBushingVoltageAndHeight(twoWindings.getHighVoltage())[1];

        //Other thing that contribute to the Overall Dims
        double conservatorHeight = bushingHeight + (conservatorDia * 0.5) + 70;
        int rollers = 0; //TODO: Has to be revised
        //We require lifting Lug's width that is projecting from the tank wall for the over-all dimension
        int liftingLugs = TankFormulas.getLiftingLugs(twoWindings.getKVA());

        int cspTankHeight = 0;
        if(isCSP){
            // calculated the required volume for 60 degree rise for various capacity. 100 mm was sufficient till 25000kVA, above needed 150 mm, verified. (19/12/2024 by TVR)
            cspTankHeight = twoWindings.getKVA() > 25000 ? 150 : 100;
            tankHeight = tankHeight + cspTankHeight;
            tankCapacity = TankFormulas.getTankCapacity(tankLength, tankWidth, tankHeight);
            tankWeight = tankCapacity * 0.6;
        }

        //Overall Dimensions
        //The Frame thickness is not considered here as the lifting lugs project beyond this.
        double overallLength = NumberFormattingUtils.nextInteger(tankLength + liftingLugs + conservatorDia + oltcBreadth);
        double overallWidth = NumberFormattingUtils.nextInteger(tankWidth + (((radiatorSection - 1 )* 50) + 100) * 2);
        //Overall Height = bushingHt + 1/2 * conservator dia + 100mm (breather) + tankHt + bottom same channel as inside.
        double overallHeight = NumberFormattingUtils.nextInteger(tankHeight + (conservatorDia /2) + hvBushingHeight + largestBlade + 100 + rollers);

        //Total Weight of Transformer
        double weightsOfActivePart = NumberFormattingUtils.nextInteger(weightCore + totalConductorWeight + channelWeight + insulationWeight);
        double weightOfTankAndAcc = NumberFormattingUtils.nextInteger(totalSteelWeight + oltcWeight + oilWeight + totalRadiatorWeight); //TODO: +bushingWeight
        double transformerWeight = NumberFormattingUtils.nextInteger(weightsOfActivePart + weightOfTankAndAcc);

        //Costing Details
        double lvCostPerKg = Objects.equals(twoWindings.getLVConductorMaterial(), Constants.COPPER) ? twoWindings.getCost().getCopperCostPerKg() :twoWindings.getCost().getAluminiumCostPerKg();
        double hvCostPerKg = Objects.equals(twoWindings.getHVConductorMaterial(), Constants.COPPER) ? twoWindings.getCost().getCopperCostPerKg() :twoWindings.getCost().getAluminiumCostPerKg();
        int lvConductorCost = (int) Math.ceil(lvCostPerKg * (weightLvCond + lvConnectionWeight));
        int hvConductorCost = (int) Math.ceil(hvCostPerKg * (weightHvCond + hvConnectionWeight));
        int conductorCost = lvConductorCost + hvConductorCost;
        int coreCost = (int) Math.ceil(twoWindings.getCost().getCoreCostPerKg() * weightCore);
        int insulationCost = (int) Math.ceil(twoWindings.getCost().getInsulationCostPerKg() * insulationWeight);
        int steelCost = (int) Math.ceil(twoWindings.getCost().getSteelCostPerKg() * totalSteelWeight);
        int oilCost = (int) Math.ceil(twoWindings.getCost().getOilCostPerKg() * totalOil);
        int radiatorCost = (int) Math.ceil(twoWindings.getCost().getRadiatorCostPerKg() * totalRadiatorWeight);
        int capitalCost = conductorCost + coreCost + insulationCost + steelCost + oilCost + radiatorCost;

        formula.put("tankLength", tankLength);
        formula.put("tankWidth", tankWidth);
        formula.put("tankHeight", tankHeight);
        formula.put("tankWeight", tankWeight);
        formula.put("wdgTankGap", wdgTankGap);
        formula.put("connectionGap", connectionGap);
        formula.put("topYokeCoverGap", topYokeCoverGap);
        formula.put("tankCapacity", tankCapacity);
        formula.put("tankWallThickness", tankWallThickness);
        formula.put("tankLidThickness", tankLidThickness);
        formula.put("tankBottomThickness", tankBottomThickness);
        formula.put("frameThickness", frameThickness);
        formula.put("weightCore", weightCore);
        formula.put("totalConductorWeight", totalConductorWeight);
        formula.put("channelWeight", channelWeight);
        formula.put("insulationWeight", insulationWeight);
        formula.put("lvConnectionWeight", lvConnectionWeight);
        formula.put("hvConnectionWeight", hvConnectionWeight);
        formula.put("tapInsWeight", tapInsWeight);
        formula.put("tapLeadWeight", tapLeadWeight);
        formula.put("totalConnectionWeight", totalConnectionWeight);
        formula.put("volumeCore", volumeCore);
        formula.put("volumeChannel", volumeChannel);
        formula.put("volumeInsulation", volumeInsulation);
        formula.put("volumeLvCond", volumeLvCond);
        formula.put("volumeHvCond", volumeHvCond);
        formula.put("volumeConnectionWeight", volumeConnectionWeight);
        formula.put("displacementVolume", displacementVolume);
        formula.put("radiator", radiatorHeight + " X " + radiatorWidth + ": " + radiatorSection +" X " + noOfRadiators);
        formula.put("noOfFins", noOfFins);
        formula.put("radiatorArea", radiatorArea);
        formula.put("noOfRadiators", noOfRadiators);
        formula.put("noOfFinsPerRadiator", noOfFinsPerRadiator);
        formula.put("totalRadiatorWeight", totalRadiatorWeight);
        formula.put("conservatorDia", conservatorDia);
        formula.put("conservatorLength", conservatorLength);
        formula.put("conservatorCapacity", conservatorCapacity);
        formula.put("oilInTank", oilInTank);
        formula.put("oilInRadiators", oilInRadiators);
        formula.put("oilInConservator", oilInConservator);
        formula.put("totalOil", totalOil);
        formula.put("oilWeight", oilWeight);
        formula.put("totalSteelWeight", totalSteelWeight);
        formula.put("coolingStatement", coolingStatement);
        formula.put("radiatorHeight", radiatorHeight);
        formula.put("radiatorWidth", radiatorWidth);
        formula.put("radiatorSection", radiatorSection);

        formula.put("lvBushingVoltage", lvBushingVoltage);
        formula.put("lvBushingCurrent", lvBushingCurrent);
        formula.put("lvBushingHeight", lvBushingHeight);
        formula.put("hvBushingVoltage", hvBushingVoltage);
        formula.put("hvBushingCurrent", hvBushingCurrent);
        formula.put("hvBushingHeight", hvBushingHeight);

        formula.put("overallLength", overallLength);
        formula.put("overallWidth", overallWidth);
        formula.put("overallHeight", overallHeight);
        formula.put("weightsOfActivePart", weightsOfActivePart);
        formula.put("weightOfTankAndAcc", weightOfTankAndAcc);
        formula.put("transformerWeight", transformerWeight);

        //Costs
        formula.put("conductorCost", conductorCost);
        formula.put("coreCost", coreCost);
        formula.put("insulationCost", insulationCost);
        formula.put("steelCost", steelCost);
        formula.put("oilCost", oilCost);
        formula.put("radiatorCost", radiatorCost);
        formula.put("capitalCost", capitalCost);

        return formula;
    }
}