package com.tf.core_service.formulas.twoWdg;

import com.tf.core_service.model.twoWindings.DryTempClass;
import com.tf.core_service.model.twoWindings.ETransCostType;
import com.tf.core_service.model.twoWindings.EVectorGroup;
import com.tf.core_service.model.twoWindings.EWindingType;
import com.tf.core_service.utils.Constants;
import com.tf.core_service.utils.NumberFormattingUtils;

import java.util.*;
import java.util.stream.DoubleStream;

public class TwoWindingsFormulas {

    public static int getFrequency(Integer frequency) {
        return Objects.requireNonNullElse(frequency, 50);
    }

    public static double getBuildFactor(double KVA, String coreType, Double buildFactor) {
        double buildFact = 0;
        if (buildFactor != null && buildFactor != 0) {
            return buildFactor;
        } else {
            if(coreType == "PRIME"){
                if(KVA <= 500){
                     buildFact = 1.3;
                }else if(KVA > 500 && KVA <= 10000){
                    buildFact = 1.25;
                }else{buildFact = 1.2;}
            } else if (coreType == "STEP_LAP") {
                if(KVA <= 500){
                    buildFact = 1.25;
                }else if(KVA > 500 && KVA <= 10000){
                    buildFact = 1.2;
                }else{buildFact = 1.1;}
            }
            return  buildFact;
        }
    }

    public static double getFluxDensity(Double fluxDensity, boolean dryType){
        if (fluxDensity != null && fluxDensity != 0) {
            return fluxDensity;
        } else {
            return dryType ? 1.6 : 1.73333;
        }
    }

    public static String getCoreMaterial(String coreMaterial) {
        if (coreMaterial != null) {
            return coreMaterial;
        } else {
            return "NipM4";
        }
    }

    public static String getCoreType(String coreType) {
        if (coreType != null) {
            return coreType;
        } else {
            return "PRIME";
        }
    }

    public static int getLowVoltage(Integer lowVoltage) {
        if (lowVoltage != null) {
            return lowVoltage;
        }
        return 433;
    }

    public static int getHighVoltage(Integer highVoltage) {
        if (highVoltage != null) {
            return highVoltage;
        }
        return 11000;
    }

    public static EVectorGroup getVectorGroup(EVectorGroup vectorGroup) {
        if (vectorGroup != null) {
            return vectorGroup;
        }
        return EVectorGroup.Dyn11;
    }

    public static double getKValue(double KVA, Double inputKValue, String lvConductorMaterial, ETransCostType eTransCostType) {
        if (inputKValue != null && inputKValue != 0.0) {
            return inputKValue;
        }
        double kValue = 0.0;
        if (eTransCostType == ETransCostType.ECONOMIC) {
            if (lvConductorMaterial.equals(Constants.COPPER)) {
                kValue = DoubleStream.of(0.45, 0.6).average().getAsDouble();
            } else if (lvConductorMaterial.equals(Constants.ALUMINIUM)) {
                kValue = DoubleStream.of(0.3, 0.4).average().getAsDouble();
            }
        } else if (eTransCostType == ETransCostType.ENERGY_EFFICIENT) {
            if (lvConductorMaterial.equals(Constants.COPPER)) {
                kValue = DoubleStream.of(0.6, 0.75).average().getAsDouble();
            } else if (lvConductorMaterial.equals(Constants.ALUMINIUM)) {
                kValue = DoubleStream.of(0.4, 0.5).average().getAsDouble();
            }
        }
        if(KVA > 1950){
            kValue = 0.442;
        }
        return kValue;
    }

    public static double getCurrentDensity(String conductorMaterial, ETransCostType eTransCostType, boolean dryType, DryTempClass dryTempClass, boolean isLv, Double currentDensity) {
        if (currentDensity != null){
            return currentDensity;
        }else {
            double currentDensityValue = 0.0;
            if (conductorMaterial.equals(Constants.COPPER) && eTransCostType.equals(ETransCostType.ECONOMIC)) {
                currentDensityValue = 4.24;
            } else if (conductorMaterial.equals(Constants.COPPER) && eTransCostType.equals(ETransCostType.ENERGY_EFFICIENT)) {
                currentDensityValue = 1.5;
            }else if(conductorMaterial.equals(Constants.ALUMINIUM) && eTransCostType.equals(ETransCostType.ECONOMIC)) {
                currentDensityValue = 2.37;
            }else if(conductorMaterial.equals(Constants.ALUMINIUM) && eTransCostType.equals(ETransCostType.ENERGY_EFFICIENT)) {
                currentDensityValue = 0.6;
            }

            if (dryTempClass.equals(DryTempClass.CLASS_B)) {

                if (conductorMaterial.equals(Constants.COPPER)) {
                    if (isLv) return 1.8;
                    else return 2.2;

                } else if (conductorMaterial.equals(Constants.ALUMINIUM)) {
                    if (isLv) return 1.4;
                    else return 1.6;
                }
            }

            else if (dryTempClass.equals(DryTempClass.CLASS_F)) {

                if (conductorMaterial.equals(Constants.COPPER)) {
                    if (isLv) return 2.2;
                    else return 2.5;

                } else if (conductorMaterial.equals(Constants.ALUMINIUM)) {
                    if (isLv) return 1.6;
                    else return 1.8;
                }
            }

            else if (dryTempClass.equals(DryTempClass.CLASS_H)) {

                if (conductorMaterial.equals(Constants.COPPER)) {
                    if (isLv) return 2.4;
                    else return 2.8;

                } else if (conductorMaterial.equals(Constants.ALUMINIUM)) {
                    if (isLv) return 1.8;
                    else return 2.0;
                }
            }
            return currentDensityValue;
        }
    }

    public static double getVoltsPerTurn(double k, double kVA) {
        return NumberFormattingUtils.threeDigitDecimal(k * Math.sqrt(kVA));
    }

    public static double getLvVoltsPerPhase(Double voltageValue, EVectorGroup vectorGroup) {
        double voltsPerPhase = Objects.equals(vectorGroup.name().charAt(1), 'y') ? voltageValue / Math.sqrt(3) : voltageValue;
        return NumberFormattingUtils.twoDigitDecimal(voltsPerPhase);
    }

    public static double getHvVoltsPerPhase(Double voltageValue, EVectorGroup vectorGroup) {
        double voltsPerPhase = Objects.equals(vectorGroup.name().charAt(0), 'Y') ? voltageValue / Math.sqrt(3) : voltageValue;
        return NumberFormattingUtils.twoDigitDecimal(voltsPerPhase);
    }

    public static int getTurnsPerPhase(double voltsPerPhase, double voltsPerTurn, Double turnsFromUser, EVectorGroup eVectorGroup, Boolean isLV) {
        if(turnsFromUser != null){
            return (int) Math.ceil(turnsFromUser);
        }else {
            return (int) Math.ceil(voltsPerPhase/voltsPerTurn);
        }
    }

    public static double getRevisedVoltsPerTurn(Double voltsPerPhase, Double lvTurnsPerPhase, EVectorGroup eVectorGroup){
        return NumberFormattingUtils.threeDigitDecimal(voltsPerPhase/lvTurnsPerPhase);
    }

    public static double getReversedRevisedVoltsPerTurn(Double netArea, Integer frequency, Double fluxDensity ){
        return NumberFormattingUtils.threeDigitDecimal(netArea * 4.44 * frequency * fluxDensity * Math.pow(10, -6));
    }

    public static double getNetArea(double revisedVoltsPerTurn, double frequency, double fluxDensity) {
        return NumberFormattingUtils.nextInteger(revisedVoltsPerTurn / (4.44 * frequency * fluxDensity * Math.pow(10, -6)));
    }

    public static double getCoreDiameter(double area, Integer coreDiaFromUser) {
        if (coreDiaFromUser != null){
            return  coreDiaFromUser;
        }
        return Math.sqrt(area / (Math.PI / 4));
    }

    public  static double[] getPossibleKValue(double KVA, String lvConductorMaterial, ETransCostType eTransCostType){
        double[] possibleKValueRange = new double[2];
//        if (eTransCostType == ETransCostType.ECONOMIC) {
//            if (lvConductorMaterial.equals(Constants.COPPER)) {
//                possibleKValueRange = new double[]{0.4, 0.65}; //The init value was 0.45 and 0.6
//            } else if (lvConductorMaterial.equals(Constants.ALUMINIUM)) {
//                possibleKValueRange = new double[]{0.25, 0.45}; //The init value was 0.3 and 0.4
//            }
//        } else if (eTransCostType == ETransCostType.ENERGY_EFFICIENT) {
//            if (lvConductorMaterial.equals(Constants.COPPER)) {
//                possibleKValueRange = new double[]{0.55, 0.8}; //The init value was 0.6 and 0.75
//            } else if (lvConductorMaterial.equals(Constants.ALUMINIUM)) {
//                possibleKValueRange = new double[]{0.35, 0.55}; //The init value was 0.4 and 0.5
//            }
//        }
        if (lvConductorMaterial.equals(Constants.COPPER)) {
            possibleKValueRange = new double[]{0.4, 0.8};
        } else if (lvConductorMaterial.equals(Constants.ALUMINIUM)) {
            possibleKValueRange = new double[]{0.25, 0.55};
        }

        return possibleKValueRange;
    }

