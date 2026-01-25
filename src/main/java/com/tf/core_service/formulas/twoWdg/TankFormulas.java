package com.tf.core_service.formulas.twoWdg;

import com.tf.core_service.model.twoWindings.ERadiatorType;
import com.tf.core_service.model.twoWindings.ETransCostType;
import com.tf.core_service.model.twoWindings.EVectorGroup;
import com.tf.core_service.utils.Constants;
import com.tf.core_service.utils.NumberFormattingUtils;

import java.util.Objects;

public class TankFormulas {
    //The OLTC can be given on any side of the transformer. Now, for default, we have assumed the transformer to have OLTC on the Length Side.
    //If the OLTC needs to be placed on Width side add it to the tankWidth.

    public static int getLargestBlade(Integer coreDiameter){
        return coreDiameter - 3 - ((coreDiameter - 3 ) % 5);
    }

    public static int getYokeInsulation(double KVA){
        int yokeInsulation = 0;
        if(KVA <= 100){
            yokeInsulation = 10;
        }else if(KVA > 100 && KVA < 2500){
            yokeInsulation = 15;
        }else if(KVA > 2500){
            yokeInsulation = 25;
        }
        return yokeInsulation;
    }

    public static int getWdgToTankGap (double hvVoltage, double KVA){
        int wdgTankGap = 25;
        if(hvVoltage <= 11000){
            if(KVA <= 1000){
                wdgTankGap = 25;
            } else if (KVA > 1000) {
                wdgTankGap = 30;
            }
        } else if (hvVoltage > 11000 && hvVoltage <= 33000) {
            if(KVA <= 100){
                wdgTankGap = 40;
            } else if (KVA > 100) {
                wdgTankGap = 50;
            }
        } else if (hvVoltage > 33000) {
            wdgTankGap = 130; //This value we have added from HRG's code.
            // Nothing beyond this is given. The previous values do not clash the values given by TVR.
            //27/5/2025
        }
        return wdgTankGap;
    }

    public static int getTankLength(double hvOd, double centerDistance,double hvVoltage, double KVA, boolean isOLTC){
        int wdgTankGap = getWdgToTankGap(hvVoltage, KVA);
        int oltcGap = 0;
        if(isOLTC && KVA > 160){
            oltcGap = 200;
        }
        //TODO: We have assumed the OLTC to be inside the tank chamber. What will be the OLTC gap if the OLTC is kept externally?
        /// Is there any other dimension of the OLTC based upon the rating or class of the transformer?
        return NumberFormattingUtils.next5or0Integer(hvOd + (2 * centerDistance) + (2 * wdgTankGap) + oltcGap);
    }

    public static int getConnectionGap(double hvVoltage){
        int connectionGap = 10;
        if(hvVoltage <= 11000){
            connectionGap = 25; //Changed from 20 to 25 (27/5/25)
        }if(hvVoltage > 11000 && hvVoltage <= 33000){
            connectionGap = 30;
        }if(hvVoltage > 33000){
            connectionGap = 100;//This value we have added from HRG's code.
            // Nothing beyond this is given. The previous values do not clash the values given by TVR.
            //27/5/2025
        }
        return connectionGap;
    }

    public static int getTankWidth(double hvOD, double hvVoltage, double KVA){
        int wdgTankGap = getWdgToTankGap(hvVoltage, KVA);
        int connectionGap = getConnectionGap(hvVoltage);
        return NumberFormattingUtils.next5or0Integer(hvOD + 2 * wdgTankGap + connectionGap);
    }

