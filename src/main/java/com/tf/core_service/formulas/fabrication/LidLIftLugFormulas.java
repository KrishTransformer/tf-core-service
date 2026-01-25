package com.tf.core_service.formulas.fabrication;

import com.tf.core_service.model.fabrication.LidLiftLug;

public class LidLIftLugFormulas {

    // lid_LiftLug_L1 calculation
    public static int calculateLiftLug_L1(int kVA,int tank_L){
        if (kVA<=500){
            return 75;
        }
        else {
            if(tank_L<=1000){
                return 50;
            }
            else {
                return 100;
            }
        }
    }

     //lid_LiftLug_L2 calculation
     public static int calculateLidLiftLugL2(int lid_LiftLug_L1) {
        return lid_LiftLug_L1 * 2 / 3;
    }

    // Lid_LiftLug_H calculation
    public static int calculateLid_LiftLug_H(int lid_LiftLug_L1) {
        return lid_LiftLug_L1 * 2 / 3;
    }

    // Lid_LiftLug_Thick calculation
    public static int calculateLid_LiftLug_Thick(int tank_Thick) {
        return tank_Thick * 2;
    }

    // Lid_LiftLug_Hole_Dia calculation
    public static int calculateLid_LiftLug_Hole_Dia(int lid_LiftLug_L1) {
        return lid_LiftLug_L1/3;
    }

    // Lid_LiftLug_Rod_Dia calculation
    public static int calculateLid_LiftLug_Rod_Dia() {
        return 5;
    }

    // Lid_LiftLug_Fillet_Rad calculation
    public static int calculateLid_Fillet_Rad() {
        return calculateLid_LiftLug_Rod_Dia();
    }

    //Lid_LiftLug_Vpos calculation
    public static int calculateLid_Lid_LiftLug_Vpos(int tank_W) {
        return tank_W/6;
    }

    public static LidLiftLug calcuateLidLiftLug(int kVA, Integer tank_L,
                                                Integer tank_Thick, Integer tank_W) {

       LidLiftLug lidLiftLug = new LidLiftLug();
       int lid_LiftLug_L1=calculateLiftLug_L1(kVA,tank_L);
       lidLiftLug.setLid_LiftLug_L1(lid_LiftLug_L1);
       lidLiftLug.setLid_LiftLug_L2(calculateLidLiftLugL2(lid_LiftLug_L1));
       lidLiftLug.setLid_LiftLug_H(calculateLid_LiftLug_H(lid_LiftLug_L1));
       lidLiftLug.setLid_LiftLug_Thick(calculateLid_LiftLug_Thick(tank_Thick));
       lidLiftLug.setLid_LiftLug_Hole_Dia(calculateLid_LiftLug_Hole_Dia(lid_LiftLug_L1));
       lidLiftLug.setLid_LiftLug_Rod_Dia(calculateLid_LiftLug_Rod_Dia());
       lidLiftLug.setLid_LiftLug_Fillet_Rad(calculateLid_Fillet_Rad());
       lidLiftLug.setLid_LiftLug_Vpos(calculateLid_Lid_LiftLug_Vpos(tank_W));
       lidLiftLug.setLid_LiftLug_Hpos(lid_LiftLug_L1);
       return lidLiftLug;
    }
}