    public static double getDiameter(double area) {
        return Math.sqrt(area / (Math.PI / 4));
    }

    public static int getHRGCoreDia(double KVA, boolean dryType){

        int coreDia = 0;
        if (dryType) {
            if (KVA <= 2) {
                coreDia = 55;
            } else if (KVA <= 5) {
                coreDia = 62;
            } else if (KVA <= 8) {
                coreDia = 68;
            } else if (KVA <= 10) {
                coreDia = 72;
            } else if (KVA <= 16) {
                coreDia = 82;
            } else if (KVA <= 25) {
                coreDia = 90;
            } else if (KVA <= 40) {
                coreDia = 102;
            } else if (KVA <= 50) {
                coreDia = 107;
            } else if (KVA <= 63) {
                coreDia = 114;
            } else if (KVA <= 100) {
                coreDia = 135;
            } else if (KVA <= 150) {
                coreDia = 145;
            } else if (KVA <= 180) {
                coreDia = 150;
            } else if (KVA <= 300) {
                coreDia = 162;
            } else if (KVA <= 450) {
                coreDia = 172;
            } else if (KVA <= 500) {
                coreDia = 210;
            } else if (KVA <= 1500) {
                coreDia = 231;
            } else if (KVA <= 1800) {
                coreDia = 260;
            } else if (KVA <= 1950) {
                coreDia = 295;
            }
        }
        else {
            if (KVA <= 2) {
                coreDia = 46;
            } else if (KVA <= 5) {
                coreDia = 52;
            } else if (KVA <= 8) {
                coreDia = 56;
            } else if (KVA <= 10) {
                coreDia = 60;
            } else if (KVA <= 16) {
                coreDia = 68;
            } else if (KVA <= 25) {
                coreDia = 75;
            } else if (KVA <= 40) {
                coreDia = 85;
            } else if (KVA <= 50) {
                coreDia = 90;
            } else if (KVA <= 63) {
                coreDia = 95;
            } else if (KVA <= 100) {
                coreDia = 112;
            } else if (KVA <= 150) {
                coreDia = 121;
            } else if (KVA <= 180) {
                coreDia = 125;
            } else if (KVA <= 300) {
                coreDia = 135;
            } else if (KVA <= 450) {
                coreDia = 143;
            } else if (KVA <= 500) {
                coreDia = 175;
            } else if (KVA <= 1500) {
                coreDia = 192;
            } else if (KVA <= 1800) {
                coreDia = 216;
            } else if (KVA <= 1950) {
                coreDia = 245;
            }

        }


            return coreDia;
    }

    public static double getGrossCoreArea(Double netCoreArea, Double dia, Integer coreDiaFromUser){
        if (coreDiaFromUser != null) {
            return Math.ceil(Math.pow(coreDiaFromUser, 2) * Math.PI / 4);
        }
        double factor = 1;
        if(dia <= 100){
            factor = 0.88;
        } else if (dia > 100 && dia <= 150) {
            factor = 0.9;
        }else if (dia > 150 && dia <= 200) {
            factor = 0.91;
        }else if (dia > 200 && dia <= 250) {
            factor = 0.92;
        }else if (dia > 250) {
            factor = 0.93;
        }
        return NumberFormattingUtils.next5or0Integer(netCoreArea / factor);
    }

    public static double getRevisedNetArea(double grossArea, double dia){
        double factor = 1;
        if(dia <= 100){
            factor = 0.88;
        } else if (dia > 100 && dia <= 150) {
            factor = 0.9;
        }else if (dia > 150 && dia <= 200) {
            factor = 0.91;
        }else if (dia > 200 && dia <= 250) {
            factor = 0.92;
        }else if (dia > 250) {
            factor = 0.93;
        }
        return NumberFormattingUtils.next5or0Integer(grossArea * factor);
    }

    public static double getCurrentPerPhase(double kVA, double voltsPerPhase) {

        return NumberFormattingUtils.twoDigitDecimal((kVA * 1000) / (3 * voltsPerPhase));
    }

    public static Integer getWindowHeight(double kValue, Integer dia, String conductorMaterial, Double givenWindowHeight, Boolean dryType) {
        if (givenWindowHeight != null) {
            return (int) Math.ceil(givenWindowHeight);
        } else {
            double windowHeightFactor = 1;
            if(conductorMaterial.equals(Constants.COPPER)){
                windowHeightFactor = (0.8 / kValue) + 0.5;
            }else{
                windowHeightFactor = (1.5 / kValue);
            }
            if(dryType){
                if(conductorMaterial.equals(Constants.COPPER)){
                    windowHeightFactor = (-3.33 * kValue) + 5.17;
                }else{
                    windowHeightFactor = (1.5 / kValue);
                }
            }
            //The above equation is obtained by solving inverse non-liner equation (k= a *f + b) (a = 1.6, b = -0.5)
            // for dryType the range is 2.5 to 3.5 times core dia based on k Value range for copper (0.8 to 0.5)
            return NumberFormattingUtils.nextInteger(windowHeightFactor * dia);
        }
    }

    public static Double getEndClearance(double KVA, double voltage, EVectorGroup eVectorGroup, Double endClr, Boolean dryType) {
        int endClearance = 8 * 2;

        if(voltage <= 1100){
            if(KVA <= 25){
                endClearance = 8 * 2;
            } else if (KVA > 25 && KVA <= 100) {
                endClearance = 10 * 2;
            }else if (KVA > 100 ) {
                endClearance = 15 * 2;
            }
        }
        else if (voltage > 1100 && voltage <= 11000) {
            if(KVA <= 25){
                endClearance = 20 * 2;
            } else if (KVA > 25 && KVA <= 1000) {
                endClearance = 25 * 2;
            }else if (KVA > 1000 ) {
                endClearance = 30 * 2;
            }
        }
        else if (voltage > 11000 && voltage <= 22000) {
            if(KVA <= 100){
                endClearance = 30 * 2;
            } else if (KVA > 100 ) {
                endClearance = 35 * 2;
            }
        }
        else if (voltage > 22000 && voltage <= 33000) {
            if(KVA <= 100){
                endClearance = 35 * 2;
            } else if (KVA > 100) {
                endClearance = 45 * 2;
            }

        }
        else if (voltage > 33000 && voltage <= 66000) {
            if(Objects.equals(eVectorGroup.name().charAt(1), 'y') || Objects.equals(eVectorGroup.name().charAt(0), 'Y')){
                endClearance = 80; //Top
                if(KVA <= 500){
                    endClearance = endClearance + 35;
                } else if (KVA > 500 && KVA <= 2500) {
                    endClearance = endClearance + 50;
                } else{
                    endClearance = endClearance + 60;
                }
            }else{
                endClearance = 80 * 2;
            }
        }
        else if (voltage > 66000 && voltage <= 132000) {
            if(Objects.equals(eVectorGroup.name().charAt(1), 'y') || Objects.equals(eVectorGroup.name().charAt(0), 'Y')){
                endClearance = 115; //Top
                if(KVA <= 500){
                    endClearance = endClearance + 35;
                } else if (KVA > 500 && KVA <= 2500) {
                    endClearance = endClearance + 50;
                } else{
                    endClearance = endClearance + 60;
                }
            }else{
                endClearance = 115 * 2;
            }
        }
        else if (voltage > 132000) {
            if(Objects.equals(eVectorGroup.name().charAt(1), 'y') || Objects.equals(eVectorGroup.name().charAt(0), 'Y')){
                endClearance = 115; //Top
                if(KVA <= 500){
                    endClearance = endClearance + 35;
                } else if (KVA > 500 && KVA <= 2500) {
                    endClearance = endClearance + 50;
                } else{
                    endClearance = endClearance + 60;
                }
            }
            else{
                endClearance = 115 * 2;
            }
        }
        if(dryType){
            if (voltage <= 1100){
                if(KVA <= 100){endClearance = 2 * 40;} else {endClearance = 2 * 60;}
            } else if (voltage <= 11000){
                endClearance = 2 * 140;
            } else if (voltage <= 22000){
                endClearance = 2 * 200;
            } else if (voltage <= 33000){
                endClearance = 2 * 240;
            }

        }

        if(endClr != null && endClr >= 0.85 * endClearance){
            return endClr;
        }else{
            return (double) endClearance;
        }
    }