    public static int getTopYokeToCover(double KVA, double hvVoltage, boolean isOLTC){
        int topYokeToCover = 60;
        //Here, if OLTC is present, the OLTC will be outside the tank. Hence, the gaps are small.
        if(isOLTC){
            if(hvVoltage <= 11000){
                if(KVA <= 160){
                    topYokeToCover = 60;
                } else if (KVA > 160 && KVA <= 1000) {
                    topYokeToCover = 75;
                } else if (KVA > 1000) {
                    topYokeToCover = 100;
                }
            }
        }
        //If there is no OLTC, OCTC will be present for sure. Hence, it will consume more space on the top.
        //TODO: Add the OLTC gaps inside the tank for 66KV onwards.
        else{
            if(hvVoltage <= 11000){
                if(KVA <= 160){
                    topYokeToCover = 60;
                } else if (KVA > 160 && KVA <= 1000) {
                    topYokeToCover = 175;
                } else if (KVA > 1000) {
                    topYokeToCover = 190;
                }
            } else if(hvVoltage > 11000 && hvVoltage <= 33000){
                if(KVA <= 1000){
                    topYokeToCover = 190;
                } else if (KVA > 1000) {
                    topYokeToCover = 200;
                }
            }
        }
        return topYokeToCover;
    }

    public static int getTankHeight(double windowHeight, double largestBlade, double KVA, double hvVoltage, boolean isOLTC, double tapStepPercent ){
        int topYokeToCover = 60;
        int yokeInsulation = 10;
        //Here, if OLTC is present, the OLTC will be outside the tank. Hence, the gaps are small.
        if(isOLTC){
            if(hvVoltage <= 11000){
                if(KVA <= 160){
                    topYokeToCover = 60;
                } else if (KVA > 160 && KVA <= 1000) {
                    topYokeToCover = 75;
                } else if (KVA > 1000) {
                    topYokeToCover = 100;
                }
            }
        }
        //If there is no OLTC, OCTC will be present for sure. Hence, it will consume more space on the top.
        //TODO: Add the OLTC gaps inside the tank for 66KV onwards.
        else{
            if(hvVoltage <= 11000){
                if(KVA <= 160 ){
                    topYokeToCover = tapStepPercent == 0 ? 60 : 175;
                } else if (KVA > 160 && KVA <= 1000) {
                    topYokeToCover = 175;
                } else if (KVA > 1000) {
                    topYokeToCover = 190;
                }
            } else if(hvVoltage > 11000 && hvVoltage <= 33000){
                if(KVA <= 1000){
                    topYokeToCover = 190;
                } else if (KVA > 1000) {
                    topYokeToCover = 200;
                }
            }
        }
        if(KVA <= 100){
            yokeInsulation = 10;
        }else if(KVA > 100 && KVA < 2500){
            yokeInsulation = 15;
        }else if(KVA > 2500){
            yokeInsulation = 25;
        }
        return NumberFormattingUtils.next5or0Integer(windowHeight + 2 * largestBlade + yokeInsulation + topYokeToCover);
    }

    public static double getTankCapacity(int tankLength, int tankWidth, int tankHeight){
        long capacity = (long) tankLength * tankWidth * tankHeight;
        return NumberFormattingUtils.nextInteger(capacity * Math.pow(10, -6));
    }

    public static double getLidThickness(double KVA) {
        if (KVA <= 63) {
            return 3;
        } else if (KVA > 63 && KVA <= 200) {
            return 4;
        } else if (KVA > 200 && KVA <= 400) {
            return 5;
        } else if (KVA > 400 && KVA <= 1000) {
            return 6;
        } else if (KVA > 1000 && KVA <= 2500) {
            return 8;
        } else if (KVA > 2500 && KVA <= 5000) {
            return 10;
        } else if (KVA > 5000 && KVA <= 10000) {
            return 12;
        } else if (KVA > 10000 && KVA <= 20000) {
            return 12;
        } else if (KVA > 20000 && KVA <= 40000) {
            return 16;
        } else if (KVA > 40000) {
            return 20;
        }
        return 0;
    }

    public static double getTankWallThickness(double KVA) {
        if (KVA <= 25) {
            return 2.5;
        } else if (KVA <= 63) {
            return 2.5;
        } else if (KVA <= 400) {
            return 4;
        } else if (KVA <= 1000) {
            return 5;
        } else if (KVA > 1000 && KVA <= 5000) {
            return 6;
        } else if (KVA > 5001 && KVA <= 10000) {
            return 8;
        } else if (KVA > 10001 && KVA <= 20000) {
            return 8;
        } else if (KVA > 20001 && KVA <= 40000) {
            return 8;
        } else if (KVA > 40001) {
            return 8;
        }
        return 0;
    }

