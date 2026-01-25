package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.RestOfVariables;
import com.tf.core_service.utils.NumberFormattingUtils;

public class RestOfVariablesFormulas {

    // Yoke_Compensation Calculation
    public static Double calculateYoke_Compensation(Boolean yoke_Holes,Integer core_Dia,Integer core_Fixture_H){
        if(!yoke_Holes) return (double) ((core_Dia - core_Fixture_H) / 2);
        else return (double) 0;
    }

    // Foundation_Type Calculation


    // Rating_Plate_L Calculation
    public static Double calculateRating_Plate_L(Integer tank_W){
        return tank_W * 0.25;
    }

    // Rating_Plate_H Calculation
    public static Double calculateRating_Plate_H(Double rating_Plate_L){
        return rating_Plate_L * 1.75;
    }

    //TieRod_L1 Calculation
    public static Integer calculateTieRod_L1(String core_Fixture_Type, Double limb_H,
                                            Integer core_Dia, Integer core_Fixture_H,
                                            Integer tieRod_Dia, Double yoke_Compensation,
                                            Double core_Fixture_Thick){

        if(core_Fixture_Type.equals("c-channel")){
            return  ((int)((limb_H + core_Dia + core_Fixture_H + (tieRod_Dia * 2) +
                    (tieRod_Dia * 0.8 * 2) + 20 - (yoke_Compensation * 2)) / 5) * 5);
        }
        else return  ((int)((limb_H + core_Dia - core_Fixture_H + core_Fixture_Thick +
                (tieRod_Dia * 2) + (tieRod_Dia * 0.8 * 2) + 20 - (yoke_Compensation * 2)) / 5) * 5);
    }

    //tieRod_L2 Calculation
    public static Integer calculateTieRod_L2(String core_Fixture_Type, Double limb_H,
                                             Integer core_Dia, Integer core_Fixture_H,
                                             Double core_Fixture_Thick, Integer tieRod_Dia,
                                             Double yoke_Compensation){
         if(core_Fixture_Type.equals("c-channel")){
            return (int)((limb_H + core_Dia - core_Fixture_H + core_Fixture_Thick + (tieRod_Dia * 2) + (tieRod_Dia * 0.8 * 2) + 20
                    - (yoke_Compensation * 2)) / 5) * 5;
        }
        else return (int)((limb_H + core_Dia - core_Fixture_H + core_Fixture_Thick + (tieRod_Dia * 2) + (tieRod_Dia * 0.8 * 2) + 20
                 - (yoke_Compensation * 2)) / 5) * 5;
    }

    //YokeRod_L Calculation
    public static Integer calculateYokeRod_L(Integer core_Dia, Double core_Fixture_Thick,
                                             Double cf_YokeRod_Plate_Thick, Integer yokeRod_Dia){
        return (int)((core_Dia + (core_Fixture_Thick * 2) + cf_YokeRod_Plate_Thick + (yokeRod_Dia * 4)) / 5) * 5;
    }

    // TieRod_L Calculation
    public static Integer calculateTieRod_L(Boolean yoke_Holes, String core_Fixture_Type,
                                            Double limb_H, Integer core_Dia,
                                            Integer core_Fixture_H, Integer tieRod_Dia){
        if(yoke_Holes){
            if(core_Fixture_Type.equals("c-channel")){
                return (int)((limb_H + core_Dia + core_Fixture_H + (tieRod_Dia * 2) + (tieRod_Dia * 0.8 * 2) + 20) / 5) * 5;
            }
            else {
                return (int)((limb_H + core_Dia - core_Fixture_H + (tieRod_Dia * 2) + (tieRod_Dia * 0.8 * 2) + 20) / 5) * 5;
            }
        }
        else {
            if(core_Fixture_Type.equals("c-channel")){
                return (int)((limb_H + (core_Fixture_H * 2) + (tieRod_Dia * 2) + (tieRod_Dia * 0.8 * 2) + 20) / 5) * 5;
            }else {
                return (int)((limb_H + (tieRod_Dia * 2) + (tieRod_Dia * 0.8 * 2) + 20) / 5) * 5;
            }
        }
    }

    // Lift_Lid_Type Calculation
    public static String calculateLift_Lid_Type(Integer kVA){
        if(kVA<=500){
            return "handle";
        }
        else return "plate";
    }

    // CoreFoot_Thick1 Calculation
    public static Integer calculateCoreFoot_Thick1(Integer kVA){
        Integer coreFoot_Thick1;
        if (kVA <= 40) {
            coreFoot_Thick1 = 8;
        } else if (kVA <= 200) {
            coreFoot_Thick1 = 10;
        } else if (kVA <= 1000) {
            coreFoot_Thick1 = 10;
        } else if (kVA <= 2000) {
            coreFoot_Thick1 = 12;
        } else if (kVA <= 2500) {
            coreFoot_Thick1 = 12;
        } else if (kVA <= 5000) {
            coreFoot_Thick1 = 16;
        } else if (kVA <= 8000) {
            coreFoot_Thick1 = 20;
        } else {
            coreFoot_Thick1 = 20;
        }
        return coreFoot_Thick1;
    }

    // Active_Height Calculation
    public static Double calculateActive_Height(Integer coreFoot_Thick1, Integer core_Bottom_Clearence, Integer core_Dia, Double limb_H){
        return coreFoot_Thick1 + core_Bottom_Clearence + core_Dia + limb_H + core_Dia;
    }