    public static Double getLvEndClearance(double KVA, EVectorGroup eVectorGroup, Double endClr, Boolean dryType, Double lowVoltage, Double highVoltage){
        double voltage = (highVoltage > 11000 && lowVoltage > 1100) ? highVoltage : lowVoltage;
        double endClearance = TwoWindingsFormulas.getEndClearance(KVA,voltage,eVectorGroup,endClr, dryType);

        int hiloGap = 0;
        if(dryType){
            if(highVoltage <= 1100){hiloGap = 15;}
            else if (highVoltage <= 11000){hiloGap = 31;}
            else if (highVoltage <= 22000){hiloGap = 58;}
            else if (highVoltage <= 33000){hiloGap = 90;}
            if(highVoltage <= 1100){
                return endClearance;
            }
            return  endClearance -  2 * hiloGap;
        } else{
            return endClearance;
        }



    }

    public static Integer getPermaWoodRing(double KVA, double voltage, Boolean dryType){
        int permaWoodRing = 0;
        if (voltage >= 11000){
            if(KVA <= 5000){
                permaWoodRing = 20;
            } else if (KVA > 5000 && KVA <= 20000) {
                permaWoodRing = 25;
            } else if (KVA > 20000 && KVA <= 50000) {
                permaWoodRing = 35;
            } else if (KVA > 50000) {
                permaWoodRing = 50;
            }
            if(dryType){permaWoodRing = 0;}
        }else permaWoodRing = 0;
        return permaWoodRing;
    }

    public static double getWindingLength(double windowHeight, double endClearance, int permaWoodRing) {
        return NumberFormattingUtils.nextInteger(windowHeight - endClearance - permaWoodRing);
    }

    public static double getDiscWindingLength(double breadth, double condInsulation, double insulationCompression, Integer NoOfDiscs, double DiscDuctSize){
        Double term1 = (breadth + (condInsulation * insulationCompression)) * NoOfDiscs;
        double term2 = (DiscDuctSize * insulationCompression) * (NoOfDiscs - 1);
        return NumberFormattingUtils.nextInteger(term1 + term2);
    }

    public static Double getLvTurnsPerLayer(double lvTurnsPerPhase , double noOfLayers) {
        return lvTurnsPerPhase / noOfLayers;
        // TODO: Write the Turns per Layer properly like in 11 x 3 + 10 format.
    }

    public static Integer getNumberOfConductors(double conductorXSection, String conductorMaterial){
        int roughNoOfConductors = 1;
        int noOfConductors = 1;
        if(conductorMaterial.equals(Constants.COPPER)){
            roughNoOfConductors = (int) Math.ceil(conductorXSection/51.14);
        }else roughNoOfConductors = (int) Math.ceil(conductorXSection/60);

        switch (roughNoOfConductors){
            case 2:
                noOfConductors = 2;
                break;
            case 3:
                noOfConductors = 3;
                break;
            case 4:
                noOfConductors = 4;
                break;
            case 5:
                noOfConductors = 5;
                break;
            case 6:
                noOfConductors = 6;
                break;
            case 7, 8:
                noOfConductors = 8;
                break;
            case 9, 10:
                noOfConductors = 10;
                break;
            case 11, 12:
                noOfConductors = 12;
                break;
            case 13, 14, 15:
                noOfConductors = 14;
                break;
            case 16, 17:
                noOfConductors = 16;
                break;
            case 18, 19:
                noOfConductors = 18;
                break;
            case 20, 21, 22:
                noOfConductors = 20;
                break;
            case 23, 24, 25:
                noOfConductors = 24;
                break;
            case 26, 27, 28, 29:
                noOfConductors = 28;
                break;
            case 30 ,31, 32, 33:
                noOfConductors = 32;
                break;
            case 34, 35, 36:
                noOfConductors = 36;
                break;
            default: noOfConductors = 1;
        }

        return noOfConductors;
    }

    public static double getConductorCrossSection(double currentPerPhase, double currentDensity) {
        return NumberFormattingUtils.threeDigitDecimal(currentPerPhase / currentDensity);
    }

    public static boolean isConductorRound(double conductorXSec){
        return conductorXSec <= 7;
    }

    public static double getXSecPerConductor(double totalCrossSection, int noOfConductors){
        return NumberFormattingUtils.twoDigitDecimal(totalCrossSection/noOfConductors);
    }

    public static Integer getAxialParallelConductors(int noOfConductors, int radialParallelConductors, Integer axialParallelCond) {
        if(axialParallelCond != null){
            return axialParallelCond;
        }else{
            return NumberFormattingUtils.nextInteger((double) noOfConductors /radialParallelConductors);
        }
    }

    public static Integer getRadialParallelConductors(int noOfConductors, int conductorFlag, Integer radialParallelCond){
        int radialParallelConductors = 1;
        if (noOfConductors == 1){
            radialParallelConductors = 1;
        }else if (noOfConductors == 2) {
            radialParallelConductors = (conductorFlag == 0) ? 1: 2;
        }else if (noOfConductors == 3) {
            radialParallelConductors = (conductorFlag == 0) ? 1: 3;
        }else if (noOfConductors == 4) {
            radialParallelConductors = (conductorFlag == 0) ? 1: 2;
        }else if (noOfConductors == 5) {
            radialParallelConductors = 1;
        }else if (noOfConductors == 6) {
            if(conductorFlag == 0){radialParallelConductors = 1;}
            else if (conductorFlag == 1) {radialParallelConductors = 2;}
            else if (conductorFlag == 2) {radialParallelConductors = 3;}
        }else if (noOfConductors == 8) {
            radialParallelConductors = (conductorFlag == 0) ? 1: 2;
        }else if (noOfConductors == 10) {
            radialParallelConductors = (conductorFlag == 0) ? 1: 2;
        }else if (noOfConductors == 12) {
            if(conductorFlag == 0){radialParallelConductors = 1;}
            else if (conductorFlag == 1) {radialParallelConductors = 2;}
            else if (conductorFlag == 2) {radialParallelConductors = 3;}
        }else if (noOfConductors == 14) {
            radialParallelConductors = (conductorFlag == 0) ? 1: 2;
        }else if (noOfConductors == 16) {
            if(conductorFlag == 0){radialParallelConductors = 1;}
            else if (conductorFlag == 1) {radialParallelConductors = 2;}
            else if (conductorFlag == 2) {radialParallelConductors = 4;}
        }else if (noOfConductors == 18) {
            if (conductorFlag == 0) {radialParallelConductors = 1;}
            else if (conductorFlag == 1) {radialParallelConductors = 2;}
            else if (conductorFlag == 2) {radialParallelConductors = 3;}
        }else if (noOfConductors == 20) {
            if (conductorFlag == 0) {radialParallelConductors = 2;}
            else if (conductorFlag == 1) {radialParallelConductors = 4;}
        }else if (noOfConductors == 24) {
            radialParallelConductors = 4;
        }else if (noOfConductors == 28) {
            radialParallelConductors = 4;
        }else if (noOfConductors == 30) {
            radialParallelConductors = 5;
        }else if (noOfConductors == 32) {
            radialParallelConductors = 4;
        }else if (noOfConductors == 36) {
            radialParallelConductors = conductorFlag == 0? 4 : 6;
        }
        if(radialParallelCond != null){
            return radialParallelCond;
        }else{
            return radialParallelConductors;
        }
    }

    public static double getBi(double windingLength, double lvTurnsPerLayer, int axialParallelConductors, int transposition, int radialParallelConductors
    ) {
        double bi = (windingLength - transposition) / ((lvTurnsPerLayer + 1) * axialParallelConductors);
        if(radialParallelConductors > 1){
            return NumberFormattingUtils.twoDigitDecimal(bi);
        } else {
            return   NumberFormattingUtils.twoDigitDecimalfloor(bi);
        }

    }

    public static double getConductorInsulation(double KVA, double voltage, boolean isConductorRound, EVectorGroup vectorGroup, Boolean isEnamel,  Double condIns, boolean dryType) {
        double conductorInsulation = 0.2;
        //If the conductor is Round
        if(isConductorRound){
            if(voltage <= 11000){
                if(KVA <= 1000){
                    conductorInsulation = 0.2;
                } else if (KVA > 1000 ) {
                    conductorInsulation = 0.22;
                }
            }else if (voltage > 11000 && voltage <= 22000) {
                conductorInsulation =0.25;
            }else if (voltage > 22000 && voltage <= 33000) {
                conductorInsulation = 0.3;
            }
        }
        //If the Conductor is Strip
        else {
            if(voltage <= 11000){
                if(KVA <= 100){
                    conductorInsulation = 0.3;
                } else if (KVA > 100 && KVA<= 1000 ) {
                    conductorInsulation = 0.35;
                }else if (KVA > 1000) {
                    conductorInsulation = 0.4;
                }
            }else if (voltage > 11000 && voltage <= 33000) {
                if(KVA <= 1000){
                    conductorInsulation = 0.5;
                } else if (KVA > 1000 ) {
                    conductorInsulation = 0.6;
                }
            }else if (voltage > 33000 && voltage <= 66000) {
                conductorInsulation = 0.8;
            }else if (voltage > 66000 && voltage <= 132000) {
                conductorInsulation = Objects.equals(vectorGroup.name().charAt(0), 'D') ? 1.2 : 1;
            }
        }
        if(isEnamel){
            if(voltage <= 11000){
                conductorInsulation = 0.05;
            } else if (voltage <= 22000) {
                conductorInsulation = 0.1;
            } else if (voltage <= 33000) {
                conductorInsulation = 0.13;
            }
        }
        if(dryType){conductorInsulation = 0.1;}
        if(condIns != null && condIns >= conductorInsulation * 0.9){
            return condIns;
        }

        return  conductorInsulation;
    }