    public static double getTankBottomThickness(double KVA) {
        if (KVA <= 63) {
            return 3;
        } else if (KVA > 63 && KVA <= 200) {
            return 4;
        } else if (KVA > 200 && KVA <= 400) {
            return 5;
        } else if (KVA > 400 && KVA <= 1000) {
            return 6;
        } else if (KVA > 1000 && KVA <= 5000) {
            return 8;
        } else if (KVA > 5000 && KVA <= 10000) {
            return 10;
        } else if (KVA > 10000 && KVA <= 20000) {
            return 12;
        } else if (KVA > 20000 && KVA <= 40000) {
            return 16;
        } else if (KVA > 40000) {
            return 20;
        }
        return 0;
    }

    public static double getFrameThickness(double KVA) {
        if (KVA <= 63) {
            return 6;
        } else if (KVA > 63 && KVA <= 200) {
            return 8;
        } else if (KVA > 200 && KVA <= 630) {
            return 10;
        } else if (KVA > 630 && KVA <= 2500) {
            return 12;
        } else if (KVA > 2500 && KVA <= 5000) {
            return 12;
        } else if (KVA > 5000 && KVA <= 10000) {
            return 12;
        } else if (KVA > 10000 && KVA <= 20000) {
            return 14;
        } else if (KVA > 20000 && KVA <= 40000) {
            return 18;
        } else if (KVA > 40000) {
            return 25;
        }
        return 0;
    }

    public static double getConnectionWeight(double conductorXsec, String conductorMaterial, int length){
        double materialDensity = Objects.equals(conductorMaterial, "COPPER") ? 8.89 : 2.703;
        return NumberFormattingUtils.oneDigitDecimal((length * conductorXsec * materialDensity) * 6 * Math.pow(10, -6));
    }

    public static double getTapInsWeight(double bareWt, double insulatedWt, int hvTurnsAtHighest, int hvTurnsAtLowest){
        return NumberFormattingUtils.oneDigitDecimal((insulatedWt - bareWt) * ((double) (hvTurnsAtHighest - hvTurnsAtLowest) / hvTurnsAtHighest));
    }

    public static int getTapLeadWeight(double hvCondCrossSec, String hvCondMaterial, boolean isOLTC, double cenDist,
                                       double limbHt, int hvOD, int coreDia, int positiveTap, int negativeTap, double hvCondIns){
        double materialDens = Objects.equals(hvCondMaterial, "COPPER") ? 8.89 : 2.703;
        double tapLeadLength = 0;
        int tapCount = positiveTap + negativeTap;
        if(isOLTC){
            tapLeadLength = (cenDist + (hvOD/2) + 500) * tapCount * 3; // 3 is the number of limbs
        } else {
            tapLeadLength = (limbHt + coreDia + 400) * tapCount * 3; // 3 is the number of limbs
        }
        double tapLeadInsWeight = (0.5 * hvCondCrossSec + 16) * tapLeadLength * hvCondIns * Math.pow(10, -6);
        return NumberFormattingUtils.nextInteger((tapLeadLength * 1.5 * hvCondCrossSec * materialDens * Math.pow(10,-6)) + tapLeadInsWeight);
    }

    public static double getChannelWeight(double largestBlade, double tankLength){
        double conditionLargeBlade = 0.6 * largestBlade;

        double ismcWeight = 1;

        if(conditionLargeBlade <= 75) {
            ismcWeight = 6.8;
        } else if (conditionLargeBlade > 75 && conditionLargeBlade <= 100) {
            ismcWeight = 9.2;
        }else if (conditionLargeBlade > 100 && conditionLargeBlade <= 125) {
            ismcWeight = 12.7;
        }else if (conditionLargeBlade > 125 && conditionLargeBlade <= 150) {
            ismcWeight = 16.4;
        }else if (conditionLargeBlade > 150 && conditionLargeBlade <= 175) {
            ismcWeight = 19.1;
        }else if (conditionLargeBlade > 175 && conditionLargeBlade <= 200) {
            ismcWeight = 22.1;
        }else if (conditionLargeBlade > 200 && conditionLargeBlade <= 225) {
            ismcWeight = 25.9;
        }else if (conditionLargeBlade > 225 && conditionLargeBlade <= 250) {
            ismcWeight = 30.4;
        }else if (conditionLargeBlade > 250 && conditionLargeBlade <= 300) {
            ismcWeight = 38.8;
        }else if (conditionLargeBlade > 300 && conditionLargeBlade <= 350) {
            ismcWeight = 42.1;
        }else if (conditionLargeBlade > 350 && conditionLargeBlade <= 400) {
            ismcWeight = 49.4;
        }
        //The ISMC calculations are a little different as, the factor that we multiply the conditionLargeBlade with can vary from 0.6 t 0.7 times.
        return NumberFormattingUtils.oneDigitDecimal(ismcWeight * 1.2 * tankLength * 4);
    }

