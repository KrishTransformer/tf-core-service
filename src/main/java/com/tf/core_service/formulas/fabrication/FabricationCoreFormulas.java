package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.FabricationCore;

public class FabricationCoreFormulas {

    // TieRod_Dia Calculation
    public static int calculateTieRod_Dia(Integer core_Dia){
        double RefDph= core_Dia*0.8;
        int tieRod_Dia;
        if((RefDph<100) || (RefDph >= 100 && RefDph < 125) || (RefDph >= 125 && RefDph < 150) ){
            tieRod_Dia = 14;
        }

        else if ((RefDph >= 150 && RefDph < 200) || (RefDph >= 200 && RefDph < 250)) {
            tieRod_Dia = 18;
        }

        else if (RefDph >= 250 && RefDph < 300) {
            tieRod_Dia = 22;
        }

        else if (RefDph >= 300 && RefDph < 350) {
            tieRod_Dia = 26;
        }

        else {
            tieRod_Dia = 32;
        }
        return tieRod_Dia;
    }

    // Core_Fixture_L Calculation
    public static int calculateCore_Fixture_L(Integer limb_CC,Integer limb_Nos,Integer tieRod_Dia){
        return (limb_CC * (limb_Nos)) + (tieRod_Dia * 2);
    }


    public static FabricationCore calculateFabricationCore(Integer core_Dia, Integer limb_CC,
                                                           Integer limb_Nos
                                                           ) {
        FabricationCore fabricationCore = new FabricationCore();
        fabricationCore.setCore_Fixture_Type("c-channel");
        fabricationCore.setCore_Bottom_Clearence(10);
        fabricationCore.setCore_Dia(core_Dia);

        Integer tieRod_Dia=calculateTieRod_Dia(core_Dia);
        fabricationCore.setCore_Fixture_L(calculateCore_Fixture_L(limb_CC,limb_Nos,tieRod_Dia));


        double RefDph= core_Dia*0.8;

        if(RefDph<100){
            fabricationCore.setCore_Fixture_H(75);
            fabricationCore.setCore_Fixture_W(40);
            fabricationCore.setCore_Fixture_Thick(4.8);
        }

        else if (RefDph >= 100 && RefDph < 125) {
            fabricationCore.setCore_Fixture_H(100);
            fabricationCore.setCore_Fixture_W(50);
            fabricationCore.setCore_Fixture_Thick(5.0);
        }

        else if (RefDph >= 125 && RefDph < 150) {
            fabricationCore.setCore_Fixture_H(125);
            fabricationCore.setCore_Fixture_W(65);
            fabricationCore.setCore_Fixture_Thick(5.3);
        }

        else if (RefDph >= 150 && RefDph < 200) {
            fabricationCore.setCore_Fixture_H(150);
            fabricationCore.setCore_Fixture_W(75);
            fabricationCore.setCore_Fixture_Thick(5.7);
        }

        else if (RefDph >= 200 && RefDph < 250) {
            fabricationCore.setCore_Fixture_H(200);
            fabricationCore.setCore_Fixture_W(75);
            fabricationCore.setCore_Fixture_Thick(6.2);
        }

        else if (RefDph >= 250 && RefDph < 300) {
            fabricationCore.setCore_Fixture_H(250);
            fabricationCore.setCore_Fixture_W(80);
            fabricationCore.setCore_Fixture_Thick(7.2);
        }

        else if (RefDph >= 300 && RefDph < 350) {
            fabricationCore.setCore_Fixture_H(300);
            fabricationCore.setCore_Fixture_W(90);
            fabricationCore.setCore_Fixture_Thick(9.8);
        }

        else if (RefDph >= 350 && RefDph < 400) {
            fabricationCore.setCore_Fixture_H(350);
            fabricationCore.setCore_Fixture_W(100);
            fabricationCore.setCore_Fixture_Thick(8.3);
        }

        else if (RefDph >= 400 && RefDph < 450) {
            fabricationCore.setCore_Fixture_H(400);
            fabricationCore.setCore_Fixture_W(100);
            fabricationCore.setCore_Fixture_Thick(8.8);
        }

        else if (RefDph >= 450 && RefDph < 500) {
            fabricationCore.setCore_Fixture_H(450);
            fabricationCore.setCore_Fixture_W(150);
            fabricationCore.setCore_Fixture_Thick(10.0);
        }

        else if (RefDph >= 500 && RefDph < 550) {
            fabricationCore.setCore_Fixture_H(500);
            fabricationCore.setCore_Fixture_W(150);
            fabricationCore.setCore_Fixture_Thick(10.0);
        }

        else if (RefDph >= 550 && RefDph < 600) {
            fabricationCore.setCore_Fixture_H(550);
            fabricationCore.setCore_Fixture_W(150);
            fabricationCore.setCore_Fixture_Thick(12.0);
        }

        else if (RefDph >= 600 && RefDph < 650) {
            fabricationCore.setCore_Fixture_H(600);
            fabricationCore.setCore_Fixture_W(180);
            fabricationCore.setCore_Fixture_Thick(15.0);
        }

        else {
            fabricationCore.setCore_Fixture_H(650);
            fabricationCore.setCore_Fixture_W(200);
            fabricationCore.setCore_Fixture_Thick(15.0);
        }

        return fabricationCore;
    }
}