    public static double getBreadth(double breadthInsulated, double ConductorInsulation, int radialParallelConductors) {
        double breadth = breadthInsulated - ConductorInsulation;
        if(radialParallelConductors > 1){
            return  NumberFormattingUtils.oneDigitDecimal(breadth);
        } else {
            return  NumberFormattingUtils.oneDigitDecimalFloor(breadth);
        }

    }

    public static double getHeight(double minConductorCrossSection, double breadth) {
        return  NumberFormattingUtils.oneDigitDecimal((minConductorCrossSection + 0.86) / breadth);
    }

    public static double getHeightInsulated(double height, double insulation) {
        return  NumberFormattingUtils.oneDigitDecimal(height + insulation);
    }

    public static double getRoundCondDia(double condXSec, Double condDiaUser, String conductorMaterial){
        if(condDiaUser != null && condDiaUser != 0){
            if(conductorMaterial.equals(Constants.COPPER)){
                return condDiaUser >= 0.3? condDiaUser : 0.3;
            }
            else{
                return condDiaUser >= 0.8? condDiaUser : 0.8;
            }
        }else {
            double dia = NumberFormattingUtils.oneDigitDecimal(TwoWindingsFormulas.getDiameter(condXSec));
            if(conductorMaterial.equals(Constants.COPPER)){
                return Math.max(dia, 0.3);
            }
            else{
                return Math.max(dia, 0.8);
            }
        }
    }

    public static int getTransposition(double bi, double windingLength, int transpose, double turnsPerLayer, int radialParallel, int lvAxialParallelCond){
        int transposition = 0;
        if(radialParallel > 1){
            transposition = (int) Math.floor(windingLength + transpose - ((bi * (turnsPerLayer +1 )) * lvAxialParallelCond));
            if(transposition > 35){
                transposition = 35;
            }
        }
        return transposition;
    }

    public static double getRevisedConductorCrossSection(double b, double h) {
        double cornerRadiusArea = 0;
        if(b >= 5.0 && b <= 20.5 && h <= 1.65){
            cornerRadiusArea = 0.215;
        }
        if(b >= 5.0 && b <= 20.0 && h > 1.65 && h <= 2.30){
            cornerRadiusArea = 0.363;
        }
        if(b >= 5.0 && b <= 20.0 && h > 2.3 && h <= 3.65){
            cornerRadiusArea = 0.550;
        }
        if(b >= 5.0 && b <= 20.0 && h > 3.65 && h <= 5.95){
            cornerRadiusArea = 0.860;
        }
        if(b >= 6.3 && b <= 20.0 && h > 5.95 && h <= 10.0){
            cornerRadiusArea = 1.340;
        }
        if(b >= 30){cornerRadiusArea = 0;} //This will be used for Foil winding.

        return  NumberFormattingUtils.threeDigitDecimal((b * h) - cornerRadiusArea);
    }

    public static double getActualConductorXSec(double revisedCondXSec, double noOfConductors) {
        return  NumberFormattingUtils.threeDigitDecimal(revisedCondXSec * noOfConductors );
    }

    public static double getInterLayerInsulation(double voltsPerTurn, double turnsPerLayer, double conductorInsulation, boolean isEnamel, Double interLayerIns, boolean dryType) {
        int bDV = isEnamel || dryType ? 12000 : 8000;
        double interLayerInsulation =  ((voltsPerTurn * 2 * 2 * turnsPerLayer ) / bDV) - conductorInsulation;
        if(interLayerIns != null && interLayerIns >= interLayerInsulation * 0.9){
            return interLayerIns;
        }
        if (interLayerInsulation < 0) {
            return 0.1;
        } else {
            return NumberFormattingUtils.oneDigitDecimal(interLayerInsulation);
        }
    }

    public static Integer getDuctSize(Double windingLength, Integer ductSizeUser, boolean dryType){
        int ductSize = 3;
        if(windingLength <= 399){
            ductSize = 3;
        } else if (windingLength > 399 && windingLength <= 499) {
            ductSize = 4;
        }else if (windingLength > 499 && windingLength <= 599) {
            ductSize = 5;
        }else if (windingLength > 599) {
            ductSize = 6;
        }
        if(dryType){ductSize = 2 * ductSize;}
        if(ductSizeUser != null && ductSizeUser >= ductSize * 0.5){
            return ductSizeUser;
        }else{
            return ductSize;
        }
    }

    public static Integer getRadialThickness(double hi, int radialParallelConductors, double noOfLayers
            , double interLayerInsulation, int ducts, double ductSize, boolean isLV) {
        double factor = 0;
        int noLayers = NumberFormattingUtils.twoDigitDecimalPart(noOfLayers) > 0 ? (int) Math.ceil(noOfLayers) : (int) noOfLayers;

        if(isLV){factor = 0.3;}
        double radialThickness =(hi * radialParallelConductors * noOfLayers) + (ducts * ductSize) +
                (interLayerInsulation * (noLayers - 1 - ducts) + ( factor * noLayers));

        return NumberFormattingUtils.nextInteger(radialThickness); // Placeholder return
    }

    public static Integer getDiscRadialThickness(double h, int radialparallel,  double condIns, double Expansion, int turnsPerDisc, int noOfDuct, int ductThickness){
        double term1 = (h + (condIns * Expansion)) * radialparallel * turnsPerDisc + noOfDuct * ductThickness;
        return NumberFormattingUtils.nextInteger(term1);
    }

    public static double getCoreLvGap(double KVA, Double voltage, Double coreToLvGap, boolean drytype) {
        double coreLvGap = 8;
        if(drytype){
            if(voltage <= 1100){
                coreLvGap = KVA<=50 ? 6 : 10;
            } else if (voltage <= 3300) {
                coreLvGap = 12;
            } else {coreLvGap = 20;}
        } else{
            if(voltage <= 1100){
            if(KVA <= 25){
                coreLvGap = 1.5;
            } else if (KVA > 25 && KVA <= 100) {
                coreLvGap = 2;
            }else if (KVA > 100 && KVA <= 1000 ) {
                coreLvGap = 3;
            }else if (KVA > 1000 && KVA <= 2500 ) {
                coreLvGap = 4;
            }else if (KVA > 2500) {
                coreLvGap = 5;
            }
        }
            else if (voltage > 1100 && voltage <= 6600) {
            if(KVA <= 1000){
                coreLvGap = 6;
            } else if (KVA > 10000) {
                coreLvGap = 7;
            }
        }
            else if (voltage > 6600 && voltage <= 11000) {
            if(KVA <= 100){
                coreLvGap = 7;
            } else if (KVA > 100 && KVA <= 2500 ) {
                coreLvGap = 8;
            }else if (KVA > 2500 ) {
                coreLvGap = 9;
            }
        }
            else if (voltage > 11000 && voltage <= 22000) {
            coreLvGap = 12;
        }
            else if (voltage > 22000 && voltage <= 33000) {
            coreLvGap = 24;
        }
            else if (voltage > 33000 && voltage <= 66000) {
            coreLvGap = 32;
        }
            else {
            coreLvGap = 50;
        }
        }

        if(coreToLvGap != null && coreToLvGap >= 0.85 * coreLvGap){
            return coreToLvGap;
        }
        else {
            return coreLvGap;
        }
    }

    public static int getID(double innerDia, double gap) {
        return (int) Math.ceil(innerDia + (2 * gap));
    }

    public static int getOD(double Id, double radialThickness) {
        return (int) Math.ceil(Id + (2 * radialThickness));
    }

    public static double getLvHvGap(double KVA, double highVoltage, EVectorGroup eVectorGroup, Double lvToHvGap, boolean dryType) {
        int lvHvGap = 8;

        if(highVoltage <= 1100){
            if(KVA <= 500){
                lvHvGap = 5;
            } else if (KVA > 500) {
                lvHvGap = 6;
            }
        } else if (highVoltage > 1100 && highVoltage <= 6600) {
            if(KVA <= 1000){
                lvHvGap = 6;
            } else if (KVA > 1000) {
                lvHvGap = 7;
            }
        }else if (highVoltage > 6600 && highVoltage <= 11000) {
            if(KVA <= 100){
                lvHvGap = 7;
            } else if (KVA > 100 && KVA <= 2500) {
                lvHvGap = 8;
            }else if (KVA > 2500) {
                lvHvGap = 9;
            }
        }else if (highVoltage > 22000 && highVoltage <= 33000) {
            lvHvGap = 18;
        }else if (highVoltage > 33000 && highVoltage <= 66000) {
            if(KVA <= 60000){
                lvHvGap = 28;
            }
        }else if (highVoltage > 66000 && highVoltage <= 132000) {
            lvHvGap = Objects.equals(eVectorGroup.name().charAt(0), 'D') ? 54 : 43;
        }
        if(dryType){
            if(highVoltage <= 11000){lvHvGap = 31;}
            else if(highVoltage <= 22000){lvHvGap = 54;}
            else if(highVoltage <= 33000){lvHvGap = 90;}
        }
        if(lvToHvGap != null && lvToHvGap >= 0.85 * lvHvGap){
            return lvToHvGap;
        }else{
            return lvHvGap;
        }
    }