    public static double displacementVolume(double mass, double density){
        return NumberFormattingUtils.oneDigitDecimal(mass / density);
    }

    public static double getInsulationWt(double KVA, double hvVoltage, EVectorGroup vectorGroup) {
        // Base calculation
        double GenInslnWt = Math.pow(KVA / 63.0, 0.75) * 7.5;
        GenInslnWt = NumberFormattingUtils.nextInteger(GenInslnWt);

        // Adjustments based on hvVoltage and vectorGroup
        if (hvVoltage > 11000 && hvVoltage < 33000) {
            GenInslnWt = Math.floor(GenInslnWt * 1.1);
        } else if (hvVoltage >= 33000 && hvVoltage < 66000) {
            GenInslnWt = Math.floor(GenInslnWt * 1.2);
        } else if (hvVoltage >= 66000 && hvVoltage < 132000 && Objects.equals(vectorGroup.name().charAt(0), 'Y')) {
            GenInslnWt = Math.floor(GenInslnWt * 1.4);
        } else if (hvVoltage >= 66000 && hvVoltage < 132000 && Objects.equals(vectorGroup.name().charAt(0), 'D')) {
            GenInslnWt = Math.floor(GenInslnWt * 1.6);
        } else if (hvVoltage >= 132000 && Objects.equals(vectorGroup.name().charAt(0), 'Y')) {
            GenInslnWt = Math.floor(GenInslnWt * 1.8);
        } else if (hvVoltage >= 132000 && Objects.equals(vectorGroup.name().charAt(0), 'D')) {
            GenInslnWt = Math.floor(GenInslnWt * 2.0);
        }

        return GenInslnWt;
    }

    public static double getHeatDisByTankWall(double tankLength, double tankWidth, double tankHeight){
        return NumberFormattingUtils.nextInteger(((tankLength + tankWidth) * 2 * tankHeight) * 500 * Math.pow(10, -6));
    }

    public static double getTopOilTemperature(double lvGradient, double hvGradient){
        double gradient = lvGradient < 14.5 && hvGradient < 14.5 ?  14.5 :  Math.max(lvGradient, hvGradient);
        return NumberFormattingUtils.oneDigitDecimal(98 - 32 - (1.1 * gradient));
    }

    public static double getRadiatorArea(double heatToBeDissipated, double topOilTemperature){
        int wattsPerMsq = 450;
        if(topOilTemperature >= 35 && topOilTemperature < 40){
            wattsPerMsq = 300;
        }else if(topOilTemperature >= 40 && topOilTemperature < 45 ){
            wattsPerMsq = 350;
        }else if(topOilTemperature >= 45 && topOilTemperature < 50 ){
            wattsPerMsq = 400;
        }else if(topOilTemperature >= 50){
            wattsPerMsq = 450;
        }
        return NumberFormattingUtils.twoDigitDecimal(heatToBeDissipated / wattsPerMsq);
    }

    public static int getRadiatorHeight(double tankHeight, double largestBlade, int yokeInsulation) {
        int radiatorHeight = 0;
        //The transformers with height less than 700mm will have a radiator of 500mm and will be placed diagonally.
        if (tankHeight <= 600) {
            radiatorHeight = 300;
        } else if (tankHeight <= 700) {
            radiatorHeight = 400;
        } else if (tankHeight <= 750) {
            radiatorHeight = 500;
        } else {
            radiatorHeight = (int) (tankHeight - largestBlade - 120 - yokeInsulation);
            int factor = radiatorHeight % 100;
            if(factor < 65){
                radiatorHeight = radiatorHeight - factor;
            }else radiatorHeight = radiatorHeight - factor + 100;
        }
        return radiatorHeight;
    }