    public static RestOfVariables calcuateRestOfVariables(Integer transformer_Weight,
                                                          String designId, Integer core_Dia,
                                                          Integer limb_CC, Integer limb_Nos,
                                                          Boolean yoke_Holes, Integer kVA,
                                                          Integer tank_W,
                                                          Double limb_H, String core_Fixture_Type,
                                                          Integer core_Fixture_H, Double core_Fixture_Thick,
                                                          Integer core_Bottom_Clearence, Double hv_OD, Integer tank_L, Boolean Inspect_Cover, Double hvb_pitch) {
        RestOfVariables restOfVariables = new RestOfVariables();
        restOfVariables.setTransformer_Weight(transformer_Weight);
        restOfVariables.setDesignId(designId);
        restOfVariables.setLimb_CC(limb_CC);
        restOfVariables.setLimb_Nos(limb_Nos);
        restOfVariables.setYoke_Holes(yoke_Holes);

        Double yoke_Compensation=calculateYoke_Compensation(yoke_Holes,core_Dia,core_Fixture_H);
        restOfVariables.setYoke_Compensation(yoke_Compensation);

        Integer stayrod_Dia;
        Integer stayrod_Nos;
        Integer tieRod_Dia;
        Double cf_YokeRod_Plate_Thick=(core_Fixture_Thick * 2);
        Integer coreFoot_Thick1=calculateCoreFoot_Thick1(kVA);

        double RefDph= core_Dia*0.8;

        if (limb_Nos >= 3) {
            restOfVariables.setActivePart_L( hv_OD + (limb_CC * 2));
        } else {
            restOfVariables.setActivePart_L(hv_OD + limb_CC);
        }

        if(RefDph<100){
           stayrod_Dia=14;
           stayrod_Nos=1;
           tieRod_Dia =14;
        }

        else if ((RefDph >= 100 && RefDph < 125) || (RefDph >= 125 && RefDph < 150)) {
            stayrod_Dia = 14;
            stayrod_Nos = 2;
            tieRod_Dia = 14;
        }

        else if ((RefDph >= 150 && RefDph < 200) || (RefDph >= 200 && RefDph < 250))  {
            stayrod_Dia = 18;
            stayrod_Nos = 2;
            tieRod_Dia = 18;
        }

        else if (RefDph >= 250 && RefDph < 300) {
            stayrod_Dia = 22;
            stayrod_Nos = 2;
            tieRod_Dia = 22;
        }

        else if (RefDph >= 300 && RefDph < 350) {
            stayrod_Dia = 26;
            stayrod_Nos = 2;
            tieRod_Dia = 26;
        }

        else  {
            stayrod_Dia = 32;
            stayrod_Nos = 2;
            tieRod_Dia = 32;
        }

        restOfVariables.setStayrod_Dia(stayrod_Dia);
        restOfVariables.setStayrod_Nos(stayrod_Nos);
        restOfVariables.setTieRod_Dia(tieRod_Dia);


        Double rating_Plate_L=calculateRating_Plate_L(tank_W);
        restOfVariables.setRating_Plate_L(rating_Plate_L);
        restOfVariables.setRating_Plate_H(calculateRating_Plate_H(rating_Plate_L));
        restOfVariables.setRating_Plate_Thick(2);
        restOfVariables.setLimb_H(limb_H);

        restOfVariables.setTieRod_L1(calculateTieRod_L1(
                core_Fixture_Type,
                limb_H,
                core_Dia,
                core_Fixture_H,
                tieRod_Dia,
                yoke_Compensation,
                core_Fixture_Thick
        ));

        restOfVariables.setTieRod_L2(calculateTieRod_L2(core_Fixture_Type,
                limb_H,
                core_Dia,
                core_Fixture_H,
                core_Fixture_Thick,
                tieRod_Dia,
                yoke_Compensation
        ));

        restOfVariables.setYokeRod_Dia(stayrod_Dia);

        restOfVariables.setYokeRod_L(calculateYokeRod_L(core_Dia,
                core_Fixture_Thick,
                cf_YokeRod_Plate_Thick,
                stayrod_Dia
        ));

        restOfVariables.setTieRod_L(calculateTieRod_L(
                yoke_Holes,
                core_Fixture_Type,
                limb_H,
                core_Dia,
                core_Fixture_H,
                tieRod_Dia
                ));

        restOfVariables.setLift_Lid_Type(calculateLift_Lid_Type(kVA));

        restOfVariables.setActive_Height(calculateActive_Height(coreFoot_Thick1,
                core_Bottom_Clearence,
                core_Dia,
                limb_H
                ));
        restOfVariables.setKVA(kVA);
        int arp_Tank_Hpos = tank_L <= 900 ? (int) -(hvb_pitch / 2) : (tank_L/2 - 50);
        int arp_Tank_Vpos = tank_L <= 900 ? 0 : -(tank_W/2 - 50);
        restOfVariables.setArp_Tank_Hpos(arp_Tank_Hpos);
        restOfVariables.setArp_Tank_Vpos(arp_Tank_Vpos);
        return restOfVariables;
    }

    public static Integer calculateStayRodL (Integer core_Dia, Integer stayrod_Dia, Double core_Fixture_Thick, Integer cf_StayRod_Plate_Thick){
        return NumberFormattingUtils.next5or0Integer(core_Dia + 2* ((2 * stayrod_Dia) + core_Fixture_Thick) + (cf_StayRod_Plate_Thick * 2));
    }

}