    public static double getLMT(double lvId, double lvOd) {
        return ((lvId + lvOd)/2000) * Math.PI ;
    }

    public static double getRectLMT(int idWidth, int idDepth, int odWidth, int odDepth, int radThick, boolean isRound){
        int i = idWidth + idDepth + odWidth + odDepth - (8 * radThick);
        if(isRound){
            return (i + (2 * Math.PI * radThick)) * Math.pow(10, -3);
        }else {
            return (i + 4 * Math.pow(2 * radThick * radThick, 0.5)) * Math.pow(10, -3);
        }
    }

    public static double getWireLength(double lmt, double turnsPerLimb, double noOfLimbs, int noOfCond) {
        double tolerance = 0;
        if(noOfCond <= 5){
            tolerance = (noOfCond * 0.01) + 1;
        }else if (noOfCond <= 10){
            tolerance = (6 * 0.01) + 1;
        }else {
            tolerance = (7 * 0.01) + 1;
        }
        double wireLength = lmt * turnsPerLimb * noOfLimbs * noOfCond * tolerance;
        return Math.ceil(wireLength);
    }

    public static double getR75(String conductorMaterial, double lmt, double turnsPerLimb, double conductorCrossSection) {
        double resistivity = conductorMaterial.equals(Constants.COPPER) ? 0.02128 : 0.0346;
        double r75 = resistivity * lmt * turnsPerLimb / conductorCrossSection;
        return NumberFormattingUtils.sixDigitDecimal(r75);
    }

    public static double getR26(double r75, String conductorMaterial) {
        double absoluteTemp = conductorMaterial.equals(Constants.COPPER) ? 235 : 225;
        return NumberFormattingUtils.sixDigitDecimal(r75 * ((absoluteTemp + 26) / (absoluteTemp + 75)));
    }

    public static double getBareWeight(double lmt, double noOfTurns, double conductorCrossSection, String conductorMaterial) {
        double density = conductorMaterial.equals(Constants.COPPER) ? 8.89 : 2.703;
        double bareWeight = lmt * noOfTurns * 3 * conductorCrossSection * Math.pow(10, -3) * density;
        return NumberFormattingUtils.oneDigitDecimal(bareWeight);
    }

    public static double getInsulatedWeight(double bi, double hi, double b, double h, String conductorMaterial, double bareWeight, boolean isEnamel) {
        double materialDensity = conductorMaterial.equals(Constants.COPPER) ? 8.89 : 2.703;
        double insDensity = isEnamel ? 1.85 : 1.00;

        return NumberFormattingUtils.oneDigitDecimal((((((bi * hi) - (b * h)) / (b * h ))*(insDensity / materialDensity)) + 1) * bareWeight);
    }

    public static double getProcurementWeight(double insulatedWeight, double noOfParallelConductors) {
        return NumberFormattingUtils.nextInteger(insulatedWeight * ((noOfParallelConductors * 0.01) + 1));
    }

    public static double getStrayLoss(double b, double bi, double h, double turnsPerLayer, double radialParallelConductors, double axialParallelConductors,  double conductorInsulation, String  conductorMaterial, double noOfLayers, double transposition, boolean isRound) {
        double slf = 1;

        if (isRound){
            slf = conductorMaterial.equals(Constants.COPPER) ? 0.8 : 0.63;
        }else {
            slf = conductorMaterial.equals(Constants.COPPER) ? 0.9622 : 0.76;
        }

        double term1 = (b * turnsPerLayer * axialParallelConductors) + transposition;
        double term2 = (bi * turnsPerLayer * axialParallelConductors) + transposition - conductorInsulation;

        double term3 = Math.pow((Math.sqrt(term1 / term2) * slf * h / 10), 4);

        return NumberFormattingUtils.fourDigitDecimal(100 * term3 * (Math.pow((noOfLayers * radialParallelConductors), 2) - 0.2) / 9);
    }

    public static double getStrayLossForDisc(double b, double h, double turnsPerLayer, double radialParallelConductors, double axialParallelConductors, double conductorInsulation, String  conductorMaterial, double noOfLayers, double windingLength){
        double slf = conductorMaterial.equals(Constants.COPPER) ? 0.9622 : 0.76;

        double term1 = (b * turnsPerLayer * axialParallelConductors);
        double term2 = (windingLength) - conductorInsulation;

        double term3 = Math.pow((Math.sqrt(term1 / term2) * slf * h / 10), 4);

        return NumberFormattingUtils.fourDigitDecimal(100 * term3 * (Math.pow((noOfLayers * radialParallelConductors), 2) - 0.2) / 9);
    }

    public static double getStrayLossForXOver(double b, double h, double turnsPerLayer, double noOfCoils, double radialParallelConductors, double axialParallelConductors, double conductorInsulation, String  conductorMaterial, double noOfLayers, double windingLength, boolean isRound){
        double slf = 1;
        if (isRound){
            slf = conductorMaterial.equals(Constants.COPPER) ? 0.8 : 0.63;
        }else {
            slf = conductorMaterial.equals(Constants.COPPER) ? 0.9622 : 0.76;
        }

        double term1 = (b * turnsPerLayer * noOfCoils * axialParallelConductors);
        double term2 = (windingLength) - conductorInsulation;

        double term3 = Math.pow((Math.sqrt(term1 / term2) * slf * h / 10), 4);

        return NumberFormattingUtils.fourDigitDecimal(100 * term3 * (Math.pow((noOfLayers * radialParallelConductors), 2) - 0.2) / 9);
    }

    public static double getStrayLossForFoil(double h, String  conductorMaterial, double noOfLayers){
        double slf = conductorMaterial.equals(Constants.COPPER) ? 0.9622 : 0.76;
        double term1 =  Math.pow(slf * (h / 10), 4);
        return NumberFormattingUtils.fourDigitDecimal(100 * term1 * (Math.pow((noOfLayers), 2) - 0.2) / 9);
    }

    public static double getLoadLoss(String conductorMaterial, double bareWeight, double currentDensity, double strayLoss) {
        double llf = conductorMaterial.equals(Constants.COPPER) ? 2.4 : 12.79;

        return NumberFormattingUtils.next5or0Integer(llf * bareWeight * Math.pow(currentDensity, 2) * (strayLoss / 100 + 1));
    }

    public static Integer getAmbientTemp(Integer ambientTemp){
        if(ambientTemp != null){
            return ambientTemp;
        }else {
            return 50;
        }
    }

    public static Integer getWindingTemp(Integer windingTemp){
        if(windingTemp != null){
            return windingTemp;
        }else {
            return 55;
        }
    }

    public static Integer getTopOilTemp(Integer topOilTemp){
        if(topOilTemp != null){
            return topOilTemp;
        }else {
            return 50;
        }
    }

    public static double getGradientLimit(boolean dryType, DryTempClass dryTempClass){
        double graduentlimit = 14.5;
        if(dryType){
            if (Objects.equals(dryTempClass.toString(),"CLASS_B")){
                graduentlimit = 90;
            } else if (Objects.equals(dryTempClass.toString(),"CLASS_F")) {
                graduentlimit = 105;
            }else if (Objects.equals(dryTempClass.toString(),"CLASS_H")) {
                graduentlimit = 115;
            }
        }
        return graduentlimit;
    }

    public static double getLvGradient(double loadLoss, double availableSurfaceForCooling, double windingLength, int transpositon, double lmt, Boolean dryType, boolean isLv ) {
        int hdf = 55;
        if((availableSurfaceForCooling - 2 )/2 != 0){
            hdf = 60;
        }
        if(dryType){
            hdf = isLv ? 4 : 5;
        }
        double gradient = loadLoss / (3 * (availableSurfaceForCooling * 0.75) * hdf * (windingLength + transpositon)* 0.001 * lmt);
        return NumberFormattingUtils.oneDigitDecimal(gradient);
    }

    public static double getHvGradient(double loadLoss, double availableSurfaceForCooling, double windingLength, int transpositon, double lmt, Boolean dryType) {
        int hdf = 55;
        if((availableSurfaceForCooling - 2 )/2 != 0){
            hdf = 60;
        }
        if(dryType){
            hdf = 5;
        }
        double gradient = loadLoss / (3 * (((availableSurfaceForCooling - 1)  * 0.75) + 1)  * hdf * (windingLength + transpositon) * 0.001 * lmt);
        return NumberFormattingUtils.oneDigitDecimal(gradient);
    }