    public static int getRadiatorWidth(int radiatorHeight, Integer userRadWidth){
        int radiatorWidth = 0;
        if(userRadWidth != null){
            radiatorWidth = userRadWidth;
        }else{
            if(radiatorHeight >= 800){
                radiatorWidth = 520;
            } else if (radiatorHeight >= 500 && radiatorHeight <= 700) {
                radiatorWidth = 300;
            } else if (radiatorHeight < 500) {
                radiatorWidth = 226;
            }
        }
        return radiatorWidth;
    }

    public static int[] getRadiatorSection(int noOfFins){
        int[] radiatorArrangement = new int[3];
        int section = 0;
        int noOfRadiators = 0;
        int revisedNoOfFins = noOfFins;
        if(noOfFins <= 8){noOfRadiators = 2;}
        else if (noOfFins > 8 && noOfFins <= 80) {
            noOfRadiators = 4;
        }else if (noOfFins > 80 && noOfFins <= 120) {
            noOfRadiators = 6;
        }else if (noOfFins > 120 && noOfFins <= 160) {
            noOfRadiators = 8;
        }else if (noOfFins > 160 && noOfFins <= 200) {
            noOfRadiators = 10;
        }else if (noOfFins > 200) {
            noOfRadiators = 12;
        }
        section = NumberFormattingUtils.nextInteger((double)noOfFins/noOfRadiators);
        revisedNoOfFins = section * noOfRadiators;
        radiatorArrangement[0] = section;
        radiatorArrangement[1] = noOfRadiators;
        radiatorArrangement[2] = revisedNoOfFins;
        return radiatorArrangement;
    }

    public static double getConservatorOil(double oilInTank, double oilInRadiators){
        //For now, we have taken 4% of total oil to be oil in conservator
        return NumberFormattingUtils.nextInteger((oilInRadiators + oilInTank ) * 0.04);
    }

    public static double getConservatorCapacity(double KVA, double totalOil){
        return NumberFormattingUtils.nextInteger(KVA < 5000 ? totalOil * 0.1 : totalOil * 0.08);
    }

    public static double getConservatorDia(double conservatorCapacity){
        return NumberFormattingUtils.next5or0Integer(Math.pow(conservatorCapacity * 4 / (3 * Math.PI), (double) 1 /3) * 100);
    }

    public static double getConservatorLength(double conservatorCapacity, double conservatorDia){
        return NumberFormattingUtils.next5or0Integer((conservatorCapacity / ((Math.PI / 4) * (Math.pow(conservatorDia, 2)))) * Math.pow(10, 6));
    }

    public static double getTotalRadiatorWeight(int radiatorLength, int radiatorWidth, int radiatorSection, int noOfRadiators){
        //Here, 1.25 is the thickness of the radiator wall.
        double radiatorWeight = NumberFormattingUtils.oneDigitDecimal(radiatorLength * radiatorWidth * radiatorSection * noOfRadiators * 2 * 1.25 * 7.85 * Math.pow(10, -6));
        double radiatorHead = NumberFormattingUtils.oneDigitDecimal(0.1 + (radiatorSection - 1) * 0.05);
        double pipeWeight = NumberFormattingUtils.oneDigitDecimal(Math.PI/4 * (Math.pow(90,2) - Math.pow(86,2)) *(radiatorHead * 2 * 7.85) / 1000);
        return NumberFormattingUtils.nextInteger(radiatorWeight + pipeWeight);
    }

