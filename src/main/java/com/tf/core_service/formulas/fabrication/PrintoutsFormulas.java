package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.Printouts;

public class PrintoutsFormulas {
    public static Printouts calculatePrintouts(Integer kVA, Integer voltsAtHv,Integer voltsAtLv,
                                               Double amperesHv,Double amperesLv,
                                               String phasesHv,String phasesLv,
                                               String coolingType,
                                               Integer frequency,Double impedance,
                                               String vectorGroup, Integer topOilTemp,Integer windingTemp,
                                               Integer weightsOfActivePart,
                                               Integer oilWeight,Integer totalOil,
                                               String basicInsulationLevelHV,String basicInsulationLevelLV,
                                               Double ampsHV,Double ampsLV,
                                               String tappingHVVariations,
                                               Integer lossesAt50,Integer lossesAt100,
                                               Integer weightOfTankAndAcc){
        Printouts printouts = new Printouts();
        printouts.setKva(kVA);
        printouts.setVoltsAtHv(voltsAtHv);
        printouts.setVoltsAtLv(voltsAtLv);
        printouts.setAmperesHv(amperesHv);
        printouts.setAmperesLv(amperesLv);
        printouts.setPhasesHv(phasesHv);
        printouts.setPhasesLv(phasesLv);
        printouts.setCoolingType(coolingType);
        printouts.setFrequency(frequency);
        printouts.setImpedance(impedance);
        printouts.setVectorGroup(vectorGroup);
        printouts.setTopOilTemp(topOilTemp);
        printouts.setWindingTemp(windingTemp);
        printouts.setWeightsOfActivePart(weightsOfActivePart);
        printouts.setOilWeight(oilWeight);
        printouts.setTotalOil(totalOil);
        printouts.setBasicInsulationLevelHV(basicInsulationLevelHV);
        printouts.setBasicInsulationLevelLV(basicInsulationLevelLV);
        printouts.setAmpsHV(ampsHV);
        printouts.setAmpsLV(ampsLV);
        printouts.setTappingHVVariations(tappingHVVariations);
        printouts.setLossesAt50(lossesAt50);
        printouts.setLossesAt100(lossesAt100);
        printouts.setWeightOfTankAndAcc(weightOfTankAndAcc);

        return  printouts;
    }
}