    public static double getLvGradientWithPartialDuct(double loadLoss, double availableSurfaceForCooling, double windingLength, double lmt) {
        int hdf = 55;
        if((availableSurfaceForCooling - 2 )/2 != 0){
            hdf = 60;
        }
        double gradient = loadLoss / (3 * (availableSurfaceForCooling * 0.375) * hdf * windingLength * 0.001 * lmt);
        return NumberFormattingUtils.oneDigitDecimal(gradient);
    }

    public static double getV0(double currentDensity, double crossSecPerCond, double strayLoss, double heightInsulated, double wdgTemp, double ambTemp){
        double temperature = NumberFormattingUtils.twoDigitDecimal(wdgTemp + ambTemp);
        double alpha = NumberFormattingUtils.twoDigitDecimal(-0.1305 * temperature + 56.4714);
        double term1 = Math.pow(currentDensity, 2) * crossSecPerCond * (1 + (strayLoss/100));
        double term2 = 20 * alpha * 0.75 * heightInsulated;
        //Above, 20 is a constant and 0.75 is a beta factor (covering factor) = 75%
        return NumberFormattingUtils.threeDigitDecimal(term1/term2);
    }

    public static double getPsi(double breadthInsulated, double radialThickness, double ductSize , int noOfDucts){
        double effRadThick = (radialThickness - (noOfDucts * ductSize) )/ (noOfDucts + 1);
        double term1 = 0.99 - ((0.4 * 0.75 * breadthInsulated) / effRadThick) - (1.39 / effRadThick);
        return NumberFormattingUtils.threeDigitDecimal(term1);
    }

    public static double getRw(double V0, double Psi, double conductorInsulation){
        double term1 = 1.16 * Math.pow(V0 * Psi, 0.2) * Math.pow(10, -4);
        double term2 = (((1 / term1 ) * 3.2 * Math.pow(10, -4) + conductorInsulation) / 3.2) * 100;
        return NumberFormattingUtils.threeDigitDecimal(term2);
    }

    public static double hvStepVoltage(double highVoltage, double stepPercent){
        return NumberFormattingUtils.oneDigitDecimal(stepPercent * highVoltage/100);
    }

    public static double ampereTurns(double lvTurnsPerPhase, double lvCurrentPerPhase){
        return NumberFormattingUtils.nextInteger(lvCurrentPerPhase * lvTurnsPerPhase);
    }

    public static double h1h2(int radialThickness, int noOfDucts, int ductThickness, double conductorInsulation){
        int noDucts = noOfDucts == 0 ? 1 : noOfDucts;
        return radialThickness - (((((double) ductThickness + conductorInsulation) / 4) + conductorInsulation)  * noDucts);

    }

    public static double getL(double lvBi, double hvBi, double lvTurnsPerLayer, double hvTurnsPerLayer, int lvAxialCond, int hvAxialCond, double lvCondIns, double hvCondIns,double lvWdgLength, double hvWdgLength, boolean isHelical) {
        double l = 1;
        if (isHelical) {
            l = ((lvBi * lvTurnsPerLayer * lvAxialCond) + (hvBi * hvTurnsPerLayer * hvAxialCond)) / 2;
        } else {
            l = (lvWdgLength + hvWdgLength - lvCondIns - hvCondIns) / 2;
        }
        return l;
    }

    public static double[] ls(double lvBi, double hvBi, double lvTurnsPerLayer, double hvTurnsPerLayer, int lvAxialCond, int hvAxialCond, double hvOd, double lvId, double lvCondIns, double hvCondIns, double lvWdgLength, double hvWdgLength, EWindingType lvWindingType, EWindingType hvWindingType, int lvTransposition, int hvNoOfCoils){
        double l = 1;
        if(Objects.equals(lvWindingType.toString(), "HELICAL") && Objects.equals(hvWindingType.toString(), "HELICAL")){
            l = NumberFormattingUtils.twoDigitDecimal(((lvBi * lvTurnsPerLayer * lvAxialCond) + lvTransposition + (hvBi * hvTurnsPerLayer * hvAxialCond) - lvCondIns - hvCondIns)/2);
        }
        else if(Objects.equals(lvWindingType.toString(), "HELICAL") && Objects.equals(hvWindingType.toString(), "XOVER")) {
            l = NumberFormattingUtils.twoDigitDecimal(((lvBi * lvTurnsPerLayer * lvAxialCond) + lvTransposition + (hvBi* hvTurnsPerLayer * hvNoOfCoils) - lvCondIns - hvCondIns) / 2);
        }
        else if(Objects.equals(lvWindingType.toString(), "HELICAL") && Objects.equals(hvWindingType.toString(), "DISC")) {
            l = NumberFormattingUtils.twoDigitDecimal(((lvBi * lvTurnsPerLayer * lvAxialCond) + lvTransposition + (hvWdgLength) - lvCondIns - hvCondIns) / 2);
        }
        else {
            double term2 = ((lvWdgLength) + (hvBi * hvTurnsPerLayer * hvAxialCond) - lvCondIns - hvCondIns) / 2;
            if(Objects.equals(lvWindingType.toString(), "DISC") && Objects.equals(hvWindingType.toString(), "HELICAL")) {
                l = NumberFormattingUtils.twoDigitDecimal(term2);
            }
            else {
                double term1 = ((lvWdgLength) + (hvBi * hvTurnsPerLayer * hvNoOfCoils) - lvCondIns - hvCondIns) / 2;
                if(Objects.equals(lvWindingType.toString(), "DISC") && Objects.equals(hvWindingType.toString(), "XOVER")) {
                    l = NumberFormattingUtils.twoDigitDecimal(term1);
                }
                else if(Objects.equals(lvWindingType.toString(), "FOIL") && Objects.equals(hvWindingType.toString(), "HELICAL")) {
                    l = NumberFormattingUtils.twoDigitDecimal(term2);
                }
                else if(Objects.equals(lvWindingType.toString(), "FOIL") && Objects.equals(hvWindingType.toString(), "XOVER")) {
                    l = NumberFormattingUtils.twoDigitDecimal(term1);
                }else{
                    l = NumberFormattingUtils.twoDigitDecimal(((lvWdgLength)+ (hvWdgLength) - lvCondIns - hvCondIns) / 2);
                }
            }
        }
        double b = NumberFormattingUtils.twoDigitDecimal((hvOd - lvId - lvCondIns - hvCondIns) / 2);

        double power = Math.PI * l / b;

        double kR = NumberFormattingUtils.threeDigitDecimal(1 - ((1 - Math.exp(power * -1)) / power));

        double ls[]  = new double[4];
        ls[0] = NumberFormattingUtils.threeDigitDecimal(l/kR);
        ls[1] = l;
        ls[2] = b;
        ls[3] = kR;
        return ls;
    }

    public static double[] ex( double voltsPerTurn, double lvHvGap, double lvCondIns, double hvCondIns, double h1, double h2, int ampereTurns, double ls, int lvOd, double frequencyFactor){
        double[] output = new double[4];
        double delta = lvHvGap + (hvCondIns + lvCondIns)/2;
        double delta1 = delta + ((h2 + h1) / 3);
        double ds = lvOd - lvCondIns + delta + (h2 - h1) / 3;
        double term1 = 1.24 * ampereTurns * delta1 * ds * Math.pow(10, -4);
        output[0] = delta;
        output[1] = delta1;
        output[2] = ds;
        output[3] = NumberFormattingUtils.twoDigitDecimal((term1 / (voltsPerTurn * ls)) * frequencyFactor);
        return output;

    }

    public static double er(double lvLoadLoss, double hvLoadLoss, double KVA, double phaseCurrent, double lowVoltage){

        double tankLossFactor;
        if(lowVoltage <= 1100){
            if(phaseCurrent <= 300){
                tankLossFactor = 0.8;
            }else if(phaseCurrent <= 700){
                tankLossFactor = 1;
            } else if (phaseCurrent > 700 && phaseCurrent <= 2000) {
                tankLossFactor = 1.5;
            } else if (phaseCurrent > 2000 && phaseCurrent <= 4000) {
                tankLossFactor = 2;
            } else {
                tankLossFactor = 3;
            }
        }else{
            if(lowVoltage <= 33000){tankLossFactor = 0.4;}
            else {tankLossFactor = 0.3;}
        }
        double totalLoadLoss = lvLoadLoss + hvLoadLoss + (tankLossFactor * KVA);
        return  NumberFormattingUtils.twoDigitDecimal((totalLoadLoss/ (KVA * Math.pow(10,3))) * 100);
    }

    public static double ek(double er, double ex){
        return NumberFormattingUtils.twoDigitDecimal(Math.sqrt(Math.pow(er, 2) + Math.pow(ex, 2)));
    }