    public static double getTotalSteelWeight(double hvVoltage, double KVA, EVectorGroup vectorGroup) {
        double totalSteelWeight = 0;

        if (hvVoltage <= 11000) {
            totalSteelWeight = Math.pow((KVA / 63), 0.7) * 122 + 1;
        } else if (hvVoltage > 11000 && hvVoltage < 33000) {
            totalSteelWeight = Math.pow((KVA / 63), 0.72) * 122 + 1;
        } else if (hvVoltage >= 33000 && hvVoltage < 66000) {
            totalSteelWeight = Math.pow((KVA / 63), 0.74) * 122 + 1;
        } else if (hvVoltage >= 66000 && hvVoltage < 132000) {
            if (Objects.equals(vectorGroup.name().charAt(0),'Y')) {
                totalSteelWeight = Math.pow((KVA / 63), 0.75) * 122 + 1;
            } else if (Objects.equals(vectorGroup.name().charAt(0),'D')) {
                totalSteelWeight = Math.pow((KVA / 63), 0.76) * 122 + 1;
            }
        } else if (hvVoltage >= 132000) {
            if (Objects.equals(vectorGroup.name().charAt(0),'Y')) {
                totalSteelWeight = Math.pow((KVA / 63), 0.77) * 122 + 1;
            } else if (Objects.equals(vectorGroup.name().charAt(0),'D')) {
                totalSteelWeight = Math.pow((KVA / 63), 0.78) * 122 + 1;
            }
        }

        return totalSteelWeight;
    }

    public static double[] getOltcSpec(double hvVoltage, int tapStepPositive, int tapStepNegative) {
        double[] calculateOltcSpec = new double[7];
        double OltcWt = 0;
        double OltcCurrent = 0;
        double OltcOilLtr = 0;
        double OltcLength = 0;
        double OltcBreadth = 0;
        double OltcHeight = 0;
        double OltcMake = 0; //This is a flag. It will be 0 if the OLTC is external and 1 if the OLTC is Internal.
        int tapCount = tapStepPositive + tapStepNegative + 1;
        if (hvVoltage <= 11000) {
            if (tapCount <= 9) { // Number of positions up to 9
                OltcWt = 250;  // without oil
                OltcCurrent = 100;
                OltcOilLtr = 98 * 1.4;
                OltcLength = 785;
                OltcBreadth = 1030;
                OltcHeight = 720;
                OltcMake = 0;
            } else { // Up to 17 steps
                OltcWt = 370;
                OltcCurrent = 200;
                OltcOilLtr = 230 * 1.4;
                OltcLength = 785;
                OltcBreadth = 1290;
                OltcHeight = 1070;
                OltcMake = 0;
            }
        } else if (hvVoltage <= 33000) { // Up to 33 kV
            if (tapCount <= 9) { // Number of positions up to 9
                OltcWt = 385;
                OltcCurrent = 200;
                OltcOilLtr = 335 * 1.4;
                OltcLength = 775;
                OltcBreadth = 1625;
                OltcHeight = 745;
                OltcMake = 0;
            } else { // Up to 17 steps
                OltcWt = 500;
                OltcCurrent = 300;
                OltcOilLtr = 650 * 1.4;
                OltcLength = 805;
                OltcBreadth = 1762;
                OltcHeight = 1215;
                OltcMake = 0;
            }
        } else if (hvVoltage <= 132000) { // Up to 132 kV
            OltcWt = 660;
            OltcCurrent = 300;
            OltcOilLtr = -300 + 300; // Displacement of oil (-300) and consumption (+300)
            OltcLength = 1000; // Increase in tank length
            OltcBreadth = 0; // No effect on overall dimension
            OltcHeight = 2450; // Minimum tank height required
            OltcMake = 1; //OLTC is placed internally.
        }
        calculateOltcSpec[0] = OltcWt;
        calculateOltcSpec[1] = OltcCurrent;
        calculateOltcSpec[2] = OltcOilLtr;
        calculateOltcSpec[3] = OltcLength;
        calculateOltcSpec[4] = OltcBreadth;
        calculateOltcSpec[5] = OltcHeight;
        calculateOltcSpec[6] = OltcMake;
        return calculateOltcSpec;
    }