    public static double getHvHVGap(double KVA, double lvVoltage, double hvVoltage, EVectorGroup eVectorGroup, Double hvToHvGap, boolean dryType) {
        int hvHvGap = 7;
        double differenceVoltage = hvVoltage - lvVoltage;
        if(dryType){
            if(hvVoltage <= 3300){
                if(KVA <= 50){hvHvGap = 12;} else if (KVA <= 500) { hvHvGap = 20;} else{hvHvGap = 36;}
            } else if (hvVoltage <= 11000) {
                hvHvGap = 36;
            } else if(hvVoltage <= 22000){hvHvGap = 54;}
            else if(hvVoltage <= 33000){hvHvGap = 90;}
            else {hvHvGap = 100;}
        }else{
            if (differenceVoltage <= 11000) {
            if(KVA <= 100){
                hvHvGap = 7;
            } else if (KVA > 100 && KVA <= 1000) {
                hvHvGap = 8;
            } else if (KVA > 1000) {
                hvHvGap = 10;
            }
        } else if (differenceVoltage > 11000 && differenceVoltage <= 33000) {
            hvHvGap = 16;
        } else if (hvVoltage > 33000 && hvVoltage <= 66000) {
            hvHvGap = Objects.equals(eVectorGroup.name().charAt(0), "D") ? 35 : 25;
        } else if (hvVoltage > 66000 && hvVoltage <= 132000) {
            hvHvGap = Objects.equals(eVectorGroup.name().charAt(0), "D") ? 60 : 25;
        }
        }
        if(hvToHvGap != null && hvToHvGap >= 0.85 * hvHvGap){
            return hvToHvGap;
        } else{
            return hvHvGap;
        }
    }

    public static int getCenterDistance(double hvOd, double hvHvGap){
        return NumberFormattingUtils.nextInteger(hvOd + hvHvGap);
    }

    public static double getCoreLength(int coreDiameter, double windowHeight, double centerDistance ){
        return 2 * coreDiameter + 3 * windowHeight + 4 * centerDistance;
    }

    public static double getCoreWeight(double coreLength, double netCoreArea){
        double coreDensity = 7.65 * Math.pow(10,-6);
        return NumberFormattingUtils.nextInteger(coreLength * netCoreArea * coreDensity) ;
    }

    public static int getCoreLoss(double coreWeight, double buildFactor, double specificLoss){
        return NumberFormattingUtils.next5or0Integer(coreWeight * buildFactor * specificLoss);
    }

    public static int getTankLoss(double KVA, double phaseCurrent, double lowVoltage, Integer tankLoss, boolean dryType){
        if (tankLoss != null){
            return tankLoss;
        }else{
            double factor;
            if(lowVoltage <= 1100){
                if(phaseCurrent <= 300){
                    factor = 0.8;
                }else if(phaseCurrent <= 700){
                    factor = 1;
                } else if (phaseCurrent > 700 && phaseCurrent <= 2000) {
                    factor = 1.5;
                } else if (phaseCurrent > 2000 && phaseCurrent <= 4000) {
                    factor = 2;
                } else {
                    factor = 3;
                }
            }else{
                if(lowVoltage <= 33000){factor = 0.4;}
                else {factor = 0.3;}
            }
            if(dryType){factor = 0.5;}
            return NumberFormattingUtils.next5or0Integer(KVA * factor);
        }
    }

    public static double getKw55(double coreLoss, double lvLoadLoss, double hvLoadLoss, double tankLoss, double lvGradient, double hvGradient){
        double gradient55 = lvGradient < 14.5 && hvGradient < 14.5 ?  14.5 :  Math.max(lvGradient, hvGradient);
        double newTopOilTemperature = 98 - 32 - 1.1 * gradient55;
        double kw55Factor = Math.pow(55 / newTopOilTemperature, (1/0.7));
        double totalLoss = coreLoss + 1.1 * (lvLoadLoss + hvLoadLoss + tankLoss);
        return NumberFormattingUtils.next5or0Integer(kw55Factor * totalLoss);
    }

    public static double getDiscDuctSize(double lineVoltage, Boolean isInnerWdg, EVectorGroup eVectorGroup){
        double discDuctSize = 0;
        if(lineVoltage <= 33000){
            discDuctSize = isInnerWdg ? 3 : 3.5;
        } else if (lineVoltage <= 66000) {
            if(!isInnerWdg){
                discDuctSize = Objects.equals(eVectorGroup.name().charAt(0), 'D') ? 4.5 : 4;
            }else discDuctSize = Objects.equals(eVectorGroup.name().charAt(1), 'd') ? 4.5 : 4;
        } else if (lineVoltage <= 132000) {
            discDuctSize = Objects.equals(eVectorGroup.name().charAt(0), 'D') ? 5 : 4.5;
        }
        return discDuctSize;
    }

    public static int[] getSpacersAndWidth(double hvId){
        int circumference = NumberFormattingUtils.next0Integer(Math.PI * hvId * 0.21);
        int noOfSpacers = 8;
        int widthOfSpacers = (int) Math.ceil((double) circumference / noOfSpacers);
        int[] passingValue = new int[2];
        while (widthOfSpacers >= 40) {
            noOfSpacers = noOfSpacers + 2;
            widthOfSpacers = (int) Math.ceil((double) circumference / noOfSpacers);
            if (widthOfSpacers < 40) {
                break;
            }
        }
        //TODO: Check if the spacerWidth is within 20.
        widthOfSpacers = NumberFormattingUtils.previous5or0Integer(widthOfSpacers);
        passingValue[0] = noOfSpacers;
        passingValue[1] = widthOfSpacers;
        return  passingValue;
    }

    //Here starts the formulae specific for Foil Winding

    public static double getFoilEndStrip(Double wdgLength){
        if(wdgLength <= 500){
            return 5;
        } else{
            return 10;
        }
    }

    public static int getFoilLength(double windingLength, Double foilLength, Double endStrip){
        if(foilLength != null){
            return (int) Math.floor(foilLength);
        } else {
            return NumberFormattingUtils.previous5or0Integer(windingLength - (endStrip * 2));
        }
    }

    //The formulae specific for XOver Winding

    public static int getNoOfCoils(double voltage, Integer noOfCoilsFromUser){
        if (noOfCoilsFromUser != null){
            return noOfCoilsFromUser;
        } else {
            if(voltage <= 11000){
                return 4;
            }else if(voltage <= 33000){
                return 6;
            }
            return 4;
        }
    }

    public static int getGapBetweenCoils(double KVA, double highVoltage){
        int gapBetweenCoil = 12;
        if(highVoltage <= 11000){
            if(KVA <= 100){
                gapBetweenCoil = 4;
            } else if (KVA > 100 && KVA <= 1000) {
                gapBetweenCoil = 5;
            } else {gapBetweenCoil = 6;}
        } else if (highVoltage > 11000 && highVoltage <= 33000) {
            if(KVA <= 2500){
                gapBetweenCoil = 6;
            } else if (KVA > 2500) {
                gapBetweenCoil = 10;
            }
        }
        return gapBetweenCoil;
    }

    public static int getWindingLengthPerCoil(double windingLength, int gapBwCoil, int noOfCoils){
        return (int) Math.floor((windingLength - gapBwCoil * (noOfCoils - 1)) / noOfCoils);
    }

    public static double getSpecificLoss (double reqFluxDen, double lowerFluxDen, double upperFluxDen, float lowerSpecificLoss, float upperSpecificLoss, int frequency){
        double term1 = (reqFluxDen - lowerFluxDen) * (upperSpecificLoss - lowerSpecificLoss);
        double specificLoss = lowerSpecificLoss + (term1 / (upperFluxDen - lowerFluxDen));
        if (frequency == 60){
            return NumberFormattingUtils.twoDigitDecimal(specificLoss * 1.32);
        }else{
            return NumberFormattingUtils.twoDigitDecimal(specificLoss);
        }
    }

    public static double getLimitEz(double KVA, Double limitEz) {
        double limEz = 0;
        if(limitEz != null){
           return limitEz;
        } else {
            if(KVA <= 10){
                limEz = 10.5;
            }
            else if(KVA <= 630){
                limEz = 4.5;
            } else if (KVA > 630 && KVA <= 1250) {
                limEz = 5;
            } else if (KVA > 1250 && KVA <= 3150) {
                limEz = 6.25;
            } else if (KVA > 3150 && KVA <= 6300) {
                limEz = 7.15;
            } else if (KVA > 6300 && KVA <= 12500) {
                limEz = 8.35;
            } else if (KVA > 12500 && KVA <= 25000) {
                limEz = 10;
            } else if (KVA > 25000 && KVA <= 200000) {
                limEz = 12.5;
            } else if (KVA > 200000) {
                limEz = 12.5;
            }
            return limEz;
        }
    }

    public static boolean isEzWithinRange(Double limitEz, Double ez, Integer deviationPercentage) {
        double minEz = (limitEz * (100 - deviationPercentage)/100);
        double maxEz = (limitEz * (100 + deviationPercentage)/100);
        return ez >= minEz && ez <= maxEz;
    }

    public static double getModifiedLimbHtForImpedance(Double ez, Double limitEz, Double limbHt, Double KVA) {
        Double newLimbHt = null;
        if(KVA > 1600){
            if (ez < limitEz) {
                newLimbHt = limbHt - ((limbHt - ((ez / limitEz) * limbHt)) * 0.15);
            } else {
                newLimbHt = limbHt + ((limbHt + ((ez / limitEz) * limbHt)) * 0.15);
            }
        } else if (KVA < 20) {
            if (ez < limitEz) {
                newLimbHt = ((limbHt - (((ez / limitEz) * limbHt)) * 0.05));
            } else {
                newLimbHt = ((limbHt + (((ez / limitEz) * limbHt)) * 0.05));
            }
        } else {
            if (ez < limitEz) {
                newLimbHt = limbHt - ((limbHt - ((ez / limitEz) * limbHt)) * 0.5 );
            } else {
                newLimbHt = limbHt + ((limbHt + ((ez / limitEz) * limbHt)) * 0.5);
            }
        }
        return newLimbHt;
    }

    public static double getEfficiencyPercentage(double kVA, int totalLoadLoss, int noLoadLoss, double loadFactor,double powerFactor){
        double term1 = (kVA * Math.pow(10,3) * powerFactor * loadFactor) + noLoadLoss + (Math.pow(loadFactor, 2) * totalLoadLoss);
        double efficiency = (kVA * Math.pow(10,3) * powerFactor * loadFactor) * 100/ term1 ;
        return NumberFormattingUtils.twoDigitDecimal(efficiency);
    }

    public static double getVoltageRegulation(double er, double ex, double powerFactor){
        double cosPhi = powerFactor;
        double sinPhi = Math.sqrt(1 - Math.pow(cosPhi, 2));
        double term1 = Math.pow((ex * cosPhi) - (er * sinPhi), 2) / 200;
        double vr = (er * cosPhi + ex * sinPhi) + term1;
        return NumberFormattingUtils.twoDigitDecimal(vr);
    }

    public static ArrayList<Integer> getTurnsAtTap(double highVoltage, int noOfTaps, int tapStepNegative, double tapStepPercent, double voltsPerTurn){
        ArrayList<Integer> turnsAtTap = new ArrayList<>();
        double leastTap = 100 - tapStepNegative * tapStepPercent;
        int turnsAtLowest = (int) (leastTap * highVoltage / (voltsPerTurn * 100));
        turnsAtTap.add(turnsAtLowest);
        for(int i = 0; i < noOfTaps - 1; i++){
            leastTap = leastTap + tapStepPercent;
            int turnsAtThisTap = (int) (leastTap * highVoltage / (voltsPerTurn * 100));
            turnsAtTap.add(turnsAtThisTap);
        }
        return turnsAtTap;
    }

    public static ArrayList<Integer> getTapVoltages(double highVoltage, int tapStepNegative, int tapStepPositive, double tapStepPercent){
        int voltageStep = (int) Math.floor(highVoltage * tapStepPercent/100);
        ArrayList<Integer> tapVoltages = new ArrayList<>();
        int voltageAtTap = 0;
        for(int i = tapStepPositive; i >= 1; i--){
            voltageAtTap = (int) Math.floor(highVoltage + (i * voltageStep));
            tapVoltages.add(voltageAtTap);
        }
        tapVoltages.add((int) highVoltage);
        for(int i = 1; i <= tapStepNegative; i++){
            voltageAtTap = (int) Math.floor(highVoltage - (i * voltageStep));
            tapVoltages.add(voltageAtTap);
        }
        return tapVoltages;
    }

    public static ArrayList<Double> getTapCurrents(int noOfTaps, ArrayList<Integer> tapVoltages, double kVA){
        ArrayList<Double> tapCurrents = new ArrayList<>();
        for(int i = 0; i < noOfTaps; i++){
            double currentAtTap = NumberFormattingUtils.twoDigitDecimal((kVA * 1000) / (3 * tapVoltages.get(i)));
            tapCurrents.add(currentAtTap);
        }
        return tapCurrents;
    }

    public static int[] getTestAndImpTest(double voltage){
        int[] testAndImpTest = new int[2];
        int impulseTestVoltage = 0;

        int testVoltage = 3;
        if (voltage > 1100) testVoltage = 10;
        if (voltage > 3600) testVoltage = 20;
        if (voltage > 7200) testVoltage = 28;
        if (voltage > 12000) testVoltage = 50;
        if (voltage > 24000) testVoltage = 70;
        if (voltage > 36000) testVoltage = 140;
        if (voltage > 72500) testVoltage = 230;
        if (voltage > 123000) testVoltage = 275;

        if(voltage <= 1100){
            impulseTestVoltage = 0;
        }else if(voltage <= 3600){
            impulseTestVoltage = 40;
        } else if (voltage <= 7200) {
            impulseTestVoltage = 60;
        } else if (voltage <= 12000) {
            impulseTestVoltage = 75;
        } else if (voltage <= 24000) {
            impulseTestVoltage = 125;
        } else if (voltage <= 36000) {
            impulseTestVoltage = 170;
        } else if (voltage <= 72500) {
            impulseTestVoltage = 325;
        } else if (voltage <= 133000) {
            impulseTestVoltage = 550;
        } else if (voltage > 133000) {
            impulseTestVoltage = 650;
        }
        testAndImpTest[0] = testVoltage;
        testAndImpTest[1] = impulseTestVoltage;
        return testAndImpTest;
    }

    public static String getCoreLvIns(EWindingType lvWdgType, double coreLvGap){
        if(Objects.equals(lvWdgType.toString(), "DISC")){
            return "3mm PB + rest Oil";
        } else if (Objects.equals(lvWdgType.toString(), "LAYERDISC")) {
            return "3mm PB + rest Oil";
        } else {
            if(coreLvGap <= 2){
                return "0.5mm PB + rest Oil";
            }else {return "1mm PB + rest Oil";}
        }
    }

    public static String getLvHvIns(EWindingType lvWdgType, EWindingType hvWdgType, double lvHvGap){
        if(Objects.equals(lvWdgType.toString(), "DISC") || Objects.equals(hvWdgType.toString(), "DISC")){
            return "3mm PB + rest Oil";
        }
        else if (Objects.equals(lvWdgType.toString(), "LAYERDISC") || Objects.equals(hvWdgType.toString(), "LAYERDISC")) {
            return "3mm PB + rest Oil";
        }
        else {
            if(lvHvGap <= 8){
                if(lvHvGap % 2 == 0){
                    return "1mm PB + " + Math.ceil((lvHvGap -1)/2) + "mm towards Lv " + Math.floor((lvHvGap-1)/2) + "mm towards Hv";
                }else{
                    return "1mm PB + " + (lvHvGap -1)/2 + "mm on both sides";
                }
            }
            else if(lvHvGap <= 16){
                if(lvHvGap % 2 == 0){
                    return "1mm x 2 PB + " + (lvHvGap -2)/2 + "mm on both sides";
                }else{
                    return "1mm x 2 PB + " + Math.ceil((lvHvGap -2)/2) + "mm towards Lv " + Math.floor((lvHvGap-2)/2) + "mm towards Hv";
                }
            }else{
                if(lvHvGap % 2 == 0){
                    return "1mm x 3 PB + " + Math.ceil((lvHvGap -3)/2) + "mm towards Lv " + Math.floor((lvHvGap-3)/2) + "mm towards Hv";
                }else{
                    return "1mm x 3 PB + " + (lvHvGap -3)/2 + "mm on both sides";
                }
            }
        }
    }

    public static String getHvHvIns(EWindingType lvWdgType, EWindingType hvWdgType, double hvHvGap){
        if(Objects.equals(lvWdgType.toString(), "DISC") || Objects.equals(hvWdgType.toString(), "DISC")){
            return "1mm x 3 PB + rest Oil";
        } else if (Objects.equals(lvWdgType.toString(), "LAYERDISC") || Objects.equals(hvWdgType.toString(), "LAYERDISC")) {
            return "1mm x 3 PB + rest Oil";
        } else {
            if(hvHvGap <= 16){
                return "1mm x 2 PB + rest Oil";
            }else {return "1mm x 4 PB + rest Oil";}
        }
    }

    public static double getNLCurrentPercentage(double coreWeight, double specificLoss, double KVA){
        return NumberFormattingUtils.twoDigitDecimal(coreWeight * specificLoss * 10 / (KVA * 10));
    }

    public static int getLossAt50Percent(int coreLoss, int tankLoss, double lvLoadLoss, double hvLoadLossAtNormal){
        double term1 = (tankLoss + lvLoadLoss + hvLoadLossAtNormal) / 4;
        return NumberFormattingUtils.nextInteger(term1 + coreLoss);
    }

    public static int getLossAt100Percent(int coreLoss, int tankLoss, double lvLoadLoss, double hvLoadLossAtNormal){
        return NumberFormattingUtils.nextInteger(tankLoss + lvLoadLoss + hvLoadLossAtNormal + coreLoss);
    }
}
//Rest of the formulae for Tank, Weights, Dimensions and Oil are all written in the TankFormulas.java