    public static double[] getBushingVoltageAndHeight(double lineVolts) {
        double bushingVoltage = 0;
        double bushingHeight = 0;
        double[] passingVals = new double[2];
        // Calculate value to compare (Int(LineVolts / 100)) / 10 in VB
        double comparisonValue = Math.floor(lineVolts / 100) / 10.0;

        if (comparisonValue < 1.1) {
            bushingVoltage = 1.1;
            bushingHeight = 372;
        } else if (comparisonValue <= 3.6) {
            bushingVoltage = 3.6;
            bushingHeight = 220;
        } else if (comparisonValue <= 7.2) {
            bushingVoltage = 7.2;
            bushingHeight = 340;
        } else if (comparisonValue <= 17.5) {
            bushingVoltage = 17.5;
            bushingHeight = 560;
        }
//        else if (comparisonValue <= 24) {
//            bushingVoltage = 24;
//            bushingHeight = 560;
//        } //Not found in IS, so, skipping this one
        else if (comparisonValue <= 36) {
            bushingVoltage = 36;
            bushingHeight = 861;
        } else if (comparisonValue <= 52) {
            bushingVoltage = 52;
            bushingHeight = 740;
        } else if (comparisonValue <= 72.5) {
            bushingVoltage = 72.5;
            bushingHeight = 1030;
        } else if (comparisonValue <= 123) {
            bushingVoltage = 123;
            bushingHeight = 1450;
        } else if (comparisonValue <= 145) {
            bushingVoltage = 145;
            bushingHeight = 1805;
        }
        passingVals[0] = bushingVoltage;
        passingVals[1] = bushingHeight;
        return passingVals;
    }

    public static int getBushingCurrent(double phaseCurrent, EVectorGroup eVectorGroup, Boolean isLV) {
        int bushingCurrent = 0;
        double lineCurrent = 0;
        if(isLV){
            lineCurrent = Objects.equals(eVectorGroup.name().charAt(1), 'y') ? phaseCurrent : phaseCurrent * Math.pow(3, (double) 1 /2);
        }else {
            lineCurrent = Objects.equals(eVectorGroup.name().charAt(0), 'Y') ? phaseCurrent : phaseCurrent * Math.pow(3, (double) 1 /2);
        }

        // Use if-else statements to replicate the Select Case structure
        if (lineCurrent < 251) {
            bushingCurrent = 250;
        } else if (lineCurrent <= 630) {
            bushingCurrent = 630;
        } else if (lineCurrent <= 1000) {
            bushingCurrent = 1000;
        } else if (lineCurrent <= 2000) {
            bushingCurrent = 2000;
        } else if (lineCurrent <= 3150) {
            bushingCurrent = 3150;
        } else { // lineCurrent > 3150
            bushingCurrent = 4000;
        }

        return bushingCurrent;
    }

    public static int getLiftingLugs(double KVA){
        int liftingLugs = 0;
        if(KVA <= 100){liftingLugs = 100;}
        else if (KVA <= 2500) {liftingLugs = 150;}
        else liftingLugs = 200;
        return liftingLugs;
    }

    public static int getCorrugationSlits(double tankSide){
        return (int) Math.floor(((tankSide - 100) / 50 )) * 2;
    }

    public static double getCorrugationArea(double kW55, ETransCostType eTransCostType){
        //These are for finding area for Corrugation
        //Economic ---> 450W/m2
        //Energy Efficient ---> 400W/m2 //TODO: To be reviewed before launching (-TVR)
        double corrugationArea = 0;
        if(Objects.equals(eTransCostType.toString(), "ECONOMIC")){
            corrugationArea = NumberFormattingUtils.twoDigitDecimal(kW55 / 450);
        }else {corrugationArea = NumberFormattingUtils.twoDigitDecimal(kW55 / 400);}
        return corrugationArea;
    }

    public static int getDepthOfCorrugation(double corrugationArea, int heightOfFin, int noOfCorrugation){
        double areaPerCorrugation = corrugationArea/noOfCorrugation;
        return NumberFormattingUtils.next0Integer(areaPerCorrugation/heightOfFin);
    }

    public static ERadiatorType getERadiatorType(ERadiatorType eRadiatorType) {
        if (eRadiatorType != null) {
            return eRadiatorType;
        }
        return ERadiatorType.RADIATOR;
    }

}
